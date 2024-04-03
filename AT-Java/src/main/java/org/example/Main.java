package org.example;
import java.io.*;


import static spark.Spark.*;

public class Main {

    public static void main(String[] args) {
        String basePath = "./";
        port(8080);

        // Servir a landing page
        get("/", (req, res) -> {
            File landingPage = new File("index.html");
            StringBuilder content = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(landingPage))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace(); // Trate os erros apropriadamente
            }
            return content.toString();
        });

        // Servir a página inicialpage
        get("/apipage", (req, res) -> {
            File apipage = new File("apipage.html");
            StringBuilder content = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new FileReader(apipage))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content.append(line).append("\n");
                }
            } catch (IOException e) {
                e.printStackTrace(); // Trate os erros apropriadamente
            }
            return content.toString();
        });

        // Rotas para Escolas
        escolasController escolasController = new escolasController(basePath);
        path("/escolas", () -> {
            get("", escolasController::getAllescolas, new JsonTransformer());
            post("", escolasController::createSchool, new JsonTransformer());
            delete("/:id", escolasController::deleteSchool, new JsonTransformer());
        });

        // Rotas para Professores
        professoresController professoresController = new professoresController(basePath);
        path("/professores", () -> {
            get("", professoresController::getAllprofessores, new JsonTransformer());
            post("", professoresController::createProfessor, new JsonTransformer());
            delete("/:id", professoresController::deleteProfessor, new JsonTransformer());
        });

        // Rotas para Alunos
        AlunosController alunosController = new AlunosController(basePath);
        path("/alunos", () -> {
            get("", alunosController::getAllAlunos, new JsonTransformer());
            post("", alunosController::createAluno, new JsonTransformer());
            delete("/:id", alunosController::deleteAluno, new JsonTransformer());
        });

        // Rotas para Turmas
        TurmasController turmasController = new TurmasController(basePath);
        path("/turmas", () -> {
            get("", turmasController::getAllTurmas, new JsonTransformer());
            post("", turmasController::createTurma, new JsonTransformer());
            delete("/:id", turmasController::deleteTurma, new JsonTransformer());
        });
    }
}
