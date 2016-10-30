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

/**
 *
 * @author marcos
 */
public class NEndereco {

    private final String UPDATE = "UPDATE endereco\n"
            + "   SET rua=?, setor=?, cep=?,cidade_id=?\n"
            + " WHERE endereco_id = ?;";

    private final String INSERT = "INSERT INTO endereco(rua, setor, cep, cidade_id)\n"
            + "    VALUES (?, ?, ?, ?);";
    private final String GET_ID = "select max(ENDERECO_ID) as endereco_id from endereco ";

    private final String DELETE = "DELETE FROM public.endereco\n"
            + " WHERE endereco_id = ?;";

    public void alterar(EEndereco endereco) {
        Connection conexao = null;
        PreparedStatement pstm = null;
        try {
            conexao = new ConnectionFactory2().getConnection();
            pstm = conexao.prepareStatement(UPDATE);
            pstm.setString(1, endereco.getRua());
            pstm.setString(2, endereco.getSetor());
            pstm.setString(3, endereco.getCep());
            pstm.setInt(4, endereco.getCidade().getId());
            pstm.setInt(5, endereco.getId());
            pstm.executeUpdate();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionFactory2.fechaConexao(conexao, pstm);
        }
    }

    public int getUltimoIdEndereco() {   
        int idEndereco;

        Connection conexao = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            conexao = new ConnectionFactory2().getConnection();
            pstm = conexao.prepareStatement(GET_ID);
            rs = pstm.executeQuery();

            if (rs.next()) {
                idEndereco = rs.getInt("endereco_id");
                return idEndereco;
            } else {
                idEndereco = 0;
                return idEndereco;
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
            pstm.execute();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            ConnectionFactory2.fechaConexao(conexao, pstm);
        }
    }
    
    public void excluiEndereco(int idEndereco) {
        PreparedStatement pstm = null;
        Connection con = null;
        try {
            con = new ConnectionFactory2().getConnection();
            pstm = con.prepareStatement(DELETE);
            pstm.setInt(1, idEndereco);
            pstm.execute();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            ConnectionFactory2.fechaConexao(con, pstm);
        }
    }
}
