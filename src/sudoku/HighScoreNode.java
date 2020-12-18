package sudoku;

import java.text.SimpleDateFormat;
import java.util.Date;

public class HighScoreNode implements Comparable<HighScoreNode> {

    // Khai báo cácc biến thành viên
    private final int score;
    private String date;


    // Hàm xây dựng
    public HighScoreNode(int score) {
        this.score = score;
        this.date = new SimpleDateFormat("MM/dd/yyyy  h:mm a").format(new Date());
    }


    // Trả về score được lưu bên trong node
    public int getScore() {
        return this.score;
    }


    // Trả về ngày ở định dạng chuỗi (đã được định dạng trước đó)
    public String getDate() {
        return this.date;
    }


    // Đặt ngày được lưu trữ thành ngày được chỉ định
    // date: ngày (mới) để lưu trữ
    public void setDate(String date) {
        this.date = date;
    }


    // So sánh hai node
    // other: node khác để so sánh
    @Override
    public int compareTo(HighScoreNode other) {
        if (this.getScore() < other.getScore())
            return -1;
        else if (this.getScore() > other.getScore())
            return 1;
        else return 0;
    }

}