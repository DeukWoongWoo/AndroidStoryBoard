package GUI.StoryBoard.Object;

import GUI.StoryBoard.Constant;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 우철 on 2016-02-13.
 */
public class Button_Click extends Button_Root {

    JLabel content = new JLabel();

    // 버튼 생성을 위해서 필요한 값들
    public Button_Click(String name , HashMap<String, ObjectCustom> list, JSONObject obj) {

        super(name, list, obj);
        setText("NEW BUTTON");
        setId("button"+name);
        checkkey=list;
        init_text();
        obj.put("type", "button");
        add(content);


    }
    public Button_Click(String name , HashMap<String, ObjectCustom> list, JSONObject obj, Point pos) {

        super(name, list, obj);

        setText("NEW BUTTON");
        setId("button"+name);


        setPosition(pos);
        obj.put("x",(long)isPosition().x);
        obj.put("y",(long)isPosition().y);

        setLocation(isPosition().x, isPosition().y);
        checkkey=list;
        init_text();
        obj.put("type", "button");
        add(content);


    }

    // 버튼 json에서 받아와서 하는 것들
    public Button_Click(HashMap<String, ObjectCustom> list, JSONObject obj) {
        super(list, obj);
        init_text();
        add(content);
    }
    public Button_Click(HashMap<String, ObjectCustom> list, JSONObject obj, ArrayList nextlist) {
        super(list, obj,nextlist);
        init_text();
        add(content);
    }
    public Button_Click(HashMap<String, ObjectCustom> list, JSONObject obj, ArrayList nextlist, HashMap<String, Activity> actList) {
        super(list, obj,nextlist, actList);
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
