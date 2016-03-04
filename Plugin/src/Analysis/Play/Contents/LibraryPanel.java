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
public class LibraryPanel extends JPanel {
    private CommandManager commandManager;

    private int type;

    public LibraryPanel(){
        setLayout(new GridLayout(4,1));
        commandManager = CommandManager.getInstance();
        init();
    }

    private void init(){
        JXComboBox menu = new JXComboBox();
        menu.addItem("Event");
        menu.addItem("Error");
        menu.addItem("Activity");

        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                type = menu.getSelectedIndex();
            }
        });

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
        create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(type==0){
                    commandManager.addLibEvent(xmlID.getText(), layout.getText());
                }else if(type == 1){
                    commandManager.addLibError(xmlID.getText(), layout.getText());
                }else if(type ==2){
                    commandManager.addLibActivity(layout.getText());
                }
            }
        });

        JButton delete = new JButton("삭제");
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commandManager.deleteLib();
            }
        });

        executePanel.add(create);
        executePanel.add(delete);

        add(executePanel);

    }
}
