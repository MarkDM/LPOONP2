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
                return idEndereco;
            } else{
                throw new RuntimeException("Endereço não encontrado!");
            }
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
}
