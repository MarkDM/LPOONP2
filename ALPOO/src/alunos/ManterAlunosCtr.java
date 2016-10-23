package alunos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JOptionPane;

public class ManterAlunosCtr implements WindowListener, ActionListener {

    private ManterAlunos manterAlunos;

    public ManterAlunosCtr(ManterAlunos manterAlunos) {
        this.manterAlunos = manterAlunos;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.manterAlunos.getBtnIncluir()) {

            NAlunos na = new NAlunos();

        } else if (e.getSource() == this.manterAlunos.getBtnAlterar()) {

        } else if (e.getSource() == this.manterAlunos.getBtnConsultar()) {

            NAlunos na = new NAlunos();
            EAluno aluno = na.getAlunoByRa(manterAlunos.getRA().getText());
            this.manterAlunos.getRA().setText(aluno.getRa());
            this.manterAlunos.getTxtNomeAluno().setText(aluno.getNome());
            this.manterAlunos.getTxtRua().setText(aluno.getRua());
            this.manterAlunos.getTxtSetor().setText(aluno.getSetor());
            this.manterAlunos.getTxtCEP().setText(aluno.getCep());
            this.manterAlunos.setDtNascimento(aluno.getData_nascimento());
            this.manterAlunos.getTxtNomeMae().setText(aluno.getNome_mae().trim());
            this.manterAlunos.getTxtNomePai().setText(aluno.getNome_pai());
            this.manterAlunos.setDtMatricula(aluno.getData_matricula());
            this.manterAlunos.getCbxCurso().setSelectedItem(aluno.getCurso().getDescricao());
            this.manterAlunos.getCbxCidadeNasc().setSelectedItem(aluno.getCidade_nascimento());
            this.manterAlunos.getCbxCidadeAtual().setSelectedItem(aluno.getCidade());

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

    private void liberaExclusao(boolean value) {

    }

    private void liberaInclusao(boolean value) {

    }

    private void liberaAlteracao(boolean value) {

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
}
