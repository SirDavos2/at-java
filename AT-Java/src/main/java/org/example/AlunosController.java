package org.example;

import spark.Request;
import spark.Response;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class AlunosController {
    private final String basePath;

    public AlunosController(String basePath) {
        this.basePath = basePath;
    }

    public Object getAllAlunos(Request req, Response res) {
        try {
            List<Aluno> alunos = readAlunosFromCSV(basePath + "alunos.csv");
            return alunos;
        } catch (IOException e) {
            res.status(500);
            return "Erro ao ler o arquivo CSV.";
        }
    }

    public Object createAluno(Request req, Response res) {
        try {
            String nome = req.queryParams("alunoName");
            int idade = Integer.parseInt(req.queryParams("alunoAge"));

            // Gerar um novo ID para o aluno
            int id = CSVUtils.generateUniqueId(basePath + "alunos.csv");

            // Formatar a linha a ser escrita no CSV
            String aluno = String.format("%d,%s,%d", id, nome, idade);

            // Escrever a linha no arquivo CSV
            CSVUtils.writeToFile(basePath + "alunos.csv", aluno);

            return "Aluno criado com sucesso.";
        } catch (Exception e) {
            res.status(500);
            return "Erro ao criar o aluno.";
        }
    }

    public Object deleteAluno(Request req, Response res) {
        try {
            int id = Integer.parseInt(req.params(":id"));
            CSVUtils.deleteRecord(basePath + "alunos.csv", id);
            return "Aluno deletado com sucesso.";
        } catch (Exception e) {
            res.status(500);
            return "Erro ao deletar o aluno.";
        }
    }

    private List<Aluno> readAlunosFromCSV(String filePath) throws IOException {
        List<Aluno> alunos = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String headerLine = br.readLine(); // Lê a linha do cabeçalho e ignora

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String nome = parts[1];
                int idade = Integer.parseInt(parts[2]);
                Aluno aluno = new Aluno(id, nome, idade);
                alunos.add(aluno);
            }
        }
        return alunos;
    }
}
