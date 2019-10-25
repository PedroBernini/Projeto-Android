package b194832_p204575.ft.unicamp.br.atividade01_fragmentos;


import android.os.Bundle;;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class AutoresFragment extends Fragment {

    private View view;
    private TextView textView;
    private String mensagem;

    public AutoresFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null)
            view =  inflater.inflate(R.layout.fragment_autores, container, false);

        return view;
    }

    public void setText(String msg){
        this.mensagem = msg;
    }

    public void onStart(){
        super.onStart();
        textView = view.findViewById(R.id.resultado);
        textView.setText("Mensagem: " + this.mensagem);
    }

}
