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
            + "            ra, nome, dt_nascimento, nome_pai, nome_mae, cidade_nascimento, \n"
            + "            uf_sigla, curso_id, data_matricula, rua, setor, cep, cidade_id, \n"
            + "            uf_nascimento_sigla)\n"
            + "    VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?,?);";

    private final String UPDATE = "UPDATE ALUNOS SET NOME = ?,DT_NASCIMENTO = ?,CIDADE_NASCIMENTO= ?"
            + "UF_NASCIMENTO = ?,NOME_PAI = ?,NOME_MAE = ?"
            + "WHERE RA = ?";

    private final String DELETE = "DELETE FROM ALUNOS WHERE RA = ?";

    private final String LIST = "select 	a.*\n"
            + "      , cid.cidade_id as cidade_nasc_id\n"
            + "      , cid.cidade_nome as cidadenascimento\n"
            + "      , cid.uf_sigla as uf_nascimento_sigla\n"
            + "      , c.*\n"
            + "      , e.endereco_id\n"
            + "      , e.rua\n"
            + "      , e.setor\n"
            + "      , e.cep\n"
            + "      , e.uf_sigla uf_atual_sigla\n"
            + "      , cd.cidade_id \n"
            + "      , cd.cidade_nome as cidade_atual\n"
            + "      , uf.uf_descricao\n"
            + "from alunos a\n"
            + "	left join curso c on c.curso_id = a.curso_id\n"
            + "	left join endereco e on e.endereco_id = a.endereco_id\n"
            + "	inner join cidades cid on cid.cidade_id = a.cidade_nascimento\n"
            + "	inner join cidades cd on cd.cidade_id = e.cidade_id\n"
            + "	inner join unidade_federativa uf on uf.uf_sigla = e.uf_sigla";

    private final String FIND = this.LIST + " WHERE RA = ? order by a.nome";

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
            pstm.setDate(3, (Date) aluno.getData_nascimento());
            pstm.setString(4, aluno.getNome_pai());
            pstm.setString(5, aluno.getNome_mae());
            pstm.setString(6, aluno.getCidade_nascimento().getNome());
            pstm.setString(7, aluno.getUf());
            pstm.setInt(8, aluno.getCurso().getId());
            pstm.setDate(9, (Date) aluno.getData_matricula());
            pstm.setString(10, aluno.getRua());
            pstm.setString(11, aluno.getSetor());
            pstm.setString(12, aluno.getCep());
            pstm.setInt(13, aluno.getCidade().getId());
            pstm.setString(14, aluno.getUf_de_nascimento());

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
                pstm.setString(6, aluno.getCidade_nascimento().getNome());
                pstm.setString(7, aluno.getUf());
                pstm.setInt(8, aluno.getCurso().getId());
                pstm.setDate(9, (Date) aluno.getData_matricula());
                pstm.setString(10, aluno.getRua());
                pstm.setString(11, aluno.getSetor());
                pstm.setString(12, aluno.getCep());
                pstm.setInt(13, aluno.getCidade().getId());
                pstm.setString(14, aluno.getUf_de_nascimento());

                pstm.execute();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                ConnectionFactory2.fechaConexao(con, pstm);
            }
        } else {
            throw new RuntimeException("Aluno Nulo");
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
                ECidades cidadeNascimento = new ECidades();
                ECidades cidadeResidencia = new ECidades();
                ECursos curso = new ECursos();

                aluno.setRa(rs.getString("ra"));
                aluno.setNome(rs.getString("nome"));
                aluno.setData_nascimento(rs.getDate("dt_nascimento"));
                cidadeNascimento.setNome(rs.getString("cidade_nascimento"));
                cidadeResidencia.setNome(rs.getString("cidade_atual"));
                aluno.setUf_de_nascimento(rs.getString("uf_nascimento_sigla"));
                aluno.setData_matricula(rs.getDate("data_matricula"));
                aluno.setNome_pai(rs.getString("nome_pai"));
                aluno.setNome_mae(rs.getString("nome_mae"));
                aluno.setRua(rs.getString("rua"));
                aluno.setCep(rs.getString("cep"));
                aluno.setUf(rs.getString("uf_atual_sigla"));
                aluno.setSetor(rs.getString("setor"));
                curso.setDescricao(rs.getString("nome_curso"));
                aluno.setCidade_nascimento(cidadeNascimento);
                aluno.setCidade(cidadeResidencia);
                aluno.setCurso(curso);

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
        ECidades cidadeNascimento = new ECidades();
        ECidades cidadeResidencia = new ECidades();
        //EEndereco endereco = new EEndereco();
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
            cidadeNascimento.setNome(rs.getString("cidadenascimento"));
            cidadeResidencia.setNome(rs.getString("cidade_atual"));
            aluno.setUf_de_nascimento(rs.getString("uf_nascimento_sigla"));
            aluno.setNome_pai(rs.getString("nome_pai"));
            aluno.setNome_mae(rs.getString("nome_mae"));
            aluno.setData_matricula(rs.getDate("data_matricula"));
            aluno.setNome_pai(rs.getString("nome_pai"));
            aluno.setNome_mae(rs.getString("nome_mae"));
            aluno.setRua(rs.getString("rua"));
            aluno.setCep(rs.getString("cep"));
            aluno.setUf(rs.getString("uf_atual_sigla"));
            aluno.setSetor(rs.getString("setor"));
            curso.setDescricao(rs.getString("nome_curso"));
            aluno.setCidade_nascimento(cidadeNascimento);
            aluno.setCidade(cidadeResidencia);
            //aluno.setEndereco(endereco);
            aluno.setCurso(curso);

            return aluno;
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            ConnectionFactory2.fechaConexao(con, pstm, rs);
        }
    }

    public void excluiAluno(String ra) {
        PreparedStatement pstm = null;
        Connection con = null;
        try {
            con = new ConnectionFactory2().getConnection();
            pstm = con.prepareStatement(DELETE);
            pstm.setString(1, ra);
            pstm.execute();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            ConnectionFactory2.fechaConexao(con, pstm);
        }
    }
}
