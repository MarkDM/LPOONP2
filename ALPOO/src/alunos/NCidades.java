/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alunos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author HJSYSTEMS
 */
public class NCidades {

    private final String getIdByNome = "select cidade_id from cidades\n"
            + "where retira_acentuacao(UPPER(trim(both cidade_nome))) = ?";

    public int getIdByNome(String nomeCidade) {
        nomeCidade = UtilStr.semAcento(nomeCidade);
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
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionFactory2.fechaConexao(conexao, pstm, rs);
        }
    }
}
