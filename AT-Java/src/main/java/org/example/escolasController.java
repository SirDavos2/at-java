package org.example;

import spark.Request;
import spark.Response;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class escolasController {
    private final String basePath;

    public escolasController(String basePath) {
        this.basePath = basePath;
    }

    public Object getAllescolas(Request req, Response res) {
        try {
            List<School> escolas = readescolasFromCSV(basePath + "escolas.csv");
            return escolas;
        } catch (IOException e) {
            res.status(500);
            return "Erro ao ler o arquivo CSV.";
        }
    }

    public Object createSchool(Request req, Response res) {
        try {
            String nome = req.queryParams("schoolName");
            String email = req.queryParams("schoolEmail");

            // Gerar um novo ID para a escola
            int id = CSVUtils.generateUniqueId(basePath + "escolas.csv");

            // Formatar a linha a ser escrita no CSV
            String escola = String.format("%d,%s,%s", id, nome, email);

            // Escrever a linha no arquivo CSV
            CSVUtils.writeToFile(basePath + "escolas.csv", escola);

            return "Escola criada com sucesso.";
        } catch (Exception e) {
            res.status(500);
            return "Erro ao criar a escola.";
        }
    }

    public Object deleteSchool(Request req, Response res) {
        try {
            int id = Integer.parseInt(req.params(":id"));
            CSVUtils.deleteRecord(basePath + "escolas.csv", id);
            return "Escola deletada com sucesso.";
        } catch (Exception e) {
            res.status(500);
            return "Erro ao deletar a escola.";
        }
    }

    private List<School> readescolasFromCSV(String filePath) throws IOException {
        List<School> escolas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String headerLine = br.readLine(); // Lê a linha do cabeçalho e ignora

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String nome = parts[1];
                String email = parts[2];
                School school = new School(id, nome, email);
                escolas.add(school);
            }
        }
        return escolas;
    }
}
