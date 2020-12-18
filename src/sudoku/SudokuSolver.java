package sudoku;

 // http://www.programcreek.com/2014/05/leetcode-sudoku-solver-java/
 // http://www.heimetli.ch/ffh/simplifiedsudoku.html

public class SudokuSolver {

    // Khai báo biến
    private final SudokuPuzzle puzzle;
    private final int[][] board;
    private boolean solvable;


    // Hàm xây dựng mặc định
    public SudokuSolver(SudokuPuzzle p) {
        this.puzzle = new SudokuPuzzle(p.initialPuzzleState());
        this.board = this.puzzle.toArray();
        this.solvable = this.puzzle.isPossible();
        if (this.solvable)
            this.solvable = this.solve();
    }


    // Nếu puzzle có thể solve --> true
    // Nếu không --> false
    public boolean isSolvable() {
        return this.solvable;
    }


    // Trả về Sudoku đã sovle hoặc null nếu không giải được
    // Puzzle trả về là bản sao (copy) của puzzle được chuyển (passed) vào contructor nhưng ở trạng thái đã giải.
    public SudokuPuzzle getSolution() {
        if (!this.solvable)
            return null;
        this.puzzle.setArray(this.board);
        return this.puzzle;
    }


    // Phương thức solve puzzle
    // Dùng mảng 2-D
    private boolean solve() {

        // Lặp qua board và bỏ qua các ô !trống
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (this.board[i][j] != 0)
                    continue;

                // (Cố) thử từng ô, nếu không hợp lệ --> quay lại thử các ô khác
                for (int k = 1; k <= 9; k++) {
                    this.board[i][j] = k;
                    if (this.puzzle.insert(k, i, j) && solve())
                        return true;
                    this.board[i][j] = 0;
                }
                return false;
            }
        }

        return this.puzzle.isComplete();
    }

}