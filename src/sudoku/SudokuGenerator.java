package sudoku;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

public class SudokuGenerator {

    // Khai báo biến
    private final SudokuPuzzle puzzle;


    // Hàm xây dựng mặc định
    public SudokuGenerator(int i) {

        // Đặt lại biến độ khó nếu quá thấp/cao
        if (i < 1)
            i = 1;
        else if (i > 5)
            i = 5;

        // Tạo puzzle
        this.puzzle = getPuzzle(i);
    }


    // Được gọi trong contructor
    // Đọc từ file được chỉ định với độ khó tương ứng
    private SudokuPuzzle getPuzzle(int i) {

        // Đọc từ file có chứa puzzle
        try {

            // Tạo stream để đọc file
            InputStream stream = SudokuGenerator.class.getResourceAsStream(i + ".txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            // Randomvà chọn ngầu nhiên một puzzle từ file
            Random r = new Random();
            int k = r.nextInt(10000);
            String line = reader.readLine();

            // Đọc qua k puzzlze
            for (int j = 0; j < k-1; j++)
                line = reader.readLine();

            // Lấy và trả về puzzle - đóng reader
            SudokuPuzzle p = new SudokuPuzzle(line);
            p.setDifficulty(i);
            reader.close();
            return p;

        } catch (Exception e) {return null;}
    }


    // Trả về sudoku đã tạo
    // Nếu không thành công --> null
    public SudokuPuzzle getPuzzle() {
        return this.puzzle;
    }

}