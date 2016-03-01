package GUI.StoryBoard.Object;

import GUI.StoryBoard.Constant;
import GUI.StoryBoard.storyBoard;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by 우철 on 2016-02-13.
 */
public class Button_Root extends ObjectCustom {


    private Point mouse_p;

    JSONObject attribute;
    /////////////////////////////////
    //          생성자들
    ////////////////////////////////
    public Button_Root() {
        addMouseListener();
        setLocation(10,10);
    }
    public Button_Root(String name_ , HashMap<String,  ObjectCustom> list, JSONObject obj) {
        long width, height, x, y ;
        String name, text, color;

        System.out.println(obj);
        name = "button"+ name_;
        text = "NEW BUTTON";
        width = Constant.buttonWidth;
        height = Constant.buttonHeight;
        x = 10;
        y = 10;
        color = "gray";

        setId(name);
        setText(text);

        setPosition(new Point((int)x, (int)y));
        setObject_height((int)height);
        setObject_width((int)width);
        setColor(color);

        this.setSize((int)width, (int)height);
        this.setLocation((int)x, (int)y);
        this.setLayout(null);
        this.setVisible(true);
        this.setBackground(Color.LIGHT_GRAY);

        obj.put("name",getId());
        obj.put("x",x);
        obj.put("y",y);
        obj.put("width",width);
        obj.put("height",height);
        obj.put("color",color);
        obj.put("type","Button");

        setId("button"+name);

        JSONObject attribute = setAttribue(obj);

        attribute.put("text",getText());
        objectJObject=obj;

        checkkey=list;
        addMouseListener();

        repaint();
   }

    public Button_Root(HashMap<String, ObjectCustom> list , JSONObject obj){
        long width, height, x, y ;
        String name, text, color;

        objectJObject=obj;
        objectList =list;
        checkkey=list;

        name =(String) objectJObject.get("name");
        text = (String)objectJObject.get("text");
        color = (String)objectJObject.get("color");
        height=(long) objectJObject.get("height");
        width=(long) objectJObject.get("width");
        x=(long) objectJObject.get("x");
        y=(long) objectJObject.get("y");

        //--------- 변수 값 지정---------------
        setId(name);
        setText(text);
        setPosition(new Point((int)x, (int)y));
        setObject_height((int)height);
        setObject_width((int)width);
        setColor(color);

        //----------창 구성--------------------
        this.setSize((int)width, (int)height);
        this.setLocation((int)x, (int)y);
        this.setLayout(null);
        this.setVisible(true);
        this.setBackground(Color.LIGHT_GRAY);

        //---------메소드----------------------
        addMouseListener();

    }
    public Button_Root(HashMap<String, ObjectCustom> list , JSONObject obj, ArrayList nextlist){
        super();
        nextActivitylist=nextlist;
        objectJObject=obj;
        objectList =list;
        checkkey=list;

        if(objectJObject.containsKey("next")){
            JSONObject temp = (JSONObject)objectJObject.get("next");
            nextActivitylist.add(temp.get("toactivity"));
        }

        //----------창 구성--------------------
        this.setSize((int)getObject_width(), (int)getObject_height());
        this.setLocation((int)isPosition().x, (int)isPosition().y);
        this.setLayout(null);
        this.setVisible(true);
        this.setBackground(Color.LIGHT_GRAY);

        //---------메소드----------------------
        addMouseListener();

    }
    public Button_Root(HashMap<String, ObjectCustom> list , JSONObject obj, ArrayList nextlist , HashMap<String, Activity> actList) {
        super();

        nextActivitylist=nextlist;
        objectJObject=obj;
        objectList =list;
        checkkey=list;
        activityList=actList;

        if(objectJObject.containsKey("next")){
            nextActivitylist.add(objectJObject.get("next"));
        }



        //----------창 구성--------------------
        this.setSize((int)getObject_width(), (int)getObject_height());
        this.setLocation((int)isPosition().x, (int)isPosition().y);
        this.setLayout(null);
        this.setVisible(true);
        this.setBackground(Color.LIGHT_GRAY);

        //---------메소드----------------------
        addMouseListener();

    }
    public Button_Root(HashMap<String, ObjectCustom> list , JSONObject obj, ArrayList nextlist , HashMap<String, Activity> actList, storyBoard stroy) {
        super();

        nextActivitylist=nextlist;
        objectJObject=obj;
        objectList =list;
        checkkey=list;
        activityList=actList;

        if(objectJObject.containsKey("attribute")){

        }

        getStroyBoard(stroy);


        if(objectJObject.containsKey("next")){
            nextActivitylist.add(objectJObject.get("next"));
        }


        //----------창 구성--------------------
        this.setSize((int)getObject_width(), (int)getObject_height());
        this.setLocation((int)isPosition().x, (int)isPosition().y);
        this.setLayout(null);
        this.setVisible(true);
        this.setBackground(Color.LIGHT_GRAY);

        //---------메소드----------------------
        addMouseListener();

    }
    public Button_Root(HashMap<String, ObjectCustom> list , JSONObject obj, ArrayList nextlist , HashMap<String, Activity> actList, storyBoard stroy, String ActivitName) {
        super(list,obj,nextlist,actList,stroy,ActivitName);


        nextActivitylist=nextlist;
        objectJObject=obj;
        objectList =list;
        checkkey=list;
        activityList=actList;
        this.activityName=ActivitName;
        getStroyBoard(stroy);


        if(objectJObject.containsKey("next")){
            nextActivitylist.add(objectJObject.get("next"));
        }


        //----------창 구성--------------------
        this.setSize((int)getObject_width(), (int)getObject_height());
        this.setLocation((int)isPosition().x, (int)isPosition().y);
        this.setLayout(null);
        this.setVisible(true);
        this.setBackground(Color.LIGHT_GRAY);

        //---------메소드----------------------
        addMouseListener();

    }


    //-------마우스 이벤트 리스너---------
    private void addMouseListener(){

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getModifiers() == MouseEvent.BUTTON3_MASK)
                {
                    PopUpMenu menu = new PopUpMenu();
                    menu.show(e.getComponent(), e.getX(), e.getY());

                }
                else if (e.getClickCount() == 2 && !e.isConsumed()) {
                    Change_Window c = new Change_Window(getId(),getText(),e.getLocationOnScreen(), e.getPoint());
                    e.consume();
                }
                mouse_p = e.getLocationOnScreen();

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

    //-------버튼의 id text 변경 ---------
    public void setting_Id_Text(String id_, String text_){
        setId(id_);
        setText(text_);
        setName(id_);
        repaint();
    }

    //------- 버튼 제거-------------------
    public void removeButton(){
        String temp = this.getId();
        String removeKey=null;
        this.setVisible(false);
        Iterator<String> buttonKeyList = checkkey.keySet().iterator();

        while(buttonKeyList.hasNext()) {
            String key = (String) buttonKeyList.next();
            Object o = checkkey.get(key);
            ObjectCustom b = (ObjectCustom) o;

            if(b.getId().equals(temp)){
                removeKey = key;
            }
        }
        if(removeKey!=null) {
            System.out.println(objectJObject);
            objectJObject.clear();
            checkkey.remove(removeKey);
        }
    }


    //      새로운 클레스들 생성 ---------
    //
    //------- 팝업 메뉴 ------------------
    class PopUpMenu extends JPopupMenu{
        JMenuItem connect;
        JMenuItem remove;
        JMenuItem library;
        public PopUpMenu() {
            connect = new JMenuItem("Connect");
            remove = new JMenuItem("Remove");
            library = new JMenuItem("library");

            connect.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Connect_NextActivity activity = new Connect_NextActivity();
                }
            });

            remove.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    removeButton();
                }
            });

            library.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Libray_Window a = new Libray_Window();
                }
            });
            add(connect);
            add(remove);
            add(library);
        }

    }
    //------- 변경창 메뉴 ----------------
    class Change_Window extends JFrame {

        JLabel name_label;
        JLabel id_label;
        JTextField name_field;
        JTextField id_field;
        JButton okbutton;
        int scale_size;

        public Change_Window(String id_, String text, Point screenPosition, Point mouse) {
            name_label = new JLabel("name :");
            id_label = new JLabel("id :");
            id_field = new JTextField(getId());
            name_field = new JTextField(getText());
            okbutton = new JButton("OK");
            scale_size=getObject_width()/20;

            this.setSize(350, 150);          //창 사이즈

            this.setUndecorated(true);      //title bar 제거
            this.setLocation(screenPosition.x - mouse.x, screenPosition.y - mouse.y);   // 현재 버튼의 위에 덮기 위한 것
            this.setVisible(true);
            this.setLayout(null);   // 자유 레이아웃
            this.getRootPane().setBorder(new LineBorder(Color.black));  // JFrame 테두리 설정

            okbutton.setMargin(new Insets(0, 0, 0, 0));

            name_label.setLocation(10, 10);
            id_label.setLocation(10, 60);
            name_field.setLocation(100, 10);
            id_field.setLocation(100, 60);
            okbutton.setLocation(200, 100);

//            name_label.setLocation(scale_size, scale_size);
//            id_label.setLocation(scale_size, scale_size*6);
//            name_field.setLocation(scale_size*10, scale_size);
//            id_field.setLocation(scale_size*10, scale_size*6);
//            okbutton.setLocation(scale_size*20, scale_size*10);

            name_label.setSize(100, 40);
            id_label.setSize(100, 40);
            name_field.setSize(200, 40);
            id_field.setSize(200, 40);
            okbutton.setSize(100, 40);

//            name_label.setSize(scale_size*10, scale_size*4);
//            id_label.setSize(scale_size*10, scale_size*4);
//            name_field.setSize(scale_size*20, scale_size*4);
//            id_field.setSize(scale_size*20, scale_size*4);
//            okbutton.setSize(scale_size*10, scale_size*4);

            this.add(name_label);
            this.add(id_label);

            this.add(name_field);
            this.add(id_field);
            this.add(okbutton);

            this.addWindowFocusListener(new WindowFocusListener() {
                @Override
                public void windowGainedFocus(WindowEvent e) {
                }

                @Override
                public void windowLostFocus(WindowEvent e) {
                    dispose();
                }
            });

            okbutton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    result();
                }
            });

            id_field.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {

                }

                @Override
                public void keyPressed(KeyEvent e) {
                    if(e.getKeyCode()==KeyEvent.VK_ENTER)
                    {
                        result();
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            });
            name_field.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {

                }

                @Override
                public void keyPressed(KeyEvent e) {
                    if(e.getKeyCode()==KeyEvent.VK_ENTER)
                    {
                        result();
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            });
        }
        // 확인 키
        public void result()
        {

            if(checkObjectId(id_field.getText())==true)
            {
                JOptionPane.showMessageDialog(null, id_field.getText()+"는 이미 중복되어있는 ID 값입니다.");
            }
            else {
                setting_Id_Text(id_field.getText(), name_field.getText());
                objectJObject.put("name", getId() );
                attributeObject.put("text", getText());
                dispose();
            }
        }
    }
    class Connect_NextActivity extends JFrame {

        JLabel next_label;

        JButton okbutton;
        JButton cancelButton;
        JComboBox<String> combo = new JComboBox<String>();
        int scale_size;

        public Connect_NextActivity(){

            this.setUndecorated(true);      //title bar 제거
            this.setSize(350, 150);          //창 사이즈
            this.setVisible(true);
            this.setLayout(null);
            this.setLocation(mouse_p.x, mouse_p.y);   // 현재 버튼의 위에 덮기 위한 것
            okbutton = new JButton("OK");
            cancelButton = new JButton("NO");
            next_label = new JLabel("Next Activity");

            okbutton.setMargin(new Insets(0, 0, 0, 0));
            okbutton.setLocation(100, 100);
            okbutton.setSize(100, 40);

            cancelButton.setMargin(new Insets(0, 0, 0, 0));
            cancelButton.setLocation(220, 100);
            cancelButton.setSize(100, 40);

            combo.setSize(300,40);
            combo.setLocation(50,50);

            next_label.setSize(100,20);
            next_label.setLocation(10,10);

            okbutton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(combo.getSelectedItem().equals("NONE")){
                        objectJObject.remove("next");
                    }
                    else
                    {
                        JSONObject tempObject= new JSONObject();
                        tempObject.put("fromactivity", activityName);
                        tempObject.put("toactivity", combo.getSelectedItem());
                       // objectJObject.put("next", combo.getSelectedItem());
                    }

                    dispose();
                }
            });
            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });

            Iterator<String> activeKeyList = activityList.keySet().iterator();

            while(activeKeyList.hasNext()){
                String key = (String) activeKeyList.next();
                Object o = activityList.get(key);
                Activity a = (Activity) o;

                combo.addItem(a.getId());
            }
            combo.addItem("NONE");

            if(objectJObject.containsKey("next")){
                if(activityList.containsKey(objectJObject.get("next")))
                    combo.setSelectedItem(objectJObject.get("next"));
                else
                    combo.setSelectedItem("NONE");
            }
            else
                combo.setSelectedItem("NONE");

            add(okbutton);
            add(cancelButton);
            add(combo);
            add(next_label);
            this.addWindowFocusListener(new WindowFocusListener() {
                @Override
                public void windowGainedFocus(WindowEvent e) {

                }

                @Override
                public void windowLostFocus(WindowEvent e) {

                    dispose();
                }
            });
        }


    }
    class Libray_Window extends JFrame{
        JComboBox<String> checklibray;
        JButton okButton;
        JButton noButton;
        JLabel label = new JLabel("라이브러리 설정");
        public Libray_Window(){
            label.setSize(100,50);
            label.setLocation(10,0);

            checklibray = new JComboBox<String>();
            checklibray.addItem("error");
            checklibray.addItem("event");
            checklibray.addItem("NONE");
            checklibray.setSize(230,50);
            checklibray.setLocation(30,45);

            if(objectJObject.containsKey("library")){

                checklibray.setSelectedItem(objectJObject.get("library"));
            }
            else
            {
                checklibray.setSelectedItem("NONE");
            }

            okButton = new JButton("OK");
            okButton.setSize(100,25);
            okButton.setLocation(50,110);

            noButton = new JButton("NO");
            noButton.setSize(100,25);
            noButton.setLocation(160,110);
            this.setUndecorated(true);      //title bar 제거
            setSize(300,150);
            setVisible(true);
            setLayout(null);
            setLocation(mouse_p.x,mouse_p.y);


            okButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if(checklibray.getSelectedItem().equals("NONE")){
                        objectJObject.remove("library");
                    }
                    else
                    {
                        objectJObject.put("library", checklibray.getSelectedItem());
                    }
                    sendLibraryData((String)objectJObject.get("library"));
                    dispose();
                }
            });
            noButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });
            add(label);
            add(checklibray);
            add(okButton);
            add(noButton);

            this.addWindowFocusListener(new WindowFocusListener() {
                @Override
                public void windowGainedFocus(WindowEvent e) {

                }

                @Override
                public void windowLostFocus(WindowEvent e) {
                    dispose();
                }
            });
        }

    }
    public void sendLibraryData(String library){
        String id_, activity_;

        id_=getId();
        activity_=activityName;

        System.out.println("id :"+id_+"  activity : "+activityName + " library :" + library);
    }
}
