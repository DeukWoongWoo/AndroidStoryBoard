package GUI.StoryBoard.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by 우철 on 2016-01-20.
 */
public class palette implements ActionListener{
    private JPanel palette;
    private JButton palette_b;
    private JPanel tagTable;
    private JButton hide_b;
    private JPanel mainContent;
    private JButton button1;
    private JButton button2;
    private JButton button3;
    private JPanel palateTop;
    private JPanel palateContent;
    private JPanel palletContent;

    public palette() {
        palette_b.addActionListener(this);
        hide_b.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==hide_b){
            mainContent.setVisible(false);
            palletContent.setBackground(Color.darkGray);
        }
        else if(e.getSource()== palette_b)
        {
            mainContent.setVisible(true);
            palletContent.setBackground(Color.gray);
        }
    }

}
