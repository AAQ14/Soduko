package com.ga.sudoku.controller;

import com.ga.sudoku.Sudoku;
import com.ga.sudoku.SudokuSolver;
import com.ga.sudoku.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/sudoku")
public class SudokuController {

    private final FileService fileService;

    @PostMapping("/solve")
    public ResponseEntity<String> solve(@RequestParam("file")MultipartFile file)throws IOException {
        fileService.validate(file);

        Sudoku sudoku = new Sudoku();
        sudoku.readFromStream(file.getInputStream());
        SudokuSolver solver = new SudokuSolver(sudoku);
        boolean solved = solver.solve();
        if(!solved){
            return ResponseEntity.badRequest().body("This puzzle had no solution");
        }
        String path = fileService.saveSolution(file.getOriginalFilename(), sudoku.boardToString());
        return ResponseEntity.ok("Solved. Solution saved at: " + path);
    }
}
