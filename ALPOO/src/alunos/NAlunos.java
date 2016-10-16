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

public class NAlunos {

    private final String INSERT = "INSERT INTO ALUNOS(RA,NOME,DT_NASCIMENTO,CIDADE_NASCIMENTO,UF_NASCIMENTO,NOME_PAI,NOME_MAE)"
            + " VALUES(?,?,?,?,?,?,?)";
    private final String UPDATE = "UPDATE ALUNOS SET NOME = ?,DT_NASCIMENTO = ?,CIDADE_NASCIMENTO= ?"
            + "UF_NASCIMENTO = ?,NOME_PAI = ?,NOME_MAE = ?"
            + "WHERE RA = ?";
    private final String DELETE = "DELETE FROM ALUNOS WHERE RA = ?";
    private final String LIST = "SELECT * FROM ALUNOS";
    private final String FIND = "SELECT * FROM ALUNOS WHERE RA = ?";

    public NAlunos() {
    }

    public void adicionarAluno(EAluno aluno) {
        Connection conexao = null;
        PreparedStatement pstm = null;
        try {
            conexao = new ConnectionFactory2().getConnection();
            pstm = conexao.prepareStatement(INSERT);
            pstm.setString(1, aluno.getRa());
            pstm.setString(2, aluno.getNome());
            pstm.setDate(3, new java.sql.Date(aluno.getData_nascimento().getTime()));
            pstm.setString(4, aluno.getCidade_nascimento());
            pstm.setString(5, aluno.getUf_de_nascimento());
            pstm.setString(6, aluno.getNome_pai());
            pstm.setString(7, aluno.getNome_mae());
            pstm.execute();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            ConnectionFactory2.fechaConexao(conexao, pstm);
        }
    }

    public void alteraCliente(EAluno aluno) {
        if (aluno != null) {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                con = new ConnectionFactory2().getConnection();
                pstm = con.prepareStatement(UPDATE);
                pstm.setString(1, aluno.getNome());
                pstm.setDate(2, new java.sql.Date(aluno.getData_nascimento().getTime()));
                pstm.setString(3, aluno.getCidade_nascimento());
                pstm.setString(4, aluno.getUf_de_nascimento());
                pstm.setString(5, aluno.getNome_pai());
                pstm.setString(6, aluno.getNome_mae());
                pstm.setString(7, aluno.getRa());
                pstm.execute();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                ConnectionFactory2.fechaConexao(con, pstm);
            }
        } else {
            throw new RuntimeException("Cliente nulo");
        }
    }

    public List<EAluno> listaAlunos() {
        List<EAluno> alunos = new ArrayList<EAluno>();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = new ConnectionFactory2().getConnection();
            pstm = con.prepareStatement(LIST);
            rs = pstm.executeQuery();
            while (rs.next()) {
                EAluno aluno = new EAluno();

                aluno.setRa(rs.getString("RA"));
                aluno.setNome(rs.getString("NOME"));
                aluno.setData_nascimento(rs.getDate("DT_NASCIMENTO"));
                aluno.setCidade_nascimento(rs.getString("CIDADE_NASCIMENTO"));
                aluno.setUf_de_nascimento(rs.getString("UF_NASCIMENTO"));
                aluno.setNome_pai(rs.getString("NOME_PAI"));
                aluno.setNome_mae(rs.getString("NOME_MAE"));

                alunos.add(aluno);
            }
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            ConnectionFactory2.fechaConexao(con, pstm, rs);
        }
        return alunos;
    }

    public EAluno getAlunoByRa(String ra) {
        EAluno aluno = new EAluno();
        PreparedStatement pstm = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = new ConnectionFactory2().getConnection();
            pstm = con.prepareStatement(FIND);
            pstm.setString(1, ra);
            rs = pstm.executeQuery();

            aluno.setRa(rs.getString("RA"));
            aluno.setNome(rs.getString("NOME"));
            aluno.setData_nascimento(rs.getDate("DT_NASCIMENTO"));
            aluno.setCidade_nascimento(rs.getString("CIDADE_NASCIMENTO"));
            aluno.setUf_de_nascimento(rs.getString("UF_NASCIMENTO"));
            aluno.setNome_pai(rs.getString("NOME_PAI"));
            aluno.setNome_mae(rs.getString("NOME_MAE"));

            return aluno;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionFactory2.fechaConexao(con, pstm, rs);
        }
    }

    public void excluiAluno(EAluno aluno) {
        PreparedStatement pstm = null;
        Connection con = null;
        try {
            con = new ConnectionFactory2().getConnection();
            pstm = con.prepareStatement(DELETE);
            pstm.setString(1, aluno.getRa());
            pstm.execute();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            ConnectionFactory2.fechaConexao(con, pstm);
        }
    }
}
