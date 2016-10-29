/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alunos;

/**
 *
 * @author Marcos
 */
public class ECidades {
    
    private int id;
    private String nome;
    private String UF;

     @Override
    public String toString() {
        return id + " - " + this.nome;
    }
    
    public ECidades() {
    }

    public ECidades(int id, String nome, String UF) {
        this.id = id;
        this.nome = nome;
       // this.UF = UF;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getUF() {
        return UF;
    }

    public void setUF(String UF) {
        this.UF = UF;
    }
    
    
    
}
