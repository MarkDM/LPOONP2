/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alunos;

import java.util.Date;
import java.util.Objects;

public class EAluno {

    private String ra;              //maxsize 7
    private String nome;        //maxsize 60
    private Date data_nascimento;
    private String cidade_nascimento; //maxsize 60
    private String uf_de_nascimento; //maxsize 2
    private String nome_pai; //maxsize 60
    private String nome_mae; //maxsize 60
    private String rua;
    private String setor;
    private String cep;
    private String cidade_atual;
    private String UF_atual;
    private String nome_curso;
    private Date data_matricula;

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public String getSetor() {
        return setor;
    }

    public void setSetor(String setor) {
        this.setor = setor;
    }

    public String getCep() {
        return cep;
    }

    public void setCep(String cep) {
        this.cep = cep;
    }

    public String getCidade_atual() {
        return cidade_atual;
    }

    public void setCidade_atual(String cidade_atual) {
        this.cidade_atual = cidade_atual;
    }

    public String getUF_atual() {
        return UF_atual;
    }

    public void setUF_atual(String UF_atual) {
        this.UF_atual = UF_atual;
    }

    public String getNome_curso() {
        return nome_curso;
    }

    public void setNome_curso(String nome_curso) {
        this.nome_curso = nome_curso;
    }

    public Date getData_matricula() {
        return data_matricula;
    }

    public void setData_matricula(Date data_matricula) {
        this.data_matricula = data_matricula;
    }

    public String getRa() {
        return ra;
    }

    public void setRa(String ra) {
        this.ra = ra;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Date getData_nascimento() {
        return data_nascimento;
    }

    public void setData_nascimento(Date data_nascimento) {
        this.data_nascimento = data_nascimento;
    }

    public String getCidade_nascimento() {
        return cidade_nascimento;
    }

    public void setCidade_nascimento(String cidade_nascimento) {
        this.cidade_nascimento = cidade_nascimento;
    }

    public String getUf_de_nascimento() {
        return uf_de_nascimento;
    }

    public void setUf_de_nascimento(String uf_de_nascimento) {
        this.uf_de_nascimento = uf_de_nascimento;
    }

    public String getNome_pai() {
        return nome_pai;
    }

    public void setNome_pai(String nome_pai) {
        this.nome_pai = nome_pai;
    }

    public String getNome_mae() {
        return nome_mae;
    }

    public void setNome_mae(String nome_mae) {
        this.nome_mae = nome_mae;
    }

    @Override
    public String toString() {
        return "EAluno{" + "ra=" + ra + ", nome=" + nome + ", data_nascimento=" + data_nascimento + ", cidade_nascimento=" + cidade_nascimento + ", uf_de_nascimento=" + uf_de_nascimento + ", nome_pai=" + nome_pai + ", nome_mae=" + nome_mae + '}';
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.ra);
        hash = 71 * hash + Objects.hashCode(this.nome);
        hash = 71 * hash + Objects.hashCode(this.data_nascimento);
        hash = 71 * hash + Objects.hashCode(this.cidade_nascimento);
        hash = 71 * hash + Objects.hashCode(this.uf_de_nascimento);
        hash = 71 * hash + Objects.hashCode(this.nome_pai);
        hash = 71 * hash + Objects.hashCode(this.nome_mae);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final EAluno other = (EAluno) obj;
        if (!Objects.equals(this.ra, other.ra)) {
            return false;
        }
        if (!Objects.equals(this.nome, other.nome)) {
            return false;
        }
        if (!Objects.equals(this.data_nascimento, other.data_nascimento)) {
            return false;
        }
        if (!Objects.equals(this.cidade_nascimento, other.cidade_nascimento)) {
            return false;
        }
        if (!Objects.equals(this.uf_de_nascimento, other.uf_de_nascimento)) {
            return false;
        }
        if (!Objects.equals(this.nome_pai, other.nome_pai)) {
            return false;
        }
        return Objects.equals(this.nome_mae, other.nome_mae);
    }
}
