package sudoku;

public class Settings {

    // Khai báo các biến thành viên
    private static boolean showTimer = true;    // hiện thị timer
    private static boolean showLegal = true;    // hiện thị legal-moves
    private static boolean highlight = true;    // double-click để đánh dấu
    private static boolean conflictingNumbers = true;   // các số không hợp lệ được đánh dấu
    private static boolean hints = true;        // bật gợi ý
    private static boolean solutions = true;    // bật giải pháp


    // Trả về true nếu tính năng 'timer' được bật
    protected static boolean showTimer() {
        return Settings.showTimer;
    }


    // Trả về true nếu tính năng 'legal-moves' được bật
    protected static boolean showLegal() {
        return Settings.showLegal;
    }


    // Trả về true nếu tính năng 'highlight' được bật
    protected static boolean showHighlighted() {
        return Settings.highlight;
    }


    // Trả về true nếu tính năng 'các số không hợp lệ' được bật
    protected static boolean showConflictingNumbers() {
        return Settings.conflictingNumbers;
    }


    // Trả về true nếu tính năng 'gợi ý' được bật
    protected static boolean showHints() {
        return Settings.hints;
    }

    // Trả về true nếu tính năng 'solution' được bật
    protected static boolean showSolutions() {
        return Settings.solutions;
    }


    // Đặt tính năng 'timer'
    // flag: cờ để bật/tắt tính năng
    protected static void showTimer(boolean flag) {
        Settings.showTimer = flag;
    }


    // Đặt tính năng 'legal-moves'
    // flag: cờ để bật/tắt tính năng
    protected static void showLegal(boolean flag) {
        Settings.showLegal = flag;
    }


    // Đặt tính năng 'highlight'
    // flag: cờ để bật/tắt tính năng
    protected static void showHighlighted(boolean flag) {
        Settings.highlight = flag;
    }


    // Đặt tính năng 'các số không hợp lệ'
    // flag: cờ để bật/tắt tính năng
    protected static void showConflictingNumbers(boolean flag) {
        Settings.conflictingNumbers = flag;
    }


    // Đặt tính năng 'gợi ý'
    // flag: cờ để bật/tắt tính năng
    protected static void showHints(boolean flag) {
        Settings.hints = flag;
    }


    // Đặt tính năng 'giải pháp'
    // flag: cờ để bật/tắt tính năng
    protected static void showSolutions(boolean flag) {
        Settings.solutions = flag;
    }

}