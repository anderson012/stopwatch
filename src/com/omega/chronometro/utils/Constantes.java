package com.omega.chronometro.utils;

import com.omega.chronometro.contador.Contador;

import javax.swing.*;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Anderson on 20/08/2015.
 */
public class Constantes {

    public static Contador contadorPrinc;
    public static Contador contadorSecund;
    public static JTextArea log = null;
    public static SimpleDateFormat format;

    public static String getAtualTime(String formato) {
        format = new SimpleDateFormat(formato);
        return format.format(new Date());
    }

    public static String getAtualTime() {
        return getAtualTime("HH:mm");
    }

    public static boolean saveLog () {
        return saveLog(true, true);
    }

    public static boolean saveLog(boolean start, boolean end) {
        if (Constantes.log.getText().trim().isEmpty()) {
            return false;
        }

        Date dataAtual = new Date();
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd-MM-yyyy");

        File logFile = new File("./log-" + format.format(dataAtual) + ".log");
        if (!logFile.exists()) {
            System.out.println("Arquivo nao encontrado, criando...");
            boolean fileCreated = false;
            try {
                fileCreated = logFile.createNewFile();
                if (!fileCreated) {
                    throw new Exception("Arquivo não foi criado!");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Não foi possivel criar o arquivo de log!");
                return false;
            }
        } else {
            System.out.println("Arquivo encontrado");
        }

        try {
            FileWriter writer = new FileWriter(logFile, true);
            format.applyPattern("dd/MM/yyyy HH:mm:ss");
            if (start) {
                writer.write(String.format("\n===== %s =====\n", format.format(dataAtual)));
            }

            writer.write(Constantes.log.getText());

            if (end) {
                writer.write("\n===== ===== ======");
            }

            writer.close();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Falha ao gravar o conteudo do log.");
            return false;
        }

        return true;
    }
}
