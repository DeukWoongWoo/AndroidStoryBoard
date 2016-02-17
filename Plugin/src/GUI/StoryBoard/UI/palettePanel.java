package GUI.StoryBoard.UI;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
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
    MouseListener li;

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


}
