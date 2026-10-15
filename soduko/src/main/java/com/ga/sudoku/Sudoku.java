package com.ga.sudoku;

import com.ga.sudoku.exceptions.InvalidCharacterException;
import com.ga.sudoku.exceptions.SudokuFileNotFoundException;
import org.springframework.security.core.parameters.P;

import java.io.*;
import java.util.Arrays;
import java.util.List;

public class Sudoku {
    private static final int BOARD_SIZE = 9;
    private static final int BOX_SIZE = 3;
    // 9x9 grid of cells; the slots start empty (null) and are filled when the puzzle is loaded
    private final SudokuCell[][] board = new SudokuCell[BOARD_SIZE][BOARD_SIZE];

    public void readFile(String file) {
        try {
            FileReader fr = new FileReader(file);
            BufferedReader br = new BufferedReader(fr);

            int row = 0;
            String line;
            while ((line = br.readLine()) != null) {

                line = line.trim();
                System.out.println(line);
                System.out.println("this is row: " + row);
                if (line.isEmpty()) continue;
                if (row >= BOARD_SIZE) {
                    throw new InvalidCharacterException("The file has more than " + BOARD_SIZE + " rows.");
                }
                String[] parts = line.split(",");
                for (String part : parts)
                    System.out.println(
                            part
                    );
                if (parts.length != BOARD_SIZE) {
                    throw new InvalidCharacterException("Row" + (row + 1) + " must have " + BOARD_SIZE + " numbers.");
                }
                for (int col = 0; col < BOARD_SIZE; col++) {
                    try {
                        int number = Integer.parseInt(parts[col].trim());
                        board[row][col] = new SudokuCell(number);
                    } catch (NumberFormatException e) {
                        throw new InvalidCharacterException("Invalid character '" + parts[col].trim() + "' at row " + (row + 1) + ", column " + (col + 1) + ".");
                    }

                }
                row++;
            }
            if (row != BOARD_SIZE) {
                throw new InvalidCharacterException("The file must have " + BOARD_SIZE + " rows, but has " + row + ".");
            }
            System.out.println();
            br.close();

        } catch (FileNotFoundException e) {
            throw new SudokuFileNotFoundException("The file " + file + " was not found.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void printPuzzle(){
        String line = "+-------+-------+-------+";
        for(int row =0; row<BOARD_SIZE; row++){
            if(row % BOX_SIZE == 0) System.out.println(line);
            System.out.print("| ");

            for (int col=0; col<BOARD_SIZE; col++){
                System.out.print(board[row][col] + " ");
                if((col + 1) % BOX_SIZE == 0)
                    System.out.print("| ");
            }
            System.out.println();
//            if((row +1) % BOX_SIZE==0 && row <BOARD_SIZE-1) System.out.println("+-------+-------+-------+");
        }
        System.out.print(line);
    }

    public boolean isNumInRow(int row, int num){
       for (int col =0; col<BOARD_SIZE; col++){
           if(board[row][col].getNumber() == num){
               return true;
           }
       }
       return false;
    }

    public boolean isNumInCol(int col, int num){
        for(int row=0; row<BOARD_SIZE; row++){
            if(board[row][col].getNumber() == num){
                return true;
            }
        }
        return false;
    }

    public boolean isNumInBox(int row, int col, int num){
        int startRow = row - row % BOX_SIZE;
        int startCol = col - col % BOX_SIZE;
        for(int i =0; i< BOX_SIZE; i++){
            for(int n = 0; n <BOX_SIZE; n++){
                System.out.print(board[startRow + i][startCol +n]);
                if(board[startRow + i][startCol +n].getNumber() == num) return true;
            }
        }
        return false;
    }

    public boolean canPlace(int row, int col, int num){
        return !(isNumInRow(row, num) && isNumInCol(col, num) && isNumInBox(row, col, num));
    }
}
