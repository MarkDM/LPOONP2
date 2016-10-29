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
 * @author HJSYSTEMS
 */
public class NCidades {

    private final String getIdByNome = "select cidade_id from cidades where UPPER(retira_acentuacao(trim(both  cidade_nome))) = ?";
    private final String LIST_CIDADES = "SELECT * FROM CIDADES";
    private final String LIST_UFS = "SELECT * FROM UNIDADE_FEDERATIVA";
    private final String INSERT = "INSERT INTO cidades(cidade_nome, uf_sigla) VALUES (?, ?);"; 
    
    public void addCidade(ECidades cidade) {
        Connection conexao = null;
        PreparedStatement pstm = null;
        try {
            conexao = new ConnectionFactory2().getConnection();
            pstm = conexao.prepareStatement(INSERT);
            pstm.setString(1, cidade.getNome());
            pstm.setString(2, cidade.getUF());
            pstm.execute();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            ConnectionFactory2.fechaConexao(conexao, pstm);
        }
    }
    
    public List<ECidades> listarCidades() {
        List<ECidades> cidades = new ArrayList<>();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = new ConnectionFactory2().getConnection();
            pstm = con.prepareStatement(LIST_CIDADES);
            rs = pstm.executeQuery();
            while (rs.next()) {

                ECidades cidade = new ECidades();
                cidade.setId(rs.getInt("cidade_id"));
                cidade.setNome(rs.getString("cidade_nome"));
                cidade.setUF(rs.getString("uf_sigla"));
                cidades.add(cidade);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            ConnectionFactory2.fechaConexao(con, pstm, rs);
        }
        return cidades;
    }
    
     public List<EUnidadeFederativa> listarUF() {
        List<EUnidadeFederativa> ufs = new ArrayList<>();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = new ConnectionFactory2().getConnection();
            pstm = con.prepareStatement(LIST_UFS);
            rs = pstm.executeQuery();
            while (rs.next()) {

                EUnidadeFederativa uf = new EUnidadeFederativa();
                uf.setUf_sigla(rs.getString("uf_sigla"));
                uf.setUf_descricao(rs.getString("uf_descricao"));

                ufs.add(uf);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            ConnectionFactory2.fechaConexao(con, pstm, rs);
        }
        return ufs;
    }

    public int getIdByNome(String nomeCidade) {
        nomeCidade = UtilStr.semAcento(nomeCidade).toUpperCase();
        int idCidade;
        Connection conexao = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conexao = new ConnectionFactory2().getConnection();
            pstm = conexao.prepareStatement(getIdByNome);
            pstm.setString(1, nomeCidade);
            rs = pstm.executeQuery();

            if (rs.next()) {
                idCidade = rs.getInt("cidade_id");
            } else {
                throw new RuntimeException("Cidade não encontrada!");
            }
            return idCidade;
        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionFactory2.fechaConexao(conexao, pstm, rs);
        }
    }
}
