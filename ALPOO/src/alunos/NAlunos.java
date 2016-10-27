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
            + "    VALUES (?, ?, ?, ?, ?, ?,?\n"
            + "		, select MAX(endereco_id) from endereco\n"
            + "		, select MAX(curso_id) from curso);";

    private final String UPDATE = "UPDATE alunos\n"
            + "   SET  nome=?, dt_nascimento=?, nome_pai=?, nome_mae=?, endereco_id=?, \n"
            + "       cidade_nascimento=?, curso_id=?, data_matricula=?\n"
            + " WHERE ra = ?;";

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
        NCidades cidadeDao = new NCidades();
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
            pstm.setDate(6, (Date) aluno.getData_matricula());
            //********** pega ID da cidade
            pstm.setInt(7, cidadeDao.getIdByNome(aluno.getCidade_nascimento().getNome()));
            //*********Inserir Endereco
            NEndereco enderecoDao = new NEndereco();
            enderecoDao.addEndereco(aluno.getEndereco());
            //********Inserir Curso
            NCursos cursoDao = new NCursos();
            cursoDao.addCurso(aluno.getCurso());
            //*******Insere aluno com o ultimo endereco e curso
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
                pstm.setString(1, aluno.getNome());
                pstm.setDate(2, (Date) aluno.getData_nascimento());
                pstm.setString(3, aluno.getNome_pai());
                pstm.setString(4, aluno.getNome_mae());
                //*****Pega ID endereco
                NEndereco enderecoDao = new NEndereco();
                pstm.setInt(5, enderecoDao.getIdEndereco(aluno.getEndereco()));
                //*****Pega ID cidade
                NCidades cidadesDao = new NCidades();
                pstm.setInt(6, cidadesDao.getIdByNome(aluno.getCidade().getNome()));
                //*****Pega ID curso
                NCursos cursosDao = new NCursos();
                pstm.setInt(7, cursosDao.getIdByNome(aluno.getCidade().getNome()));
                //*****
                pstm.setDate(9, (Date) aluno.getData_matricula());
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
