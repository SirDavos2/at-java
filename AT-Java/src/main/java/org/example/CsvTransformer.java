package org.example;

import java.util.List;

class CsvTransformer implements spark.ResponseTransformer {
    @Override
    public String render(Object model) throws Exception {
        if (model instanceof List<?>) {
            List<String> lines = (List<String>) model;
            StringBuilder csv = new StringBuilder();
            for (String line : lines) {
                csv.append(line).append("\n");
            }
            return csv.toString();
        } else {
            throw new IllegalArgumentException("O modelo não é uma lista de strings.");
        }
    }
}