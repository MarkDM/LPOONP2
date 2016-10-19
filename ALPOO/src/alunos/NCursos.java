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
 * @author igort_000
 */
public class NCursos {
    private final String INSERT = "INSERT INTO CURSO(nome_curso)  VALUES (?)";
    private final String LIST = "SELECT * FROM CURSO";
    
    public void addCurso(ECursos curso){
         Connection conexao = null;
        PreparedStatement pstm = null;
        try {
            conexao = new ConnectionFactory2().getConnection();
            pstm = conexao.prepareStatement(INSERT);
            pstm.setString(1,curso.getDescricao());
            pstm.execute();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            ConnectionFactory2.fechaConexao(conexao, pstm);
        }
    }
    
    public List<ECursos> listar(){
         List<ECursos> cursos = new ArrayList<ECursos>();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = new ConnectionFactory2().getConnection();
            pstm = con.prepareStatement(LIST);
            rs = pstm.executeQuery();
            while (rs.next()) {
                ECursos curso = new ECursos();

                curso.setId(rs.getInt("curso_id"));
                curso.setDescricao(rs.getString("nome_curso"));
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
