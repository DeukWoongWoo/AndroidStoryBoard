package GUI.StoryBoard.Object;

import GUI.StoryBoard.Constant;
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

    private String text;

    protected HashMap<String, ObjectCustom> checkkey;

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
        obj.put("text", getText());
        obj.put("type","Button");

        setId("button"+name);
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
        long width, height, x, y ;
        String name, text, color;

        nextActivitylist=nextlist;
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

        if(objectJObject.containsKey("next")){
            nextActivitylist.add(objectJObject.get("next"));
        }


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


    //------private 변수 접근함수-------
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
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
    //-------버튼 아이디 중복 확인--------
    public boolean checkButtonId(String id_) {
        String temp = this.getId();
        boolean checkId = false;
        Iterator<String> buttonKeyList = checkkey.keySet().iterator();

        while(buttonKeyList.hasNext()) {
            String key = (String) buttonKeyList.next();
            Object o = checkkey.get(key);
            ObjectCustom b = (ObjectCustom) o;
            if(b.getId().equals(id_)){
                if(temp.equals(id_))
                    continue;

                checkId = true;
            }

        }

        return checkId;
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
        public PopUpMenu() {
            connect = new JMenuItem("Connect");
            remove = new JMenuItem("Remove");

            connect.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println(getId()+" "+isPosition());
                }
            });

            remove.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    removeButton();
                }
            });

            add(connect);
            add(remove);
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
            if(checkButtonId(id_field.getText())==true)
            {
                JOptionPane.showMessageDialog(null, id_field.getText()+"는 이미 중복되어있는 ID 값입니다.");
            }
            else {
                setting_Id_Text(id_field.getText(), name_field.getText());
                objectJObject.put("name", getId() );
                objectJObject.put("text", getText());
                dispose();
            }
        }
    }



}
