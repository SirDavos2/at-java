package org.example;

public class Professor {
    private int id;
    private String nome;
    private double salario;
    private boolean mestre;
    private String endereco;
    private int idEscola;

    public Professor(int id, String nome, double salario, boolean mestre, String endereco, int idEscola) {
        this.id = id;
        this.nome = nome;
        this.salario = salario;
        this.mestre = mestre;
        this.endereco = endereco;
        this.idEscola = idEscola;
    }
}
