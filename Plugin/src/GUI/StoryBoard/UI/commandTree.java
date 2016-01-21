package GUI.StoryBoard.UI;

import com.intellij.openapi.util.IconLoader;

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
    private Icon commandOn;
    private Icon commandOff;
    private boolean commandTreeVisible =true;

    public commandTree() {
        command_b.addActionListener(this);
        hide_b.addActionListener(this);
        commandOn= IconLoader.findIcon("/icon/commandOn.png");
        commandOff= IconLoader.findIcon("/icon/commandOff.png");
        command_b.setIcon(commandOn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==hide_b){
            commandTree_main.setVisible(false);
            command_b.setIcon(commandOff);
            commandTreeVisible=true;
        }
        else if(e.getSource()== command_b)
        {
            if(commandTreeVisible==false) {
                commandTree_main.setVisible(true);
                command_b.setIcon(commandOn);
                commandTreeVisible=true;
            }
            else
            {
                commandTree_main.setVisible(false);
                command_b.setIcon(commandOff);
                commandTreeVisible=false;
            }
        }
    }

}
