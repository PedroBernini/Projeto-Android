package b194832_p204575.ft.unicamp.br.atividade01_fragmentos.alunos;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Arrays;

import b194832_p204575.ft.unicamp.br.atividade01_fragmentos.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class AlunosFragment extends Fragment {

    public RecyclerView mRecyclerView;
    public MyFirstAdapter mAdapter
            ;
    public View view;
    public MyReplaceFragment  myReplaceFragment;

    public interface onBiografiaRequest{
        void setPosition(int position);
    }

    public interface MyReplaceFragment {
        void replaceBiografia(int position);
    }

    public void setMyReplaceFragment(MyReplaceFragment myReplaceFragment) {
        this.myReplaceFragment = myReplaceFragment;
    }

    public AlunosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (view == null)
            view =  inflater.inflate(R.layout.fragment_alunos, container, false);

        mRecyclerView = view.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        mAdapter = new MyFirstAdapter(new ArrayList(Arrays.asList(Alunos.alunos)));

        mAdapter.setMyOnItemClickListener(
                new MyFirstAdapter.MyOnItemClickListener() {
                    @Override
                    public void myOnItemClick(String nome) {
                        Toast.makeText(getActivity(), nome, Toast.LENGTH_SHORT).show();
                    }
                }
        );

        mAdapter.setMyOnItemLongClickListener(
                new MyFirstAdapter.MyOnItemLongClickListener() {
                    @Override
                    public void mostrarBiografia(int posicao) {
                        myReplaceFragment.replaceBiografia(posicao);
                    }
                }
        );
        mRecyclerView.setAdapter(mAdapter);
        return view;

    }

}
