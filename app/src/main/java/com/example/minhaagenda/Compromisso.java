package com.example.minhaagenda;

public class Compromisso {
    private String rData;

    private String rHora;

    private String rDesc;

    public void setrData(String data){rData = data;};
    public void setrHora(String hora){rHora = hora;};
    public void setrDesc(String desc){rDesc = desc;};
    public String getrData(){return rData;};
    public String getrHora(){return rHora;};
    public String getrDesc(){return rDesc;};
}
