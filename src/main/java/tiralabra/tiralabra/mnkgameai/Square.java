package tiralabra.tiralabra.mnkgameai;

public class Square {

    private int row;
    private int column;
    private char symbol;
    private int sameSymbolN;
    private int sameSymbolNE;
    private int sameSymbolE;
    private int sameSymbolSE;
    private int sameSymbolS;
    private int sameSymbolSW;
    private int sameSymbolW;
    private int sameSymbolNW;

    public Square(int row, int column, char symbol) {
        this.row = row;
        this.column = column;
        this.symbol = symbol;
        this.sameSymbolN = 0;
        this.sameSymbolNE = 0;
        this.sameSymbolE = 0;
        this.sameSymbolSE = 0;
        this.sameSymbolS = 0;
        this.sameSymbolSW = 0;
        this.sameSymbolW = 0;
        this.sameSymbolNW = 0;
    }

    /**
     * @return the row
     */
    public int getRow() {
        return row;
    }

    /**
     * @param row the row to set
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * @return the column
     */
    public int getColumn() {
        return column;
    }

    /**
     * @param column the column to set
     */
    public void setColumn(int column) {
        this.column = column;
    }

    /**
     * @return the symbol
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * @param symbol the symbol to set
     */
    public void setSymbol(char symbol) {
        this.symbol = symbol;
    }

    /**
     * @return the sameSymbolN
     */
    public int getSameSymbolN() {
        return sameSymbolN;
    }

    /**
     * @param sameSymbolN the sameSymbolN to set
     */
    public void setSameSymbolN(int sameSymbolN) {
        this.sameSymbolN = sameSymbolN;
    }

    /**
     * @return the sameSymbolNE
     */
    public int getSameSymbolNE() {
        return sameSymbolNE;
    }

    /**
     * @param sameSymbolNE the sameSymbolNE to set
     */
    public void setSameSymbolNE(int sameSymbolNE) {
        this.sameSymbolNE = sameSymbolNE;
    }

    /**
     * @return the sameSymbolE
     */
    public int getSameSymbolE() {
        return sameSymbolE;
    }

    /**
     * @param sameSymbolE the sameSymbolE to set
     */
    public void setSameSymbolE(int sameSymbolE) {
        this.sameSymbolE = sameSymbolE;
    }

    /**
     * @return the sameSymbolSE
     */
    public int getSameSymbolSE() {
        return sameSymbolSE;
    }

    /**
     * @param sameSymbolSE the sameSymbolSE to set
     */
    public void setSameSymbolSE(int sameSymbolSE) {
        this.sameSymbolSE = sameSymbolSE;
    }

    /**
     * @return the sameSymbolS
     */
    public int getSameSymbolS() {
        return sameSymbolS;
    }

    /**
     * @param sameSymbolS the sameSymbolS to set
     */
    public void setSameSymbolS(int sameSymbolS) {
        this.sameSymbolS = sameSymbolS;
    }

    /**
     * @return the sameSymbolSW
     */
    public int getSameSymbolSW() {
        return sameSymbolSW;
    }

    /**
     * @param sameSymbolSW the sameSymbolSW to set
     */
    public void setSameSymbolSW(int sameSymbolSW) {
        this.sameSymbolSW = sameSymbolSW;
    }

    /**
     * @return the sameSymbolW
     */
    public int getSameSymbolW() {
        return sameSymbolW;
    }

    /**
     * @param sameSymbolW the sameSymbolW to set
     */
    public void setSameSymbolW(int sameSymbolW) {
        this.sameSymbolW = sameSymbolW;
    }

    /**
     * @return the sameSymbolNW
     */
    public int getSameSymbolNW() {
        return sameSymbolNW;
    }

    /**
     * @param sameSymbolNW the sameSymbolNW to set
     */
    public void setSameSymbolNW(int sameSymbolNW) {
        this.sameSymbolNW = sameSymbolNW;
    }

    void newSymbol(char symbol, Square[][] squares) {
        this.symbol = symbol;
        if (this.row > 0 && squares[this.row - 1][this.column].symbol == this.symbol) {
            this.sameSymbolN = squares[this.row - 1][this.column].sameSymbolN + 1;
        }
        if (this.row > 0 && this.column > 0 && squares[this.row - 1][this.column - 1].symbol == this.symbol) {
            this.sameSymbolNW = squares[this.row - 1][this.column - 1].sameSymbolNW + 1;
        }
        if (this.column > 0 && squares[this.row][this.column - 1].symbol == this.symbol) {
            this.sameSymbolW = squares[this.row][this.column - 1].sameSymbolW + 1;
        }
        if (this.row < squares.length - 1 && this.column > 0 && squares[this.row + 1][this.column - 1].symbol == this.symbol) {
            this.sameSymbolSW = squares[this.row + 1][this.column - 1].sameSymbolSW + 1;
        }
        updateN(this.row + 1, this.column, symbol, squares, this.sameSymbolN);
        updateNW(this.row + 1, this.column + 1, symbol, squares, this.sameSymbolNW);
        updateW(this.row, this.column + 1, symbol, squares, this.sameSymbolW);
        updateSW(this.row - 1, this.column + 1, symbol, squares, this.sameSymbolSW);
    }

    private void updateN(int row, int column, char symbol, Square[][] squares, int sameSymbolN) {
        if (row < 0 || column < 0 || row >= squares.length || column >= squares[0].length) {
            return;
        }
        if (squares[row][column].symbol == symbol) {
            squares[row][column].sameSymbolN = sameSymbolN + 1;
            updateN(row + 1, column, symbol, squares, sameSymbolN + 1);
        }
    }

    private void updateNW(int row, int column, char symbol, Square[][] squares, int sameSymbolNW) {
        if (row < 0 || column < 0 || row >= squares.length || column >= squares[0].length) {
            return;
        }
        if (squares[row][column].symbol == symbol) {
            squares[row][column].sameSymbolNW = sameSymbolNW + 1;
            updateN(row + 1, column + 1, symbol, squares, sameSymbolNW + 1);
        }
    }

    private void updateW(int row, int column, char symbol, Square[][] squares, int sameSymbolW) {
        if (row < 0 || column < 0 || row >= squares.length || column >= squares[0].length) {
            return;
        }
        if (squares[row][column].symbol == symbol) {
            squares[row][column].sameSymbolW = sameSymbolW + 1;
            updateN(row, column + 1, symbol, squares, sameSymbolW + 1);
        }
    }

    private void updateSW(int row, int column, char symbol, Square[][] squares, int sameSymbolSW) {
        if (row < 0 || column < 0 || row >= squares.length || column >= squares[0].length) {
            return;
        }
        if (squares[row][column].symbol == symbol) {
            squares[row][column].sameSymbolSW = sameSymbolSW + 1;
            updateN(row - 1, column + 1, symbol, squares, sameSymbolSW + 1);
        }
    }
}
