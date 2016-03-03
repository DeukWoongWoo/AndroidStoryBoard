package GUI.StoryBoard.Object;

import Analysis.RedoUndo.CodeBuilder.Type;
import GUI.StoryBoard.Constant;
import GUI.StoryBoard.storyBoard;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 우철 on 2016-03-01.
 */
public class CheckBox extends ObjectCustom {
    protected HashMap<String, ObjectCustom> checkkey;
    JLabel content = new JLabel("",SwingConstants.LEFT);
    Image img;
    ImagePanel1 panel1 ;

    public CheckBox() {
        //----------창 구성--------------------
        this.setSize(Constant.imageVIewWidth,Constant.imageViewHeight);
        this.setLayout(new BorderLayout());
        this.setVisible(true);
        this.setBackground(Color.LIGHT_GRAY);
        this.setOpaque(false);
    }
    public CheckBox(String name_ , HashMap<String,  ObjectCustom> list, JSONObject obj, Point p) {
        long width, height, x, y ;
        String name, color;
        panel1 = new ImagePanel1("/icon/radio.png");
        System.out.println(obj);
        name = "@+id/"+"CheckBox"+ name_;
        width = Constant.buttonWidth;
        height = Constant.buttonHeight;
        x = p.x;
        y = p.y;
        color = "gray";

        setId(name);

        setPosition(new Point((int)x, (int)y));
        setObject_height((int)height);
        setObject_width((int)width);
        setColor(color);

        this.setSize((int)width, (int)height);
        this.setLocation((int)x, (int)y);

        obj.put("name",getId());
        obj.put("x",x);
        obj.put("y",y);
        obj.put("width",width);
        obj.put("height",height);
        obj.put("type","CheckBox");


        content.setText(getText());
        setId(name);
        objectJObject=obj;

        checkkey=list;

        this.setLayout(new BorderLayout());
        this.setVisible(true);
        this.setBackground(Color.LIGHT_GRAY);
        this.setOpaque(false);
        addMouseListener();
        add(content);
        add(panel1, BorderLayout.WEST);
        repaint();
    }
    public CheckBox(HashMap<String, ObjectCustom> list, JSONObject obj, ArrayList nextlist, HashMap<String, Activity> actList, storyBoard stroy, String ActivitName) {
        super(list,obj,nextlist,actList,stroy,ActivitName);
        panel1 = new ImagePanel1("/icon/radio.png");
        nextActivitylist = nextlist;
        objectJObject = obj;
        objectList = list;
        checkkey = list;
        activityList = actList;
        this.XmlName = ActivitName;


        getStroyBoard(stroy);
        //----------창 구성--------------------
        this.setSize((int) getObject_width(), (int) getObject_height());
        this.setLocation((int) isPosition().x, (int) isPosition().y);
        this.setLayout(new BorderLayout());
        this.setVisible(true);
        this.setBackground(Color.LIGHT_GRAY);
        this.setOpaque(false);
        init_text();
        //---------메소드----------------------
        addMouseListener();
        add(content, BorderLayout.CENTER);
        add(panel1, BorderLayout.WEST);
    }
    public CheckBox(Constant.ObjectNew objectNew) {
        long width, height, x, y ;
        String name, color;
        typeObject= Type.CheckBox;
        panel1 = new ImagePanel1("/icon/radio.png");
        name = "@+id/"+"CheckBox"+ objectNew.name;
        setText("New CheckBox");


        parentWidth=objectNew.parentWidth;
        parentHeight=objectNew.parentHeight;


        width = Constant.buttonWidth;
        height = Constant.buttonHeight;


        x = objectNew.mousep.x;
        y = objectNew.mousep.y;

        objectJObject=objectNew.jObject;
        checkkey=objectNew.objectList;
        setObjectName(objectNew.name);

        setId("@+id/"+"checkbox"+objectNew.name);

        this.setBackground(Color.LIGHT_GRAY);
        this.setLayout(new BorderLayout());
        this.setSize((int)width,(int)height);
        this.setOpaque(false);
        setPosition(objectNew.mousep);

        objectNew.jObject.put("x",(long)isPosition().x*2);
        objectNew.jObject.put("y",(long)isPosition().y*2);
        objectNew.jObject.put("name",getObjectName());
        objectNew.jObject.put("width",width*2);
        objectNew.jObject.put("height",height*2);
        objectNew.jObject.put("type","CheckBox");

        setLocation(isPosition().x, isPosition().y);
        init_text();


        checkkey=objectNew.objectList;




        add(content, BorderLayout.CENTER);
        add(panel1, BorderLayout.WEST);

        JSONObject attribute = setAttribue(objectNew.jObject);
        attribute.put("text",getText());

        revalidate();       // 무효화 선언된 화면을 알려줌
        repaint();

    }

    private void addMouseListener() {

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && !e.isConsumed()) {
                    Change_Window c = new Change_Window(getId(),getText(),e.getLocationOnScreen(), e.getPoint());
                    e.consume();
                }
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

            name_label.setSize(100, 40);
            id_label.setSize(100, 40);
            name_field.setSize(200, 40);
            id_field.setSize(200, 40);
            okbutton.setSize(100, 40);

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

                fixObject(1);
                dispose();
            }
        }
    }
    //-------체크박스의 id text 변경 ---------
    public void setting_Id_Text(String id_, String text_){
        setId(id_);
        setText(text_);
        setName(id_);
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


    class PopUpMenu extends JPopupMenu{
        JMenuItem remove;
        public PopUpMenu() {

            remove = new JMenuItem("Remove");



            remove.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    removeObject();

                }
            });
            add(remove);
        }

    }
}
