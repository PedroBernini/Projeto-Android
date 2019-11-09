package b194832_p204575.ft.unicamp.br.atividade01_fragmentos;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class MailFragment extends Fragment {

    private View view;
    private EditText destinatario;
    private EditText mensagem;
    private Button button;

    public MailFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null)
            view = inflater.inflate(R.layout.fragment_mail, container, false);

        destinatario = view.findViewById(R.id.para);
        mensagem = view.findViewById(R.id.message);

        Button enviar = view.findViewById(R.id.btn_send);

        enviar.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String msg = MailFragment.this.mensagem.getText().toString();
                        ((MainActivity)getActivity()).clickSend(msg);
                    }
                }
        );

        return view;
    }
}
