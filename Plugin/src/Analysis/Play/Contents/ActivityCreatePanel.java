package Analysis.Play.Contents;

import Analysis.RedoUndo.CommandManager;
import org.jdesktop.swingx.JXComboBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by woong on 2016-03-04.
 */
public class ActivityCreatePanel extends JPanel{
    private CommandManager commandManager;

    public ActivityCreatePanel(){
        setLayout(new GridLayout(2,1));

        commandManager = CommandManager.getInstance();

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
        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commandManager.createActivity(className.getText());
            }
        });

        JButton delete = new JButton("삭제");
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commandManager.deleteActivity(className.getText());
            }
        });

        executePanel.add(create);
        executePanel.add(delete);

        add(executePanel);

    }
}
