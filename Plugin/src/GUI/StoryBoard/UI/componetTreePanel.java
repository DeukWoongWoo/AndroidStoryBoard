package GUI.StoryBoard.UI;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by 우철 on 2016-02-18.
 */
public class componetTreePanel extends JPanel {
    JPanel rightPanel = new JPanel();
    JPanel CenterPanel =new JPanel();
    JPanel leftTopPanel = new JPanel();
    JPanel leftCenterPanel = new JPanel();
    JButton componetTreeB = new JButton("ComponetTree");
    JButton hideB = new JButton("hide");
    MouseListener li;

    public componetTreePanel() {
        //기존의 jPanel setting
        setLayout(new BorderLayout());

        add(rightPanel,"East");
        rightPanel.setBorder(new LineBorder(Color.black));
        rightPanel.setPreferredSize(new Dimension(100, 100));
        rightPanel.setLayout(new FlowLayout());
        rightPanel.add(componetTreeB);

        add(CenterPanel, "Center");
        CenterPanel.setLayout(new BorderLayout());
        CenterPanel.setBorder(new LineBorder(Color.black));
        CenterPanel.add(leftTopPanel,"North");
        leftTopPanel.setBackground(Color.BLACK);
        leftTopPanel.setPreferredSize(new Dimension(250,50));
        leftTopPanel.add(hideB);
        hideB.setSize(50,50);
        CenterPanel.add(leftCenterPanel, "Center");


        componetTreeB.addMouseListener(new MouseListener() {
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
        hideB.addMouseListener(new MouseListener() {
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
