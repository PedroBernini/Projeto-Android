package b194832_p204575.ft.unicamp.br.atividade01_fragmentos.jogo3;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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

public class AlunosFraseFragment extends Fragment {

    private View lview;
    TextView textView;
    TextView txtResult;
    String nomeCorreto;
    String url = "https://aulafirebase-38b8e.firebaseio.com/BD/Jogo3";
    private Button btnNovoJogo;
    int erro;
    int acertos;

    private RadioGroup radioGroup;

    private RadioButton nome1;
    private RadioButton nome2;
    private RadioButton nome3;
    private RadioButton nome4;
    private RadioButton nome5;

    ArrayList<String> listaNomes = new ArrayList<>();


    public AlunosFraseFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (lview == null) {
            lview = inflater.inflate(R.layout.fragment_alunosfrase, container, false);
        }

        getActivity().setTitle("Jogo 3");

        textView = lview.findViewById(R.id.txtFrase);
        txtResult = lview.findViewById(R.id.resultado);

        btnNovoJogo = (Button) lview.findViewById(R.id.btnNovoGame);

        btnNovoJogo.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        proximoJogo();
                    }
                }
        );
        radioGroup = lview.findViewById(R.id.radioGroup);

        nome1 = lview.findViewById(R.id.nome1);
        nome2 = lview.findViewById(R.id.nome2);
        nome3 = lview.findViewById(R.id.nome3);
        nome4 = lview.findViewById(R.id.nome4);
        nome5 = lview.findViewById(R.id.nome5);

        textView.setText("");
        txtResult.setText("");

        nome1.setText("");
        nome2.setText("");
        nome3.setText("");
        nome4.setText("");
        nome5.setText("");

        listaNomes.clear();

        radioGroup.clearCheck();

        new MyAlunosFraseAsyncTask(this).execute();

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

        new FirebaseJogo3Alunos(this).execute(urlGet, comando);

    }

    public void atualizaFirebase(JSONObject jsonObject){

        String comando = "PATCH";
        String nomeSemEspaco = nomeCorreto.replaceAll(" ", "_");
       // String nomeSemEspaco = "Alfredo";

        if((jsonObject.toString().toLowerCase().contains(nomeSemEspaco.toLowerCase())) == true){
            try {
                int acertosDB = jsonObject.getJSONObject(nomeSemEspaco).getInt("Acertos");
                int errosDB = jsonObject.getJSONObject(nomeSemEspaco).getInt("Erros");

                int acertoAtt = acertosDB + acertos;
                int errosAtt = errosDB + erro;


                String jsonInsert = "{\"Acertos\": \""+acertoAtt+"\", \"Erros\": \""+errosAtt+"\"}";
                String urlInsert = url + "/"+nomeSemEspaco+"/.json";

                new FirebaseJogo3Alunos(this).execute(urlInsert, comando, jsonInsert);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }else{
            String jsonInsert = "{\""+nomeSemEspaco+"\":{\"Acertos\":\""+Integer.toString(acertos)+"\",\"Erros\":\""+Integer.toString(erro)+"\"}}";
            String urlInsert = url + "/.json";
            new FirebaseJogo3Alunos(this).execute(urlInsert, comando, jsonInsert);

        }
        acertos = 0;
        erro = 0;

    }

    public void proximoJogo(){

        textView.setText("");
        txtResult.setText("");

        nome1.setText("");
        nome2.setText("");
        nome3.setText("");
        nome4.setText("");
        nome5.setText("");

        listaNomes.clear();

        radioGroup.clearCheck();

        new MyAlunosFraseAsyncTask(this).execute();
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

        if(nome1.isChecked() && nome1.getText().equals(nomeCorreto)){ opcaoCorreta();

        }else if(nome2.isChecked() && nome2.getText().equals(nomeCorreto)){ opcaoCorreta();

        }else if(nome3.isChecked() && nome3.getText().equals(nomeCorreto)){ opcaoCorreta();

        }else if(nome4.isChecked() && nome4.getText().equals(nomeCorreto)){ opcaoCorreta();

        }else if(nome5.isChecked() && nome5.getText().equals(nomeCorreto)){ opcaoCorreta();

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

            textView.append(jsonObject.getString("frase"));

            nomeCorreto = jsonObject.getString("nome");

            listaNomes.add(jsonObject.getString("nome"));
            listaNomes.add(jsonArray.getString(0));
            listaNomes.add(jsonArray.getString(1));
            listaNomes.add(jsonArray.getString(2));
            listaNomes.add(jsonArray.getString(3));

            Collections.shuffle(listaNomes);

            nome1.setText(listaNomes.get(0));
            nome2.setText(listaNomes.get(1));
            nome3.setText(listaNomes.get(2));
            nome4.setText(listaNomes.get(3));
            nome5.setText(listaNomes.get(4));


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}

