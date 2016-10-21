/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package alunos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NAlunos {

    private final String INSERT = "INSERT INTO alunos(\n"
            + "            ra, nome, dt_nascimento, nome_pai, nome_mae, endereco_id, cidade_nascimento, \n"
            + "            uf_sigla, curso_id, data_matricula)\n"
            + " VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?);";

    private final String UPDATE = "UPDATE ALUNOS SET NOME = ?,DT_NASCIMENTO = ?,CIDADE_NASCIMENTO= ?"
            + "UF_NASCIMENTO = ?,NOME_PAI = ?,NOME_MAE = ?"
            + "WHERE RA = ?";

    private final String DELETE = "DELETE FROM ALUNOS WHERE RA = ?";

    private final String LIST = "select al.ra\n"
            + "       ,al.nome\n"
            + "       ,al.dt_nascimento\n"
            + "       ,cidn.cidade_nome as CidadeNascimento\n"
            + "       ,al.nome_pai\n"
            + "       ,al.nome_mae\n"
            + "       ,en.rua\n"
            + "       ,en.setor\n"
            + "       ,en.cep\n"
            + "       ,cid.cidade_nome   as Cidade_Atual\n"
            + "       ,ufn.uf_descricao  as UF_Nascimento\n"
            + "       ,ufn.uf_sigla      as UF_Nascimento_sigla\n"
            + "       ,uf.uf_descricao   as UF_Atual\n"
            + "       ,uf.uf_sigla       as UF_Atual_sigla\n"
            + "       ,c.nome_curso      as Curso\n"
            + "       ,al.data_matricula\n"
            + "\n"
            + "from alunos as al\n"
            + "inner join cidades as cidn on al.cidade_nascimento = cidn.cidade_id\n"
            + "inner join endereco as en on en.endereco_id = al.endereco_id\n"
            + "inner join cidades  as cid on cid.cidade_id = en.cidade_id\n"
            + "inner join unidade_federativa as ufn on ufn.uf_sigla = en.uf_sigla\n"
            + "inner join unidade_federativa as uf on uf.uf_sigla = en.uf_sigla\n"
            + "inner join curso as c on c.curso_id = al.curso_id";

    private final String FIND = this.LIST + " WHERE RA = ? order by al.nome";

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

    public void alteraAluno(EAluno aluno) {
        if (aluno != null) {
            Connection con = null;
            PreparedStatement pstm = null;
            try {
                con = new ConnectionFactory2().getConnection();
                pstm = con.prepareStatement(UPDATE);
                pstm.setString(1, aluno.getRa());
                pstm.setString(2, aluno.getNome());
                pstm.setDate(3, (Date) aluno.getData_nascimento());
                pstm.setString(4, aluno.getNome_pai());
                pstm.setString(5, aluno.getNome_mae());

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
                aluno.setData_nascimento(rs.getDate("dt_nascimento"));
                aluno.setCidade_nascimento(rs.getString("cidade_nascimento"));
                aluno.setUf_de_nascimento(rs.getString("uf_nascimento"));
                aluno.setNome_pai(rs.getString("nome_pai"));
                aluno.setNome_mae(rs.getString("nome_mae"));

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
        EEndereco endereco = new EEndereco();
        ECursos curso = new ECursos();
        PreparedStatement pstm = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = new ConnectionFactory2().getConnection();
            pstm = con.prepareStatement(FIND);
            pstm.setString(1, ra);
            rs = pstm.executeQuery();

            rs.next();

            aluno.setRa(rs.getString("ra"));
            aluno.setNome(rs.getString("nome"));
            aluno.setData_nascimento(rs.getDate("dt_nascimento"));
            aluno.setCidade_nascimento(rs.getString("cidadenascimento"));
            aluno.setUf_de_nascimento(rs.getString("uf_nascimento"));
            aluno.setNome_pai(rs.getString("nome_pai"));
            aluno.setNome_mae(rs.getString("nome_mae"));
            aluno.setData_matricula(rs.getDate("data_matricula"));
            aluno.setNome_pai(rs.getString("nome_pai"));
            aluno.setNome_mae(rs.getString("nome_mae"));
            endereco.setRua(rs.getString("rua"));
            endereco.setCidade(rs.getString("cidade_atual"));
            endereco.setCep(rs.getString("cep"));
            endereco.setUF(rs.getString("uf_atual_sigla"));
            endereco.setSetor(rs.getString("setor"));
            curso.setDescricao(rs.getString("curso"));
            aluno.setEndereco(endereco);
            aluno.setCurso(curso);

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
