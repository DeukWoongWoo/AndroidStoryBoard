package GUI.StoryBoard.UI;

import GUI.StoryBoard.Constant;

import javax.swing.*;
import java.awt.*;

/**
 * Created by 우철 on 2016-02-17.
 */
public class menuPanel extends JPanel {
    JButton openB= new JButton("OPEN");
    JButton saveB= new JButton("SAVE");
    JButton refreshB= new JButton("REFRESH");
    JButton redoB= new JButton("REDO");
    JButton undoB= new JButton("UNDO");

    JButton[] buttongroup = {openB,saveB,refreshB,undoB,redoB};

    public menuPanel() {
        setLayout(new FlowLayout(FlowLayout.LEFT));
        setSize(1000,100);
        setBackground(Color.BLACK);

        for(int i=0;i<5;i++)
        {
            buttongroup[i].setSize(Constant.MENUBUTTONSIZE);
            buttongroup[i].setMargin(new Insets(0,0,0,0));
            add(buttongroup[i]);
        }
    }
}
