package GUI.StoryBoard.UI;

import GUI.StoryBoard.storyBoard_func;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created by 우철 on 2016-01-20.
 */
public class storyboard{
    private JPanel storyBoard;
    private JPanel mainContent;
    private JPanel buttonGroup;
    private JButton plus_b;
    private JButton link_b;
    private JButton remove_b;
    private JPanel mainview;

    public storyboard() throws IOException {
        mainview = new storyBoard_func();
        mainview.setVisible(true);
    }

}
