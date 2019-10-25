package b194832_p204575.ft.unicamp.br.atividade01_fragmentos.jogo3;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Collections;

import b194832_p204575.ft.unicamp.br.atividade01_fragmentos.R;

public class AlunosFraseFragment extends Fragment {

    private View lview;
    TextView textView;
    TextView txtResult;

    String nomeCorreto;

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

        radioGroup = lview.findViewById(R.id.radioGroup);

        nome1 = lview.findViewById(R.id.nome1);
        nome2 = lview.findViewById(R.id.nome2);
        nome3 = lview.findViewById(R.id.nome3);
        nome4 = lview.findViewById(R.id.nome4);
        nome5 = lview.findViewById(R.id.nome5);

        new MyAlunosFraseAsyncTask(this).execute();

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