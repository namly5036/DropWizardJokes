package com.demo.dto;

import java.util.List;

public class ChucknorrisSearchDto {
    int total;
    List<ChucknorrisSearchItemDto> result;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<ChucknorrisSearchItemDto> getResult() {
        return result;
    }

    public void setResult(List<ChucknorrisSearchItemDto> result) {
        this.result = result;
    }
}
