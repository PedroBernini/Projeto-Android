package b194832_p204575.ft.unicamp.br.atividade01_fragmentos.jogo;

import android.widget.ImageView;

import java.util.ArrayList;

public abstract class AbstractPuzzle {
    private Board board;
    private ArrayList<ImageView> imagesViews;

    public AbstractPuzzle(Board board, ArrayList<ImageView> imagesViews) {
        this.board = board;
        this.imagesViews = imagesViews;
        this.startGame();
        this.redraw();

        for (int i=0; i<board.getNumLines(); i++) {
            for(int j=0; j<board.getNumColumns(); j++){
                this.addListener(imagesViews.get(board.getNumColumns()*i + j), i, j);
            }
        }
    }

    public void redraw() {
        for (int i=0; i<board.getNumLines(); i++) {
            for(int j=0; j<board.getNumColumns(); j++){
                imagesViews.get(board.getNumColumns()*i + j).setImageResource(board.getGameBlock(i,j));
            }
        }
    }

    public Board getBoard() {
        return board;
    }

    public void setBoard(Board board) {
        this.board = board;
    }

    public void startGame() {
        board.startGame();
    }


    public abstract void addListener(ImageView imageView, int line, int column);
    public abstract boolean endGame();
}
