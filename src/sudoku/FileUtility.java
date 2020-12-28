package sudoku;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class FileUtility {

    // Đường dẫn nơi lưu file trò chơi, best-time và puzzle
    protected static final String PATH = System.getProperty("user.home") +
            System.getProperty("file.separator") + "Sudoku" + System.getProperty("file.separator");
    
//    protected static final String MY_PUZZLES_PATH = FileUtility.PATH + "My Puzzles" +
//            System.getProperty("file.separator");


    // Lưu Sudoku puzzle vào một tệp lưu trong đường dẫn thư mục được chỉ định 
    // cũng như độ khó của nó, để tải và tiếp tục chơi trò chơi sau này.
    // p: sudoku puzzle để save.
    // difficulty: độ khó.
    // path: đường dẫn đầy đủ để lưu puzzle
    protected static void saveGame(SudokuPuzzle p, int difficulty, String path) {

        // Nếu puzzle = null --> null
        if (p == null) {
            WindowUtility.errorMessage("Có lỗi xảy ra khi lưu Puzzle rồi !",
                    "Lỗi khi lưu Puzzle");
            return;
        }

        // Ghi game vào file
        PrintWriter writer = null;
        try {

            // Tạo đối tượng writer để ghi vào file
            File file = new File(path);
            file.createNewFile();
            writer = new PrintWriter(new FileWriter(path));

            // Ghi các trạng thái puzzle, thời gian và độ khó vào file
            writer.write(p.initialPuzzleState() + "\n");
            writer.write(p.currentPuzzleState() + "\n");
            writer.write(Integer.toString(difficulty) + "\n");
            writer.write(Integer.toString(BestTimes.time));
            writer.close();
        } catch (Exception e) {/* Ignore exceptions */} finally {writer.close();}
    }


    // Tải game đã được lưu từ đường dẫn
    // Trả về sudoku puzzle đã lưu trong file
    // path: đường dẫn nơi lưu file puzzlle
    protected static SudokuPuzzle loadGame(String path) {

        // Khai báo biến
        String line;
        SudokuPuzzle p;
        int[][] board;
        int k;
        BufferedReader reader = null;

        // Load game đã lưu từ file
        try {

            // Tạo đối tượng reader để đọc file
            FileInputStream file = new FileInputStream(path);
            DataInputStream input = new DataInputStream(file);
            reader = new BufferedReader(new InputStreamReader(input));

            // Đọc trạng thái ban đầu của puzzle
            line = reader.readLine();
            p = new SudokuPuzzle(line);

            // Tạo mảng 2-D để lưu trữ trạng thái trò chơi đã lưu
            line = reader.readLine();
            k = 0;
            board = new int[9][9];

            // Đọc trạng thái theo từng kí tự, load mảng.
            for (int i = 0; i < 9; i++) {
                for (int j = 0; j < 9; j++) {
                    board[i][j] = Integer.parseInt(Character.toString(line.charAt(k++)));
                }
            }

            // Đặt độ khó cho puzzle
            line = reader.readLine();
            p.setDifficulty(Integer.parseInt(line));

            // Đặt hẹn giờ (timer) thành thời gian để lưu
            line = reader.readLine();
            BestTimes.time = Integer.parseInt(line);

            // Đặt board để đọc, trả về puzzle
            p.setArray(board);
            reader.close();
            return p;

        } catch (Exception e) {
            try {
                reader.close();
            } catch (IOException ex) {}
            return null;
        }
    }


    // Kiểm tra xem file có hợp lệ hay không
    // File hợp lệ được bắt đầu bằng chứ cái | số, chỉ chứa chữ cái, số, dấu cách, dấu gạch dưới
    // Dấu gạch ngang, gạch chéo
    //Kết thúc bằng một chữ cái hoặc số
    // s: tên file để kiểm tra
    protected static boolean fileNameValid(String s) {
        return s.matches("^[a-zA-Z0-9][a-zA-Z0-9\\s_/-]*([^\\s_/-])$");
    }


    // Kiểm tra xem tên file có là duy nhất không
    // s: tên file
    // path: đường dẫn file để scan
    // Trẩ về true nếu file là duy nhất
    protected static boolean nameIsUnique(String s, String path) {

        /* Obtain all saved address books from the folder */
        File folder = new File(path);
        File[] fileList = folder.listFiles();

        // Scan từng tên file, đảm bảo không trùng nhau
        for (File file : fileList) {
            if (file.isFile() && file.toString().endsWith(".dat") &&
                    file.getName().toLowerCase().equals(s.toLowerCase()))
                return false;
        }
        return true;
    }


    // Trả về file và tên file được chỉ định
    // Trả về null nếu file không tồn tại
    // n: tên file cần lấy
    // path: đường dẫn hoặc thư mục cần xem
    protected static File getFile(String n, String path) {

        // Đi tới thư mục được chỉ định, nhận danh sách tất cả các file ở đó
        File folder = new File(path);
        File[] fileList = folder.listFiles();

        // Kiểm tra tên từng file
        for (File file : fileList) {
            if (file.isFile() && file.toString().endsWith(".dat") &&
                    file.getName().toLowerCase().equals(n.toLowerCase()))
                return file;
        }
        return null;
    }


    // Sao chép nội dung của tệp bên trong thư mục nguồn được chỉ định
    // và xuất tệp sang thư mục được chỉ định.
    // path: đường dẫn thư mục cần sao chép
    // dest: thư mục đích để lưu file đã sao chép
    protected static boolean copyFile(String path, String dest) {

        // Tạo luồng
        FileInputStream fis = null;
        FileOutputStream fos = null;
        boolean flag = true;

        try {

            // Mở 2 luồng để thực hiện việc sao chép
            File sourceFile = new File(path);
            File destinationFile = new File(dest);
            fis = new FileInputStream(sourceFile);
            fos = new FileOutputStream(destinationFile);

            // Copy nội dung theo dòng
            int bufferSize;
            byte[] buffer = new byte[512];
            while ((bufferSize = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, bufferSize);
            }

            // Đóng luồng
            fis.close();
            fos.close();
            return true;
        } catch (Exception e) {
            flag = false;
        } finally {
            try {
                fis.close();
                fos.close();
            } catch (Exception e) {}
            return flag;
        }
    }
    
    // Tải cài đặt của chương trình từ tệp lưu 'settings.txt'
    // đặt cài đặt của chương trình như được chỉ định.
    protected static void loadSettings() {

        // Tải setting, tạo đối tượng reader để đọc file
        try (BufferedReader reader =
                new BufferedReader(new FileReader(FileUtility.PATH + "settings.txt"))) {

            // Read setting để hiện thị game-timer
            if (reader.readLine().equals("true"))
                Settings.showTimer(true);
            else
                Settings.showTimer(false);

            // Reading setting để hiện thị legal-moves
            if (reader.readLine().equals("true"))
                Settings.showLegal(true);
            else
                Settings.showLegal(false);

            // Read setting để highlighting các số
            if (reader.readLine().equals("true"))
                Settings.showHighlighted(true);
            else
                Settings.showHighlighted(false);

            // Read setting để highlighting số không hợp lệ (illegal numbers)
            if (reader.readLine().equals("true"))
                Settings.showConflictingNumbers(true);
            else
                Settings.showConflictingNumbers(false);

            // Read setting để showing gợi ý (hints) */
            if (reader.readLine().equals("true"))
                Settings.showHints(true);
            else
                Settings.showHints(false);

            // Read setting để showing giải pháp (solutions) */
            if (reader.readLine().equals("true"))
                Settings.showSolutions(true);
            else
                Settings.showSolutions(false);
            reader.close();
        } catch (Exception e) {/* Ignore exceptions */}
    }


    // Lưu các setting hiện tại vào file setting.txt
    // Được thực hiện từ class Setting.java
    protected static void saveSettings() {

        // Tạo file mới
        File file = new File(FileUtility.PATH + "settings.txt");

        // Mở file để ghi nội dung
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {

            // Ghi các giá trị boolean vào file
            writer.write(Boolean.toString(Settings.showTimer()) + "\n");
            writer.write(Boolean.toString(Settings.showLegal()) + "\n");
            writer.write(Boolean.toString(Settings.showHighlighted()) + "\n");
            writer.write(Boolean.toString(Settings.showConflictingNumbers()) + "\n");
            writer.write(Boolean.toString(Settings.showHints()) + "\n");
            writer.write(Boolean.toString(Settings.showSolutions()));
            writer.close();
        } catch (Exception e) {/* Ignore exceptions */}
    }


    // Load tất cả các files chứa best-times
    // Và load best-time vào danh sách hiển thị
    protected static void loadBestTimes() {
        FileUtility.loadBestTimes("novice.dat");
        FileUtility.loadBestTimes("easy.dat");
        FileUtility.loadBestTimes("medium.dat");
        FileUtility.loadBestTimes("hard.dat");
        FileUtility.loadBestTimes("expert.dat");
    }


    // Phương thức tải riêng lẻ file best-times
    // s: tên file
    private static void loadBestTimes(String s) {

        // Open và read file chứa best-time
        try {

            // Tạo reader để đọc từ file
            FileInputStream file = new FileInputStream(FileUtility.PATH + s);
            DataInputStream input = new DataInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));

            // Arraylist để lưu giữ lại times
            ArrayList<HighScoreNode> list;
            switch (s) {
                case "novice.dat":  // Novice best-times
                    list = BestTimes.novice;
                    break;
                case "easy.dat":    // Easy best-times
                    list = BestTimes.easy;
                    break;
                case "medium.dat":  // Medium best-times
                    list = BestTimes.medium;
                    break;
                case "hard.dat":    // Hard best-times
                    list = BestTimes.hard;
                    break;
                default:            // Expert best-times
                    list = BestTimes.expert;
                    break;
            }

            // Đọc từng dòng của file
            String str = reader.readLine();
            while (str != null) {

                // Đọc dòng, tách dữ liệu khỏi dấu phẩy, chuyển data --> số nguyên
                String[] data = str.split(",");
                int score = Integer.parseInt(data[0]);

                // Tạo nút và thêm nó vào danh sách
                HighScoreNode node = new HighScoreNode(score);
                node.setDate(data[1]);
                list.add(node);
                str = reader.readLine();

                // Kết thúc file, đóng reader
            } reader.close();
        } catch (Exception e) {/* Ignore exceptions */}
    }


    // Lưu tất cả best-times vào files.
    protected static void saveBestTimes() {
        FileUtility.saveBestTimes("novice.dat");
        FileUtility.saveBestTimes("easy.dat");
        FileUtility.saveBestTimes("medium.dat");
        FileUtility.saveBestTimes("hard.dat");
        FileUtility.saveBestTimes("expert.dat");
    }


    // Lưu danh sách best-time vào file
    // s: tên file cần lưu
    private static void saveBestTimes(String s) {

        // Ghi file
        try {

            // Tạo file-writer, ghi vào file
            File file = new File(FileUtility.PATH + s);
            file.createNewFile();
            PrintWriter writer = new PrintWriter(new FileWriter(FileUtility.PATH + s));

            // Arraylist để đọc thời gian */
            ArrayList<HighScoreNode> list;
            switch (s) {
                case "novice.dat":  // Novice best-times
                    list = BestTimes.novice;
                    break;
                case "easy.dat":    // Easy best-times
                    list = BestTimes.easy;
                    break;
                case "medium.dat":  // Medium best-times
                    list = BestTimes.medium;
                    break;
                case "hard.dat":    // Hard best-times
                    list = BestTimes.hard;
                    break;
                default:            // Expert best-times
                    list = BestTimes.expert;
                    break;
            }

            // Ghi dữ liệu của mỗi node vào file
            for (HighScoreNode node : list) {
                writer.print(Integer.toString(node.getScore()));
                writer.print(",");
                writer.print(node.getDate());
                writer.print("\n");
            }

            // Kết thúc file, đóng write
            writer.close();

        } catch (Exception e) {/* Ignore exceptions */}
    }

}