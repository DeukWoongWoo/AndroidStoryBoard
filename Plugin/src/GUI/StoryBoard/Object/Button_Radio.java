package GUI.StoryBoard.Object;

import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * Created by 우철 on 2016-02-13.
 */
public class Button_Radio extends Button_Root {

    JLabel content =new JLabel();
    public Button_Radio(String name, HashMap<String, ObjectCustom> hash) {


        checkkey=hash;
        setText("NEW RadioButton");
        setId("Radio button"+name);

        content = new JLabel(getText());

        setLayout(null);

        setObject_height(30);
        setObject_width(150);

        content.setLocation(30,0);
        content.setSize(100,30);

        setBackground(Color.white);
        setSize(getObject_width(), getObject_height());
        add(content);


        System.out.println("RadioButton");
        System.out.println(isPosition());

    }

    public Button_Radio(HashMap<String, ObjectCustom> list, JSONObject obj) {
        super(list, obj);

        init_text();

        add(content);
        repaint();
    }

    @Override
    public void setting_Id_Text(String id_, String text_) {
        setId(id_);
        setText(text_);
        content.setText(text_);
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
