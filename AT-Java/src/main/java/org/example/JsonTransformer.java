package org.example;

import com.google.gson.Gson;

class JsonTransformer implements spark.ResponseTransformer {
    private Gson gson = new Gson();

    @Override
    public String render(Object model) {
        return gson.toJson(model);
    }
}