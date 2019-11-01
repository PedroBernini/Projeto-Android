package b194832_p204575.ft.unicamp.br.atividade01_fragmentos.jogo2;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import b194832_p204575.ft.unicamp.br.atividade01_fragmentos.R;
import b194832_p204575.ft.unicamp.br.atividade01_fragmentos.alunos.Aluno;
import b194832_p204575.ft.unicamp.br.atividade01_fragmentos.alunos.Alunos;
import b194832_p204575.ft.unicamp.br.atividade01_fragmentos.database.DatabaseNameHelper;
import b194832_p204575.ft.unicamp.br.atividade01_fragmentos.interfaces.OnBiografiaRequest;


public class NameFragment extends Fragment {

    private View lview;
    private DatabaseNameHelper dbHelper;
    private SQLiteDatabase sqLiteDatabase;

    private Random random = new Random();
    private String nomeCorreto;
    private int positionAluno;
    private int numTentativas;
    private Float acerto = 0.0f;
    private Float erro = 0.0f;
    private ArrayList<Aluno> alunosList = new ArrayList(Arrays.asList(Alunos.alunos));
    private ImageView imageView;
    private TextView txtTentativas;
    private TextView txtFeedback;
    private ArrayList<Button> arrayListButton;

    private OnBiografiaRequest onBiografiaRequest;

    public void setOnBiografiaRequest(OnBiografiaRequest onBiografiaRequest) {
        this.onBiografiaRequest = onBiografiaRequest;
    }

    public NameFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (lview == null) {
            lview = inflater.inflate(R.layout.fragment_name, container, false);
        }

        imageView = lview.findViewById(R.id.imageFoto);
        txtTentativas = lview.findViewById(R.id.txtTentativas);
        txtFeedback = lview.findViewById(R.id.txtFeedback);

        arrayListButton = new ArrayList<>();
        arrayListButton.add((Button) lview.findViewById(R.id.button1));
        arrayListButton.add((Button) lview.findViewById(R.id.button2));
        arrayListButton.add((Button) lview.findViewById(R.id.button3));
        arrayListButton.add((Button) lview.findViewById(R.id.button4));
        arrayListButton.add((Button) lview.findViewById(R.id.button5));
        arrayListButton.add((Button) lview.findViewById(R.id.button6));
        arrayListButton.add((Button) lview.findViewById(R.id.button7));
        arrayListButton.add((Button) lview.findViewById(R.id.button8));
        arrayListButton.add((Button) lview.findViewById(R.id.button9));

        startGame();

        View.OnClickListener guessButtonListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nomeEscolhido = ((Button) v).getText().toString();
                if (nomeEscolhido.equals( nomeCorreto) ){
                    txtFeedback.setText("Correto!!");
                    new Handler().postDelayed(
                            new Runnable() {
                                @Override
                                public void run() {
                                    startGame();
                                }
                            }, 800);
                } else {
                    txtFeedback.setText("Incorreto!!");

                    onInserirTableImg(nomeCorreto);
                    onInserirTableBtn(nomeEscolhido);

                    numTentativas--;
                    txtTentativas.setText("Tentativas: " + numTentativas);

                    if (numTentativas <= 0) {
                        txtFeedback.setText("Você Perdeu!!");

                        new Handler().postDelayed(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        if (onBiografiaRequest != null) {
                                            erro = erro + 1.0f;
                                            onBiografiaRequest.onRequest(positionAluno);

                                        }
                                    }
                                }, 800);
                    }
                }
            }
        };

        for (int i = 0; i < 9; i++) {
            arrayListButton.get(i).setOnClickListener(guessButtonListener);
        }

        return lview;
    }

    private void startGame() {
        acerto = acerto + 1.0f;

        int guess = random.nextInt(Alunos.alunos.length);
        positionAluno = guess;
        Aluno aluno = Alunos.alunos[guess];
        nomeCorreto = aluno.getNome().split(" ")[0].toLowerCase();
        imageView.setImageResource(aluno.getFoto());
        numTentativas = 3;
        txtTentativas.setText("Tentativas: " + numTentativas);
        txtFeedback.setText("");

        ArrayList<String> arrayList = new ArrayList<String>();
        for (int i = 0; i < 9; i++) {
            Aluno candidate = Alunos.alunos[(guess + i) % Alunos.alunos.length];
            arrayList.add(candidate.getNome().split(" ")[0].toLowerCase());
        }

        Collections.shuffle(arrayList);
        for (int i = 0; i < 9; i++) {
            arrayListButton.get(i).setText(arrayList.get(i));
        }
    }

    public void onStart() {
        super.onStart();
        dbHelper = new DatabaseNameHelper(this.getActivity());
        sqLiteDatabase = dbHelper.getReadableDatabase();
    }

    public void onStop() {
        super.onStop();
        onInserirTablePorcAmostral(acerto, erro);
        acerto = 0.0f;
        erro = 0.0f;
        sqLiteDatabase.close();
        dbHelper.close();
    }

    public List verificaExist(String nomeInsert, int AB){

        String sql = "";
        if(AB == 1){
            sql = "Select * from pessoaMenosConhecida";
        }else if(AB == 2){
            sql = "Select * from nomeMaisClicado";
        }
        String nomeTabela;

        List<String> list = new ArrayList<String>();
        String existe = "false";
        String qtd;
        String id;

        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        if (cursor.moveToFirst()) {
            do {
                nomeTabela = cursor.getString(1);
                if(nomeInsert.equals(nomeTabela)){
                    existe = "true";
                    id = cursor.getString(0);
                    qtd = cursor.getString(2);
                    list.add(existe);
                    list.add(id);
                    list.add(qtd);
                }

            } while (cursor.moveToNext());
        }

        cursor.close();
        return list;
    }

    public void onInserirTableImg(String nome) {

        ContentValues contentValues = new ContentValues();
        int qtd;
        int id;

        List<String> list = verificaExist(nome, 1);
        if(list != null && !list.isEmpty()){
            if(list.get(0).equals("true")){
                id = Integer.parseInt(list.get(1));
                qtd = Integer.parseInt(list.get(2));

                qtd++;

                contentValues.put("nome", nome);
                contentValues.put("qtd", qtd);

                sqLiteDatabase.update("pessoaMenosConhecida", contentValues, "_id="+id, null);
            }

        }else{
            qtd = 1;
            contentValues.put("nome", nome);
            contentValues.put("qtd", qtd);

            sqLiteDatabase.insert("pessoaMenosConhecida", null, contentValues);
        }
    }

    public void onInserirTableBtn(String nome) {

        ContentValues contentValues = new ContentValues();
        int qtd;
        int id;

        List<String> list = verificaExist(nome, 2);
        if(list != null && !list.isEmpty()){
            if(list.get(0).equals("true")){
                id = Integer.parseInt(list.get(1));
                qtd = Integer.parseInt(list.get(2));

                qtd++;

                contentValues.put("nome", nome);
                contentValues.put("qtd", qtd);

                sqLiteDatabase.update("nomeMaisClicado", contentValues, "_id="+id, null);
            }

        }else{
            qtd = 1;
            contentValues.put("nome", nome);
            contentValues.put("qtd", qtd);

            sqLiteDatabase.insert("nomeMaisClicado", null, contentValues);
        }

    }

    public void onInserirTablePorcAmostral(Float acerto, Float erro) {

        ContentValues contentValues = new ContentValues();
        String sql = "";
        Float acertoTable = 0.0f;
        Float erroTable = 0.0f;

        sql = "Select * from PorcentagemAmostral";

        Cursor cursor = sqLiteDatabase.rawQuery(sql, null);

        //Verificar se a tabela está vazia
        if(cursor.getCount() == 0){
            contentValues.put("qtdJogadas", acerto);
            contentValues.put("qtdErros", erro);
        }else {
            if (cursor.moveToFirst()) {
                do {
                    acertoTable = cursor.getFloat(1);
                    erroTable = cursor.getFloat(2);
                } while (cursor.moveToNext());
            }
            contentValues.put("qtdJogadas", acerto + acertoTable);
            contentValues.put("qtdErros", erro + erroTable);
        }

        //Apagar toda tabela pro novo insert
        String tabela = "PorcentagemAmostral";
        sqLiteDatabase.execSQL("DELETE FROM " + tabela);

        sqLiteDatabase.insert("PorcentagemAmostral", null, contentValues);
    }
}
