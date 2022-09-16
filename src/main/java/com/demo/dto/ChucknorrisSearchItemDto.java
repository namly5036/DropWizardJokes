package com.demo.dto;

import java.util.List;

public class ChucknorrisSearchItemDto {
    List<String> categories;
    String created_at;
    String icon_url;
    String id;
    String updated_at;
    String url;
    String value;

    public List<String> getCategories() {
        return categories;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public String getId() {
        return id;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getUrl() {
        return url;
    }

    public String getValue() {
        return value;
    }

    public void setCategories(List<String> categories) {
        this.categories = categories;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
