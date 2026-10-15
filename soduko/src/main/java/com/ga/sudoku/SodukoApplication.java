package com.ga.sudoku;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SodukoApplication {

	public static void main(String[] args) {
		Sudoku sudoku = new Sudoku();
		sudoku.readFile("puzzle1.txt");
		SudokuSolver sudokuSolver = new SudokuSolver(sudoku);
		if(sudokuSolver.solve()){
			System.out.println("Solution: ");
			sudoku.printPuzzle();
		}
		sudoku.printPuzzle();
		System.out.println();
		System.out.println(sudoku.isNumInRow(0,5));
		sudoku.isNumInBox(7,4 ,2);
	}

}
