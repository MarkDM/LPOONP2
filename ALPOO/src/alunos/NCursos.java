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

    private final String INSERT = "INSERT INTO CURSOS(nome_curso)  VALUES (?)";
    private final String LIST = "SELECT * FROM CURSO";
    private final String GET_BY_NOME = "SELECT curso_id FROM curso\n"
            + "where UPPER(retira_acentuacao(trim(both  nome_curso))) = ?";

    public int getIdByNome(String nomeCurso) {
        nomeCurso = UtilStr.semAcento(nomeCurso).toUpperCase();
        int idCurso;
        Connection conexao = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conexao = new ConnectionFactory2().getConnection();
            pstm = conexao.prepareStatement(GET_BY_NOME);
            pstm.setString(1, nomeCurso);
            rs = pstm.executeQuery();

            if (rs.next()) {
                idCurso = rs.getInt("curso_id");
            } else {
                throw new RuntimeException("Curso não encontrado!");
            }
            return idCurso;
        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionFactory2.fechaConexao(conexao, pstm, rs);
        }
    }

    public void addCurso(ECursos curso) {
        Connection conexao = null;
        PreparedStatement pstm = null;
        try {
            conexao = new ConnectionFactory2().getConnection();
            pstm = conexao.prepareStatement(INSERT);
            pstm.setString(1, curso.getDescricao());
            pstm.execute();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            ConnectionFactory2.fechaConexao(conexao, pstm);
        }
    }

    public List<ECursos> listar() {
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
