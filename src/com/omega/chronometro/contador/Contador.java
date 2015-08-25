package com.omega.chronometro.contador;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Anderson on 20/08/2015.
 */
public class Contador extends Thread {
    private JLabel hr;
    private boolean started = false;
    private boolean zerado = false;
    private Integer max = 0;
    private String time;

    public Contador(JLabel hora) {
        this.hr = hora;
    }

    @Override
    public void run() {
        try {
            int segundo = 0;
            int hora = 0;
            int minuto = 0;
            while (true) {
                if (isStarted()) {
                    this.hr.setForeground(new Color(0, 0, 0));
                    if (isZerado()) {
                        segundo = 0;
                        hora = 0;
                        minuto = 0;
                        setZerado(false);
                    }

                    if (segundo == 59) {
                        segundo = 00;
                        minuto = minuto + 1;
                    }

                    if (minuto == 59) {
                        minuto = 00;
                        hora = hora + 1;
                    }
                    segundo++;
                    String timer = completaComZero(hora) + ":" +
                            completaComZero(minuto) + ":" +
                            completaComZero(segundo);
                    this.time = timer;
                    this.hr.setText(timer);
                    this.hr.revalidate();
                } else {
                    if (!isZerado()) {
                        if(!this.hr.getForeground().equals(new Color(211, 48, 54))){
                            this.hr.setForeground(new Color(211, 48, 54));
                        }else{
                            this.hr.setForeground(new Color(0, 0, 0));
                        }

                    }
                }
                Thread.sleep(1000);
            }
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }

    public boolean isZerado() {
        return zerado;
    }

    public void setZerado(boolean zerado) {
        this.zerado = zerado;
    }

    public boolean isStarted() {
        return started;
    }

    public void setStarted(boolean started) {
        this.started = started;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    private String completaComZero(Integer i) {
        String retorno = null;
        if (i < 10) {
            retorno = "0" + i;
        } else {
            retorno = i.toString();
        }
        return retorno;
    }
}
