package org.example;

import spark.Request;
import spark.Response;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class professoresController {
    private final String basePath;

    public professoresController(String basePath) {
        this.basePath = basePath;
    }

    public Object getAllprofessores(Request req, Response res) {
        try {
            List<Professor> professores = readprofessoresFromCSV(basePath + "professores.csv");
            return professores;
        } catch (IOException e) {
            res.status(500);
            return "Erro ao ler o arquivo CSV.";
        }
    }

    public Object createProfessor(Request req, Response res) {
        try {
            String nome = req.queryParams("professorName");
            double salario = Double.parseDouble(req.queryParams("professorSalary"));
            boolean mestre = Boolean.parseBoolean(req.queryParams("professorMaster"));
            String endereco = req.queryParams("professorAddress");
            int idEscola = Integer.parseInt(req.queryParams("professorSchoolId"));

            // Corrigindo a formatação da string para corresponder à ordem dos campos na classe Professor
            String professor = String.format("%d,%s,%.2f,%s,%s,%d",
                    idEscola, nome, salario, mestre, endereco, idEscola);
            CSVUtils.writeToFile(basePath + "professores.csv", professor);
            return "Professor criado com sucesso.";
        } catch (Exception e) {
            res.status(500);
            return "Erro ao criar o professor.";
        }
    }

    public Object deleteProfessor(Request req, Response res) {
        try {
            int id = Integer.parseInt(req.params(":id"));
            CSVUtils.deleteRecord(basePath + "professores.csv", id);
            return "Professor deletado com sucesso.";
        } catch (Exception e) {
            res.status(500);
            return "Erro ao deletar o professor.";
        }
    }

    private List<Professor> readprofessoresFromCSV(String filePath) throws IOException {
        List<Professor> professores = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String headerLine = br.readLine(); // Lê a linha do cabeçalho e ignora

            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                int id = Integer.parseInt(parts[0]);
                String nome = parts[1];
                double salario = Double.parseDouble(parts[2]);
                boolean mestre = Boolean.parseBoolean(parts[3]);
                String endereco = parts[4];
                int idEscola = Integer.parseInt(parts[5]);
                Professor professor = new Professor(id, nome, salario, mestre, endereco, idEscola);
                professores.add(professor);
            }
        }
        return professores;
    }
}
