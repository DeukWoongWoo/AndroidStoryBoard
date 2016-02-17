package GUI.StoryBoard.UI;

import javax.swing.*;
import java.awt.*;

/**
 * Created by 우철 on 2016-02-17.
 */
public class palettePanel extends JPanel {
    JPanel leftPanel = new JPanel();
    JPanel CenterPanel =new JPanel();
    JPanel rightTopPanel = new JPanel();
    JPanel rightCenterPanel = new JPanel();
    JButton palettelToggleB = new JButton("palette");
    JButton hideButton = new JButton();
    public palettePanel() {
        //기존의 jPanel setting
        setSize(500,5000);
        setLayout(new BorderLayout());
        add(leftPanel,"West");
        add(CenterPanel, "Center");



    }
}
