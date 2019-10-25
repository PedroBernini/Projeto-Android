package b194832_p204575.ft.unicamp.br.atividade01_fragmentos.jogo3;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MyAlunosFraseAsyncTask extends AsyncTask<Void, Void, String> {
    AlunosFraseFragment fragment;


    public MyAlunosFraseAsyncTask(AlunosFraseFragment fragment) {
        this.fragment = fragment;
    }

    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(Void... voids) {

        HttpURLConnection httpURLConnection;
        try {
            /*
               Endereço que será acessado.
             */
            String HOST = "https://sa4a4dtiv4.execute-api.eu-west-1.amazonaws.com/default/PythonHTTP1?kind=alunos&num_outros=4";

        /*
          Abrindo uma conexão com o servidor
        */

            URL url = new URL(HOST);
            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
        /*
          Lendo a resposta do servidor
        */
            BufferedReader reader = new BufferedReader(new
                    InputStreamReader(httpURLConnection.getInputStream()));


            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            return sb.toString();
        } catch (IOException e) {
            Log.v("Erro", e.getMessage());
            return "Exception\n" + e.getMessage();
        }
    }

    @Override
    protected void onPostExecute(String args) {
        try {
            JSONObject jsonObject = new JSONObject(args);
            fragment.atualizaLayout(jsonObject);

        } catch(JSONException e) {
            fragment.textView.append("ERRO: Não foi possível converter em JSONObject: " + args+"\n");
        }


    }
}