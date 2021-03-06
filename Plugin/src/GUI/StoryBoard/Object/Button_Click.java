package GUI.StoryBoard.Object;

import Analysis.RedoUndo.CodeBuilder.Type;
import GUI.StoryBoard.Constant;
import GUI.StoryBoard.storyBoard;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 우철 on 2016-02-13.
 */
public class Button_Click extends Button_Root {

    JLabel content = new JLabel("",SwingConstants.CENTER);

    // 버튼 생성을 위해서 필요한 값들
    public Button_Click(String name , HashMap<String, ObjectCustom> list, JSONObject obj) {

        super(name, list, obj);
        setText("NEW BUTTON");
        setId("button"+name);
        checkkey=list;
        init_text();
        obj.put("type", "Button");
        add(content,BorderLayout.CENTER);


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
        obj.put("type", "Button");
        add(content,"Center");


    }
    public Button_Click(Constant.ObjectNew objectNew) {

        super(objectNew.name, objectNew.objectList, objectNew.jObject);
        typeObject= Type.Button;

        setText("NEW BUTTON");
        setId("@+id/"+"button"+objectNew.name);

        parentWidth=objectNew.parentWidth;
        parentHeight=objectNew.parentHeight;

        setPosition(objectNew.mousep);
        objectNew.jObject.put("x",(long)isPosition().x);
        objectNew.jObject.put("y",(long)isPosition().y);

        setLocation(isPosition().x, isPosition().y);
        checkkey=objectNew.objectList;
        init_text();
        objectNew.jObject.put("type", "Button");
        add(content, BorderLayout.CENTER);


        JSONObject attribute = setAttribue(objectNew.jObject);
        attribute.put("text",getText());

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
    public Button_Click(HashMap<String, ObjectCustom> list, JSONObject obj, ArrayList nextlist, HashMap<String, Activity> actList,    storyBoard stroy) {
        super(list, obj,nextlist, actList,stroy);
        init_text();
        add(content);
    }
    public Button_Click(HashMap<String, ObjectCustom> list, JSONObject obj, ArrayList nextlist, HashMap<String, Activity> actList,    storyBoard stroy, String ActivitName) {
        super(list, obj,nextlist, actList,stroy, ActivitName);
        typeObject= Type.Button;
        init_text();
        setLayout(new BorderLayout());
        add(content,BorderLayout.CENTER);
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
        content.setFont(new Font("Serif", Font.PLAIN, getObject_height()/5 ));
        content.setForeground(Color.black);
    }



}
