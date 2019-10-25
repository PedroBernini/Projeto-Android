package b194832_p204575.ft.unicamp.br.atividade01_fragmentos.database;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import b194832_p204575.ft.unicamp.br.atividade01_fragmentos.database.DatabaseNameHelper;
import b194832_p204575.ft.unicamp.br.atividade01_fragmentos.R;


public class DatabaseFragment extends Fragment {

    private DatabaseNameHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;

    private EditText edtId;
    private EditText edtTexto;
    private TextView txtOutput;

    private View view;

    public DatabaseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (view == null) {
            view = inflater.inflate(R.layout.fragment_database, container, false);
        }

        edtId = view.findViewById(R.id.edtId);
        edtTexto = view.findViewById(R.id.edtTexto);
        txtOutput = view.findViewById(R.id.txtOutput);

        /*
        Configurando os Listeners
         */
        view.findViewById(R.id.btnInserir).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onInserir();
                    }
                }
        );

        view.findViewById(R.id.btnRemover).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onRemover();
                    }
                }
        );

        view.findViewById(R.id.btnSelecionar).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onSelecionar();
                    }
                }
        );

        view.findViewById(R.id.btnAtualizar).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onAtualizar();
                    }
                }
        );

        view.findViewById(R.id.btnATask1).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onInserirbtnATask1();
                    }
                }
        );

        view.findViewById(R.id.btnATask2).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onInserirbtnATask2();
                    }
                }
        );

        view.findViewById(R.id.btnATask3).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        onInserirbtnATask3(
                                sqLiteDatabase,
                                edtId.getText().toString(),
                                edtTexto.getText().toString()
                        );
                    }
                }
        );

        return view;
    }

    public void onStart() {
        super.onStart();
        dbHelper = new DatabaseNameHelper(getActivity());
        sqLiteDatabase = dbHelper.getReadableDatabase();
    }

    public void onStop() {
        super.onStop();
        sqLiteDatabase.close();
        dbHelper.close();
    }

    public void onInserir() {
        int id = Integer.parseInt(edtId.getText().toString());
        String texto = edtTexto.getText().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put("_id", id);
        contentValues.put("texto", texto);

        sqLiteDatabase.insert("tabela", null, contentValues);
    }

    public void onAtualizar() {
        int id = Integer.parseInt(edtId.getText().toString());
        String texto = edtTexto.getText().toString();

        ContentValues contentValues = new ContentValues();
        contentValues.put("texto", texto);

        String whereClause = "_id = ?";
        String[] whereArgs = new String[]{Integer.toString(id)};

        sqLiteDatabase.update("tabela", contentValues, whereClause, whereArgs);
    }

    public void onRemover() {
        int id = Integer.parseInt(edtId.getText().toString());

        String whereClause = "_id = ?";
        String[] whereArgs = new String[]{Integer.toString(id)};

        sqLiteDatabase.delete("tabela", whereClause, whereArgs);
    }

    public void onSelecionar() {
        String sql = "Select * from tabela";

        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            String str = "";
            do {
                int id = cursor.getInt(0);
                String texto = cursor.getString(1);

                str = str + id + "," + texto + "\n";

            } while (cursor.moveToNext());
            txtOutput.setText(str);
        }
        cursor.close();

    }


    public void onInserirbtnATask1() {
        new InsertAsyncTask().execute();
    }

    public void onInserirbtnATask2() {
        new InsertAsyncTaskV2().execute(
                edtId.getText().toString(),
                edtTexto.getText().toString()
        );
    }

    public static void onInserirbtnATask3(final SQLiteDatabase sqLiteDatabase, String id, String text) {

        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... strings) {
                String id       = (String)strings[0];
                String texto = (String)strings[1];

                ContentValues contentValues = new ContentValues();
                contentValues.put("_id", id);
                contentValues.put("texto", texto);

                sqLiteDatabase.insert("tabela", null, contentValues);
                return null;
            }
        }.execute(id, text);
    }

    class InsertAsyncTask extends AsyncTask<Void, Void, Void> {

        private ContentValues contentValues;
        private int passo;

        @Override
        protected void onPreExecute(){
            int id       = Integer.parseInt(edtId.getText().toString());
            String texto = edtTexto.getText().toString();

            contentValues = new ContentValues();
            contentValues.put("_id", id);
            contentValues.put("texto", texto);
        }

        @Override
        protected Void doInBackground(Void... voids) {
            passo = 1;
            publishProgress();

            sqLiteDatabase.insert("tabela", null, contentValues);

            passo = 2;
            publishProgress();

            return null;
        }

        @Override
        protected  void onProgressUpdate(Void... voids){
            Toast.makeText(getActivity(), "Passo = "+passo, Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Void voidObject){
            txtOutput.setText("Finished");
        }
    }


    class InsertAsyncTaskV2 extends AsyncTask<String, Integer, Boolean> {
        @Override
        protected void onPreExecute(){

        }

        @Override
        protected Boolean doInBackground(String... strings) {

            publishProgress(1);

            int id       = Integer.parseInt(strings[0]);
            String texto = strings[1];
            ContentValues contentValues = new ContentValues();
            contentValues.put("_id", id);
            contentValues.put("texto", texto);

            publishProgress(2);

            try {
                sqLiteDatabase.insertOrThrow("tabela", null, contentValues);
                publishProgress(3);
                return true;
            } catch (SQLException exception){
                return false;
            }
        }

        @Override
        protected  void onProgressUpdate(Integer... progress){
            Toast.makeText(getContext(), "Passo = "+progress[0], Toast.LENGTH_SHORT).show();
        }


        @Override
        protected void onPostExecute(Boolean result){
            if (result) {
                txtOutput.setText("Finished");
            } else {
                txtOutput.setText("Error Occurred");
            }
        }
    }
}
