package b194832_p204575.ft.unicamp.br.atividade01_fragmentos.jogo3;


import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import b194832_p204575.ft.unicamp.br.atividade01_fragmentos.R;
import b194832_p204575.ft.unicamp.br.atividade01_fragmentos.jogo3.firebase.FirebaseJogo3Alunos;
import b194832_p204575.ft.unicamp.br.atividade01_fragmentos.jogo3.firebase.FirebaseJogo3Frases;

/**
 * A simple {@link Fragment} subclass.
 */
public class FrasesAlunoFragment extends Fragment {

    private View lview;
    TextView textView;
    TextView txtResult;
    String url = "https://aulafirebase-38b8e.firebaseio.com/BD/Jogo1";
    int erro;
    int acertos;

    String fraseCorreta;

    private RadioGroup radioGroup;

    private RadioButton frase1;
    private RadioButton frase2;
    private RadioButton frase3;
    private RadioButton frase4;
    private RadioButton frase5;

    ArrayList<String> listaFrases = new ArrayList<>();

    public FrasesAlunoFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (lview == null) {
            lview = inflater.inflate(R.layout.fragment_frases_aluno, container, false);
        }

        getActivity().setTitle("Jogo 3");

        textView = lview.findViewById(R.id.txtNomeAluno);
        txtResult = lview.findViewById(R.id.resultado);

        radioGroup = lview.findViewById(R.id.radioGroup);

        frase1 = lview.findViewById(R.id.frase1);
        frase2 = lview.findViewById(R.id.frase2);
        frase3 = lview.findViewById(R.id.frase3);
        frase4 = lview.findViewById(R.id.frase4);
        frase5 = lview.findViewById(R.id.frase5);

        textView.setText("");
        txtResult.setText("");

        frase1.setText("");
        frase2.setText("");
        frase3.setText("");
        frase4.setText("");
        frase5.setText("");

        listaFrases.clear();

        radioGroup.clearCheck();

        new MyFrasesAlunoAsyncTask(this).execute();

        lview.findViewById(R.id.btnChecar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checar();
            }
        });

        return lview;
    }

    public void firebase(){

        String comando = "GET";
        String urlGet = url + "/.json";

        new FirebaseJogo3Frases(this).execute(urlGet, comando);

    }

    public void atualizaFirebase(JSONObject jsonObject){

        String comando = "PATCH";
        String nomeSemEspaco = textView.getText().toString().replaceAll(" ", "_");
        // String nomeSemEspaco = "Alfredo";

        if((jsonObject.toString().toLowerCase().contains(nomeSemEspaco.toLowerCase())) == true){
            try {
                int acertosDB = jsonObject.getJSONObject(nomeSemEspaco).getInt("Acertos");
                int errosDB = jsonObject.getJSONObject(nomeSemEspaco).getInt("Erros");

                int acertoAtt = acertosDB + acertos;
                int errosAtt = errosDB + erro;


                String jsonInsert = "{\"Acertos\": \""+acertoAtt+"\", \"Erros\": \""+errosAtt+"\"}";
                String urlInsert = url + "/"+nomeSemEspaco+"/.json";

                new FirebaseJogo3Frases(this).execute(urlInsert, comando, jsonInsert);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            String jsonInsert = "{\""+nomeSemEspaco+"\":{\"Acertos\":\""+Integer.toString(acertos)+"\",\"Erros\":\""+Integer.toString(erro)+"\"}}";
            String urlInsert = url + "/.json";
            new FirebaseJogo3Frases(this).execute(urlInsert, comando, jsonInsert);

        }
        acertos = 0;
        erro = 0;

    }

    public void proximoJogo(){

        textView.setText("");
        txtResult.setText("");

        frase1.setText("");
        frase2.setText("");
        frase3.setText("");
        frase4.setText("");
        frase5.setText("");

        listaFrases.clear();

        radioGroup.clearCheck();

        new MyFrasesAlunoAsyncTask(this).execute();
    }

    public void opcaoCorreta(){

        txtResult.setText("Correto!");
        txtResult.setTextColor(Color.parseColor("#29cf13"));

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                proximoJogo();
            }
        }, 1500);

        acertos = 1;
        firebase();

    }

    public void checar(){

        if(frase1.isChecked() && frase1.getText().equals(fraseCorreta)){ opcaoCorreta();

        }else if(frase2.isChecked() && frase2.getText().equals(fraseCorreta)){ opcaoCorreta();

        }else if(frase3.isChecked() && frase3.getText().equals(fraseCorreta)){ opcaoCorreta();

        }else if(frase4.isChecked() && frase4.getText().equals(fraseCorreta)){ opcaoCorreta();

        }else if(frase5.isChecked() && frase5.getText().equals(fraseCorreta)){ opcaoCorreta();

        }else{

            txtResult.setText("Errou!");
            txtResult.setTextColor(Color.parseColor("#ff0000"));

            final Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    txtResult.setText("");
                }
            }, 1000);

            erro = 1;
            firebase();
        }
    }

    public void atualizaLayout(JSONObject jsonObject){
        try {
            JSONArray jsonArray = jsonObject.getJSONArray("outros");

            textView.append(jsonObject.getString("nome"));

            fraseCorreta = jsonObject.getString("frase");

            listaFrases.add(jsonObject.getString("frase"));
            listaFrases.add(jsonArray.getString(0));
            listaFrases.add(jsonArray.getString(1));
            listaFrases.add(jsonArray.getString(2));
            listaFrases.add(jsonArray.getString(3));

            Collections.shuffle(listaFrases);

            frase1.setText(listaFrases.get(0));
            frase2.setText(listaFrases.get(1));
            frase3.setText(listaFrases.get(2));
            frase4.setText(listaFrases.get(3));
            frase5.setText(listaFrases.get(4));


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}
