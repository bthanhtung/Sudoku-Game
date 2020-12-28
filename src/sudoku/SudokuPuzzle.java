package sudoku;

public class SudokuPuzzle {

    // Khai báo các biến private
    private int difficulty;
    private String initialState;
    private int[][] board;


    // Hàm xây dựng được truyền vào 81 kí tự số
    public SudokuPuzzle() {
        this("000000000000000000000000000000000000000000000000000000000000000000000000000000000");
    }


    // Hàm xây dựng có đối số, nhận vào trạng thái ban đầu của puzzle
    public SudokuPuzzle(String init) {

        // Khai báo & khởi tạo các biến
        this.difficulty = 0;
        this.initialState = init;
        this.board = new int[9][9];
        char[] chars = init.toCharArray();
        int temp, index = 0;

        // Lặp qua chuỗi đã cho để thiết lập bảng
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                temp = Character.getNumericValue(chars[index++]);
                if (temp == -1)     // Nếu không phải số nguyên thì được đặt là 0
                    this.board[i][j] = 0;
                else
                    this.board[i][j] = temp;
            }
        }
    }

    // Chèn các số được chỉ định vào bảng và vị trí (x,y)
    // Nếu số hợp lệ -> true, không hợp lệ -> false
    // val: số để chèn vào sudoku board, 1-9
    // r: hàng để chèn số vào
    // c: cột để chèn số vào
    public boolean insert(int val, int r, int c) {
        if (1 > val || val > 9 || 0 > r || r > 8 || 0 > c || c > 8)
            return false;
        this.board[r][c] = val;
        boolean[] legalMoves = this.getLegalMoves(r, c);
        return legalMoves[this.board[r][c]-1];
    }


    // Xóa giá trị tại vị trí được chỉ định khỏi Sudoku board.
    // r: hàng có giá trị bị xóa
    // c: cột có giá trị bị xóa
    public void remove(int r, int c) {
        if (0 > r || r > 8 || 0 > c || c > 8)
            return;
        this.board[r][c] = 0;
    }

    // Trả về danh sách các số hợp lệ có thể chèn được vào ô đang chỉ định
    // r: row | c: column
    public boolean[] getLegalMoves(int r, int c) {
        boolean[] legalMoves = {true, true, true, true, true, true, true, true, true};
        legalMoves = this.legalMovesInRow(r, c, legalMoves);
        legalMoves = this.legalMovesInColumn(c, r, legalMoves);
        legalMoves = this.legalMovesInSubGrid(r, c, legalMoves);
        return legalMoves;
    }


    // Trả về số được chỉ định trên board. Nếu trống --> 0
    // r: hàng nhận giá trị
    // c: cột nhận giá trị
    public int getValue(int r, int c) {
        return this.board[r][c];
    }


    // Trả về true nếu Puzzle có thể giải được - không có hàng | cột | block nào trùng.
    // Nếu không --> false
    public boolean isPossible() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (this.board[i][j] == 0)
                    continue;
                boolean temp[] = this.getLegalMoves(i, j);
                if (!temp[this.board[i][j] - 1])
                    return false;
            }
        }
        return true;
    }


    // Kiểm tra xem Puzzle đã giải hoàn chỉnh chưa
    // Nếu chưa --> false, ngược lại --> true

    public boolean isComplete() {

        // Kiểm tra tất cả các hàng
        for (int i = 0; i < 9; i++) {
            if (!this.rowIsComplete(i))
                return false;
        }

        // Kiểm tra tất cả các cột
        for (int i = 0; i < 9; i++) {
            if (!this.columnIsComplete(i))
                return false;
        }

        // Kiểm tra các lưới con (block)
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (!this.subGridIsComplete(i * 3, j * 3))
                    return false;
            }
        }

        // Tất cả các cràng buộc ddều thỏa mã
        return true;
    }


    // In Sudoku Puzzle 
    public void print() {
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (this.board[i][j] != 0)
                    System.out.print(this.board[i][j] + " ");
                else
                    System.out.print(". ");
                if (j == 2 || j == 5)
                    System.out.print("| ");
            }
            System.out.println();
            if (i == 2 || i == 5)
                System.out.println("------+-------+------");
        }
    }


    // Đặt lại Sudoku Puzzle về trạng thái ban đầu
    public void resetPuzzle() {
        this.board = new int[9][9];
        char[] chars = this.initialState.toCharArray();
        int temp, index = 0;

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                temp = Character.getNumericValue(chars[index++]);
                if (temp == -1)
                    this.board[i][j] = 0;
                else
                    this.board[i][j] = temp;
            }
        }
    }


    // Hoàn toàn đặt lại Puzzle về trạng thái trống và đặt lại trạng thái puzzle
    protected void hardReset() {
        for (int i = 0; i < 9; i++)
            for (int j = 0; j < 9; j++)
                this.board[i][j] = 0;
        this.initialState = this.currentPuzzleState();
    }


    // Trả về các số đã được fill trong puzzle 
    protected int getNumberFilled() {
        int amt = 0;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                if (this.board[i][j] != 0)
                    amt++;
            }
        }
        return amt;
    }


    // Trả về danh sách các ô xung đột trong puzzle
    // Chuỗi chứa danh sách các ô này có định dạng "(i, j) (i, j) (i, j) ..."
    // trong đó i là hàng và j là cột.
    // r: row, c: column
    // Các ô xung đột sẽ được làm nổi bật
    protected String getConflictingSquares(int r, int c) {
        String str = "";
        str = this.getConflictingRow(r, c, str);
        str = this.getConflictingColumn(c, r, str);
        str = this.getConflictingSubGrid(r, c, str);
        return str;
    }


    //  Trả về mảng số nguyên 2 chiều đại diện cho Sudoku board
    public int[][] toArray() {
        return this.board;
    }


    // Đặt Sudoku board thành mảng số nguyên 2 chiều được chỉ định. 
    // Được sử dụng sau khi được chuyển qua Sudoku Solver và tải lên giao diện người dùng.
    // b: mảng để đặt Sudoku board
    protected void setArray(int[][] b) {
        this.board = b;
    }


    // Trả về độ khó của puzzle (1-5)
    // Hiện thị trong Sudoku JFrame
    protected int getDifficulty() {
        return this.difficulty;
    }


    // Đặt độ khó của puzzle
    // d = difficulty
    protected void setDifficulty(int d) {
        this.difficulty = d;
    }


    // Trả về chuỗi đại diện cho puzzle khi nó được khởi tạo
    // Dùng để lưu tiến trình trò chơi (progress midgame)
    protected String initialPuzzleState() {
        return this.initialState;
    }


    // Đặt trạng thái ban đầu của Sudoku
    // s: trạng thái ban đầu đề đặt puzzle
    protected void setInitialPuzzleState(String s) {
        this.initialState = s;
    }


    // Trả về chuỗi sudoku puzzle ở trạng thái hiện tại của nó.
    // Dùng để lưu tiến trình trò chơi (progress midgame)
    protected String currentPuzzleState() {
        String s = "";
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 9; j++) {
                s += this.board[i][j];
            }
        }
        return s;
    }

    // Trả về true nếu hàng hợp lệ
    private boolean rowIsComplete(int r) {
        boolean[] row = {false, false, false, false, false, false, false, false, false};
        for (int i = 0; i < 9; i++) {
            if (this.board[r][i] == 0)
                return false;
            else if (row[this.board[r][i]-1])
                return false;
            else
                row[this.board[r][i]-1] = true;
        }
        return true;
    }

    // Trả về true nếu cột hợp lệ
    private boolean columnIsComplete(int c) {
        boolean[] row = {false, false, false, false, false, false, false, false, false};
        for (int i = 0; i < 9; i++) {
            if (this.board[i][c] == 0)
                return false;
            else if (row[this.board[i][c]-1])
                return false;
            else
                row[this.board[i][c]-1] = true;
        }
        return true;
    }

    // Trả về true nếu sub-grid hợp lệ
    private boolean subGridIsComplete(int r, int c) {
        boolean[] grid = {false, false, false, false, false, false, false, false, false};
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int m = ((r / 3) * 3 + (i % 3));
                int n = ((c / 3) * 3 + (j % 3));
                if (this.board[m][n] == 0)
                    return false;
                else if (grid[this.board[m][n]-1])
                    return false;
                else
                    grid[this.board[m][n]-1] = true;
            }
        }
        return true;
    }

    // Trả về các ô xung đột trong hàng
    private String getConflictingRow(int r, int c, String squares) {

        for (int k = 0; k < 9; k++) {
            if (k == c)
                continue;

            if (this.board[r][c] == this.board[r][k])
                squares += String.format("(%d %d) ", r, k);
        }
        return squares;
    }


    // Trả về các ô xung đột trong cột
    private String getConflictingColumn(int c, int r, String squares) {
        for (int k = 0; k < 9; k++) {
            if (k == r)
                continue;
            if (this.board[r][c] == this.board[k][c])
                squares += String.format("(%d %d) ", k, c);
        }
        return squares;
    }


    // Trả về các ô xung đột trong sub-grid
    private String getConflictingSubGrid(int r, int c, String squares) {
         for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int m = ((r / 3) * 3 + (i % 3));
                int n = ((c / 3) * 3 + (j % 3));
                if (r == m && c == n)
                    continue;
                if (this.board[m][n] == this.board[r][c])
                    squares += String.format("(%d %d) ", m, n);
            }
        }
        return squares;
    }

    // Trả về một mảng hợp lệ trong hàng
    private boolean[] legalMovesInRow(int r, int c, boolean[] list) {
        for (int i = 0; i < 9; i++) {
            if (i == c)
                continue;
            if (this.board[r][i] != 0)
                list[this.board[r][i]-1] = false;
        }
        return list;
    }

   // Trả về một mảng hợp lệ trong cột
    private boolean[] legalMovesInColumn(int c, int r, boolean[] list) {
        for (int i = 0; i < 9; i++) {
            if (i == r)
                continue;
            if (this.board[i][c] != 0)
                list[this.board[i][c]-1] = false;
        }
        return list;
    }

    // Trả về một mảng hợp lệ trong sub-grid
    private boolean[] legalMovesInSubGrid(int r, int c, boolean[] list) {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                int m = ((r / 3) * 3 + (i % 3));
                int n = ((c / 3) * 3 + (j % 3));
                if (r == m && c == n)
                    continue;
                if (this.board[m][n] != 0)
                    list[this.board[m][n]-1] = false;
            }
        }
        return list;
    }
}