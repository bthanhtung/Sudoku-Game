package sudoku;

import java.awt.Toolkit;
import javax.swing.JOptionPane;

public class WindowUtility {

    // Hiển thị cửa sổ + thông báo cho người chơi
    public static void displayInfo(String msg, String title) {
        Runnable runnable =
            (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.asterisk");
        if (runnable != null)
            runnable.run();
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.INFORMATION_MESSAGE);
    }


    // Nhắc người chơi nhập chuỗi thông qua cửa sổ --> trả về chuỗi để sử dụng
    // msg: thông báo của prompt-box
    public static String getEntry(String msg) {
        Runnable runnable =
            (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.asterisk");
        if (runnable != null)
            runnable.run();
        return JOptionPane.showInputDialog(null, msg, "");
    }


    // Ask Yes/No ~ true/false
    // qu: quests để hỏi người chơi
    // title: Nội dụng question
    public static boolean askYesNo(String qu, String title) {
        Runnable runnable =
            (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.exclamation");
        if (runnable != null)
            runnable.run();
        return JOptionPane.showConfirmDialog(null, qu, title,
               JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) == JOptionPane.YES_OPTION;
    }


    //Hiển thị error-window với thông báo cho người chơi
    // msg: thông báo lỗi
    // title: nội dụng của msg
    public static void errorMessage(String msg, String title) {
        Runnable runnable =
            (Runnable) Toolkit.getDefaultToolkit().getDesktopProperty("win.sound.hand");
        if (runnable != null)
            runnable.run();
        JOptionPane.showMessageDialog(null, msg, title, JOptionPane.ERROR_MESSAGE);
    }

}