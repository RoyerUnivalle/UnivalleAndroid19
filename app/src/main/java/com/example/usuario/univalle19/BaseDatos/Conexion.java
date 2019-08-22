package com.example.usuario.univalle19.BaseDatos;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Usuario on 22/08/2019.
 */

public class Conexion extends SQLiteOpenHelper{

    public String  query = "CREATE TABLE usuarios " +
            "(id INTEGER PRIMARY KEY AUTOINCREMENT, " +
            "old_passwd TEXT, new_passwd TEXT)";

    public Conexion(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
