package b194832_p204575.ft.unicamp.br.atividade01_fragmentos.jogo3;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import b194832_p204575.ft.unicamp.br.atividade01_fragmentos.R;

/**
 * A simple {@link Fragment} subclass.
 */


public class FrasesAlunoFragment extends Fragment {


    private View lview;
    TextView textView;
    TextView txtResult;

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

        new MyFrasesAlunoAsyncTask(this).execute();

        lview.findViewById(R.id.btnChecar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checar();
            }
        });

        return lview;
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
