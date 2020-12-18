package sudoku;

import java.awt.Color;

public class GUIColors {

    // Màu Bright-green được sử dụng để highlighting
    protected static final Color GREEN = new Color(51, 220, 51);

    // Màu Dark-green được sử dụng để 'complete' trong status-box khi người dùng complete một puzzle
    protected static final Color DARK_GREEN = new Color(0, 175, 0);

    // Màu Blue được dùng để tô màu các số có thể chỉnh sửa trong sudoku puzzle
    protected static final Color BLUE = new Color(0, 51, 190);

    // Màu Red được sử dụng để tô màu các số không hợp lệ
    protected static final Color RED = new Color(255, 0, 0);

    /* A bright yellowish color used for coloring the currently focused text pane */
    protected static final Color SELECTED = new Color(255, 255, 150);

    // Màu light-blue được sử dụng để tô màu nền cho Sudoku puzzle
    protected static final Color BACKGROUND = new Color(204, 204, 255);

    // Màu yellowish được sử dụng để tô màu nền cho menus
    protected static final Color MENU_BACKGROUND = new Color(255, 255, 204);

    // Solid black
    protected static final Color BLACK = Color.BLACK;

    // Solid white
    protected static final Color WHITE = Color.WHITE;

}