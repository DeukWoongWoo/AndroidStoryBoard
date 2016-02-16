package GUI.StoryBoard.Object;

import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * Created by 우철 on 2016-02-13.
 */
public class Button_Click extends Button_Root {

    JLabel content = new JLabel();

    public Button_Click(String name , HashMap<String, ObjectCustom> hash) {

        setText("NEW BUTTON");
        setId("button"+name);
        checkkey=hash;
        content.setText(getText());
        setObject_height(30);
        setObject_width(150);

        setBackground(Color.lightGray);
        setSize(getObject_width(), getObject_height());
        add(content);

    }

    public Button_Click(HashMap<String, ObjectCustom> list, JSONObject obj) {
        super(list, obj);
        init_text();
        add(content);
    }

    @Override
    public void setting_Id_Text(String id_, String text_) {
        setId(id_);
        setText(text_);
        content.setText(text_);
        revalidate();
        repaint();
    }

    public void init_text()
    {
        content.setText(getText());
        content.setVisible(true);
        content.setLocation(getObject_width()/10,0);
        content.setSize(getObject_width()-getObject_width()/10, getObject_height());
        content.setFont(new Font("Serif", Font.PLAIN, getObject_height()/2 ));
        content.setForeground(Color.black);
    }



}
