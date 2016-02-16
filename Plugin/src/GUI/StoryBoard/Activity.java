package GUI.StoryBoard;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by 우철 on 2016-02-12.
 */
public class Activity extends JPanel {
    private String id;
    private int activity_width, activity_height;
    private Point activity_position;
    JLabel nameLabel = new JLabel();
    JSONObject activityObject;
    HashMap<String, Activity> activitylist;
    HashMap <String,  ObjectCustom> objectList = new HashMap();

    private boolean draggable = true;
    protected Point anchorPoint;
    protected Cursor draggingCursor = Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
    protected boolean overbearing = false;


    int buttonNum =1;
    int radiobuttonNum=1;

    //--------------- 생성자들 -----------------
    public Activity(String name) {
        setId(name);
        this.setName(name);
        this.setSize(Constant.activitySize_X, Constant.activitySize_Y);
        this.setBackground(Color.WHITE);
        this.setBorder(new LineBorder(Color.black));
        this.setLayout(null);
        addDragListeners();
    }
    public Activity(String name , HashMap<String, Activity> list){
        setId(name);
        this.setName(name);
        this.setSize(Constant.activitySize_X, Constant.activitySize_Y);
        this.setBackground(Color.WHITE);
        this.setBorder(new LineBorder(Color.black));
        this.setLayout(null);
        this.setLocation(10,10);
        addDragListeners();
        activitylist =list;
    }
    public Activity(HashMap<String, Activity> list , JSONObject obj){
        long width, height, x, y ;
        String name;

        activityObject =obj;
        activitylist =list;
        addDragListeners();


        name =(String) activityObject.get("name");
        height=(long) activityObject.get("height");
        width=(long) activityObject.get("width");
        x=(long) activityObject.get("x");
        y=(long) activityObject.get("y");

        //--------- 변수 값 지정---------------
        setId(name);
        setActivity_position(new Point((int)x, (int)y));
        setActivity_height((int)height+(int)height/10);
        setActivity_width((int)width);

        //----------창 구성--------------------
        this.setSize((int)width, (int)height+(int)height/10);
        this.setLocation((int)x, (int)y);
        this.setBorder(new LineBorder(Color.black));
        this.setLayout(null);
        this.setBackground(Color.black);

        nameLabel.setText(getId());
        nameLabel.setLocation((int)width/10,0);
        nameLabel.setSize(getActivity_width()-(int)width/10, getActivity_height()/10);
        nameLabel.setForeground(Color.white);
        nameLabel.setFont(new Font("Serif", Font.PLAIN, getActivity_height()/15 ));
        add(nameLabel);


        makeAllObject(activityObject);

    }

    //---------------private 접근함수 ------------
    public boolean isOverbearing() {
        return overbearing;
    }
    public void setOverbearing(boolean overbearing) {
        this.overbearing = overbearing;
    }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public Point getActivity_position() {
        return activity_position;
    }
    public void setActivity_position(Point activity_position) {
        this.activity_position = activity_position;
    }
    public int getActivity_width() {
        return activity_width;
    }
    public void setActivity_width(int activity_width) {
        this.activity_width = activity_width;
    }
    public int getActivity_height() {
        return activity_height;
    }
    public void setActivity_height(int activity_height) {
        this.activity_height = activity_height;
    }

    //---------------------------------------------
    //          Method 들
    //---------------------------------------------

    //-----------이름 변경----------------------
    public void settingName(){
        String tempName;
        tempName = JOptionPane.showInputDialog("현재 이름은 : " + getId() + " 입니다. 변경될 이름을 입력해주세요");
        if(tempName==null){

        }
        else {
            if (checkName(tempName) == false) {
                setId(tempName);
                nameLabel.setText(tempName);
            } else {
                JOptionPane.showMessageDialog(null, tempName + "은 이미 중복되어있는 ID 값입니다.");
            }
        }
    }
    //-----------Activit 제거-------------------
    public void removeActivity() {

        String temp = this.getId();
        String removeKey=null;
        this.setVisible(false);
        Iterator<String>buttonKeyList = activitylist.keySet().iterator();

        setVisible(false);

        while(buttonKeyList.hasNext()) {
            String key = (String) buttonKeyList.next();
            Object o = activitylist.get(key);
            Activity a = (Activity) o;

            if(a.getId().equals(temp)){
                removeKey = key;
            }
        }
        if(removeKey!=null) {

            activitylist.remove(removeKey);
        }


    }
    //-----------새로운 버튼 생성---------------
    public void newButton(){

        Button_Click b = new Button_Click(""+buttonNum, objectList);

        add(b);
        objectList.put(""+buttonNum , b);
        revalidate();       // 무효화 선언된 화면을 알려줌
        repaint();          // 다시 그려준다.
        buttonNum++;

    }
    //-----------새로운 Radio 버튼 생성---------
    public void newRadioButton(){
        Button_Radio b = new Button_Radio(""+radiobuttonNum, objectList);
        add(b);

        revalidate();       // 무효화 선언된 화면을 알려줌
        repaint();          // 다시 그려준다.
        radiobuttonNum++;
    }
    //-------------id 중복 확인 -----------------
    public boolean checkName(String id_) {
        String temp = this.getId();
        boolean checkId = false;
        Iterator<String> buttonKeyList = activitylist.keySet().iterator();

        while(buttonKeyList.hasNext()) {
            String key = (String) buttonKeyList.next();
            Object o = activitylist.get(key);
            Activity a = (Activity) o;
            if(a.getId().equals(id_)){
                if(temp.equals(id_))
                    continue;

                checkId = true;
            }

        }

        return checkId;
    }
    //-------------마우스 드레그 리스너----------
    private void addDragListeners(){

        final Activity handle = this;
        addMouseMotionListener(new MouseAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {
                anchorPoint = e.getPoint();
                setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

            }

            @Override
            public void mouseDragged(MouseEvent e) {
                int anchorX = anchorPoint.x;
                int anchorY = anchorPoint.y;
                Point parentOnScreen = getParent().getLocationOnScreen();
                Point mouseOnScreen = e.getLocationOnScreen();
                Point position = new Point(mouseOnScreen.x - parentOnScreen.x - anchorX, mouseOnScreen.y - parentOnScreen.y - anchorY);

                setLocation(position);

                //Change Z-Buffer if it is "overbearing"
                if (overbearing) {
                    getParent().setComponentZOrder(handle, 0);
                    repaint();
                }


            }


        });

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


    //-------------------------------------------
    //-------------------------------------------
    //--------------처음 만들기------------------
    public ObjectCustom createObjectCusthom(String type , JSONObject jobj) {

        if(type.equals("linear layout")){
            Layout_Linear_Root linear = new Layout_Linear_Root(objectList, jobj);
            return linear;
        }
        else if(type.equals("relative layout")){
            ObjectCustom obj = new ObjectCustom();
            return obj;
        }
        else
        {
            ObjectCustom obj = new ObjectCustom();
            return obj;
        }

    }

    //jobj ==> activity들이 들어있는 jobj;
    public void makeAllObject(JSONObject jobj){

        JSONArray objectArray;
        objectArray = (JSONArray)jobj.get("object");
        for(int i=0; i<objectArray.size(); i++){
            String type;
            ObjectCustom temp;
            JSONObject tempJsonObject;
            tempJsonObject = (JSONObject)objectArray.get(i);

            type =(String)tempJsonObject.get("type");
            temp=createObjectCusthom(type,tempJsonObject);
            objectList.put((String)tempJsonObject.get("name"),temp);
            add(temp);

        }
    }


    //---------------------------------------------
    //---------------------------------------------


    //-----------------팝업 메뉴 클레스----------
    class PopUpMenu extends JPopupMenu{
        JMenuItem anItem;
        JMenuItem button;
        JMenuItem Radio_button;
        JMenuItem set_name;

        public PopUpMenu() {
            anItem = new JMenuItem("Remove");
            button = new JMenuItem("New Button");
            Radio_button = new JMenuItem("New RadioButton");
            set_name = new JMenuItem("Set Name");

            anItem.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    System.out.println("clicked destroy");
                    removeActivity();

                }
            });

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

            set_name.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    settingName();
                }
            });
            add(anItem);
            add(button);
            add(Radio_button);
            add(set_name);
        }

    }



}