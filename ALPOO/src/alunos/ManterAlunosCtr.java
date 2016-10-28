package alunos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import javax.swing.JOptionPane;

public class ManterAlunosCtr implements WindowListener, ActionListener, KeyListener {

    private final ManterAlunos manterAlunos;

    public ManterAlunosCtr(ManterAlunos manterAlunos) {
        this.manterAlunos = manterAlunos;
        //coloca a janela no centro da tela
        this.manterAlunos.setLocationRelativeTo(null);
        setLimiteCampo();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.manterAlunos.getBtnIncluir()) {
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

                endereco.setRua(this.manterAlunos.getTxtRua().getText());
                endereco.setSetor(this.manterAlunos.getTxtSetor().getText());
                endereco.setCep(this.manterAlunos.getTxtCEP().getText().replace(".", "").replace("-", ""));
                endereco.setUF(this.manterAlunos.getCbxUF().getSelectedItem().toString());
                cidadeResidencia.setId(cidadeDao.getIdByNome(this.manterAlunos.getCbxCidadeAtual().getSelectedItem().toString()));
                cidadeResidencia.setNome(this.manterAlunos.getCbxCidadeAtual().getSelectedItem().toString());
                cidadeResidencia.setUF(this.manterAlunos.getCbxUF().getSelectedItem().toString());
                endereco.setCidade(cidadeResidencia);
                aluno.setEndereco(endereco);

                aluno.setNome_pai(this.manterAlunos.getTxtNomePai().getText());
                aluno.setNome_mae(this.manterAlunos.getTxtNomeMae().getText());
                aluno.setData_matricula(f.parse(this.manterAlunos.getTxtDataMatricula().getText()));

                curso.setId(cursoDao.getIdByNome(this.manterAlunos.getCbxCurso().getSelectedItem().toString()));
                curso.setDescricao(this.manterAlunos.getCbxCurso().getSelectedItem().toString());
                aluno.setCurso(curso);
                alunoDao.adicionarAluno(aluno);
                JOptionPane.showMessageDialog(null, "Aluno "+ aluno.getNome() + " adicionado com sucesso!");
                limparCampos();
            } catch (ParseException ex) {
                JOptionPane.showMessageDialog(null, ex.getMessage());
            }

        } else if (e.getSource() == this.manterAlunos.getBtnAlterar()) {

        } else if (e.getSource() == this.manterAlunos.getBtnConsultar()) {

            NAlunos na = new NAlunos();
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

        } else if (e.getSource() == this.manterAlunos.getBtnExcluir()) {

            String ra = manterAlunos.getRA().getText();

            if (!ra.equals("")) {
                NAlunos na = new NAlunos();

                int option = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir o Aluno atual? ", "Exclusão de Aluno", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);

                if (option == 0) {

                    try {
                        na.excluiAluno(ra);
                        JOptionPane.showMessageDialog(manterAlunos, "Aluno " + manterAlunos.getRA() + " foi excluido", "", JOptionPane.OK_OPTION);
                    } catch (Exception ex) {
                        JOptionPane.showMessageDialog(null, ex.getMessage());
                    }
                }

            } else {
                JOptionPane.showMessageDialog(manterAlunos, "Nehum aluno para excluir, favor preencher o RA", "Erro", JOptionPane.OK_OPTION);
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

        }
    }

    private void limparCampos(){
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

    private void listarCidadesUfs() {
        this.manterAlunos.getCbxCidadeAtual().removeAllItems();
        this.manterAlunos.getCbxUF().removeAllItems();
        NEndereco enderecoDao = new NEndereco();

        for (ECidades cidade : enderecoDao.listarCidades()) {
            this.manterAlunos.getCbxCidadeAtual().addItem(cidade.getNome());
            this.manterAlunos.getCbxCidadeNasc().addItem(cidade.getNome());
        }

        for (EUnidadeFederativa uf : enderecoDao.listarUF()) {

            this.manterAlunos.getCbxUF().addItem(uf.getUf_sigla());
            this.manterAlunos.getCbxUFNasc().addItem(uf.getUf_sigla());
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        listarCursos();
        listarCidadesUfs();
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

    public void setLimiteCampo(){
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
