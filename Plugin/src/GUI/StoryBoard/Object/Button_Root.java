package GUI.StoryBoard.Object;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
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
    public Button_Root(String name , HashMap<String,  ObjectCustom> hash) {
        setText("NEW BUTTON");
        setId("button"+name);
        checkkey=hash;
   }
    public Button_Root(HashMap<String, ObjectCustom> list , JSONObject obj){
        long width, height, x, y ;
        String name, text, color;
        JSONArray objectArray;

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

            checkkey.remove(removeKey);
        }
    }


    //      새로운 클레스들 생성 ---------
    //
    //------- 팝업 메뉴 ------------------
    class PopUpMenu extends JPopupMenu{
        JMenuItem anItem;
        JMenuItem remove;
        public PopUpMenu() {
            anItem = new JMenuItem("Connect");
            remove = new JMenuItem("Remove");

            anItem.addActionListener(new ActionListener() {
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

            add(anItem);
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

        public Change_Window(String id_, String text, Point screenPosition, Point mouse) {
            name_label = new JLabel("name :");
            id_label = new JLabel("id :");
            id_field = new JTextField(getId());
            name_field = new JTextField(getText());
            okbutton = new JButton("OK");

            this.setSize(160, 80);          //창 사이즈
            this.setUndecorated(true);      //title bar 제거
            this.setLocation(screenPosition.x - mouse.x, screenPosition.y - mouse.y);   // 현재 버튼의 위에 덮기 위한 것
            this.setVisible(true);
            this.setLayout(null);   // 자유 레이아웃
            this.getRootPane().setBorder(new LineBorder(Color.black));  // JFrame 테두리 설정

            okbutton.setMargin(new Insets(0, 0, 0, 0));

            name_label.setLocation(5, 5);
            id_label.setLocation(5, 30);
            name_field.setLocation(55, 5);
            id_field.setLocation(55, 30);
            okbutton.setLocation(100, 55);

            name_label.setSize(50, 20);
            id_label.setSize(50, 20);
            name_field.setSize(100, 20);
            id_field.setSize(100, 20);
            okbutton.setSize(50, 20);

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
                dispose();
            }
        }
    }



}