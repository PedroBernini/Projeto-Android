package b194832_p204575.ft.unicamp.br.atividade01_fragmentos;


import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicReference;

import b194832_p204575.ft.unicamp.br.atividade01_fragmentos.jogo.Board;
import b194832_p204575.ft.unicamp.br.atividade01_fragmentos.jogo.Boards;
import b194832_p204575.ft.unicamp.br.atividade01_fragmentos.jogo.EmptyPuzzle;

public class PuzzleFragment extends Fragment {

    private ArrayList<ImageView> imageViews;
    private LinearLayout view;
    private EmptyPuzzle abstractPuzzle;
    private Spinner spinner;
    private EmptyPuzzle puzzle;

    public PuzzleFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (view == null) {
            view = (LinearLayout) inflater.inflate(R.layout.fragment_puzzle, container, false);

            spinner = view.findViewById(R.id.spinner_Id);
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getContext(), R.array.grid_options, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);

            view.findViewById(R.id.botao_iniciar).setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    LinearLayout gridLayout = view.findViewById(R.id.layout_Id);
                    gridLayout.removeAllViews();

                    if (spinner.getSelectedItem().toString().equals("Rodrigo")) {
                        startPuzzle(1, view);
                    } else {
                        startPuzzle(0, view);
                    }
                }
            });

            startPuzzle(0, view);
        }
        return view;
    }

    public void startPuzzle(int puzzle, LinearLayout view) {

        Board board = Boards.getPuzzle(puzzle);
        ArrayList<ImageView> imageViews = new ArrayList<>();

        LinearLayout puzzleLayout = view.findViewById(R.id.layout_Id);

        for(int i = 0; i<board.getNumLines(); i++){
            LinearLayout row = new LinearLayout(getContext());
            row.setOrientation(LinearLayout.HORIZONTAL);
            row.setLayoutParams(
                    new LinearLayout.LayoutParams(
                            ViewGroup.LayoutParams.WRAP_CONTENT,
                            ViewGroup.LayoutParams.WRAP_CONTENT
                    ));

            for(int j = 0; j<board.getNumColumns(); j++){
                ImageView imageView = new ImageView(getContext());
                imageView.setAdjustViewBounds(true);
                imageView.setScaleType(ImageView.ScaleType.FIT_XY);

                imageView.setLayoutParams(
                        new LinearLayout.LayoutParams(
                                board.getWidth(),
                                board.getHeight()
                        ));

                row.addView(imageView);
                imageViews.add(imageView);
            }
            puzzleLayout.addView(row);
        }
        this.puzzle = new EmptyPuzzle(board, imageViews);
        this.puzzle.setPuzzleBridge(new EmptyPuzzle.PuzzleBridge() {

            @Override
            public void showWinPopup() {
                Toast.makeText(getContext(), "Você ganhou!", Toast.LENGTH_LONG).show();
            }
        });
    }
}
