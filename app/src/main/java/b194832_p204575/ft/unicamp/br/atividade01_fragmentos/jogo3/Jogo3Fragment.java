package b194832_p204575.ft.unicamp.br.atividade01_fragmentos.jogo3;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import b194832_p204575.ft.unicamp.br.atividade01_fragmentos.MainActivity;
import b194832_p204575.ft.unicamp.br.atividade01_fragmentos.R;

public class Jogo3Fragment extends Fragment {

    private View lview;
    private RadioButton alunosFrase;
    private RadioButton frasesAluno;
    private RadioGroup radioGroup;

    public Jogo3Fragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        if (lview == null) {
            lview = inflater.inflate(R.layout.fragment_jogo3_fragment, container, false);
        }

        alunosFrase = lview.findViewById(R.id.alunosFrase);
        frasesAluno = lview.findViewById(R.id.frasesAluno);
        radioGroup = lview.findViewById(R.id.escolherJogo);

        lview.findViewById(R.id.comecar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(alunosFrase.isChecked()){

                    radioGroup.clearCheck();
                    ((MainActivity) getActivity ()).replaceAlunosfrase();

                }else if (frasesAluno.isChecked()){

                    radioGroup.clearCheck();
                    ((MainActivity) getActivity ()).replaceFrasesAluno();

                }else{
                    Toast.makeText(getActivity(),"Selecione um jogo!",Toast.LENGTH_SHORT).show();
                }

            }
        });

        return lview;
    }

}