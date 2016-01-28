package GUI.StoryBoard.UI;

import org.w3c.dom.css.RGBColor;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/**
 * Created by 우철 on 2016-01-25.
 */
public class StoryBoard_function extends JPanel {
    private JLabel name;
    private ArrayList<JLabel> items = new ArrayList<>();
    private int count;

    public StoryBoard_function() {
        System.out.println("storyboard create function is called");
        makeActivityName("abcd");
        makeActivityName("test");
        makeActivityName("wow");
    }

    private void makeActivityName(String id){
        JLabel name = new JLabel(id);
        name.setFont(new Font("Verdana",1,20));
        name.setForeground(Color.white);
        items.add(name);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        System.out.println("paintCoponent is called");
        drawActivity(10,10, g ,0);
        drawActivity(400,10, g, 1);
        drawActivity(800,10,g, 2);
    }

    public void drawActivity(int startx, int starty, Graphics g, int ID)
    {
        g.setColor(Color.BLACK);
        g.fillRoundRect(startx,starty, 200, 384 ,10, 10);
        g.setColor(Color.white);
        g.fillRoundRect(startx+4,starty+32, 192, 320, 10,  10);
        g.setColor(Color.black);
        g.fillRect(startx+4,starty+32, 192, 12);
        g.setColor(new Color(46,82,154));
        g.fillRect(startx+4,starty+44, 192, 32);
     /*      for(JLabel label : items) {
            label.setLocation(startx + 4, starty + 10);
            label.setVisible(true);
            this.add(label);
        }
*/
        name = items.get(ID);
        name.setLocation(startx+4,starty+10);
        name.setVisible(true);
        this.add(name);
    }
}

