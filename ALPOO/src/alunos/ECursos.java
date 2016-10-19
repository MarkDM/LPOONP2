/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alunos;

import java.util.Date;

/**
 *
 * @author Marcos
 */
public class ECursos {

    private int id;
    private String descricao;

    public ECursos() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        if (descricao.length() > 60 || descricao.contains("/!@#$¨¨&*")) {
            throw new RuntimeException("Valor digitado invalido!");
        } else {
            this.descricao = descricao;
        }
    }

    @Override
    public String toString() {
        return "ECursos{" + "id=" + id + ", descricao=" + descricao + '}';
    }
}
