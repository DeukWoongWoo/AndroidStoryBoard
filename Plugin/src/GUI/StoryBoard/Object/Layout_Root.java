package GUI.StoryBoard.Object;

import GUI.StoryBoard.Constant;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;

/**
 * Created by 우철 on 2016-02-16.
 */
public class Layout_Root extends ObjectCustom {

    int buttonNum =1;
    int radiobuttonNum=1;
    int linearlayoutNum=1;
    public Layout_Root() {

    }
    public Layout_Root(HashMap<String, ObjectCustom> list , JSONObject obj) {
        long width, height, x, y ;
        String name;
        JSONArray objectArray;

        objectJObject=obj;
        objectList =list;

        name =(String) objectJObject.get("name");
        height=(long) objectJObject.get("height");
        width=(long) objectJObject.get("width");
        x=(long) objectJObject.get("x");
        y=(long) objectJObject.get("y");

        //--------- 변수 값 지정---------------
        setId(name);
        setPosition(new Point((int)x, (int)y));
        setObject_height((int)height);
        setObject_width((int)width);

        //----------창 구성--------------------
        this.setSize((int)width, (int)height);
        this.setLocation((int)x, (int)y);
        this.setLayout(null);
        this.setVisible(true);
        this.setOpaque(false);
        addMouseListner();
        makeAllObject(objectJObject);
    }

    public Layout_Root(String name_, HashMap<String, ObjectCustom> list , JSONObject obj)
    {
        long width, height, x, y ;
        JSONArray objarr =new JSONArray();
        String name, text, color;

        System.out.println(obj);
        name = "Layout"+ name_;
        width = Constant.layoutWidth;
        height = Constant.layoutHeight;
        x = 10;
        y = 10;
        color = "gray";

        setId(name);

        setPosition(new Point((int)x, (int)y));
        setObject_height((int)height);
        setObject_width((int)width);
        setColor(color);

        this.setSize((int)width, (int)height);
        this.setLocation((int)x, (int)y);
        this.setLayout(null);
        this.setVisible(true);
        this.setOpaque(false);

        obj.put("name",getId());
        obj.put("x",x);
        obj.put("y",y);
        obj.put("width",width);
        obj.put("height",height);
        obj.put("color",color);
        obj.put("type","layout");
        obj.put("object", objarr );

        setId("layout"+name);
        objectJObject=obj;
        addMouseListner();
        repaint();
    }

    public ObjectCustom CreateObjectCustom(String type, JSONObject jobj){
        if(type.equals("linear layout")){
            Layout_Linear linear = new Layout_Linear(objectList, jobj);
            return linear;
        }
        else if(type.equals("button")){
            Button_Click b = new Button_Click(objectList, jobj);
            return b;
        }
        else if(type.equals("radio button")){
            Button_Radio b= new Button_Radio(objectList, jobj);
            return b;
        }
        else
        {
            ObjectCustom obj = new ObjectCustom();
            obj=null;
            return obj;
        }

    }

    //jobj 는 layout들이 들어있는 obj
    public void makeAllObject(JSONObject jobj){

        JSONArray objectArray;
        objectArray = (JSONArray)jobj.get("object");

        for(int i=0; i<objectArray.size(); i++){
            JSONObject tempJsonObject;
            tempJsonObject = (JSONObject)objectArray.get(i);
            if(tempJsonObject.isEmpty())
            {
                System.out.println(objectArray.get(i)+"이 지워졌습니다");
                objectArray.remove(i);
                i=-1;
            }
        }
        for(int i=0; i<objectArray.size(); i++){
            String type;
            ObjectCustom temp;
            JSONObject tempJsonObject;
            tempJsonObject = (JSONObject)objectArray.get(i);

            type =(String)tempJsonObject.get("type");
            temp=CreateObjectCustom(type,tempJsonObject);
            objectList.put((String)tempJsonObject.get("name"),temp);
            add(temp);

        }
    }

    //-----------새로운 버튼 생성---------------
    public void newButton(){
        JSONArray tempArray;
        JSONObject tempObj;
        tempArray = (JSONArray)objectJObject.get("object");
        tempObj = new JSONObject();
        System.out.println(objectJObject);
        System.out.println(tempArray);

        Button_Click b = new Button_Click(""+buttonNum, objectList,tempObj);

        tempArray.add(tempObj);

        System.out.println(tempArray);

        add(b);
        objectList.put(""+buttonNum , b);
        revalidate();       // 무효화 선언된 화면을 알려줌
        repaint();          // 다시 그려준다.
        buttonNum++;

    }
    //-----------새로운 Radio 버튼 생성---------
    public void newRadioButton(){
        JSONArray tempArray;
        JSONObject tempObj;
        tempArray = (JSONArray)objectJObject.get("object");
        tempObj = new JSONObject();
        System.out.println(objectJObject);
        System.out.println(tempArray);

        Button_Radio b = new Button_Radio(""+radiobuttonNum, objectList,tempObj);

        tempArray.add(tempObj);

        add(b);

        revalidate();       // 무효화 선언된 화면을 알려줌
        repaint();          // 다시 그려준다.
        radiobuttonNum++;
    }

    public void newLInearLayout(){
        JSONArray tempArray;
        JSONObject tempObj;
        tempArray = (JSONArray)objectJObject.get("object");
        tempObj = new JSONObject();
        System.out.println(objectJObject);
        System.out.println(tempArray);

        Layout_Linear b = new Layout_Linear(""+linearlayoutNum, objectList,tempObj);

        tempArray.add(tempObj);
        System.out.println(tempArray);
        add(b);

        revalidate();       // 무효화 선언된 화면을 알려줌
        repaint();          // 다시 그려준다.
        linearlayoutNum++;
    }


    public void addMouseListner(){
        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getModifiers() == MouseEvent.BUTTON3_MASK)
                {
                    PopUpMenu menu = new PopUpMenu();
                    menu.show(e.getComponent(), e.getX(), e.getY());

                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
    }

    class PopUpMenu extends JPopupMenu {

        JMenuItem button;
        JMenuItem Radio_button;
        JMenuItem linear_layout;

        public PopUpMenu() {
            button = new JMenuItem("New Button");
            Radio_button = new JMenuItem("New RadioButton");
            linear_layout = new JMenuItem("New linear_layout");



            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    newButton();
                }
            });

            Radio_button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    newRadioButton();
                }
            });

            linear_layout.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    newLInearLayout();
                }
            });
            add(button);
            add(Radio_button);
            add(linear_layout);
        }

    }






}
