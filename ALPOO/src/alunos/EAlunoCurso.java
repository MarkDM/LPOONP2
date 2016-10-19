/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alunos;

import java.util.Date;

/**
 *
 * @author marcos
 */
public class EAlunoCurso {
    
    private EAluno aluno;
    private ECursos cursos;
    private Date data_matricula;
 
    public Date getData_matricula() {
        return data_matricula;
    }

    public void setData_matricula(Date data_matricula) {
        this.data_matricula = data_matricula;
    }
    
    public EAluno getAluno() {
        return aluno;
    }

    public void setAluno(EAluno aluno) {
        this.aluno = aluno;
    }

    public ECursos getCursos() {
        return cursos;
    }

    public void setCursos(ECursos cursos) {
        this.cursos = cursos;
    }
    
    
}
