package sudoku;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        
        // Tải thư mục Sudoku trên máy tính người dùng
        File dir = new File(FileUtility.PATH);
        if (!dir.exists())
            dir.mkdir();

        // Tải các cài đặt
        FileUtility.loadSettings();
        FileUtility.loadBestTimes();

        // Tạo cửa sổ mới
        MainFrame f = new MainFrame(360, 30);
    }

}