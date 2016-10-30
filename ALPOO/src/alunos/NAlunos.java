package alunos;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class NAlunos {

    private final String INSERT = "INSERT INTO alunos(ra, nome, dt_nascimento, nome_pai, nome_mae, data_matricula, cidade_nascimento, endereco_id, curso_id)\n"
            + "            VALUES (?, ?,?, ?, ?, ?, ?\n"
            + "                     , (select MAX(endereco_id) from endereco)\n"
            + "                     , ?\n"
            + "                 );";

    private final String INSERT_SEM_ENDERECO = "INSERT INTO alunos(ra, nome, dt_nascimento, nome_pai, nome_mae, data_matricula, cidade_nascimento, endereco_id, curso_id)\n"
            + "            VALUES (?, ?,?, ?, ?, ?, ?, NULL, ?);";

    private final String UPDATE = "UPDATE alunos\n"
            + "   SET nome=?, dt_nascimento=?, nome_pai=?, nome_mae=?, \n"
            + "       cidade_nascimento=?, curso_id=?, data_matricula=?, endereco_id=?\n"
            + " WHERE ra=?;";

    private final String UPDATE_SEM_ENDERECO = "UPDATE alunos\n"
            + "   SET nome=?, dt_nascimento=?, nome_pai=?, nome_mae=?, \n"
            + "       cidade_nascimento=?, curso_id=?, data_matricula=?, endereco_id=NULL\n"
            + " WHERE ra=?;";

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
            + "      , e.uf_sigla as uf_atual_sigla\n"
            + "      , cd.cidade_id \n"
            + "      , cd.cidade_nome as cidade_atual\n"
            + "      , cd.uf_sigla as uf_descricao\n"
            + "from alunos a\n"
            + "	inner join curso c on c.curso_id = a.curso_id\n"
            + "	left join endereco e on e.endereco_id = a.endereco_id\n"
            + "	inner join cidades cid on cid.cidade_id = a.cidade_nascimento\n"
            + "	left join cidades cd on cd.cidade_id = e.cidade_id\n";

    private final String FIND = this.LIST + " WHERE RA = ? order by a.nome";

    public NAlunos() {
    }

    public void adicionarAluno(EAluno aluno, boolean adiconarEndereco) {
        NCidades cidadeDao = new NCidades();
        NCursos cursoDao = new NCursos();
        Connection conexao = null;
        PreparedStatement pstm = null;
        try {
            if (adiconarEndereco) {
                //*********Inserir Endereco
                NEndereco enderecoDao = new NEndereco();
                enderecoDao.addEndereco(aluno.getEndereco());
            }
            conexao = new ConnectionFactory2().getConnection();
            if (adiconarEndereco) {
                //*******Insere aluno com o ultimo endereco 
                pstm = conexao.prepareStatement(INSERT);
            } else {
                //*******Insere aluno com o  endereco null
                pstm = conexao.prepareStatement(INSERT_SEM_ENDERECO);
            }
            pstm.setString(1, aluno.getRa());
            pstm.setString(2, aluno.getNome());
            pstm.setDate(3, new java.sql.Date(aluno.getData_nascimento().getTime()));
            pstm.setString(4, aluno.getNome_pai());
            pstm.setString(5, aluno.getNome_mae());
            pstm.setDate(6, new java.sql.Date(aluno.getData_matricula().getTime()));
            pstm.setInt(7, aluno.getCidade_nascimento().getId());
            pstm.setInt(8, aluno.getCurso().getId());

            pstm.execute();
        } catch (SQLException ex) {
            throw new RuntimeException(ex);
        } finally {
            ConnectionFactory2.fechaConexao(conexao, pstm);
        }
    }

    public void adicionarAluno(EAluno aluno) {
        adicionarAluno(aluno, true);
    }

    public void alteraAluno(EAluno aluno, boolean alterarEndereco) {
        if (aluno != null) {
            Connection conexao = null;
            PreparedStatement pstm = null;
            try {
                if (alterarEndereco) {
                    //*********altera Endereco
                    NEndereco enderecoDao = new NEndereco();
                    if (aluno.getEndereco().getId() == 0) {
                        enderecoDao.addEndereco(aluno.getEndereco());
                        aluno.getEndereco().setId(enderecoDao.getIdEndereco(aluno.getEndereco()));
                    } else {
                        enderecoDao.alterar(aluno.getEndereco());
                    }
                }
                conexao = new ConnectionFactory2().getConnection();
                if (alterarEndereco) {
                    //*******altera aluno com o ultimo endereco 
                    pstm = conexao.prepareStatement(UPDATE);
                } else {
                    //*******altera aluno com o  endereco null
                    pstm = conexao.prepareStatement(UPDATE_SEM_ENDERECO);
                }

                pstm.setString(1, aluno.getNome());
                pstm.setDate(2, new java.sql.Date(aluno.getData_nascimento().getTime()));
                pstm.setString(3, aluno.getNome_pai());
                pstm.setString(4, aluno.getNome_mae());
                pstm.setInt(5, aluno.getCidade_nascimento().getId());
                pstm.setInt(6, aluno.getCurso().getId());
                pstm.setDate(7, new java.sql.Date(aluno.getData_matricula().getTime()));
                if (alterarEndereco) {
                    pstm.setInt(8, aluno.getEndereco().getId());
                    pstm.setString(9, aluno.getRa());
                } else {
                    pstm.setString(8, aluno.getRa());
                }
                pstm.execute();
            } catch (Exception e) {
                throw new RuntimeException(e);
            } finally {
                ConnectionFactory2.fechaConexao(conexao, pstm);
            }
        } else {
            throw new RuntimeException("Aluno Nulo");
        }
    }

    public List<EAluno> listaAlunos() {
        List<EAluno> alunos = new ArrayList<>();
        Connection con = null;
        PreparedStatement pstm = null;
        ResultSet rs = null;
        try {
            con = new ConnectionFactory2().getConnection();
            pstm = con.prepareStatement(LIST);
            rs = pstm.executeQuery();
            while (rs.next()) {
                EAluno aluno = new EAluno();
                EEndereco endereco = new EEndereco();
                ECidades cidadeNascimento = new ECidades();
                ECursos curso = new ECursos();
                //setando aluno
                aluno.setRa(rs.getString("ra"));
                aluno.setNome(rs.getString("nome"));
                aluno.setData_nascimento(rs.getDate("dt_nascimento"));
                aluno.setNome_pai(rs.getString("nome_pai"));
                aluno.setNome_mae(rs.getString("nome_mae"));
                aluno.setData_matricula(rs.getDate("data_matricula"));

                //setando cidade nascimento
                cidadeNascimento.setId(rs.getInt("cidade_nasc_id"));
                cidadeNascimento.setNome(rs.getString("cidade_nascimento"));
                cidadeNascimento.setUF(rs.getString("uf_nascimento_sigla"));
                aluno.setCidade_nascimento(cidadeNascimento);

                //setando curso
                curso.setId(rs.getInt("curso_id"));
                curso.setDescricao(rs.getString("nome_curso"));
                aluno.setCurso(curso);

                //setando endereço
                endereco.setId(rs.getInt("endereco_id"));
                endereco.setRua(rs.getString("rua"));
                endereco.setSetor(rs.getString("setor"));
                endereco.setCep(rs.getString("cep"));
                endereco.setUF(rs.getString("uf_atual_sigla"));
                ECidades cidadeAtual = new ECidades();
                cidadeAtual.setId(rs.getInt("cidade_id"));
                cidadeAtual.setNome(rs.getString("cidade_atual"));
                cidadeAtual.setUF(rs.getString("uf_descricao"));
                endereco.setCidade(cidadeAtual);
                aluno.setEndereco(endereco);

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
        ECidades cidadeNascimento = new ECidades();
        ECursos curso = new ECursos();
        PreparedStatement pstm = null;
        Connection con = null;
        ResultSet rs = null;
        try {
            con = new ConnectionFactory2().getConnection();
            pstm = con.prepareStatement(FIND);
            pstm.setString(1, ra);
            rs = pstm.executeQuery();

            if (rs.next()) {

                //setando aluno
                aluno.setRa(rs.getString("ra"));
                aluno.setNome(rs.getString("nome"));
                aluno.setData_nascimento(rs.getDate("dt_nascimento"));
                aluno.setNome_pai(rs.getString("nome_pai"));
                aluno.setNome_mae(rs.getString("nome_mae"));
                aluno.setData_matricula(rs.getDate("data_matricula"));

                //setando cidade nascimento
                cidadeNascimento.setId(rs.getInt("cidade_nasc_id"));
                cidadeNascimento.setNome(rs.getString("cidade_nascimento"));
                cidadeNascimento.setUF(rs.getString("uf_nascimento_sigla"));
                aluno.setCidade_nascimento(cidadeNascimento);

                //setando curso
                curso.setId(rs.getInt("curso_id"));
                curso.setDescricao(rs.getString("nome_curso"));
                aluno.setCurso(curso);

                //setando endereço
                endereco.setId(rs.getInt("endereco_id"));
                endereco.setRua(rs.getString("rua"));
                endereco.setSetor(rs.getString("setor"));
                endereco.setCep(rs.getString("cep"));
                endereco.setUF(rs.getString("uf_atual_sigla"));
                ECidades cidadeAtual = new ECidades();
                cidadeAtual.setId(rs.getInt("cidade_id"));
                cidadeAtual.setNome(rs.getString("cidade_atual"));
                cidadeAtual.setUF(rs.getString("uf_descricao"));
                endereco.setCidade(cidadeAtual);
                aluno.setEndereco(endereco);

                return aluno;
            } else {
                throw new RuntimeException("Aluno não encontrado!");
            }
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
