package com.ga.sudoku;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SodukoApplication {

	public static void main(String[] args) {
		Sudoku sudoku = new Sudoku();
		sudoku.readFile("puzzle1.txt");
		sudoku.printPuzzle();
		System.out.println();
		System.out.println(sudoku.isNumInRow(0,5));
		sudoku.isNumInBox(7,4 ,2);
	}

}
