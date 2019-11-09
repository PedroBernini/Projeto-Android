package b194832_p204575.ft.unicamp.br.atividade01_fragmentos.jogo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {
    private int numLines;
    private int numColumns;
    private List<Integer> blocks;
    private List<Integer> gameIndex;
    private int width;
    private int height;
    private int[] dummyCell = new int[2];

    public Board(int numLines, int numColumns, List<Integer> blocks, int width, int height) {
        this.numLines = numLines;
        this.numColumns = numColumns;
        this.blocks = blocks;
        this.width = width;
        this.height = height;
        this.dummyCell = new int[2];

        gameIndex = new ArrayList<>();
        for(int i=0; i<blocks.size(); i++) {
            gameIndex.add(i);
        }

        this.startGame();
    }

    public void startGame() {
        Collections.shuffle(this.gameIndex);
    }

        public int getCorrectBlock(int line, int column) {
        return blocks.get(line*this.getNumColumns() + column);
    }

    public int getGameBlock(int line, int column) {
        return blocks.get(gameIndex.get(line*this.getNumColumns() + column));
    }

    public void swap(int line1, int column1, int line2, int column2) {
        int index1 =  line1*this.numColumns+column1;
        int index2 =  line2*this.numColumns+column2;

        Collections.swap(gameIndex,index1,index2);
    }

    public void atualizarPosicaoDummy(){
        int dummyCell = getCorrectBlock(0,0);

        for(int i = 0; i < this.numLines; i++) {
            for(int j = 0; j < this.numColumns; j++) {
                if (getGameBlock(i,j) == dummyCell) {
                    this.dummyCell[0] = i;
                    this.dummyCell[1] = j;
                }
            }
        }
    }

    public int getNumLines() {
        return numLines;
    }

    public int getNumColumns() {
        return numColumns;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int[] getDummyCell() {
        return dummyCell;
    }

    public void setDummyCell(int[] dummyCell) {
        this.dummyCell = dummyCell;
    }
}
