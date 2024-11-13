package com.example.minhaagenda;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class BancoCompromissoHelper extends SQLiteOpenHelper {
    public BancoCompromissoHelper(@Nullable Context context) {
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
}
