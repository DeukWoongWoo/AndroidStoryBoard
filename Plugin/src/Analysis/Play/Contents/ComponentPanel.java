package Analysis.Play.Contents;

import Analysis.RedoUndo.CodeBuilder.Type;
import Analysis.RedoUndo.CommandManager;
import org.jdesktop.swingx.JXComboBox;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by woong on 2016-03-04.
 */
public class ComponentPanel extends JPanel {
    private int type;
    private Type component;

    private CommandManager commandManager;

    public ComponentPanel(){
        setLayout(new GridLayout(5,1));

        commandManager = CommandManager.getInstance();

        init();
    }

    private void init(){
        JXComboBox local = new JXComboBox();
        local.setPreferredSize(new Dimension(200, 30));
        local.addItem("Local");
        local.addItem("Member");
        local.addItem("Function");

        local.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                type = local.getSelectedIndex();
            }
        });

        add(local);

        JXComboBox menu = new JXComboBox();
        menu.setPreferredSize(new Dimension(200, 30));
        menu.addItem("Button");
        menu.addItem("CheckBox");
        menu.addItem("RadioButton");
        menu.addItem("TextView");
        menu.addItem("ImageView");

        menu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                switch (menu.getSelectedIndex()){
                    case 0:
                        component = Type.Button;
                        break;
                    case 1:
                        component = Type.CheckBox;
                        break;
                    case 2:
                        component = Type.RadioButton;
                        break;
                    case 3:
                        component = Type.TextView;
                        break;
                    case 4:
                        component = Type.ImageView;
                        break;
                }
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
                    commandManager.createLocalComponent(xmlID.getText(), layout.getText(), component);
                }else if(type==1){
                    commandManager.createMemberComponent(xmlID.getText(), layout.getText(), component);
                }else if(type==2){
                    commandManager.createFucnComponent(xmlID.getText(), layout.getText(), component);
                }
            }
        });

        JButton delete = new JButton("삭제");
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(type==0){
                    commandManager.deleteLocalComponent(xmlID.getText(), layout.getText(), component);
                }else if(type==1){
                    commandManager.deleteMemberComponent(xmlID.getText(), layout.getText(), component);
                }else if(type==2){
                    commandManager.deleteFuncComponent(xmlID.getText(), layout.getText(), component);
                }
            }
        });

        executePanel.add(create);
        executePanel.add(delete);

        add(executePanel);

    }
}
