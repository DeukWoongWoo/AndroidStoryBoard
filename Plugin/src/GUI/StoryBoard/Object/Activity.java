package GUI.StoryBoard.Object;

import GUI.StoryBoard.Constant;
import GUI.StoryBoard.UI.palettePanel;
import GUI.StoryBoard.storyBoard;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by 우철 on 2016-02-12.
 */
public class Activity extends JPanel {
    private String id;
    String Xmlname;
    private int activity_width, activity_height;
    private Point activity_position;
    private String type = "activity";

    public Point mouse_p;
    String activityname;
    JLabel nameLabel = new JLabel();
    JSONObject activityObject;
    JSONObject xmlObject;
    HashMap<String, Activity> activitylist;
    JSONArray Xmllist;

    HashMap <String, ObjectCustom> objectList = new HashMap();
    ArrayList nextActivitylist = new ArrayList();
    storyBoard storyboard;
    palettePanel panel;

//    UseLibraryParser  useLibraryParser = new UseLibraryParser();



    protected Point anchorPoint;
    protected Cursor draggingCursor = Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR);
    protected boolean overbearing = false;




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
    public Activity(String name , HashMap<String, Activity> list, JSONObject obj){
        String layout_type ="linear layout";
        activityObject =obj;
        JSONArray array = new JSONArray();


        setId(name);
        this.setName(name);
        this.setSize(Constant.activitySize_X, Constant.activitySize_Y);
        this.setBackground(Color.BLACK);
        this.setBorder(new LineBorder(Color.black));
        this.setLayout(null);
        this.setLocation(10,10);

        setId(name);
        setActivity_width(Constant.activitySize_X);
        setActivity_height(Constant.activitySize_Y);
        setActivity_position(new Point(10,10));

        obj.put("name",getId());
        obj.put("x", (long)getActivity_position().x);
        obj.put("y", (long)getActivity_position().y);
        obj.put("width", (long)getActivity_width());
        obj.put("height", (long)getActivity_height());
        obj.put("object", array);


        if(layout_type.equals("linear layout")){
            JSONObject tempobj= new JSONObject();
            Layout_Linear_Root a =  new Layout_Linear_Root(getId(), objectList , tempobj);
            array.add(tempobj);
        }
        else{
            JSONObject tempobj= new JSONObject();
            Layout_Relative_Root a =  new Layout_Relative_Root(getId(), objectList , tempobj);
            array.add(tempobj);
        }

        addDragListeners();
        activitylist =list;

    }
    public Activity(String name , HashMap<String, Activity> list, JSONObject obj, Point pos){
        String layout_type ="linear layout";
        activityObject =obj;
        JSONArray array = new JSONArray();


        setId(name);
        this.setName(name);
        this.setSize(Constant.activitySize_X/2, Constant.activitySize_Y/2);
        this.setBackground(Color.BLACK);
        this.setBorder(new LineBorder(Color.black));
        this.setLayout(null);
        this.setLocation(pos.x, pos.y);

        setId(name);
        setActivity_width(Constant.activitySize_X);
        setActivity_height(Constant.activitySize_Y);
        setActivity_position(pos);

        obj.put("name",getId());
        obj.put("x", (long)getActivity_position().x);
        obj.put("y", (long)getActivity_position().y);
        obj.put("width", (long)getActivity_width());
        obj.put("height", (long)getActivity_height());

//        if(layout_type.equals("linear layout")){
//            JSONObject tempobj= new JSONObject();
//            Layout_Linear_Root a =  new Layout_Linear_Root(getId(), objectList , tempobj);
//            array.add(tempobj);
//        }
//        else{
//            JSONObject tempobj= new JSONObject();
//            Layout_Relative_Root a =  new Layout_Relative_Root(getId(), objectList , tempobj);
//            array.add(tempobj);
//        }

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
        height=(long) activityObject.get("height")/2;
        width=(long) activityObject.get("width")/2;
        x=(long) activityObject.get("x");
        y=(long) activityObject.get("y");

        //--------- 변수 값 지정---------------
        setId(name);
        setActivity_position(new Point((int)x, (int)y));
        setActivity_height(((int)height+(int)height/10));
        setActivity_width((int)width);

        //----------창 구성--------------------
        this.setSize(((int)width), ((int)height+(int)height/10)/2);
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
    public Activity(HashMap<String, Activity> list , JSONObject obj, palettePanel pan){
        long width, height, x, y ;
        String name;
        panel=pan;
        activityObject =obj;
        activitylist =list;
        addDragListeners();


        name =(String) activityObject.get("name");
        height=(long) activityObject.get("height")/2;
        width=(long) activityObject.get("width")/2;
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
    public Activity(HashMap<String, Activity> list , JSONObject obj, palettePanel pan, storyBoard stroy){
        long width, height, x, y ;
        String name;
        panel=pan;
        activityObject =obj;
        activitylist =list;
        getStroyBoard(stroy);
        addDragListeners();
        Xmllist =(JSONArray)stroy.jobjRoot.get("xmls");


        name =(String) activityObject.get("name");
        height=(long) activityObject.get("height")/2;
        width=(long) activityObject.get("width")/2;
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

    }
    public Activity(HashMap<String, Activity> list , JSONObject obj, palettePanel pan, storyBoard stroy , JSONObject xmlJObject){
        long width, height, x, y ;
        String name;
        panel=pan;
        activityObject =obj;
        activitylist =list;
        this.xmlObject =xmlJObject;
        getStroyBoard(stroy);
        addDragListeners();
        Xmlname = (String)this.xmlObject.get("name");
        Xmllist =(JSONArray)stroy.jobjRoot.get("xmls");

        if(activityObject.containsKey("next")) {
            JSONArray nextAct = (JSONArray) activityObject.get("next");


            for (int i = 0; nextAct.size() > i; i++) {
                nextActivitylist.add((String) nextAct.get(i));
            }
        }

        name =(String) activityObject.get("name");
        height=(long) activityObject.get("height")/2;
        width=(long) activityObject.get("width")/2;
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



        makeAllObject(xmlJObject);

    }

    public Activity(HashMap<String, Activity> list , JSONObject obj, palettePanel pan, storyBoard stroy, String activityname){
        long width, height, x, y ;

        panel=pan;
        activityObject =obj;
        activitylist =list;
        getStroyBoard(stroy);
        addDragListeners();

        this.activityname =activityname;

        Xmlname =(String) activityObject.get("name");
        height=(long) activityObject.get("height")/2;
        width=(long) activityObject.get("width")/2;
        x=(long) activityObject.get("x");
        y=(long) activityObject.get("y");

        //--------- 변수 값 지정---------------
        setId(activityname);
        setActivity_position(new Point((int)x, (int)y));
        setActivity_height((int)height+(int)height/10);
        setActivity_width((int)width);

        //----------창 구성--------------------
        this.setSize((int)width, (int)height+(int)height/10);
        this.setLocation((int)x, (int)y);
        this.setBorder(new LineBorder(Color.black));
        this.setLayout(null);
        this.setBackground(Color.black);

        nameLabel.setText(this.activityname);
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
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public ArrayList getNextActivitylist(){
        return nextActivitylist;
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
                activityObject.put("name", tempName);

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





     /*   while(buttonKeyList.hasNext()) {
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
//*/

    }
    public void removeActivity_json() {
        String temp = getId();
        String removeKey=null;
        Iterator<String>buttonKeyList = activitylist.keySet().iterator();
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
        setVisible(false);


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
                getParent().repaint();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                getParent().repaint();
                int anchorX = anchorPoint.x;
                int anchorY = anchorPoint.y;
                Point parentOnScreen = getParent().getLocationOnScreen();
                Point mouseOnScreen = e.getLocationOnScreen();
                Point position = new Point(mouseOnScreen.x - parentOnScreen.x - anchorX, mouseOnScreen.y - parentOnScreen.y - anchorY);

                setLocation(position);
                setActivity_position(position);

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
                mouse_p = e.getLocationOnScreen();

            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {



                activityObject.put("x", (long)getActivity_position().x);
                activityObject.put("y", (long)getActivity_position().y);
                storyboard.setRootJObject();
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
            Layout_Linear_Root linear = new Layout_Linear_Root(objectList, jobj, panel, nextActivitylist, activitylist,storyboard, Xmlname);

            return linear;
        }
        else if(type.equals("RelativeLayout")){

            Layout_Relative_Root relative = new Layout_Relative_Root(objectList,jobj, panel, nextActivitylist, activitylist ,storyboard, Xmlname);

            return relative;
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

            for (int i = 0; i < objectArray.size(); i++) {
                String type;
                ObjectCustom temp;
                JSONObject tempJsonObject;
                tempJsonObject = (JSONObject) objectArray.get(i);

                type = (String) tempJsonObject.get("type");
                temp = createObjectCusthom(type, tempJsonObject);
                objectList.put((String) tempJsonObject.get("name"), temp);
                add(temp);

            }

    }


    //-----------------팝업 메뉴 클레스----------
    class PopUpMenu extends JPopupMenu{
        JMenuItem remove;
        JMenuItem set_name;
        JMenuItem set_xml;

        public PopUpMenu() {
            remove = new JMenuItem("Remove");
            set_name = new JMenuItem("Set Name");
            set_xml = new JMenuItem("connect Xml");

            remove.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {

                    removeActivity_json();
                    activityObject.clear();

                }
            });

            set_name.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    settingName();
                }
            });
            set_xml.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Connect_Xml a =new Connect_Xml();
                }
            });

            add(remove);
            add(set_name);
            add(set_xml);
        }

    }


    public void getStroyBoard(storyBoard story){
        storyboard =story;

    }

    class Connect_Xml extends JFrame {

        JLabel next_label;

        JButton okbutton;
        JButton cancelButton;
        JComboBox<String> combo = new JComboBox<String>();
        int scale_size;

        public Connect_Xml(){

            this.setUndecorated(true);      //title bar 제거
            this.setSize(350, 150);          //창 사이즈
            this.setVisible(true);
            this.setLayout(null);
            this.setLocation(mouse_p.x, mouse_p.y);   // 현재 버튼의 위에 덮기 위한 것
            okbutton = new JButton("OK");
            cancelButton = new JButton("NO");
            next_label = new JLabel("XML Choice :");

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
                        activityObject.remove("xmlName");
                    }
                    else
                    {
                        activityObject.put("xmlName", combo.getSelectedItem());
                    }

                    dispose();
                    storyboard.setRootJObject();
                    storyboard.drawActivity();
                }
            });
            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });


            for(int i=0; i<Xmllist.size(); i++)
            {
                JSONObject tempobj;
                tempobj=(JSONObject)Xmllist.get(i);
                combo.addItem((String)tempobj.get("name"));
            }

            combo.addItem("NONE");

            if(activityObject.containsKey("xmlName")){
                combo.setSelectedItem(activityObject.get("xmlName"));
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
}
