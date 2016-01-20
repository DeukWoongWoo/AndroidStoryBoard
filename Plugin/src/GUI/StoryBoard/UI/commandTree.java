package GUI.StoryBoard.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by 우철 on 2016-01-20.
 */
public class commandTree implements ActionListener{
    private JPanel commandTree;
    private JButton command_b;
    private JButton hide_b;
    private JPanel commandTree_main;
    private JButton button1;
    private JButton button2;
    private JButton button3;

    public commandTree() {
        command_b.addActionListener(this);
        hide_b.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==hide_b){
            commandTree_main.setVisible(false);
        }
        else if(e.getSource()== command_b)
        {
            commandTree_main.setVisible(true);
        }
    }

}
