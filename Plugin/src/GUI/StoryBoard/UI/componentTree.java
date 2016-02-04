package GUI.StoryBoard.UI;

import com.intellij.openapi.util.IconLoader;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by 우철 on 2016-01-20.
 */
public class componentTree implements ActionListener{
    private JPanel componentTree;
    private JButton component_b;
    private JButton hide_b;
    private JPanel componentTree_main;
    private Icon commponentOn;
    private Icon commponentOff;
    private boolean commandTreeVisible =true;

    public componentTree() {
        component_b.addActionListener(this);
        hide_b.addActionListener(this);
        commponentOn = IconLoader.findIcon("/icon/componentOn.png");
        commponentOff = IconLoader.findIcon("/icon/componentOff.png");
        component_b.setIcon(commponentOn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==hide_b){
            componentTree_main.setVisible(false);
            component_b.setIcon(commponentOff);
            commandTreeVisible=true;
        }
        else if(e.getSource()== component_b)
        {
            if(commandTreeVisible==false) {
                componentTree_main.setVisible(true);
                component_b.setIcon(commponentOn);
                commandTreeVisible=true;
            }
            else
            {
                componentTree_main.setVisible(false);
                component_b.setIcon(commponentOff);
                commandTreeVisible=false;
            }
        }
    }

}
