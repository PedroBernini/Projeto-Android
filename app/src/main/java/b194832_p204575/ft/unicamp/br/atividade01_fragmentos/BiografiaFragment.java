package b194832_p204575.ft.unicamp.br.atividade01_fragmentos;


import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import b194832_p204575.ft.unicamp.br.atividade01_fragmentos.alunos.Aluno;
import b194832_p204575.ft.unicamp.br.atividade01_fragmentos.alunos.Alunos;


/**
 * A simple {@link Fragment} subclass.
 */
public class BiografiaFragment extends Fragment {


    private View view;
    private ImageView imageView;
    private TextView txtNome;
    private TextView txtDescricao;
    private int position;

    public BiografiaFragment() {
        this.position = 0;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(view == null) {
            view = inflater.inflate(R.layout.fragment_biografia, container, false);
        }

        imageView = view.findViewById(R.id.imagem_biografia);
        txtDescricao = view.findViewById(R.id.descricao_biografia);
        txtNome = view.findViewById(R.id.nome_biografia);

        atualizaBiografia();

        Button voltar = view.findViewById(R.id.botao_voltar);
        voltar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(getPosition() == 0){
                            setPosition(Alunos.alunos.length - 1);
                        } else {
                            setPosition(getPosition() - 1);
                        }
                        atualizaBiografia();
                    }
                }
        );

        Button avancar = view.findViewById(R.id.botao_avancar);
        avancar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(getPosition() == Alunos.alunos.length - 1) {
                            setPosition(0);
                        } else {
                            setPosition(getPosition() + 1);
                        }
                        atualizaBiografia();
                    }
                }
        );

        return view;
    }

    public void atualizaBiografia() {
        Aluno aluno = Alunos.alunos[position];
        txtDescricao.setText(Html.fromHtml(aluno.getDescricao()));
        txtNome.setText(aluno.getNome());
        imageView.setImageResource(aluno.getFoto());
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public int getPosition() {
        return this.position;
    }

    public void avancarClick(View view) {
        setPosition(this.getPosition() + 1);
    }

    public void voltarClick(View view) {
        setPosition(this.getPosition() - 1);
    }
}
