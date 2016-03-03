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
public class Button_Radio extends Button_Root {

    JLabel content =new JLabel("",SwingConstants.LEFT);
    CirclePanel circle = new CirclePanel();

    public Button_Radio(String name, HashMap<String, ObjectCustom> list, JSONObject obj) {

        super(name,list,obj);
        typeObject= Type.RadioButton;

        content = new JLabel(getText());
        checkkey=list;
        setText("NEW RadioButton");
        setId("Radio button"+name);

        obj.put("name", getId());
        obj.put("text", getText());
        obj.put("type", "radio button");

        init_text();

        setBackground(Color.white);
        circle.repaint();
        add(circle);
        add(content);

        objectJObject=obj;

        revalidate();       // 무효화 선언된 화면을 알려줌
        repaint();
    }
    public Button_Radio(String name, HashMap<String, ObjectCustom> list, JSONObject obj, Point point) {

        super(name,list,obj);

        content = new JLabel(getText());
        checkkey=list;
        setText("NEW RadioButton");
        setId("Radio button"+name);
        add(content);
        setPosition(point);
        setLocation(point.x , point.y);
        obj.put("x", (long)point.x);
        obj.put("y", (long)point.y);


        obj.put("name", getId());
        obj.put("text", getText());
        obj.put("type", "RadioButton");

        init_text();

        setBackground(Color.white);
        add(circle, BorderLayout.WEST);
        add(content, BorderLayout.CENTER);

        objectJObject=obj;
        revalidate();       // 무효화 선언된 화면을 알려줌
        repaint();

    }
    public Button_Radio(Constant.ObjectNew objectNew) {


        super(objectNew.name,objectNew.objectList,objectNew.jObject);
        typeObject= Type.RadioButton;

        setText("NEW RadioButton");
        setId("@+id/"+"Radio button"+objectNew.name);

        parentWidth=objectNew.parentWidth;
        parentHeight=objectNew.parentHeight;

        checkkey=objectNew.objectList;
        setPosition(objectNew.mousep);
        objectNew.jObject.put("x", (long)objectNew.mousep.x*2);
        objectNew.jObject.put("y", (long)objectNew.mousep.y*2);
        objectNew.jObject.put("type", "RadioButton");

        setLocation(isPosition().x, isPosition().y);
        checkkey=objectNew.objectList;
        this.add(content,BorderLayout.CENTER);
        init_text();

        this.setOpaque(false);
        circle.repaint();

        setBackground(Color.white);
        add(circle, BorderLayout.WEST);
        add(content, BorderLayout.CENTER);


        JSONObject attribute = setAttribue(objectNew.jObject);
        attribute.put("text",getText());
        revalidate();       // 무효화 선언된 화면을 알려줌
        repaint();



    }

    public Button_Radio(HashMap<String, ObjectCustom> list, JSONObject obj) {
        super(list, obj);

        init_text();

        add(content);
        setBackground(Color.white);
        repaint();
    }
    public Button_Radio(HashMap<String, ObjectCustom> list, JSONObject obj, ArrayList nextlist) {
        super(list, obj, nextlist);

        init_text();

        add(content);
        setBackground(Color.white);
        repaint();
    }

    public Button_Radio(HashMap<String, ObjectCustom> list, JSONObject obj, ArrayList nextlist, HashMap<String, Activity> actlist) {
        super(list, obj, nextlist, actlist);

        init_text();

        add(content);
        setBackground(Color.white);
        repaint();
    }
    public Button_Radio(HashMap<String, ObjectCustom> list, JSONObject obj, ArrayList nextlist, HashMap<String, Activity> actlist,    storyBoard stroy,    String ActivitName) {
        super(list, obj, nextlist, actlist,stroy, ActivitName);

        init_text();
        circle.repaint();
        add(circle, BorderLayout.WEST);
        add(content, BorderLayout.CENTER);


        this.setOpaque(false);
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
        content.setForeground(Color.black);
    }


    class CirclePanel extends JPanel{

        Image img=new ImageIcon("/icon/radio.png").getImage();


        public CirclePanel(){
            setPreferredSize(new Dimension(50,50));
            setMaximumSize(new Dimension(50,50));
            this.setVisible(true);
            this.setOpaque(false);


            revalidate();       // 무효화 선언된 화면을 알려줌
            repaint();
        }


        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g.drawImage(img, 0, 0, 50, 50, null);
        }

        @Override
        public void paintComponents(Graphics g) {
            super.paintComponents(g);

        }
    }

}

