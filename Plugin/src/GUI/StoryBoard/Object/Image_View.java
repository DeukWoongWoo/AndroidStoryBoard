package GUI.StoryBoard.Object;

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
 * Created by 우철 on 2016-02-29.
 */
public class Image_View extends ObjectCustom {
    protected HashMap<String, ObjectCustom> checkkey;
    String imagePath;

    public Image_View(HashMap<String, ObjectCustom> list , JSONObject obj, ArrayList nextlist , HashMap<String, Activity> actList, storyBoard stroy, String ActivitName) {
        long width, height, x, y ;
        String name;


        nextActivitylist=nextlist;
        objectJObject=obj;
        objectList =list;
        checkkey=list;
        activityList=actList;
        this.activityName=ActivitName;

        if(objectJObject.containsKey("attribute")){

        }

        getStroyBoard(stroy);
        name =(String) objectJObject.get("name");
        if(objectJObject.containsKey("src")){

        }

        height=(long) objectJObject.get("height");
        width=(long) objectJObject.get("width");
        x=(long) objectJObject.get("x");
        y=(long) objectJObject.get("y");

        if(objectJObject.containsKey("next")){
            nextActivitylist.add(objectJObject.get("next"));
        }


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
        this.setBackground(Color.LIGHT_GRAY);

        //---------메소드----------------------
        addMouseListener();

    }
    private void addMouseListener(){

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && !e.isConsumed()) {
                    Change_Window c = new Change_Window(getId(),e.getLocationOnScreen(), e.getPoint());
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
    class Change_Window extends JFrame {


        JLabel id_label;
        JTextField id_field;
        JButton okbutton;
        int scale_size;

        public Change_Window(String id_, Point screenPosition, Point mouse) {
            id_label = new JLabel("id :");
            id_field = new JTextField(getId());
            okbutton = new JButton("OK");
            scale_size=getObject_width()/20;

            this.setSize(350, 150);          //창 사이즈

            this.setUndecorated(true);      //title bar 제거
            this.setLocation(screenPosition.x - mouse.x, screenPosition.y - mouse.y);   // 현재 버튼의 위에 덮기 위한 것
            this.setVisible(true);
            this.setLayout(null);   // 자유 레이아웃
            this.getRootPane().setBorder(new LineBorder(Color.black));  // JFrame 테두리 설정

            okbutton.setMargin(new Insets(0, 0, 0, 0));

            id_label.setLocation(10, 60);

            id_field.setLocation(100, 60);
            okbutton.setLocation(200, 100);

//            name_label.setLocation(scale_size, scale_size);
//            id_label.setLocation(scale_size, scale_size*6);
//            name_field.setLocation(scale_size*10, scale_size);
//            id_field.setLocation(scale_size*10, scale_size*6);
//            okbutton.setLocation(scale_size*20, scale_size*10);


            id_label.setSize(100, 40);

            id_field.setSize(200, 40);
            okbutton.setSize(100, 40);

//            name_label.setSize(scale_size*10, scale_size*4);
//            id_label.setSize(scale_size*10, scale_size*4);
//            name_field.setSize(scale_size*20, scale_size*4);
//            id_field.setSize(scale_size*20, scale_size*4);
//            okbutton.setSize(scale_size*10, scale_size*4);


            this.add(id_label);

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

        }
        // 확인 키
        public void result()
        {
            if(checkButtonId(id_field.getText())==true)
            {
                JOptionPane.showMessageDialog(null, id_field.getText()+"는 이미 중복되어있는 ID 값입니다.");
            }
            else {
                setId(id_field.getText());
                repaint();
                objectJObject.put("name", getId() );
                dispose();
            }
        }
    }
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
}
