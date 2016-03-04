package Analysis.Play.Contents;

import org.jdesktop.swingx.JXComboBox;

import javax.swing.*;
import java.awt.*;

/**
 * Created by woong on 2016-03-04.
 */
public class LibraryPanel extends JPanel {
    public LibraryPanel(){
        setLayout(new GridLayout(4,1));

        init();
    }

    private void init(){
        JXComboBox menu = new JXComboBox();
        menu.addItem("Event");
        menu.addItem("Error");
        menu.addItem("Activity");

        add(menu);

        JPanel idPanel = new JPanel();
        idPanel.setLayout(new FlowLayout());

        idPanel.add(new JLabel("Xml ID : "));

        JTextField xmlID = new JTextField();
        xmlID.setPreferredSize(new Dimension(100, 30));
        idPanel.add(xmlID);

        add(idPanel);

        JPanel layoutPanel = new JPanel();
        layoutPanel.setLayout(new FlowLayout());

        layoutPanel.add(new JLabel("Layout : "));

        JTextField layout = new JTextField();
        layout.setPreferredSize(new Dimension(100, 30));
        layoutPanel.add(layout);

        add(layoutPanel);

        JPanel executePanel = new JPanel();
        executePanel.setLayout(new FlowLayout());

        JButton create = new JButton("생성");
        JButton delete = new JButton("삭제");

        executePanel.add(create);
        executePanel.add(delete);

        add(executePanel);

    }
}
