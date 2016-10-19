/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alunos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marcos
 */
public class NAlunoCurso {

    private final String LIST_BY_RA = "select c.nome_curso\n"
            + "       ,alc.dt_matricula\n"
            + "       ,c.curso_id\n"
            + "from curso as c\n"
            + "inner join aluno_curso as alc on alc.curso_id = c.curso_id\n"
            + "inner join alunos as al on al.ra = alc.aluno_ra\n"
            + "where al.ra = ?";

    public List<ECursos> getCursosByAluno() {
        List<ECursos> cursos = new ArrayList<>();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = new ConnectionFactory2().getConnection();
            pstm = con.prepareStatement(LIST_BY_RA);
            rs = pstm.executeQuery();
            while (rs.next()) {
                ECursos curso = new ECursos();
                
               // curso.setId(rs.get);
                
                cursos.add(curso);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            ConnectionFactory2.fechaConexao(con, pstm, rs);
        }
        return cursos;
    }
}
