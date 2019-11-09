package b194832_p204575.ft.unicamp.br.atividade01_fragmentos.alunos;

import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import b194832_p204575.ft.unicamp.br.atividade01_fragmentos.R;

public class MyFirstAdapter extends RecyclerView.Adapter {

    private ArrayList<Aluno> alunos;
    public MyOnItemClickListener myOnItemClickListener;
    public MyOnItemLongClickListener  myOnItemLongClickListener;

    public interface MyOnItemClickListener {
       void myOnItemClick(String nome);
    }

    public interface MyOnItemLongClickListener {
        void mostrarBiografia(int posicao);
    }

    public void setMyOnItemClickListener(MyOnItemClickListener myOnItemClickListener) {
        this.myOnItemClickListener = myOnItemClickListener;
    }

    public void setMyOnItemLongClickListener(MyOnItemLongClickListener myOnItemLongClickListener) {
        this.myOnItemLongClickListener = myOnItemLongClickListener;
    }

    MyFirstAdapter(ArrayList<Aluno> alunos) {
        this.alunos = alunos;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.adapter_layout, parent, false);
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (myOnItemClickListener != null){
                    TextView txtNome = view.findViewById(R.id.nome);
                    myOnItemClickListener.myOnItemClick(txtNome.getText().toString());
                }
        }
    });

        final MyFirstViewHolder viewHolder = new MyFirstViewHolder(view);
        view.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                myOnItemLongClickListener.mostrarBiografia(viewHolder.position);
                return false;
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MyFirstViewHolder)holder).onBind(alunos.get(position), position);
    }


    @Override
    public int getItemCount() {
        return alunos.size();
    }

    private class MyFirstViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private TextView txtNome;
        private TextView txtDescricao;
        public int position;

        public MyFirstViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imagem);
            txtDescricao = itemView.findViewById(R.id.descricao);
            txtNome = itemView.findViewById(R.id.nome);
        }

        public void onBind(final Aluno aluno, final int position){
            this.position = position;
            txtNome.setText(aluno.getNome());
            txtDescricao.setText(Html.fromHtml(aluno.getDescricao()));
            imageView.setImageResource(aluno.getFoto());
        }
    }
}
