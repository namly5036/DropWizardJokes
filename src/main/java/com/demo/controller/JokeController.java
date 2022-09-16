package com.demo.controller;

import com.demo.dto.ChucknorrisSearchDto;
import io.github.resilience4j.ratelimiter.RateLimiter;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.Invocation;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Produces(MediaType.APPLICATION_JSON)
@Path("/jokes/")
public class JokeController {
    private RateLimiter limiter;
    private Client client;

    public JokeController(RateLimiter limiter, Client client) {
        this.limiter = limiter;
        this.client = client;
    }

    @GET
    public Response searchJokes(@QueryParam("query") String query) {
        if (Objects.isNull(query) || query.trim().length() < 3 || query.trim().length() > 120) {
            return Response.ok().build();
        }
        ChucknorrisSearchDto searchResult;
        Supplier<ChucknorrisSearchDto> limitedSupplier = RateLimiter.decorateSupplier(limiter, () -> this.searchChucknorris(query));
        try {
            searchResult = limitedSupplier.get();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.ok(e.getMessage()).build();
        }
        return Response.ok(searchResult).build();
    }

    private ChucknorrisSearchDto searchChucknorris(String query) {
        WebTarget webTarget = client.target("https://api.chucknorris.io/jokes/search?query=" + query);
        Invocation.Builder invocationBuilder = webTarget.request(MediaType.APPLICATION_JSON);
        Response response = invocationBuilder.get();
        ChucknorrisSearchDto searchResult = response.readEntity(ChucknorrisSearchDto.class);
        Pattern p = Pattern.compile("(?i)^" + query + " |[^a-zA-Z0-9](" + query + "+)[^a-zA-Z0-9]");
        searchResult.setResult(
                searchResult.getResult()
                        .parallelStream()
                        .filter(item -> {
                            Matcher m = p.matcher(item.getValue());
                            return m.find();
                        }).collect(Collectors.toList())
        );
        searchResult.setTotal(searchResult.getResult().size());
        return searchResult;
    }
}