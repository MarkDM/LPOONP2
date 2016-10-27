/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alunos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author marcos
 */
public class NEndereco {

    private final String INSERT_CIDADE = "INSERT INTO CIDADES (cidade_nome,uf_sigla) values (?,?)";
    private final String LIST_CIDADES = "SELECT * FROM CIDADES";
    private final String LIST_UFS = "SELECT * FROM UNIDADE_FEDERATIVA";
    private final String INSERT = "INSERT INTO endereco(rua, setor, cep, cidade_id, uf_sigla)\n"
            + "    VALUES (?, ?, ?, ?, ?);";
    private final String GET_ID = "SELECT endereco_id \n"
            + "	FROM endereco \n"
            + "WHERE retira_acentuacao(UPPER(TRIM(BOTH rua))) = ?\n"
            + "  and retira_acentuacao(UPPER(TRIM(BOTH setor))) = ?\n"
            + "  and retira_acentuacao(UPPER(TRIM(BOTH cep))) =   ?\n"
            + "  and retira_acentuacao(UPPER(TRIM(BOTH uf_sigla))) = ?";
    
    public int getIdEndereco(EEndereco endereco){
        String rua = UtilStr.semAcento(endereco.getRua()).toUpperCase().trim();
        String setor = UtilStr.semAcento(endereco.getSetor()).toUpperCase().trim();
        String cep = UtilStr.semAcento(endereco.getCep()).toUpperCase().trim();
        String uf_sigla = UtilStr.semAcento(endereco.getUF()).toUpperCase().trim();
        
        int idEndereco;
        
        Connection conexao = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conexao = new ConnectionFactory2().getConnection();
            pstm = conexao.prepareStatement(GET_ID);
            pstm.setString(1, rua);
            pstm.setString(2, setor);
            pstm.setString(3, cep);
            pstm.setString(4, uf_sigla);
            rs = pstm.executeQuery();

            if (rs.next()) {
                idEndereco = rs.getInt("endereco_id");
            } else {
                throw new RuntimeException("Cidade não encontrada!");
            }
            return idEndereco;
        } catch (SQLException | RuntimeException e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionFactory2.fechaConexao(conexao, pstm, rs);
        }
    }

    public void addEndereco(EEndereco endereco) {
        Connection conexao = null;
        PreparedStatement pstm = null;
        try {
            conexao = new ConnectionFactory2().getConnection();
            pstm = conexao.prepareStatement(INSERT);
            pstm.setString(1, endereco.getRua());
            pstm.setString(2, endereco.getSetor());
            pstm.setString(3, endereco.getCep());
            pstm.setInt(4, endereco.getCidade().getId());
            pstm.setString(5, endereco.getUF());
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

}
