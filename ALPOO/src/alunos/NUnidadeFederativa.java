/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alunos;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author igort_000
 */
public class NUnidadeFederativa {

    private final String INSERT = "INSERT INTO public.unidade_federativa(uf_descricao, uf_sigla)\n"
            + "    VALUES (?, ?);";

    public void addUF(EUnidadeFederativa uf) {
        Connection conexao = null;
        PreparedStatement pstm = null;
        try {
            conexao = new ConnectionFactory2().getConnection();
            pstm = conexao.prepareStatement(INSERT);
            pstm.setString(1, uf.getUf_descricao());
            pstm.setString(2, uf.getUf_sigla());
            pstm.execute();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            ConnectionFactory2.fechaConexao(conexao, pstm);
        }
    }
}
