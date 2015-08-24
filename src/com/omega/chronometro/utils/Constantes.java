package com.omega.chronometro.utils;

import com.omega.chronometro.contador.Contador;

import javax.swing.*;
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

}
