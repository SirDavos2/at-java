package org.example;

public class Turma {
    private int id;
    private String nome;
    private int idEscola;

    public Turma(int id, String nome, int idEscola) {
        this.id = id;
        this.nome = nome;
        this.idEscola = idEscola;
    }

    public int getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public int getIdEscola() {
        return idEscola;
    }
}
