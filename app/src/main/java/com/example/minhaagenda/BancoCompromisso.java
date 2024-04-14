package com.example.minhaagenda;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.TextView;
import java.util.UUID;

import androidx.annotation.Nullable;

import java.util.UUID;

public class BancoCompromisso extends SQLiteOpenHelper {

    public BancoCompromisso(@Nullable Context context) {
        super(context, "bancoCompromisso", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql_bancoCompromisso = "CREATE TABLE bancoCompromisso(" +
                "DATA TEXT," +
                "HORA TEXT," +
                "DESCRICAO TEXT)";

        db.execSQL(sql_bancoCompromisso);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        String sql_bancoCompromisso_upgrade = "DROP TABLE IF EXISTS bancoCompromisso;";

        db.execSQL(sql_bancoCompromisso_upgrade);

        onCreate(db);
    }

    public Boolean insereResposta(String data, String hora, String descricao){

        SQLiteDatabase db = getWritableDatabase();
        ContentValues valuesResposta = new ContentValues();

        Log.d("Respostas", data + "\n" + hora + "\n" + descricao);

        valuesResposta.put("DATA", data);
        valuesResposta.put("HORA", hora);
        valuesResposta.put("DESCRICAO", descricao);

        try {
            db.insertOrThrow("bancoCompromisso", null, valuesResposta);
        } catch(SQLException e) {
            db.close();
            return false;
        }

        return true;
    }

    public void buscaResposta(String compara, TextView exibe, TextView eventos) {
        SQLiteDatabase db = getReadableDatabase();

        String sql_resp = "SELECT * FROM bancoCompromisso WHERE DATA = '" + compara + "' ORDER BY HORA ASC";

        Cursor cursor = db.rawQuery(sql_resp, null);

        exibe.setText("");

        if(cursor.getCount() == 0){
            exibe.append("\n" + "Não há Compromisso(s)" + "\n");
        }

        eventos.setText(String.format("Você tem %d compromisso(s)", cursor.getCount()));

        while (cursor.moveToNext()) {
            String hora = cursor.getString(cursor.getColumnIndexOrThrow("HORA"));
            String data = cursor.getString(cursor.getColumnIndexOrThrow("DATA"));
            String descricao = cursor.getString(cursor.getColumnIndexOrThrow("DESCRICAO"));

            exibe.append("\n" + hora + " -> " + descricao + "\n");
        }

        db.close();

        cursor.close();
    }

    public void removeBancoAgenda() {
        SQLiteDatabase db = getWritableDatabase();
        try {
            db.delete("bancoCompromisso", null, null);
        } catch (SQLException e) {
            Log.e("BancoResp", "Erro ao remover dados da tabela bancoResp: " + e.getMessage());
        } finally {
            db.close();
        }
    }
}

