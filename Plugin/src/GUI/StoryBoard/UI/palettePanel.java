package GUI.StoryBoard.UI;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by 우철 on 2016-02-17.
 */
public class palettePanel extends JPanel {
    JPanel leftPanel = new JPanel();
    JPanel CenterPanel =new JPanel();
    JPanel rightTopPanel = new JPanel();
    JPanel rightCenterPanel = new JPanel();
    JButton palettelToggleB = new JButton("palette");
    JButton hideButton = new JButton("hide");

    JButton activity_b = new JButton("Activity");
    JButton linear_b = new JButton("Linear Layout");
    JButton relative_b = new JButton("Relative Layout");
    JButton button_b = new JButton("Button");
    JButton radio_b = new JButton("Radio Button");
    JButton none_b = new JButton("none");
    JButton image_b =new JButton("Image View");

    public int choice = 0;

    public palettePanel() {
        //기존의 jPanel setting
        setLayout(new BorderLayout());



            add(leftPanel,"West");
                leftPanel.setBorder(new LineBorder(Color.black));
                leftPanel.setPreferredSize(new Dimension(100, 100));
                leftPanel.setLayout(new FlowLayout());
                leftPanel.add(palettelToggleB);

             add(CenterPanel, "Center");
                CenterPanel.setLayout(new BorderLayout());
                CenterPanel.setBorder(new LineBorder(Color.black));
                CenterPanel.add(rightTopPanel,"North");
                    rightTopPanel.setBackground(Color.BLACK);
                    rightTopPanel.setPreferredSize(new Dimension(250,50));
                    rightTopPanel.add(hideButton);
                        hideButton.setSize(50,50);
                CenterPanel.add(rightCenterPanel, "Center");
                        rightCenterPanel.setLayout(new GridLayout(20,1));
                        rightCenterPanel.add(activity_b);
                        rightCenterPanel.add(linear_b);
                        rightCenterPanel.add(relative_b);
                        rightCenterPanel.add(button_b);
                        rightCenterPanel.add(radio_b);
                        rightCenterPanel.add(none_b);
                        rightCenterPanel.add(image_b);
        activity_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice=1;
            }
        });
        linear_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice=2;
            }
        });
        relative_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice=3;
            }
        });
        button_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice=4;
            }
        });
        radio_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice=5;
            }
        });
        image_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice =6;
            }
        });
        none_b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                choice=0;
            }
        });



        palettelToggleB.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(CenterPanel.isVisible()==true)
                    CenterPanel.setVisible(false);
                else
                    CenterPanel.setVisible(true);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        hideButton.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                CenterPanel.setVisible(false);
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });

    }



    public int getChoice() {
        return choice;
    }

    public void setChoice(int choice) {
        this.choice = choice;
    }
}
