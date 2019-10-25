package b194832_p204575.ft.unicamp.br.atividade01_fragmentos.jogo2;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.text.DecimalFormat;

import b194832_p204575.ft.unicamp.br.atividade01_fragmentos.R;
import b194832_p204575.ft.unicamp.br.atividade01_fragmentos.database.DatabaseNameHelper;

/**
 * A simple {@link Fragment} subclass.
 */
public class EstatisticaFragment extends Fragment {

    private DatabaseNameHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;
    private TextView pessoaDificil;
    private TextView nomeConfuso;
    private TextView porcentagemAmostral;
    private View lview;

    public EstatisticaFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if (lview == null) {
            lview = inflater.inflate(R.layout.fragment_estatistica, container, false);
        }

        pessoaDificil = lview.findViewById(R.id.text_pessoa_dificil);
        nomeConfuso = lview.findViewById(R.id.text_nome_confuso);
        porcentagemAmostral = lview.findViewById(R.id.porcentagem_amostral);

        return lview;
    }

    public void onCulsultaPorcentagem(){

        DecimalFormat df = new DecimalFormat();
        df.setMaximumFractionDigits(2);
        String sql = "Select * from PorcentagemAmostral";
        Float acerto = 0.0f;
        Float erro = 0.0f;
        Float porcentagem = 0.0f;

        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                acerto = cursor.getFloat(1);
                erro = cursor.getFloat(2);

            } while (cursor.moveToNext());

        }
        cursor.close();

        porcentagem = (erro * 100) / acerto;
        System.out.println(("SSAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa"));

        System.out.println((erro));
        System.out.println((acerto));

        System.out.println((porcentagem));
        porcentagemAmostral.setText(String.valueOf(porcentagem));
    }

    public void onConsultaA() {

        String sql = "Select * from pessoaMenosConhecida";
        String texto = "";
        int qtd = 0;
        int temp = 0;


        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                qtd = cursor.getInt(2);
                if (qtd >= temp){
                    texto = cursor.getString(1);
                    qtd = cursor.getInt(2);
                    temp = qtd;
                }
                qtd = temp;
            } while (cursor.moveToNext());

            String textoFirstUpper = texto.substring(0, 1).toUpperCase() + texto.substring(1);
            pessoaDificil.setText(textoFirstUpper);

        }
        cursor.close();
    }

    public void onConsultaNomeConfuso() {

        String sql = "Select * from nomeMaisClicado";
        String texto = "";
        int qtd = 0;
        int temp = 0;

        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                qtd = cursor.getInt(2);
                if (qtd >= temp){
                    texto = cursor.getString(1);
                    temp = qtd;
                }
            } while (cursor.moveToNext());
            qtd = temp;
            String textoFirstUpper = texto.substring(0, 1).toUpperCase() + texto.substring(1);
            nomeConfuso.setText(textoFirstUpper);
        }
        cursor.close();
    }


    public void onStart() {
        super.onStart();
        dbHelper = new DatabaseNameHelper(this.getActivity());
        sqLiteDatabase = dbHelper.getReadableDatabase();

        onConsultaA();
        onConsultaNomeConfuso();
        onCulsultaPorcentagem();
    }

    public void onStop() {
        super.onStop();
        sqLiteDatabase.close();
        dbHelper.close();
    }

}
