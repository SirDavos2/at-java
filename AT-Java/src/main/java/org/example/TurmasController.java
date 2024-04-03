package org.example;

import spark.Request;
import spark.Response;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TurmasController {
    private final String basePath;

    public TurmasController(String basePath) {
        this.basePath = basePath;
    }

    public Object getAllTurmas(Request req, Response res) {
        try {
            List<Turma> turmas = readTurmasFromCSV(basePath + "turmas.csv");
            return turmas;
        } catch (IOException e) {
            res.status(500);
            return "Erro ao ler o arquivo CSV.";
        }
    }

    public Object createTurma(Request req, Response res) {
        try {
            String nome = req.queryParams("turmaName");
            int idEscola = Integer.parseInt(req.queryParams("turmaSchoolId"));

            // Gerar um novo ID para a turma
            int id = CSVUtils.generateUniqueId(basePath + "turmas.csv");

            // Formatar a linha a ser escrita no CSV
            String turma = String.format("%d,%s,%d", id, nome, idEscola);

            // Escrever a linha no arquivo CSV
            CSVUtils.writeToFile(basePath + "turmas.csv", turma);

            return "Turma criada com sucesso.";
        } catch (Exception e) {
            res.status(500);
            return "Erro ao criar a turma.";
        }
    }

    public Object deleteTurma(Request req, Response res) {
        try {
            int id = Integer.parseInt(req.params(":id"));
            CSVUtils.deleteRecord(basePath + "turmas.csv", id);
            return "Turma deletada com sucesso.";
        } catch (Exception e) {
            res.status(500);
            return "Erro ao deletar a turma.";
        }
    }

    private List<Turma> readTurmasFromCSV(String filePath) throws IOException {
        List<Turma> turmas = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String headerLine = br.readLine(); // Lê a linha do cabeçalho e ignora

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String nome = parts[1];
                int idEscola = Integer.parseInt(parts[2]);
                Turma turma = new Turma(id, nome, idEscola);
                turmas.add(turma);
            }
        }
        return turmas;
    }
}
