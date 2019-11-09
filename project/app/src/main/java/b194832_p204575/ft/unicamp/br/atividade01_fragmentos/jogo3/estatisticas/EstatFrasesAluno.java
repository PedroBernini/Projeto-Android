package b194832_p204575.ft.unicamp.br.atividade01_fragmentos.jogo3.estatisticas;


import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import b194832_p204575.ft.unicamp.br.atividade01_fragmentos.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class EstatFrasesAluno extends Fragment {


    TextView textView;
    private View lview;

    public EstatFrasesAluno() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (lview == null) {
            lview = inflater.inflate(R.layout.fragment_estat_frases_aluno, container, false);
        }

        textView = lview.findViewById(R.id.txtEstFrases);

        String comando = "GET";
        String urlGet = "https://aulafirebase-38b8e.firebaseio.com/BD/Jogo3.json";

        new EstFirebaseJogo3Alunos(this).execute(urlGet, comando);

        return lview;
    }

    public void atualizaLayoult(JSONObject jsonObject) throws JSONException {

        String stringEstatisticas = "";
        JSONArray keys = jsonObject.names ();
        int porcAcertos;
        int porcErros;

        for (int i = 0; i < keys.length (); ++i) {

            String key = keys.getString (i);
            if(!key.equals("DefaultInsert")){
                JSONObject values = new JSONObject(jsonObject.getString (key));

                int acertos = values.getInt("Acertos");
                int erros = values.getInt("Erros");

                if(acertos == 0){

                    porcAcertos = 0;
                    porcErros = 100;

                }else if(erros == 0){

                    porcAcertos = 100;
                    porcErros = 0;

                }else{

                    porcAcertos = (100 * acertos) / (acertos + erros);
                    porcErros = 100 - porcAcertos;

                }

                stringEstatisticas = stringEstatisticas + "<b>" + key + "</b>" + ": Acertos: <b>" + porcAcertos + "</b>% Erros: <b>" + porcErros + "</b>% <br/><br/>";

            }

        }
        textView.setText(Html.fromHtml(stringEstatisticas));

    }

}
