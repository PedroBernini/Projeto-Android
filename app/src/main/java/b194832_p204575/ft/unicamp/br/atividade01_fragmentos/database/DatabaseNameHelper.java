package b194832_p204575.ft.unicamp.br.atividade01_fragmentos.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by ulisses on 3/30/16.
 */
public class DatabaseNameHelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "BANCOJOGO2";
    private static final int DB_VERSION = 1;

    public DatabaseNameHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL("CREATE TABLE pessoaMenosConhecida (_id INTEGER PRIMARY KEY AUTOINCREMENT, nome Text, qtd INTEGER);");

        db.execSQL("CREATE TABLE nomeMaisClicado (_id INTEGER PRIMARY KEY AUTOINCREMENT, nome Text, qtd INTEGER);");

        db.execSQL("CREATE TABLE PorcentagemAmostral (_id INTEGER PRIMARY KEY AUTOINCREMENT,  qtdJogadas REAL, qtdErros REAL);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
/*
        if (oldVersion < 2){
            db.execSQL("ALTER TABLE tabela " +
                    "ADD COLUMN texto;");
        }*/
    }

}

