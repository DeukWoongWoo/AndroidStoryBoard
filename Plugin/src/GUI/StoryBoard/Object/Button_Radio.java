package GUI.StoryBoard.Object;

import GUI.StoryBoard.Constant;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

/**
 * Created by 우철 on 2016-02-13.
 */
public class Button_Radio extends Button_Root {

    JLabel content =new JLabel();
    public Button_Radio(String name, HashMap<String, ObjectCustom> list, JSONObject obj) {

        super(name,list,obj);

        content = new JLabel(getText());
        checkkey=list;
        setText("NEW RadioButton");
        setId("Radio button"+name);


        obj.put("name", getId());
        obj.put("text", getText());
        obj.put("type", "radio button");

        init_text();

        setBackground(Color.white);
        add(content);
        objectJObject=obj;
    }

    public Button_Radio(HashMap<String, ObjectCustom> list, JSONObject obj) {
        super(list, obj);

        init_text();

        add(content);
        setBackground(Color.white);
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
