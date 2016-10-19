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

        } else if (e.getSource() == this.manterAlunos.getBtnAlterar()) {

        } else if (e.getSource() == this.manterAlunos.getBtnConstultar()) {

            NAlunos na = new NAlunos();
            EAluno aluno = na.getAlunoByRa(manterAlunos.getRA());
            this.manterAlunos.setRA(aluno.getRa());
            this.manterAlunos.setNome(aluno.getNome());
            this.manterAlunos.setRua(aluno.getEndereco().getRua());
            this.manterAlunos.setSetor(aluno.getEndereco().getSetor());
            this.manterAlunos.setCep(aluno.getEndereco().getCep());
            this.manterAlunos.setDtNascimento(aluno.getData_nascimento());
            this.manterAlunos.setNomeMae(aluno.getNome_mae());
            this.manterAlunos.setNomePai(aluno.getNome_pai());
            this.manterAlunos.setDtMatricula(aluno.getData_matricula());

        } else if (e.getSource() == this.manterAlunos.getBtnExcluir()) {

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
                    JOptionPane.showMessageDialog(null, "Curso adicionado com sucesso!");
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(null, ex.getMessage());
                }
            }
        }
    }

    @Override
    public void windowOpened(WindowEvent e) {
        NCursos cursoDao = new NCursos();
        for (ECursos curso : cursoDao.listar()) {
            this.manterAlunos.getCbxCurso().addItem(curso.getDescricao());
        }
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
