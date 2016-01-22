package GUI.StoryBoard.UI;

import com.intellij.openapi.util.IconLoader;
import com.intellij.ui.components.JBOptionButton;

import javax.help.MainWindow;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.plaf.IconUIResource;
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
    private JPanel palateTop;
    private JPanel palateContent;
    private JButton etc_b;
    private JPanel palletContent;
    private Icon palatteOn;
    private Icon palatteOff;
    private Icon etcOn;
    private Icon etcOff;
    boolean a=false;
    boolean mainContentVisble=true;
    public palette() {

        palette_b.addActionListener(this);
        hide_b.addActionListener(this);
        etc_b.addActionListener(this);

        palatteOn=IconLoader.findIcon("/icon/palatte_on.png");
        palatteOff=IconLoader.findIcon("/icon/palatte_off.png");
        etcOn= IconLoader.findIcon("/icon/etc_on.png");
        etcOff = IconLoader.findIcon("/icon/etc_off.png");

        palette_b.setIcon(palatteOn);
        etc_b.setIcon(etcOff);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==hide_b){
            mainContent.setVisible(false);
            palette_b.setIcon(palatteOff);
            mainContentVisble=false;
        }
        else if(e.getSource()== palette_b)
        {
            if(mainContentVisble==false) {
                mainContent.setVisible(true);
                palette_b.setIcon(palatteOn);
                mainContentVisble=true;
            }
            else{
                mainContent.setVisible(false);
                palette_b.setIcon(palatteOff);
                mainContentVisble=false;
            }
        }
        else if(e.getSource()==etc_b)
        {
            if(a==false){
                etc_b.setIcon(etcOn);
                a=true;
            }
            else{
                etc_b.setIcon(etcOff);
                a=false;
            }
        }
    }


}
