package com.ga.sudoku;

import com.ga.sudoku.exceptions.InvalidCharacterException;
import com.ga.sudoku.exceptions.SudokuFileNotFoundException;

import java.io.*;
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
}
