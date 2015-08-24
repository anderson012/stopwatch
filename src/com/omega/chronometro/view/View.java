package com.omega.chronometro.view;

import com.omega.chronometro.contador.Contador;
import com.omega.chronometro.utils.Constantes;

import javax.swing.*;
import java.awt.event.*;

public class View extends JFrame {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonParar;
    private JLabel lblTempo;
    private JTextArea textArea1;
    private JLabel lblTmpPausa;
    private JScrollPane scrollpaneLog;
    private JPopupMenu popupMenu;
    public static View instance = null;

    public View() {
        super("StopWatch 1.0");
        setContentPane(contentPane);
        setAlwaysOnTop(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onStartPause();
            }
        });

        buttonParar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onParar();
            }
        });

        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });

        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);


        initPopup();
        Constantes.log = textArea1;
        contentPane.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (MouseEvent.BUTTON3 == e.getButton()) {
                    popupMenu.show(contentPane, e.getX(), e.getY());
                }
            }
        });
        textArea1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (MouseEvent.BUTTON3 == e.getButton()) {
                    popupMenu.show(contentPane, e.getX(), e.getY());
                }
            }
        });
    }

    private void initPopup(){
        popupMenu = new JPopupMenu();
        JMenuItem mnuConfig = new JMenuItem("Configurações");
        JMenuItem mnuTimerMaximo = new JMenuItem("Tempo Máximo");
        final JMenuItem  mnuHide = new JMenuItem("hide Log");
        mnuTimerMaximo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String valorMaximo = JOptionPane.showInputDialog(null, "Informe o tempo máximo 0 = Infinito", "Tempo Maximo do Timer", JOptionPane.PLAIN_MESSAGE);
                Constantes.contadorPrinc.setMax(Integer.parseInt(valorMaximo));
            }
        });

        mnuHide.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (scrollpaneLog.isVisible()) {
                    scrollpaneLog.setVisible(false);
                    getInstance().pack();
                    mnuHide.setText("show Log");
                } else {
                    scrollpaneLog.setVisible(true);
                    getInstance().pack();
                    mnuHide.setText("hide Log");
                }
            }
        });
        popupMenu.add(mnuConfig);
        popupMenu.add(mnuTimerMaximo);
        popupMenu.add(mnuHide);
    }

    private void onStartPause() {
        if (Constantes.contadorPrinc == null) {
            Constantes.contadorPrinc = new Contador(lblTempo);
            Constantes.contadorPrinc.start();
        }
        if (Constantes.contadorPrinc.isStarted()) {
            Constantes.contadorPrinc.setStarted(false);
            buttonOK.setText("Iniciar");
            if(Constantes.contadorSecund == null){
                Constantes.contadorSecund = new Contador(lblTmpPausa);
                Constantes.contadorSecund.start();
            }
            String motivo = JOptionPane.showInputDialog(null, "Informe o motivo da pausa", "Posso saber porque pausar?", JOptionPane.QUESTION_MESSAGE);
            Constantes.log.append("\n"+Constantes.getAtualTime() + " - "+Constantes.contadorPrinc.getTime() +" - pausa "+ motivo);
            Constantes.contadorSecund.setStarted(true);
        } else {
            if(Constantes.contadorSecund != null && Constantes.contadorSecund.isStarted()){
                Constantes.log.append("\n"+Constantes.getAtualTime() + " - Retomado após - "+Constantes.contadorSecund.getTime());
                Constantes.contadorSecund.setStarted(false);
                Constantes.contadorSecund.setZerado(true);
            }

            Constantes.contadorPrinc.setStarted(true);
            lblTmpPausa.setText("00:00:00");
            buttonOK.setText("Pausar");
            buttonParar.setEnabled(true);
        }

    }

    private void onParar() {
        if(Constantes.contadorPrinc != null){
            Constantes.contadorPrinc.setStarted(false);
            Constantes.contadorPrinc.setZerado(true);
        }

        if(Constantes.contadorSecund != null){
            Constantes.contadorSecund.setStarted(false);
            Constantes.contadorSecund.setZerado(true);
        }

        if(!buttonParar.getText().equals("Parar")){
            lblTempo.setText("00:00:00");
            lblTmpPausa.setText("00:00:00");
            buttonParar.setText("Parar");
            buttonParar.setEnabled(false);
            Integer resp = JOptionPane.showConfirmDialog(null, "Deseja limpar o Log?");
            if(resp == 0 && Constantes.log != null){
                Constantes.log.setText("");
            }
        }else{
            buttonParar.setText("Zerar");
        }

        buttonOK.setText("Iniciar");
    }

    public static View getInstance(){
        if(instance == null){
            instance = new View();
        }
        return instance;
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e1) {
            System.out.print("não foi possivel setar a interface windows");
        }

        getInstance().pack();
        getInstance().setVisible(true);

    }
}
