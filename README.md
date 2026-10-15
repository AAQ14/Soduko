# Soduko

# Sudoku Solver (Spring Boot)
A Spring Boot mini project that loads a Sudoku puzzle from a text file, solves it with a backtracking algorithm, prints the board in ASCII format, and saves the solution to a file. A REST endpoint lets you upload a puzzle file and get the path of the solution file back.

## Technologies used
- Java 17
- Spring Boot 4.1.1 (Spring Web MVC)
- Lombok
- Maven

## Puzzle file format

A plain `.txt` file with **9 lines of 9 numbers separated by commas**. Use `0` for an empty cell. Blank lines are ignored.

```
0,0,0,1,5,0,0,7,0
1,0,6,0,0,0,8,2,0
3,0,0,8,6,0,0,4,0
9,0,0,4,0,0,5,6,7
0,0,4,7,0,8,3,0,0
7,3,2,0,0,6,0,0,4
0,4,0,0,8,1,0,0,9
0,1,7,0,0,0,2,0,8
0,5,0,0,3,7,0,0,0
```

## The algorithm: backtracking
- Find an empty cell. The solver scans the board for the first cell that holds 0.
- Try numbers 1 to 9. For each number, it checks the Sudoku rules: the number must not already be in the same row, the same column or the same 3×3 box.
- Place and continue. When a number is allowed, it is placed in the cell, and the solver moves on to the next empty cell (the same step repeats, using recursion).
- Backtrack on a dead end. If a later cell has no valid number, an earlier guess was wrong. The solver removes the last number (sets the cell back to 0) and tries the next number in that cell.
- Finish. When no empty cell is left, the board is complete and valid. If every number has been tried in the first empty cell and none works, the puzzle has no solution.
The solver returns true when the puzzle is solved and false when no solution exists.

## REST API
`POST /api/sudoku/solve`
- Content type: `multipart/form-data`
- Field: `file` (a `.txt` puzzle file)

Validation rules for the upload:

- The file is required and not empty.
- Maximum size is 10 KB.
- Content type must be `text/plain` and the extension must be `.txt`.

**Responses**

| Status | Meaning | Body |
|---|---|---|
| 200 OK | Puzzle solved | `Solved. Solution saved at: <path>` |
| 400 Bad Request | Invalid upload or invalid puzzle content.

## Running the project
run `SodukoApplication` from your IDE. The server starts on `http://localhost:8080`.

## Possible improvements
Interactive play: let the user type numbers into the board (row, column, value) instead of only uploading a file.
