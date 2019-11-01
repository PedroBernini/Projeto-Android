package b194832_p204575.ft.unicamp.br.atividade01_fragmentos.internet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import b194832_p204575.ft.unicamp.br.atividade01_fragmentos.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InternetFragment extends Fragment {

    private View lview;
    private Button btnMetodo;
    private Spinner spinner;

    public InternetFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (lview == null) {
            lview = inflater.inflate(R.layout.fragment_internet, container, false);
        }

        final TextView textView = lview.findViewById(R.id.textView);
        final EditText editText = lview.findViewById(R.id.editText);

        btnMetodo = lview.findViewById(R.id.btnViaCep);
        spinner = (Spinner) lview.findViewById(R.id.restSpinner);

        btnMetodo.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String url = "https://projetodisciplina-16b48.firebaseio.com/.json";
                        String selected = spinner.getSelectedItem().toString();

                        if (selected.equals("GET")) {
                            new MyFirstWebService(textView).execute(url, "GET");
                        } else {
                            String json = "{\"Caracteristica\":\"Gostoso\",\"ID\":8,\"Nome\":\"Pedro\"}";
                            new MyFirstWebService(textView).execute(url, selected, json);
                        }
                    }
                }



        );

        return lview;
    }

}
