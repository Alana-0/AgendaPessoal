package com.example.minhaagenda;

import android.util.Log;

import java.util.Calendar;

public class DataJava {
    Calendar c = Calendar.getInstance();

    int dia = c.get(Calendar.DAY_OF_MONTH);
    int mes = c.get(Calendar.MONTH);
    int ano = c.get(Calendar.YEAR);

    int hora = c.get(Calendar.HOUR_OF_DAY);

    int minutos = c.get(Calendar.MINUTE);

    public int getDia(){return dia;};
    public int getMes(){return mes;};
    public int getAno(){return ano;};

    public int getHora(){return hora;};

    public int getMinutos(){return minutos;};
}
