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
public class ActivityLinkPanel extends JPanel {
    private CommandManager commandManager;

    public ActivityLinkPanel(){
        setLayout(new GridLayout(4,1));
        commandManager = CommandManager.getInstance();
        init();
    }

    private void init(){
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

        JPanel activityPanel = new JPanel();
        activityPanel.setLayout(new FlowLayout());

        activityPanel.add(new JLabel("Activity Name : "));

        JTextField activityName = new JTextField();
        activityName.setPreferredSize(new Dimension(100, 30));
        activityPanel.add(activityName);

        add(activityPanel);

        JPanel executePanel = new JPanel();
        executePanel.setLayout(new FlowLayout());

        JButton create = new JButton("생성");
        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commandManager.linkActivity(xmlID.getText(), layout.getText(), activityName.getText());
            }
        });

        JButton delete = new JButton("삭제");
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commandManager.deleteLinkActivity(xmlID.getText(), layout.getText(), activityName.getText());
            }
        });

        executePanel.add(create);
        executePanel.add(delete);

        add(executePanel);

    }
}
