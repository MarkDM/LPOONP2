package alunos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class ManterAlunosCtr implements WindowListener, ActionListener, KeyListener, FocusListener {

    private final ManterAlunos manterAlunos;
    boolean enderecoObrigatorio = false;
    boolean consultou = false;
    List<ECidades> cidades = new ArrayList<>();
    List<EUnidadeFederativa> ufs = new ArrayList<>();

    public ManterAlunosCtr(ManterAlunos manterAlunos) {
        this.manterAlunos = manterAlunos;
        //coloca a janela no centro da tela
        this.manterAlunos.setLocationRelativeTo(null);
        setLimiteCampo();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.manterAlunos.getBtnIncluir()) {
            if (!CamposObrigatorioPreenchido()) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatorios! (*)");
            } else if (enderecoObrigatorio) {
                JOptionPane.showMessageDialog(null, "Preencha todos os cmpos referente a endereço!");
            } else {
                try {
                    EAluno aluno = new EAluno();
                    ECidades cidadeResidencia = new ECidades();
                    ECidades cidadeNascimento = new ECidades();
                    EEndereco endereco = new EEndereco();
                    ECursos curso = new ECursos();
                    NCursos cursoDao = new NCursos();
                    NCidades cidadeDao = new NCidades();
                    NAlunos alunoDao = new NAlunos();

                    aluno.setRa(this.manterAlunos.getRA().getText());
                    aluno.setNome(this.manterAlunos.getTxtNomeAluno().getText());

                    SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
                    aluno.setData_nascimento(f.parse(this.manterAlunos.getTxtDataNasc().getText()));
                    cidadeNascimento.setId(cidadeDao.getIdByNome(this.manterAlunos.getCbxCidadeNasc().getSelectedItem().toString()));
                    cidadeNascimento.setNome(this.manterAlunos.getCbxCidadeNasc().getSelectedItem().toString());
                    cidadeNascimento.setUF(this.manterAlunos.getCbxUFNasc().getSelectedItem().toString());
                    aluno.setCidade_nascimento(cidadeNascimento);

                    if (enderecoObrigatorio) {
                        endereco.setRua(this.manterAlunos.getTxtRua().getText());
                        endereco.setSetor(this.manterAlunos.getTxtSetor().getText());
                        endereco.setCep(this.manterAlunos.getTxtCEP().getText().replace(".", "").replace("-", ""));
                        endereco.setUF(this.manterAlunos.getCbxUF().getSelectedItem().toString());
                        cidadeResidencia.setId(cidadeDao.getIdByNome(this.manterAlunos.getCbxCidadeAtual().getSelectedItem().toString()));
                        cidadeResidencia.setNome(this.manterAlunos.getCbxCidadeAtual().getSelectedItem().toString());
                        cidadeResidencia.setUF(this.manterAlunos.getCbxUF().getSelectedItem().toString());
                        endereco.setCidade(cidadeResidencia);
                        aluno.setEndereco(endereco);
                    }

                    aluno.setNome_pai(this.manterAlunos.getTxtNomePai().getText());
                    aluno.setNome_mae(this.manterAlunos.getTxtNomeMae().getText());
                    aluno.setData_matricula(f.parse(this.manterAlunos.getTxtDataMatricula().getText()));

                    curso.setId(cursoDao.getIdByNome(this.manterAlunos.getCbxCurso().getSelectedItem().toString()));
                    curso.setDescricao(this.manterAlunos.getCbxCurso().getSelectedItem().toString());
                    aluno.setCurso(curso);
                    alunoDao.adicionarAluno(aluno, enderecoObrigatorio);
                    JOptionPane.showMessageDialog(null, "Aluno " + aluno.getNome() + " adicionado com sucesso!");
                    limparCampos();
                } catch (ParseException ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        } else if (e.getSource() == this.manterAlunos.getBtnAlterar()) {
            if (!consultou) {
                JOptionPane.showMessageDialog(null, "É preciso Consultar sua RA para Alterar seu cadastro!");
            } else {
                if (!CamposObrigatorioPreenchido()) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos obrigatorios! (*)");
                } else if (enderecoObrigatorio) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os cmpos referente a endereço!");
                } else {
                    try {
                        EAluno aluno = new EAluno();
                        ECidades cidadeResidencia = new ECidades();
                        ECidades cidadeNascimento = new ECidades();
                        EEndereco endereco = new EEndereco();
                        ECursos curso = new ECursos();
                        NCursos cursoDao = new NCursos();
                        NCidades cidadeDao = new NCidades();
                        NAlunos alunoDao = new NAlunos();

                        aluno.setRa(this.manterAlunos.getRA().getText());
                        aluno.setNome(this.manterAlunos.getTxtNomeAluno().getText());

                        SimpleDateFormat f = new SimpleDateFormat("dd/MM/yyyy");
                        aluno.setData_nascimento(f.parse(this.manterAlunos.getTxtDataNasc().getText()));
                        cidadeNascimento.setId(cidadeDao.getIdByNome(this.manterAlunos.getCbxCidadeNasc().getSelectedItem().toString()));
                        cidadeNascimento.setNome(this.manterAlunos.getCbxCidadeNasc().getSelectedItem().toString());
                        cidadeNascimento.setUF(this.manterAlunos.getCbxUFNasc().getSelectedItem().toString());
                        aluno.setCidade_nascimento(cidadeNascimento);

                        if (enderecoObrigatorio) {
                            endereco.setRua(this.manterAlunos.getTxtRua().getText());
                            endereco.setSetor(this.manterAlunos.getTxtSetor().getText());
                            endereco.setCep(this.manterAlunos.getTxtCEP().getText().replace(".", "").replace("-", ""));
                            endereco.setUF(this.manterAlunos.getCbxUF().getSelectedItem().toString());
                            cidadeResidencia.setId(cidadeDao.getIdByNome(this.manterAlunos.getCbxCidadeAtual().getSelectedItem().toString()));
                            cidadeResidencia.setNome(this.manterAlunos.getCbxCidadeAtual().getSelectedItem().toString());
                            cidadeResidencia.setUF(this.manterAlunos.getCbxUF().getSelectedItem().toString());
                            endereco.setCidade(cidadeResidencia);
                            NEndereco enderecoDao = new NEndereco();
                            endereco.setId(enderecoDao.getIdEndereco(endereco));
                            aluno.setEndereco(endereco);
                        }

                        aluno.setNome_pai(this.manterAlunos.getTxtNomePai().getText());
                        aluno.setNome_mae(this.manterAlunos.getTxtNomeMae().getText());
                        aluno.setData_matricula(f.parse(this.manterAlunos.getTxtDataMatricula().getText()));

                        curso.setId(cursoDao.getIdByNome(this.manterAlunos.getCbxCurso().getSelectedItem().toString()));
                        curso.setDescricao(this.manterAlunos.getCbxCurso().getSelectedItem().toString());
                        aluno.setCurso(curso);
                        alunoDao.alteraAluno(aluno, enderecoObrigatorio);
                        JOptionPane.showMessageDialog(null, "Aluno " + aluno.getNome() + " alterado com sucesso!");
                        limparCampos();
                    } catch (ParseException ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                }
            }
        } else if (e.getSource() == this.manterAlunos.getBtnConsultar()) {
            NAlunos na = new NAlunos();
            try {
                EAluno aluno = na.getAlunoByRa(manterAlunos.getRA().getText());
                this.manterAlunos.getRA().setText(aluno.getRa());
                this.manterAlunos.getTxtNomeAluno().setText(aluno.getNome());
                this.manterAlunos.getTxtRua().setText(aluno.getEndereco().getRua());
                this.manterAlunos.getTxtSetor().setText(aluno.getEndereco().getSetor());
                this.manterAlunos.getTxtCEP().setText(aluno.getEndereco().getCep());
                this.manterAlunos.setDtNascimento(aluno.getData_nascimento());
                this.manterAlunos.getTxtNomeMae().setText(aluno.getNome_mae().trim());
                this.manterAlunos.getTxtNomePai().setText(aluno.getNome_pai());
                this.manterAlunos.setDtMatricula(aluno.getData_matricula());
                this.manterAlunos.getCbxCurso().setSelectedItem(aluno.getCurso().getDescricao());
                this.manterAlunos.getCbxCidadeNasc().setSelectedItem(aluno.getCidade_nascimento().getNome());
                this.manterAlunos.getCbxCidadeAtual().setSelectedItem(aluno.getEndereco().getCidade().getNome());
                consultou = true;
            } catch (Exception erro) {
                JOptionPane.showMessageDialog(null, erro.getMessage());
            }
        } else if (e.getSource() == this.manterAlunos.getBtnExcluir()) {

            String ra = manterAlunos.getRA().getText();

            if (consultou) {
                NAlunos na = new NAlunos();

                int option = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir o Aluno atual? ", "Exclusão de Aluno", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (option == 0) {

                    try {
                        na.excluiAluno(ra);
                        JOptionPane.showMessageDialog(null, "Aluno " + manterAlunos.getRA().getText() + " foi excluido", "", JOptionPane.OK_OPTION);
                        limparCampos();
                        this.manterAlunos.getTxtRA().requestFocus();
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Aviso", JOptionPane.WARNING_MESSAGE);
                    }
                }

            } else {
                JOptionPane.showMessageDialog(manterAlunos, "Não foi possivel excluir Aluno, Você deve consultar sua RA primeiro!", "Aviso", JOptionPane.WARNING_MESSAGE);
            }

        } else if (e.getSource() == this.manterAlunos.getBtnSair()) {
            windowClosing(null);

        } else if (e.getSource() == this.manterAlunos.getBtnAddCurso()) {

            String nomeCurso = JOptionPane.showInputDialog(null, "Digite o nome do curso: ", "Cadastro Curso", JOptionPane.OK_CANCEL_OPTION);
            if (nomeCurso != null) {
                try {
                    ECursos c = new ECursos();
                    c.setDescricao(nomeCurso);
                    NCursos cursoDAO = new NCursos();
                    cursoDAO.addCurso(c);
                    listarCursos();
                    JOptionPane.showMessageDialog(null, "Curso adicionado com sucesso!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }

        } else if (e.getSource() == this.manterAlunos.getMiCidades()) {
            try {
                String cidadeUf = JOptionPane.showInputDialog(null, "Digite a Sigla da UF e depois o Nome da cidade", "Cadastro Cidade", JOptionPane.OK_CANCEL_OPTION);
                if (cidadeUf != null) {
                    String ufDigitada = cidadeUf.trim().substring(0, 2);
                    Estados estado = validaUF(ufDigitada);
                    if (estado != null) {
                        String cidadeNome = cidadeUf.substring(2, cidadeUf.length()).trim();
                        ECidades cidade = new ECidades();
                        cidade.setNome(cidadeNome);
                        cidade.setUF(estado.name());

                        NCidades cidadeDao = new NCidades();
                        cidadeDao.addCidade(cidade);
                        JOptionPane.showMessageDialog(null, "Cidade " + cidadeNome + " adicionado com sucesso!");
                    } else {
                        JOptionPane.showMessageDialog(null, "UF digitada não existe ou não esta cadastrada!");
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }
        } else if (e.getSource() == this.manterAlunos.getMiUf()) {
            try {
                String nomeUf = JOptionPane.showInputDialog(null, "Digite a Sigla da UF", "Cadastro UF", JOptionPane.OK_CANCEL_OPTION);
                if (nomeUf != null) {
                    boolean ufExiste = false;
                    nomeUf = nomeUf.trim();
                    EUnidadeFederativa uf = new EUnidadeFederativa();

                    Estados estado = validaUF(nomeUf);
                    if (estado != null) {
                        ufExiste = true;
                        uf.setUf_sigla(estado.name());
                        uf.setUf_descricao(estado.toString());
                    }

                    if (ufExiste) {
                        NUnidadeFederativa ufDao = new NUnidadeFederativa();
                        ufDao.addUF(uf);
                        getCidadesufs();
                        listarCidadesUfsNasc();
                        JOptionPane.showMessageDialog(null, "UF " + estado.name() + " " + estado.getEstado() + " adicionado com sucesso!");
                    } else {
                        JOptionPane.showMessageDialog(null, "UF digitada não existe!");
                    }
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }

        }
    }

    private Estados validaUF(String uf) {
        for (Estados estado : Estados.values()) {
            if (uf.toUpperCase().equals(estado.name())) {
                return estado;
            }
        }
        return null;
    }

    /**
     * @return true caso todos os campos obrigatorios forem preenchidos.
     */
    private boolean CamposObrigatorioPreenchido() {
        return !("".equals(this.manterAlunos.getTxtRA().getText().trim())
                || "".equals(this.manterAlunos.getTxtNomeAluno().getText().trim())
                || "".equals(this.manterAlunos.getTxtDataNasc().getText().trim())
                || "".equals(this.manterAlunos.getTxtDataMatricula().getText().trim())
                || "".equals(this.manterAlunos.getTxtNomeMae().getText().trim()));
    }

    private void limparCampos() {
        this.manterAlunos.getTxtRA().setText("");
        this.manterAlunos.getTxtNomeAluno().setText("");
        this.manterAlunos.getTxtDataNasc().setText("");
        this.manterAlunos.getTxtRua().setText("");
        this.manterAlunos.getTxtSetor().setText("");
        this.manterAlunos.getTxtCEP().setText("");
        this.manterAlunos.getTxtNomePai().setText("");
        this.manterAlunos.getTxtNomeMae().setText("");
        this.manterAlunos.getTxtDataMatricula().setText("");
    }

    private void listarCursos() {
        this.manterAlunos.getCbxCurso().removeAllItems();
        NCursos cursoDao = new NCursos();
        for (ECursos curso : cursoDao.listar()) {
            this.manterAlunos.getCbxCurso().addItem(curso.getDescricao());
        }
    }

    private void getCidadesufs() {
        NCidades cidadesDao = new NCidades();
        cidades = cidadesDao.listarCidades();
        ufs = cidadesDao.listarUF();
    }

    private void listarCidadesUfs() {
        this.manterAlunos.getCbxCidadeAtual().removeAllItems();
        this.manterAlunos.getCbxUF().removeAllItems();

        cidades.stream().forEach((cidade) -> {
            this.manterAlunos.getCbxCidadeAtual().addItem(cidade.getNome());
        });

        ufs.stream().forEach((uf) -> {
            this.manterAlunos.getCbxUF().addItem(uf.getUf_sigla());
        });
    }

    private void listarCidadesUfsNasc() {
        this.manterAlunos.getCbxCidadeNasc().removeAllItems();
        this.manterAlunos.getCbxUFNasc().removeAllItems();

        cidades.stream().forEach((cidade) -> {
            this.manterAlunos.getCbxCidadeNasc().addItem(cidade.getNome());
        });

        ufs.stream().forEach((uf) -> {
            this.manterAlunos.getCbxUFNasc().addItem(uf.getUf_sigla());
        });
    }

    @Override
    public void windowOpened(WindowEvent e) {
        listarCursos();
        getCidadesufs();
        listarCidadesUfsNasc();
    }

    @Override
    public void windowClosing(WindowEvent e) {
        System.exit(0); // encerra a execução da classe
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        if (ke.getSource() == this.manterAlunos.getTxtRA()) {
            this.manterAlunos.getTxtRA().setText(this.manterAlunos.getTxtRA().getText().toUpperCase());
        }
    }

    @Override
    public void focusGained(FocusEvent fe) {

    }

    @Override
    public void focusLost(FocusEvent fe) {
        //Verificação no Campo RA
        if (fe.getSource() == this.manterAlunos.getTxtRA()) {
            if (this.manterAlunos.getTxtRA().getText().length() < 7) {
                JOptionPane.showMessageDialog(null, "Campo RA deve conter 7 digitos!");
                this.manterAlunos.getTxtRA().requestFocus();
            }
            //Verificação nos campos De endereço 
        } else if (fe.getSource() == this.manterAlunos.getTxtRua() || fe.getSource() == this.manterAlunos.getTxtSetor()
                || fe.getSource() == this.manterAlunos.getTxtCEP()) {

            JTextField campo = (JTextField) fe.getSource();
            String texto = campo.getText().replace(".", "").replace("-", "");

            if (!"".equals(texto.trim())) {
                enderecoObrigatorio = true;
                listarCidadesUfs();
            }
        }
    }

    private void setLimiteCampo() {
        this.manterAlunos.getTxtRA().setDocument(new DocumentoLimitado(7));
        this.manterAlunos.getTxtNomeAluno().setDocument(new DocumentoLimitado(60));
        this.manterAlunos.getTxtRua().setDocument(new DocumentoLimitado(40));
        this.manterAlunos.getTxtSetor().setDocument(new DocumentoLimitado(40));
        this.manterAlunos.getTxtNomePai().setDocument(new DocumentoLimitado(60));
        this.manterAlunos.getTxtNomeMae().setDocument(new DocumentoLimitado(60));
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }

    @Override
    public void keyTyped(KeyEvent ke) {

    }

    @Override
    public void keyPressed(KeyEvent ke) {

    }
}
