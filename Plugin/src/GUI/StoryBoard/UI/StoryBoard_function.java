package GUI.StoryBoard.UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Created by 우철 on 2016-01-25.
 */
public class StoryBoard_function extends JPanel implements MouseMotionListener, MouseListener{
    private JLabel name;
    private ArrayList<Activity> items = new ArrayList<>();
    private int count;
    public boolean YN =false;
    public JButton plus_button = new JButton();
    public boolean b_YN = false;
    Activity act = new Activity(10,10);
    Activity temp = new Activity(20,20);

    public StoryBoard_function() {
        System.out.println("storyboard create function is called");
        makeActivityName("abcd");
        makeActivityName("test");
        makeActivityName("wow");

        this.addMouseMotionListener(this);
        this.addMouseListener(this);

        plus_button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.print("clciked plus button");
                b_YN=true;
            }
        });
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
        act.draw_Activity(g);
        temp.draw_Activity(g);
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

    @Override
    public void mouseDragged(MouseEvent e) {
        System.out.print("Mouse Dragged" + e.getPoint().x + ", "+ e.getPoint().y +"\n");
      /*  act.set_point(e.getPoint().x,e.getPoint().y);
        repaint();*/
        if(YN==true)
        {
            act.set_point(e.getPoint().x,e.getPoint().y);
            repaint();
        }


    }

    @Override
    public void mouseMoved(MouseEvent e) {
        System.out.print("Mouse Move" + e.getPoint().x + ", "+ e.getPoint().y +"\n");

    }

    @Override
    public void mouseClicked(MouseEvent e) {
        System.out.print("Mouse Clicked" + e.getPoint().x + ", "+ e.getPoint().y +"\n");

    }

    @Override
    public void mousePressed(MouseEvent e) {
        System.out.print("Mouse Pressed" + e.getPoint().x + ", "+ e.getPoint().y +"\n");
        if(act.x_left<e.getPoint().x && act.x_right>e.getPoint().x && act.y_top<e.getPoint().y && act.y_bottom>e.getPoint().y)
        {
            YN=true;
        }
        plus_button.setVisible(false);
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        System.out.print("Mouse Released" + e.getPoint().x + ", "+ e.getPoint().y +"\n");
        YN=false;
        plus_button.setVisible(true);
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        System.out.print("Mouse Enterd" + e.getPoint().x + ", "+ e.getPoint().y +"\n");
        if(b_YN==true)
        {
            Activity aasdfasdf = new Activity(e.getPoint().x, e.getPoint().y);
            items.add(aasdfasdf);
            temp.set_point(e.getPoint().x ,e.getPoint().y);
            b_YN=false;
        }
        repaint();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        System.out.print("Mouse EXIT" + e.getPoint().x + ", "+ e.getPoint().y +"\n");
        b_YN=false;
    }


}

class Activity implements ActionListener{
    int x_left, y_top;
    int x_right, y_bottom;
    public Activity(int x, int y){
        this.x_left =x;
        this.y_top =y;
        this.x_right = x + 200;
        this.y_bottom = y + 384;
    }

    public void draw_Activity(Graphics g){
        g.setColor(Color.BLACK);
        g.fillRoundRect(x_left, y_top, 200, 384 ,10, 10);
        g.setColor(Color.white);
        g.fillRoundRect(x_left +4, y_top +32, 192, 320, 10,  10);
        g.setColor(Color.black);
        g.fillRect(x_left +4, y_top +32, 192, 12);
        g.setColor(new Color(46,82,154));
        g.fillRect(x_left +4, y_top +44, 192, 32);

    }

    public void set_point(int x, int y){
        this.x_left =x;
        this.y_top =y;
        this.x_right = x + 200;
        this.y_bottom = y + 384;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}