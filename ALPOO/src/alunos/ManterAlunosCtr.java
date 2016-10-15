package alunos;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class ManterAlunosCtr implements WindowListener, ActionListener {

    private ManterAlunos manterAlunos;

    public ManterAlunosCtr(ManterAlunos manterAlunos) {
        this.manterAlunos = manterAlunos;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        
//        if (e.getSource() == this.manterAlunos.getBtnIncluir()) {
//
//        } else if (e.getSource() == this.manterAlunos.getBtnAlterar()) {
//
//        } else if (e.getSource() == this.manterAlunos.getBtnConsultar()) {
//
//        } else if (e.getSource() == this.manterAlunos.getBtnExcluir()) {
//
//        } else if (e.getSource() == this.manterAlunos.getBtnSair()) {
//            windowClosing(null);
//        }
    }

    @Override
    public void windowOpened(WindowEvent e) {

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
