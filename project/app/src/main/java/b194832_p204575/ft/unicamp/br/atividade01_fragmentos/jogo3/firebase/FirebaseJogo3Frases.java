package b194832_p204575.ft.unicamp.br.atividade01_fragmentos.jogo3.firebase;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import b194832_p204575.ft.unicamp.br.atividade01_fragmentos.jogo3.AlunosFraseFragment;
import b194832_p204575.ft.unicamp.br.atividade01_fragmentos.jogo3.FrasesAlunoFragment;

public class FirebaseJogo3Frases extends AsyncTask<String, Void, String> {

    int numArgs;
    FrasesAlunoFragment fragment;

    public FirebaseJogo3Frases(FrasesAlunoFragment fragment) {
        this.fragment = fragment;
    }


    @Override
    protected void onPreExecute() {
    }

    @Override
    protected String doInBackground(String... args) {

        numArgs = args.length;

        if (args.length == 0) {
            return "No Parameter";
        }

        HttpURLConnection httpURLConnection;

        try {

            URL url = new URL(args[0]);

            httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setReadTimeout(10000);
            httpURLConnection.setConnectTimeout(15000);
            httpURLConnection.setRequestMethod(args[1]);


            if (args.length == 3){
                httpURLConnection.addRequestProperty("Content-Type","application/json");

                OutputStreamWriter outputStreamWriter = new OutputStreamWriter(httpURLConnection.getOutputStream(),"UTF-8");
                outputStreamWriter.write(args[2]);
                outputStreamWriter.flush();
                outputStreamWriter.close();

            }

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
        if(numArgs != 3){
            try {
                JSONObject jsonObject = new JSONObject(args);
                fragment.atualizaFirebase(jsonObject);

            } catch(JSONException e) {
                System.out.println("ERRO: Não foi possível");
            }
        }

    }

}
