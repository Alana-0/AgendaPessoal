package com.example.minhaagenda;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private TextView text;
    private TextView vizu;
    private TextView showTextHora;
    private TextView desc;
    private TextView setaDia;
    private TextView eventos;
    private Button buttonDate;
    private Button excluiTudo;
    private Button button2;
    private Button buttonSend;
    private Button botaoHoje;
    private Button botaoOutraData;

    int[] flag = {0,0};

    private Compromisso compromisso = new Compromisso();

    private DataJava dataPrograma = new DataJava();

    BancoCompromisso bancoCompromisso = new BancoCompromisso(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        text = findViewById(R.id.showText);
        buttonDate = findViewById(R.id.button);

        showTextHora = findViewById(R.id.showTextHora);
        button2 = findViewById(R.id.button2);

        vizu = findViewById(R.id.vizu);

        desc = findViewById(R.id.desc);

        setaDia = findViewById(R.id.setaDia);

        buttonSend = findViewById(R.id.buttonSend);

        botaoHoje = findViewById(R.id.botaoHoje);

        botaoOutraData = findViewById(R.id.botaoOutraData);

        excluiTudo = findViewById(R.id.excluiTudo);

        eventos = findViewById(R.id.eventos);

        openDialogVerificaHoje();

        buttonDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag[0] = 1;
                openDialog();
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                flag[1] = 1;
                openDialogHora();
            }
        });

        buttonSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String verificaDigitado = String.valueOf(desc.getText());
                verificaDigitado = verificaDigitado.trim();
                if(verificaDigitado.isEmpty()) {
                    showToast("Descrição Vazia");
                } else {
                    compromisso.setrDesc(verificaDigitado);
                    if(flag[0] == 1 && flag[1] == 1) {
                        desc.setText("");
                        showTextHora.setText("");
                        text.setText("");
                        Boolean verifica = bancoCompromisso.insereResposta(compromisso.getrData(), compromisso.getrHora(), compromisso.getrDesc());
                        flag[0] = 0;
                        flag[1] = 0;
                        Log.i("DataAgenda", compromisso.getrData());
                        Log.i("HoraAgenda", compromisso.getrHora());
                        Log.i("DescricaoAgenda", compromisso.getrDesc());
                        if(verifica){
                            showToast("Enviado com Sucesso");
                            openDialogVerificaHoje();
                        }
                        else {showToast("Erro ao Enviar");}
                    } else {
                        showToast("Campo(s) inválido(s)");
                    }
                }
            }
        });

        excluiTudo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vizu.setText("");
                bancoCompromisso.removeBancoAgenda();
                vizu.setText("\n" + "Não há Compromisso(s)" + "\n");
                eventos.setText("Você tem 0 compromisso(s)");
                showToast("Agenda Excluída");
                openDialogVerificaHoje();
            }
        });

        botaoHoje.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogVerificaHoje();
            }
        });

        botaoOutraData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialogVerifica();
            }
        });

    }

    private void openDialog() {
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                int mesA = month+1;
                text.setText(String.valueOf(day) + "."+ String.valueOf(mesA) + "." + String.valueOf(year));
                compromisso.setrData(String.valueOf(day)+"-"+String.valueOf(month)+"-"+String.valueOf(year));
            }
        }, dataPrograma.getAno(), dataPrograma.getMes(), dataPrograma.getDia());
        dialog.show();
    }

    private void openDialogHora() {
        TimePickerDialog dialogDois = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hora, int min) {
                String minutoTransform = String.valueOf(min);
                String horaTransform = String.valueOf(hora);
                if(minutoTransform.length() == 1){
                    minutoTransform = "0" + String.valueOf(min);
                }

                if(horaTransform.length() == 1) {
                    horaTransform = "0" + String.valueOf(hora);
                }
                showTextHora.setText(String.valueOf(horaTransform) + ":"+ String.valueOf(min));
                compromisso.setrHora(String.valueOf(horaTransform)+":"+String.valueOf(minutoTransform));
            }
        }, dataPrograma.getHora(), dataPrograma.getMinutos(), true);
        dialogDois.show();
    }

    private void openDialogVerifica() {
        DatePickerDialog dialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int day) {
                int mesA = month+1;
                setaDia.setText(String.valueOf(day) + "."+ String.valueOf(mesA) + "." + String.valueOf(year));
                String salvaData = String.valueOf(day)+"-"+String.valueOf(month)+"-"+String.valueOf(year);
                Log.d("Salva", salvaData);

                bancoCompromisso.buscaResposta(salvaData, vizu, eventos);
            }
        }, dataPrograma.getAno(), dataPrograma.getMes(), dataPrograma.getDia());
        dialog.show();
    }

    private void openDialogVerificaHoje() {
        String salvaData = String.valueOf(dataPrograma.getDia())+"-"+String.valueOf(dataPrograma.getMes())+"-"+String.valueOf(dataPrograma.getAno());
        int mesA = dataPrograma.getMes()+1;
        setaDia.setText(String.valueOf(dataPrograma.getDia()) + "."+ String.valueOf(mesA) + "." + String.valueOf(dataPrograma.getAno()));
        Log.d("Salva", salvaData);
        showToast("Agenda de Hoje!");

        bancoCompromisso.buscaResposta(salvaData, vizu, eventos);
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}