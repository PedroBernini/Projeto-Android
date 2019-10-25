package b194832_p204575.ft.unicamp.br.atividade01_fragmentos.jogo;

import android.view.View;
import android.widget.ImageView;
import java.util.ArrayList;
import static java.lang.Math.abs;

public class EmptyPuzzle extends AbstractPuzzle {
    private PuzzleBridge puzzleBridge;

    public EmptyPuzzle(Board board, ArrayList<ImageView> imageViews) {
        super(board, imageViews);
    }

    public interface PuzzleBridge{
        public void showWinPopup();
    }

    public void setPuzzleBridge(PuzzleBridge puzzleBridge){
        this.puzzleBridge = puzzleBridge;
    }

    public void addListener(ImageView imageView, final int line, final int column) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getBoard().atualizarPosicaoDummy();
                int linhaDummy = getBoard().getDummyCell()[0];
                int colunaDummy = getBoard().getDummyCell()[1];

                if (linhaDummy == line || colunaDummy == column) {
                    //mover dummy entre linhas
                    int diff = abs(linhaDummy - line);

                    for (int i = 0; i < diff; i++) {
                        if (linhaDummy > line) {
                            getBoard().swap(linhaDummy, colunaDummy, linhaDummy - 1, colunaDummy);
                            linhaDummy--;
                        } else {
                            getBoard().swap(linhaDummy, colunaDummy, linhaDummy + 1, colunaDummy);
                            linhaDummy++;
                        }
                    }

                    //mover dummy entre colunas
                    diff = abs(colunaDummy - column);

                    for (int j = 0; j < diff; j++) {
                        if (colunaDummy > column) {
                            getBoard().swap(linhaDummy, colunaDummy, linhaDummy, colunaDummy-1);
                            colunaDummy--;
                        } else {
                            getBoard().swap(linhaDummy, colunaDummy, linhaDummy, colunaDummy+1);
                            colunaDummy++;
                        }
                    }

                    getBoard().atualizarPosicaoDummy();

                    redraw();

                    if (endGame()) {
                        puzzleBridge.showWinPopup();
                    }
                }
            }
        });
    }

    public boolean endGame() {
        for (int i = 0; i < this.getBoard().getNumLines(); i++){
            for(int j = 0; j < this.getBoard().getNumColumns(); j++){
                if (this.getBoard().getGameBlock(i, j) != this.getBoard().getCorrectBlock(i, j))
                    return false;
            }
        }
        return true;
    }

}
