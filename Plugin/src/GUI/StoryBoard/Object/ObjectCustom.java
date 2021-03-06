package GUI.StoryBoard.Object;

import Analysis.Constant.ConstantEtc;
import Analysis.Constant.SharedPreference;
import Analysis.RedoUndo.CodeBuilder.Type;
import Analysis.RedoUndo.CommandManager;
import GUI.StoryBoard.Constant;
import GUI.StoryBoard.UI.palettePanel;
import GUI.StoryBoard.storyBoard;
import Xml.JsonToXml;
import Xml.XmlToJson;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import sun.management.snmp.jvminstr.JvmOSImpl;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by 우철 on 2016-02-13.
 */
public class ObjectCustom extends JPanel {
    int parentHeight, parentWidth;
    palettePanel panel;
    //--- Json 요소들 -----------------------
    private String id , objectName,color, objectType;
    private Point object_position;
    private int object_width, object_height;
    private String library_string;
    public String XmlName;
    private String text=null;
    private boolean fixedYN=false;
    private boolean librayYN;
    private boolean focusYN =false;

    //--- json attribute 값 -----------------
    private String layout_width, layout_height, layout_alignParentStart, layout_marginStart, layout_alignParentTop;
    private String layout_alignParentLeft, layout_marginLeft, layout_marginTop , textAllCaps;

    public int totalscale;
    private boolean overbearing = true;
    public storyBoard storyboard;

    private int cursor;
    private Point startPos = null;

    public JSONObject objectJObject;
    public JSONObject attributeObject;

    protected HashMap<String, ObjectCustom> checkkey;
    public HashMap<String, ObjectCustom> objectList;
    public HashMap<String, Activity> activityList;
    public ArrayList nextActivitylist;
    Type typeObject;

    public ObjectCustom() {
        addDragListeners();
        addmouseClickEvent();
        addFocusEvent();
        setBorder(new ResizableBorder(8));
      //  getObjectElement();
    }
    public ObjectCustom(HashMap<String, ObjectCustom> list , JSONObject obj, ArrayList nextlist , HashMap<String, Activity> actList, storyBoard stroy, String ActivitName) {

        nextActivitylist=nextlist;
        objectJObject=obj;
        objectList =list;
        checkkey=list;
        activityList=actList;
        getStroyBoard(stroy);
        this.XmlName =ActivitName;

        addDragListeners();
        addmouseClickEvent();
        addFocusEvent();
        setBorder(new ResizableBorder(8));
        getObjectElement();
    }
    //레이아웃이 받아오는 상속
    public ObjectCustom(HashMap<String, ObjectCustom> list , JSONObject obj, palettePanel pan, ArrayList nextlist, HashMap<String, Activity> actList, storyBoard stroy, String ActivitName) {

        panel = pan;
        nextActivitylist=nextlist;
        objectJObject=obj;
        objectList =list;
        checkkey=list;
        activityList=actList;
        getStroyBoard(stroy);
        this.XmlName =ActivitName;

        addDragListeners();
        addmouseClickEvent();
        addFocusEvent();
        setBorder(new ResizableBorder(8));
        getObjectElement();


    }


    // 객체를 움직이기 위한 값들

    public void addDragListeners(){

        ObjectCustom handle = this;
        addMouseMotionListener(new MouseAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {
                parentHeight= getParent().getHeight();
                parentWidth=getParent().getWidth();


                if (hasFocus()) {
                    ResizableBorder border = (ResizableBorder) getBorder();
                    setCursor(Cursor.getPredefinedCursor(border.getCursor(e)));
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {

                if (startPos != null) {

                    int x = getX();
                    int y = getY();
                    int w = getWidth();
                    int h = getHeight();

                    int dx = e.getX() - startPos.x;
                    int dy = e.getY() - startPos.y;

                    switch (cursor) {
                        case Cursor.N_RESIZE_CURSOR:
                            if (!(h - dy < 50)) {
                                // 위를 침범할 때
                                if(y+dy<0) {
                                    setBounds(x, 0, w, h + y);
                                    setJsonXYWH(x,0,w,h+y,objectJObject);
                                }
                                else {
                                    setBounds(x, y + dy, w, h - dy);
                                    setJsonXYWH(x,y+dy,w,h-dy,objectJObject);
                                }
                                resize();
                                fixedYN=true;
                            }
                            break;

                        case Cursor.S_RESIZE_CURSOR:
                            if (!(h + dy < 50)) {
                                //아래를 침범했을 때
                                if(y+h+dy>getParent().getHeight())
                                {
                                    setBounds(x,y,w,getParent().getHeight()-y);
                                    setJsonXYWH(x,y,w,getParent().getHeight()-y,objectJObject);
                                }
                                else{
                                    setBounds(x, y, w, h + dy);
                                    setJsonXYWH(x, y, w, h + dy,objectJObject);
                                }
                                startPos = e.getPoint();
                                resize();
                                fixedYN=true;
                            }
                            break;

                        case Cursor.W_RESIZE_CURSOR:
                            if (!(w - dx < 50)) {
                                if(x+dx<0) {
                                    setBounds(0, y, w + x, h);
                                    setJsonXYWH(0, y, w + x, h,objectJObject);
                                }
                                else {
                                    setBounds(x + dx, y, w - dx, h);
                                    setJsonXYWH(x + dx, y, w - dx, h,objectJObject);
                                }
                                resize();
                                fixedYN=true;
                            }
                            break;

                        case Cursor.E_RESIZE_CURSOR:
                            if (!(w + dx < 50)) {
                                if(getParent().getWidth()<x+w+dx){
                                    setBounds(x, y, getParent().getWidth()-x, h);
                                    setJsonXYWH(x, y, getParent().getWidth()-x, h,objectJObject);
                                }
                                else{
                                    setBounds(x, y, w + dx, h);
                                    setJsonXYWH(x, y, w + dx, h,objectJObject);
                                }

                                startPos = e.getPoint();
                                fixedYN=true;
                                resize();
                            }
                            break;

                        case Cursor.NW_RESIZE_CURSOR:
                            if (!(w - dx < 50) && !(h - dy < 50)) {
                                if(x+dx<0 && y+dy<0)
                                {
                                    setBounds(0, 0, w + x, h +y);
                                    setJsonXYWH(0, 0, w + x, h + y,objectJObject);
                                }
                                else if(x+dx<0){
                                    setBounds(0, y + dy, w + x, h - dy);
                                    setJsonXYWH(0, y + dy, w + x, h - dy,objectJObject);
                                }
                                else if(y+dy<0)
                                {
                                    setBounds(x + dx, 0, w - dx, h +y );
                                    setJsonXYWH(x + dx, 0, w - dx, h +y ,objectJObject);
                                }
                                else {
                                    setBounds(x + dx, y + dy, w - dx, h - dy);
                                    setJsonXYWH(x + dx, y + dy, w - dx, h - dy,objectJObject);
                                }
                                fixedYN=true;
                                resize();
                            }
                            break;

                        case Cursor.NE_RESIZE_CURSOR:
                            if (!(w + dx < 50) && !(h - dy < 50)) {
                                if(x+w+dx>getParent().getWidth() && y+dy<0){
                                    setBounds(x, 0, getParent().getWidth()-x, h + y);
                                    setJsonXYWH(x, 0, getParent().getWidth()-x, h + y,objectJObject);
                                }
                                else if(x+w+dx>getParent().getWidth()){
                                    setBounds(x, y + dy, getParent().getWidth()-x, h - dy);
                                    setJsonXYWH(x, y + dy, getParent().getWidth()-x, h - dy,objectJObject);
                                }
                                else if(y+dy<0){
                                    setBounds(x, 0, w + dx, h + y);
                                    setJsonXYWH(x, 0, w + dx, h + y,objectJObject);
                                }
                                else{
                                    setBounds(x, y + dy, w + dx, h - dy);
                                    setJsonXYWH(x, y + dy, w + dx, h - dy,objectJObject);
                                }
                                fixedYN=true;
                                startPos = new Point(e.getX(), startPos.y);
                                resize();
                            }
                            break;

                        case Cursor.SW_RESIZE_CURSOR:
                            if (!(w - dx < 50) && !(h + dy < 50)) {
                                if(x+dx<0 && getParent().getHeight()<y+h+dy)
                                {
                                    setBounds(0, y, w + x, getParent().getHeight()-y);
                                    setJsonXYWH(0, y, w + x, getParent().getHeight()-y,objectJObject);
                                }
                                else if(x+dx<0)
                                {
                                    setBounds(0, y, w + x, h + dy);
                                    setJsonXYWH(0, y, w + x, h + dy,objectJObject);

                                }
                                else if(getParent().getHeight()<y+h+dy)
                                {
                                    setBounds(x + dx, y, w - dx, getParent().getHeight()-y);
                                    setJsonXYWH(x + dx, y, w - dx,getParent().getHeight()-y,objectJObject);
                                }
                                else
                                {
                                    setBounds(x + dx, y, w - dx, h + dy);
                                    setJsonXYWH(x + dx, y, w - dx, h + dy,objectJObject);
                                }
                                fixedYN=true;
                                startPos = new Point(startPos.x, e.getY());
                                resize();
                            }
                            break;

                        case Cursor.SE_RESIZE_CURSOR:
                            if (!(w + dx < 50) && !(h + dy < 50)) {
                                if(getParent().getWidth()<x+w+dx && getParent().getHeight()<y+h+dy)
                                {
                                    setBounds(x, y,getParent().getWidth()-x, getParent().getHeight()-y);
                                    setJsonXYWH(x, y, getParent().getWidth()-x, getParent().getHeight()-y,objectJObject);
                                }
                                else if(getParent().getWidth()<x+w+dx)
                                {
                                    setBounds(x, y, getParent().getWidth()-x, h + dy);
                                    setJsonXYWH(x, y, getParent().getWidth()-x, h + dy,objectJObject);

                                }
                                else if(getParent().getHeight()<y+h+dy)
                                {
                                    setBounds(x , y, w + dx, getParent().getHeight()-y);
                                    setJsonXYWH(x , y, w + dx,getParent().getHeight()-y,objectJObject);
                                }
                                else
                                {
                                    setBounds(x, y, w + dx, h + dy);
                                    setJsonXYWH(x, y, w + dx, h + dy,objectJObject);
                                }
                                fixedYN=true;
                                startPos = e.getPoint();
                                resize();
                            }
                            break;

                        case Cursor.MOVE_CURSOR:
                            Rectangle bounds = getBounds();

                            if(x+dx<0) dx=-x;
                            if(x+w+dx>getParent().getWidth()) dx=getParent().getWidth()-x-w;
                            if(y+dy<0) dy= -y;
                            if(y+h+dy>getParent().getHeight()) dy=getParent().getHeight()-y-h;
                            bounds.translate(dx, dy);
                            setBounds(bounds);
                            setJsonXYWH(bounds.x ,bounds.y, w, h, objectJObject);
                            fixedYN=true;

                            resize();
                    }

                    setCursor(Cursor.getPredefinedCursor(cursor));
                }
            }

        });

    }
    public void addmouseClickEvent(){


        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {


            }

            @Override
            public void mousePressed(MouseEvent e) {
                setBorder(new ResizableBorder(8));

                ResizableBorder border = (ResizableBorder) getBorder();
                cursor = border.getCursor(e);
                startPos = e.getPoint();
                requestFocus();
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                System.out.println("Send Data :::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::");
                System.out.println(objectJObject);
                if(fixedYN)
                    fixObject(2);
                repaint();
                startPos = null;
                fixedYN=false;
                System.out.println("Mouse Released");
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                if(focusYN==false)
                    setBorder(new LineBorder(Color.black));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBorder(new ResizableBorder(8));
                setCursor(Cursor.getDefaultCursor());
            }
        });
    }
    public void addFocusEvent(){
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                focusYN=true;
            }

            @Override
            public void focusLost(FocusEvent e) {
                focusYN=false;
                setBorder(new ResizableBorder(8));
                startPos = null;
                repaint();

            }
        });
    }

    public boolean isOverbearing() {
        return overbearing;
    }
    public void setOverbearing(boolean overbearing) {
        this.overbearing = overbearing;
    }


    //-------------------------------------------------
    //          private 변수 접근 메소드
    //-------------------------------------------------
    protected void setPosition(Point position) {this.object_position = position; }
    protected Point isPosition() { return object_position; }
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public int getObject_width() {
        return object_width;
    }
    public void setObject_width(int object_width) {
        this.object_width = object_width;
    }
    public int getObject_height() {
        return object_height;
    }
    public void setObject_height(int object_height) {
        this.object_height = object_height;
    }
    public String getLayout_marginTop() {
        return layout_marginTop;
    }
    public void setLayout_marginTop(String layout_marginTop) {
        this.layout_marginTop = layout_marginTop;
    }
    public String getLayout_width() {
        return layout_width;
    }
    public void setLayout_width(String layout_width) {
        this.layout_width = layout_width;
    }
    public String getLayout_height() {
        return layout_height;
    }
    public void setLayout_height(String layout_height) {
        this.layout_height = layout_height;
    }
    public String getLayout_alignParentStart() {
        return layout_alignParentStart;
    }
    public void setLayout_alignParentStart(String layout_alignParentStart) {
        this.layout_alignParentStart = layout_alignParentStart;
    }
    public String getLayout_marginStart() {
        return layout_marginStart;
    }
    public void setLayout_marginStart(String layout_marginStart) {
        this.layout_marginStart = layout_marginStart;
    }
    public String getLayout_alignParentTop() {
        return layout_alignParentTop;
    }
    public void setLayout_alignParentTop(String layout_alignParentTop) {
        this.layout_alignParentTop = layout_alignParentTop;
    }
    public String getLayout_alignParentLeft() {
        return layout_alignParentLeft;
    }
    public void setLayout_alignParentLeft(String layout_alignParentLeft) {
        this.layout_alignParentLeft = layout_alignParentLeft;
    }
    public String getLayout_marginLeft() {
        return layout_marginLeft;
    }
    public void setLayout_marginLeft(String layout_marginLeft) {
        this.layout_marginLeft = layout_marginLeft;
    }
    public String getTextAllCaps() {
        return textAllCaps;
    }
    public void setTextAllCaps(String textAllCaps) {
        this.textAllCaps = textAllCaps;
    }
    public boolean isLibrayYN() {
        return librayYN;
    }
    public void setLibrayYN(boolean librayYN) {
        this.librayYN = librayYN;
    }
    public String getLibrary_string() {
        return library_string;
    }
    public void setLibrary_string(String library_string) {
        this.library_string = library_string;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getObjectName() {
        return objectName;
    }
    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }
    public String getObjectType() {
        return objectType;
    }
    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }
    //--------------------------------------------------------------------------------------------------------------
    private void resize() {
        if (getParent() != null) {
            ((JComponent) getParent()).revalidate();
        }
    }

    public void setJsonXYWH(int x, int y, int w, int h , JSONObject job){
        job.put("x",(long)x);
        job.put("y",(long)y);
        job.put("width",(long)w);
        job.put("height",(long)h);

    }

    public void getStroyBoard(storyBoard story){
        storyboard =story;
    }

    public void fixObject(int scale){
        totalscale=scale;
        removeAttribue();
        setAttribue(objectJObject);
        setScale(scale);

        System.out.println("BeforeObject :"+ objectJObject);
        System.out.println("BeforeObject :"+ storyboard.jobjRoot);

        storyboard.setRootJObject(storyboard.jobjRoot);

        String pathpath;
        pathpath= SharedPreference.PROJECT.get().getBasePath() + ConstantEtc.PROJECT_XML_PATH + "/assets/plugin.txt";

//
//        JsonToXml jsonToXml = new JsonToXml();
//        jsonToXml.make(pathpath);
//
//        XmlToJson xmlToJson = new XmlToJson();
//        xmlToJson.make();
//
//
//
//        System.out.println("AfterObject :"+ objectJObject);
//        System.out.println("AfterObject :"+ storyboard.jobjRoot);
//
//        storyboard.drawActivity();
        storyboard.saveAndDraw();
    }
    public void newObject(){
        storyboard.setRootJObject(storyboard.jobjRoot);

        String pathpath;
        pathpath= SharedPreference.PROJECT.get().getBasePath() + ConstantEtc.PROJECT_XML_PATH + "/assets/plugin.txt";

        JsonToXml jsonToXml = new JsonToXml();
        jsonToXml.make(pathpath);
        XmlToJson xmlToJson = new XmlToJson();
        xmlToJson.make();


        storyboard.drawActivity();

    }
    public void removeAttribue(){
        if(objectJObject.containsKey("attribute")) {
            objectJObject.remove("attribute");


        }
    }
    //입력 -> attribute 가 필요한 json 출력 attribute json
    public JSONObject setAttribue(JSONObject json){
        JSONObject attribute= new JSONObject();

        int center_x,center_y, parent_w, parent_h;


        String top,bottom,right,left;


        long isX , isY;
        if(totalscale==1){
            isX=(long)json.get("x")/2;
            isY=(long)json.get("y")/2;
        }else{
            isX=(long)json.get("x");
            isY=(long)json.get("y");
        }
        center_x=(int)isX+getWidth()/2;
        center_y=(int)isY+getWidth()/2;
        parent_w=parentWidth;
        parent_h=parentHeight;
        System.out.println(isX+ " , " +isY);
        System.out.println(isPosition()+ "  "+ parent_h+"  "+parent_w);

        top = String.valueOf(isY);
        bottom = String.valueOf( (parentHeight-(isY+getObject_height())) );
        right = String.valueOf( (parentWidth-(isX+getObject_width())) );
        left = String.valueOf( (isX) );

        System.out.println("top:"+top +"  bottom:"+bottom+ " right"+right+" left"+left);
        top+="dp";
        bottom+="dp";
        right+="dp";
        left+="dp";
        attribute.put("id",getId());

        attribute.put("layout_width","wrap_content");
        attribute.put("layout_height","wrap_content");
        attribute.put("textAllCaps","false");

        if(text!=null){
            attribute.put("text",getText());
        }
        // 왼쪽 상단
        if(center_x<parent_w && center_y<parent_h){
            attribute.put("layout_alignParentStart","true");
            attribute.put("layout_alignParentLeft","true");

            attribute.put("layout_marginLeft",left);
            attribute.put("layout_marginStart",left);

            attribute.put("layout_marginTop",top);
            attribute.put("layout_alignParentTop","true");

        }
        //왼쪽 하단
        else if(center_x<parent_w && center_y>parent_h){
            attribute.put("layout_alignParentStart","true");
            attribute.put("layout_alignParentLeft","true");

            attribute.put("layout_marginLeft",left);
            attribute.put("layout_marginStart",left);

            attribute.put("layout_marginBottom",bottom);
            attribute.put("layout_alignParentBottom","true");
        }
        //오른쪽 상단
        else if(center_x>parent_w && center_y<parent_h){
            attribute.put("layout_alignParentEnd","true");
            attribute.put("layout_alignParentRight","true");

            attribute.put("layout_marginRight",right);
            attribute.put("layout_marginEnd",right);

            attribute.put("layout_marginBottom",bottom);
            attribute.put("layout_alignParentTop","true");

        }
        //오른쪽 하단
        else if(center_x<parent_w && center_y<parent_h){
            attribute.put("layout_alignParentEnd","true");
            attribute.put("layout_alignParentRight","true");

            attribute.put("layout_marginRight",right);
            attribute.put("layout_marginEnd",right);

            attribute.put("layout_marginBottom",bottom);
            attribute.put("layout_alignParentBottom","true");
        }
        // 수평 수직
        else if(center_x==parent_w && center_y==parent_h){
            attribute.put("layout_centerlnParent","both");
        }
        //수평
        else if(center_y==parent_h){
            attribute.put("layout_centerlnParent","vertical");
            if(center_x<parent_w){
                attribute.put("layout_marginLeft",left);
                attribute.put("layout_marginStart", left);
                attribute.put("layout_alignParentTop","true");
            }
            else{
                attribute.put("layout_marginRight",right);
                attribute.put("layout_marginEnd",right);
                attribute.put("layout_alignParentBottom","true");
            }
        }
        // 수직
        else if(center_x==parent_w){
            attribute.put("layout_centerlnParent","horizontal");
            if(center_y<parent_h){
                attribute.put("layout_marginTop",top);
            }
            else{
                attribute.put("layout_marginBottom",bottom);
            }
        }

        json.put("attribute",attribute);
        return attribute;
    }
    public void sendData(){

        storyboard.getRootObject();
    }

    public void getObjectElement(){
        long width, height, x, y ;

        height=(long) objectJObject.get("height")/2;
        width=(long) objectJObject.get("width")/2;
        x=(long) objectJObject.get("x")/2;
        y=(long) objectJObject.get("y")/2;

        if(objectJObject.containsKey("name"))
            setObjectName((String)objectJObject.get("name"));
        if(objectJObject.containsKey("x") && objectJObject.containsKey("y"))
            setPosition(new Point((int)x,(int)y));
        if(objectJObject.containsKey("width"))
            setObject_width((int)width);
        if(objectJObject.containsKey("height"))
            setObject_height((int)height);
        if(objectJObject.containsKey("type"))
            setObjectType((String)objectJObject.get("type"));


        getAttributeJson();
    }

    public void getAttributeJson(){
        if(objectJObject.containsKey("attribute"))
        {
            attributeObject=(JSONObject)objectJObject.get("attribute");

            if(attributeObject.containsKey("text")){
                setText((String)attributeObject.get("text"));
            }
            if(attributeObject.containsKey("id")){
                setId((String)attributeObject.get("id"));
            }
        }
    }
    public boolean checkObjectId(String id_) {
        String temp = this.getId();
        boolean checkId = false;
        Iterator<String> ObjectKeyList = checkkey.keySet().iterator();

        while (ObjectKeyList.hasNext()) {
            String key = (String) ObjectKeyList.next();
            Object o = checkkey.get(key);
            ObjectCustom b = (ObjectCustom) o;
            if(b.getId()==null)
                continue;

            if (b.getId().equals(id_)) {
                if (temp.equals(id_))
                    continue;

                checkId = true;
            }

        }

        return checkId;
    }

    public void setScale(int scale){
        long width, height, x, y ;

        height=(long) objectJObject.get("height")*scale;
        width=(long) objectJObject.get("width")*scale;
        x=(long) objectJObject.get("x")*scale;
        y=(long) objectJObject.get("y")*scale;

        objectJObject.put("x",x);
        objectJObject.put("y",y);
        objectJObject.put("height",height);
        objectJObject.put("width",width);

    }

    class ImagePanel1 extends JPanel
    {

        private Image img;

        public ImagePanel1(){

        }
        public ImagePanel1(String img) {
            this(new ImageIcon(img).getImage());
        }

        public ImagePanel1(Image img) {
            this.img = img;
        }

        public void paintComponent(Graphics g) {
            g.drawImage(img, 0, 0, 50,50,this);

        }

        public Image getImageView(){
            return this.img;
        }
    }

    //------- 버튼 제거-------------------
    public void removeObject(){
        String temp = this.getId();
        String removeKey=null;
        this.setVisible(false);
        Iterator<String> objectKeyList = checkkey.keySet().iterator();

        while(objectKeyList.hasNext()) {
            String key = (String) objectKeyList.next();
            Object o = checkkey.get(key);
            ObjectCustom b = (ObjectCustom) o;
            System.out.println("탐색하는 아이디 :"+b.getId());
            System.out.println("지워야 할 아이디 :"+ temp);
            if(b.getId()==null)
                continue;
            if(b.getId().equals(temp)){
                removeKey = key;
                System.out.println("지우는 키 :"+removeKey);
            }
        }
        if(removeKey!=null) {

            objectJObject.clear();
            checkkey.remove(removeKey);
        }
        JSONArray tempArr =(JSONArray)storyboard.jobjRoot.get("xmls");
        for(int j=0; j<tempArr.size(); j++){
            JSONObject tempobj = (JSONObject)tempArr.get(j);
            if(XmlName.equals(tempobj.get("name"))){
                JSONArray objectobject = (JSONArray)tempobj.get("object");

                JSONObject haha =(JSONObject)objectobject.get(0);
                JSONArray arrayTemp = (JSONArray)haha.get("object");

                for(int k=0; k<arrayTemp.size(); k++) {
                    JSONObject lala = (JSONObject)arrayTemp.get(k);
                    if(lala.isEmpty()) {
                        arrayTemp.remove(k);
                        break;
                    }
                }
            }
        }


        storyboard.setRootJObject(storyboard.jobjRoot);

        String pathpath;
        pathpath = SharedPreference.PROJECT.get().getBasePath() + ConstantEtc.PROJECT_XML_PATH + "/assets/plugin.txt";
        System.out.println(storyboard.jobjRoot);
        JsonToXml jsonToXml = new JsonToXml();
        jsonToXml.make(pathpath);

        XmlToJson xmlToJson = new XmlToJson();
        xmlToJson.make();
        storyboard.setRootJObject(storyboard.jobjRoot);

        CommandManager deleteobject = CommandManager.getInstance();
        deleteobject.deleteLocalComponent(getId().split("/")[1], XmlName.split("\\.")[0], typeObject);

    }

    public void saveAndDraw(){
        storyboard.setRootJObject(storyboard.jobjRoot);

        String pathpath;
        pathpath= SharedPreference.PROJECT.get().getBasePath() + ConstantEtc.PROJECT_XML_PATH + "/assets/plugin.txt";

        JsonToXml jsonToXml = new JsonToXml();
        jsonToXml.make(pathpath);

        XmlToJson xmlToJson = new XmlToJson();
        xmlToJson.make();
        storyboard.drawActivity();

    }



}
