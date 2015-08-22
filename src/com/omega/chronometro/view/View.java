package com.omega.chronometro.view;

import com.omega.chronometro.contador.Contador;
import com.omega.chronometro.utils.Constantes;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

import javax.swing.*;
import java.awt.event.*;

public class View extends JFrame {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonParar;
    private JLabel lblTempo;
    private JTextArea textArea1;
    private JPopupMenu popupMenu;

    public View() {
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
    }

    private void initPopup(){
        popupMenu = new JPopupMenu();
        JMenuItem mnuConfig = new JMenuItem("Configurações");
        JMenuItem mnuTimerMaximo = new JMenuItem("Tempo Máximo");
        mnuTimerMaximo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String valorMaximo = JOptionPane.showInputDialog(null, "Informe o tempo máximo 0 = Infinito", "Tempo Maximo do Timer", JOptionPane.PLAIN_MESSAGE);
                Constantes.contadorPrinc.setMax(Integer.parseInt(valorMaximo));
            }
        });
        popupMenu.add(mnuConfig);
        popupMenu.add(mnuTimerMaximo);
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
                Constantes.contadorSecund = new Contador(new JLabel("secundario"));
            }
            String motivo = JOptionPane.showInputDialog(null, "informe o motivo da pausa", "Posso saber porque pausar?", JOptionPane.QUESTION_MESSAGE);
            Constantes.log.append("\n"+Constantes.contadorPrinc.getTime() +" - pausa - "+ motivo);
            Constantes.contadorSecund.setStarted(true);
        } else {

            Constantes.log.append("\n"+Constantes.contadorSecund.getTime()+" - Tempo de pausa");
            Constantes.contadorSecund.setStarted(false);
            Constantes.contadorSecund.setZerado(true);
            Constantes.contadorPrinc.setStarted(true);
            buttonOK.setText("Pausar");
        }

    }

    private void onParar() {
        Constantes.contadorPrinc.setStarted(false);
        Constantes.contadorPrinc.setZerado(true);
        lblTempo.setText("00:00:00");
        buttonOK.setText("Iniciar");
    }

    public static void main(String[] args) {
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e1) {
            System.out.print("não foi possivel setar a interface windows");
        }

        View dialog = new View();
        dialog.pack();
        dialog.setVisible(true);

    }
}
