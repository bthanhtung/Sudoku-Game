package sudoku;

import java.util.ArrayList;
import java.util.Collections;

public class BestTimes {

    // Biến tạm để lưu thời gian resume/save của trò chơi
    protected static int time = 0;

    // Lưu thời gian tốt nhất cho novice
    protected static ArrayList<HighScoreNode> novice = new ArrayList<HighScoreNode>();

    // Lưu thời gian tốt nhất cho easy
    protected static ArrayList<HighScoreNode> easy = new ArrayList<HighScoreNode>();

    // Lưu thời gian tốt nhất cho medium
    protected static ArrayList<HighScoreNode> medium = new ArrayList<HighScoreNode>();

    // Lưu thời gian tốt nhất cho hard
    protected static ArrayList<HighScoreNode> hard = new ArrayList<HighScoreNode>();

    // Lưu thời gian tốt nhất cho expert
    protected static ArrayList<HighScoreNode> expert = new ArrayList<HighScoreNode>();


    // Insert thời gian vàoa danh sách tương ứng với độ khó 
    // Nếu là best-time --> true
    // time: thời gian để thêm vào danh sácch.
    // difficulty: độ khó của puzzle được giải
    // (1 = novice, 2 = easy, 3 = medium, 4 = hard, 5+ = expert).
    protected static boolean insertBestTime(int time, int difficulty) {

        HighScoreNode node = new HighScoreNode(time);
        switch (difficulty) {
            case 1:
                BestTimes.novice.add(node);
                Collections.sort(BestTimes.novice);
                if (BestTimes.novice.size() > 10)
                    BestTimes.novice.remove(10);
                return BestTimes.novice.contains(node);
            case 2:
                BestTimes.easy.add(node);
                Collections.sort(BestTimes.easy);
                if (BestTimes.easy.size() > 10)
                    BestTimes.easy.remove(10);
                return BestTimes.easy.contains(node);
            case 3:
                BestTimes.medium.add(node);
                Collections.sort(BestTimes.medium);
                if (BestTimes.medium.size() > 10)
                    BestTimes.medium.remove(10);
                return BestTimes.medium.contains(node);
            case 4:
                BestTimes.hard.add(node);
                Collections.sort(BestTimes.hard);
                if (BestTimes.hard.size() > 10)
                    BestTimes.hard.remove(10);
                return BestTimes.hard.contains(node);
            default:
                BestTimes.expert.add(node);
                Collections.sort(BestTimes.expert);
                if (BestTimes.expert.size() > 10)
                    BestTimes.expert.remove(10);
                return BestTimes.expert.contains(node);
        }
    }


    // Định dạng thời gian hh:mm:ss
    // time: thời gian.
    protected static String timeToString(int time) {

        // Khai báo các biến seconds, minutes và hours
        int sec = (time % 60);
        int min = (time / 60);
        int hrs = ((time / 60) / 60);

        // Trả về thời gian với định dạng hh:mm:ss
        if (hrs == 0)
            return String.format("%d:%02d", min, sec);
        else
            return String.format("%d:%02d:%02d", hrs, min, sec);
    }

}