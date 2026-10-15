package com.ga.sudoku;

public class SudokuSolver {
    private final Sudoku sudoku;

    public SudokuSolver(Sudoku sudoku){
        this.sudoku = sudoku;

    }

    public boolean solve(){
        for(int i =0; i<9; i++){
            for(int j =0; j<9; j++){
                if (sudoku.getCell(i, j).isEmpty()) {
                    for(int num =1; num<=9; num++){
                        if(sudoku.canPlace(i, j, num)){
                            sudoku.setCell(i, j, num);
                            if(solve()) return true;
                            sudoku.setCell(i,j, 0);
                        }
                    }
                    return false;
                }
            }
        }
        return true;
    }

}
