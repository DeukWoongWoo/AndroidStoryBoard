package Analysis.Play.Contents;

import org.jdesktop.swingx.JXComboBox;

import javax.swing.*;
import java.awt.*;

/**
 * Created by woong on 2016-03-04.
 */
public class ActivityCreatePanel extends JPanel{
    public ActivityCreatePanel(){
        setLayout(new GridLayout(2,1));

        init();
    }

    private void init(){
        JPanel classPanel = new JPanel();
        classPanel.setLayout(new FlowLayout());

        classPanel.add(new JLabel("Class Name : "));

        JTextField className = new JTextField();
        className.setPreferredSize(new Dimension(100, 30));
        classPanel.add(className);

        add(classPanel);

        JPanel executePanel = new JPanel();
        executePanel.setLayout(new FlowLayout());

        JButton create = new JButton("생성");
        JButton delete = new JButton("삭제");

        executePanel.add(create);
        executePanel.add(delete);

        add(executePanel);

    }
}
