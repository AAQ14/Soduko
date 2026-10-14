package com.ga.sudoku;

import com.ga.sudoku.exceptions.InvalidCharacterException;
import lombok.Getter;


@Getter
public class SudokuCell {
    private int number;

    public SudokuCell(int number){
        validate(number);
        this.number = number;
    }


    public void setNumber(int number){
        validate(number);
        this.number = number;
    }

    private static void validate(int number){
        if(!(number>=0 && number<=9)) {
            throw new InvalidCharacterException("The character entered must be between 0 and 9.");
        }
    }

    public boolean isEmpty(){
        return getNumber() ==0 ;
    }

    @Override
    public String toString() {
        if(isEmpty()) return ".";
        return Integer.toString(getNumber());
    }
}
