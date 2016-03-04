package GUI.StoryBoard;

import Analysis.Constant.ConstantEtc;
import Analysis.Constant.SharedPreference;
import GUI.StoryBoard.Object.Activity;
import GUI.StoryBoard.Object.Xml;
import GUI.StoryBoard.UI.palettePanel;
import com.sun.org.apache.xpath.internal.operations.And;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

import Xml.*;


/**
 * Created by 우철 on 2016-02-11.
 */
public class storyBoard extends JPanel {
    CustomJpanel jpan;
    JScrollPane scroll;
    public JSONObject jobjRoot;
    public JSONObject AobjRoot;
    public Point mouse_p;

    JSONArray activityArrayData;
    JSONArray xmlArrayData;

    palettePanel parlPanel = new palettePanel();
    private Point prePoint;
    private int zoom = 0;
    private String appName;
    private boolean isActivity;
    private Point scroll_p = new Point(0, 0);
    private int x, y;
    HashMap<String, Activity> activity_list = new HashMap();

    // 생성자----------------------------------------------------------------
    public storyBoard() throws IOException {

        jpan = new CustomJpanel();
        jpan.setLayout(null);
        scroll = new JScrollPane(jpan, JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);


        jpan.setPreferredSize(new Dimension(7000, 7000));
        this.setLayout(new BorderLayout());
        this.add(scroll, "Center");


        this.setVisible(true);
        // 이동을 위한 마우스 이벤트
        scroll.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                isActivity = false;
                scroll.repaint();

                int preX = prePoint.x;
                int preY = prePoint.y;
                int temp_Vertical;
                int temp_Horizon;
                temp_Vertical = scroll.getVerticalScrollBar().getValue();
                temp_Horizon = scroll.getHorizontalScrollBar().getValue();

                scroll.getVerticalScrollBar().setValue(temp_Vertical + (preY - e.getY()));
                scroll.getHorizontalScrollBar().setValue(temp_Horizon + (preX - e.getX()));


                scroll_p.y = scroll.getVerticalScrollBar().getValue();
                scroll_p.x = scroll.getHorizontalScrollBar().getValue();


                setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                prePoint = e.getPoint();

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                if (parlPanel.getChoice() == 1) {
                    x = e.getX();
                    y = e.getY();
                    isActivity = true;
                    scroll.revalidate();       // 무효화 선언된 화면을 알려줌
                    scroll.repaint();          // 다시 그려준다.
                }

                prePoint = e.getPoint();
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

            }


        });
        scroll.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getModifiers() == MouseEvent.BUTTON3_MASK) {
                    PopUpMenu menu = new PopUpMenu();
                    menu.show(e.getComponent(), e.getX(), e.getY());
                    mouse_p = e.getLocationOnScreen();
                } else if (parlPanel.getChoice() == 1) {
                    NewWindow a = new NewWindow(e.getPoint());


                    parlPanel.setChoice(0);
                    isActivity = false;
                    scroll.revalidate();       // 무효화 선언된 화면을 알려줌
                    scroll.repaint();          // 다시 그려준다.

                }
                else if (e.getClickCount() == 2 && !e.isConsumed()) {
                    AndDraw();
                }
                isActivity = false;
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
                isActivity = false;
                scroll.revalidate();       // 무효화 선언된 화면을 알려줌
                scroll.repaint();          // 다시 그려준다.

            }
        });
        // 버튼 클릭을 위한 마우스 이벤트
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }
        });

        // 전부 그린다.
        add(parlPanel, "West");

        drawActivity_first();
    }


    // 다시 그리기 위한 함수-------------------------------------------------
    public void repaint_window() {
        revalidate();       // 무효화 선언된 화면을 알려줌
        repaint();
    }

    //-------------Json 받아서 그려주는 함수---------------------------------
    //JObjectRoute 파일 경로. text 파일을 읽어온다.
    JSONObject parserJObject(String JObjectRoute) {
        JSONParser par = new JSONParser();

        try {
            FileReader in = new FileReader(JObjectRoute);
            Object obj = par.parse(in);
            return (JSONObject) obj;
        } catch (Exception e) {
            return null;
        }

    }

    /// Activity key 값과 전부 제거
    public void removeAllActivity() {
        Iterator<String> activityKeyList = activity_list.keySet().iterator();


        while (activityKeyList.hasNext()) {
            String key = (String) activityKeyList.next();
            Object o = activity_list.get(key);
            Activity a = (Activity) o;
            a.removeActivity();
        }

        activity_list.clear();   // 현재까지 key 값들 모두s 제거
    }

    // JSON 파일을 받은 것을 전부 그려준다.
    public void drawActivity() {
        removeAllActivity();

        String pathpath;
        pathpath = SharedPreference.PROJECT.get().getBasePath() + ConstantEtc.PROJECT_XML_PATH + "/assets/plugin.txt";

        jobjRoot = parserJObject(pathpath);

        System.out.println(jobjRoot);
        setAppName((String) jobjRoot.get("appName"));
        activityArrayData = (JSONArray) AobjRoot.get("activities");
        xmlArrayData = (JSONArray) jobjRoot.get("xmls");

        for (int i = 0; i < activityArrayData.size(); i++) {
            JSONObject tempJsonObject;
            tempJsonObject = (JSONObject) activityArrayData.get(i);
            if (tempJsonObject.isEmpty()) {
                activityArrayData.remove(i);
                i = -1;
            }
        }

        // 가지고 있는 액티비티를 만든다.
        for (int i = 0; i < activityArrayData.size(); i++) {
            JSONObject activity_jobj;
            JSONArray activity_list;
            String xmlname;
            activity_jobj = (JSONObject) activityArrayData.get(i);

            if (activity_jobj.containsKey("xmlName")) {
                xmlname = (String) activity_jobj.get("xmlName");

                // xml 이름이 있나 없나 탐색
                for (int j = 0; j < xmlArrayData.size(); j++) {
                    JSONObject xmltempJobj;
                    xmltempJobj = (JSONObject) xmlArrayData.get(j);
                    if (xmlname.equals(xmltempJobj.get("name"))) {

                        Activity a = new Activity(this.activity_list, activity_jobj, parlPanel, this, xmltempJobj);

                        this.activity_list.put((String) activity_jobj.get("name"), a);
                        a.setOverbearing(true);
                        jpan.add(a);
                        break;

                    } else {
                        if (j == xmlArrayData.size() - 1) {
                            activity_jobj.remove("xmlName");
                            Activity a = new Activity(this.activity_list, activity_jobj, parlPanel, this);

                            this.activity_list.put((String) activity_jobj.get("name"), a);
                            a.setOverbearing(true);
                            jpan.add(a);
                        }
                    }

                }
            } else {
                Activity a = new Activity(this.activity_list, activity_jobj, parlPanel, this);

                this.activity_list.put((String) activity_jobj.get("name"), a);
                a.setOverbearing(true);
                jpan.add(a);
            }
        }

        revalidate();       // 무효화 선언된 화면을 알려줌
        repaint();          // 다시 그려준다.
    }
    public void drawActivity_first() {
        removeAllActivity();

        String pathpath, pathpath2;
        pathpath = SharedPreference.PROJECT.get().getBasePath() + ConstantEtc.PROJECT_XML_PATH + "/assets/plugin.txt";

        AobjRoot = parserJObject(pathpath);
        jobjRoot = parserJObject(pathpath);

        SaveActivity(AobjRoot);

        System.out.println(jobjRoot);
        setAppName((String) jobjRoot.get("appName"));
        activityArrayData = (JSONArray) jobjRoot.get("activities");
        xmlArrayData = (JSONArray) jobjRoot.get("xmls");

        for (int i = 0; i < activityArrayData.size(); i++) {
            JSONObject tempJsonObject;
            tempJsonObject = (JSONObject) activityArrayData.get(i);
            if (tempJsonObject.isEmpty()) {
                activityArrayData.remove(i);
                i = -1;
            }
        }

        // 가지고 있는 액티비티를 만든다.
        for (int i = 0; i < activityArrayData.size(); i++) {
            JSONObject activity_jobj;
            JSONArray activity_list;
            String xmlname;
            activity_jobj = (JSONObject) activityArrayData.get(i);

            if (activity_jobj.containsKey("xmlName")) {
                xmlname = (String) activity_jobj.get("xmlName");

                // xml 이름이 있나 없나 탐색
                for (int j = 0; j < xmlArrayData.size(); j++) {
                    JSONObject xmltempJobj;
                    xmltempJobj = (JSONObject) xmlArrayData.get(j);
                    if (xmlname.equals(xmltempJobj.get("name"))) {

                        Activity a = new Activity(this.activity_list, activity_jobj, parlPanel, this, xmltempJobj);

                        this.activity_list.put((String) activity_jobj.get("name"), a);
                        a.setOverbearing(true);
                        jpan.add(a);
                        break;

                    } else {
                        if (j == xmlArrayData.size() - 1) {
                            activity_jobj.remove("xmlName");
                            Activity a = new Activity(this.activity_list, activity_jobj, parlPanel, this);

                            this.activity_list.put((String) activity_jobj.get("name"), a);
                            a.setOverbearing(true);
                            jpan.add(a);
                        }
                    }

                }
            } else {
                Activity a = new Activity(this.activity_list, activity_jobj, parlPanel, this);

                this.activity_list.put((String) activity_jobj.get("name"), a);
                a.setOverbearing(true);
                jpan.add(a);
            }
        }

        revalidate();       // 무효화 선언된 화면을 알려줌
        repaint();          // 다시 그려준다.
    }

    public void drawActivity_temp() {
        removeAllActivity();

        setAppName((String) jobjRoot.get("appName"));
        activityArrayData = (JSONArray) jobjRoot.get("activity");
        // 가지고 있는 액티비티를 만든다.
        for (int i = 0; i < activityArrayData.size(); i++) {
            JSONObject activity_jobj;
            activity_jobj = (JSONObject) activityArrayData.get(i);
            if (activity_jobj.isEmpty()) {
                activityArrayData.remove(i);
                i = -1;
            }
        }
        for (int i = 0; i < activityArrayData.size(); i++) {
            JSONObject activity_jobj;
            activity_jobj = (JSONObject) activityArrayData.get(i);


            Activity a = new Activity(activity_list, activity_jobj, parlPanel, this);
            activity_list.put((String) activity_jobj.get("name"), a);
            a.setOverbearing(true);
            jpan.add(a);

        }

        revalidate();       // 무효화 선언된 화면을 알려줌
        repaint();          // 다시 그려준다.
    }

    //--------------새로운 Activity를 만들기 위해 필요함---------------------
    // 새로운 activity를 만드는 함수
    public void makeNewActivity(String resultStr, HashMap<String, Activity> list) {
        if (list.containsKey(resultStr) == true) {
            JOptionPane.showMessageDialog(null, resultStr + "는 이미 중복되어있는 ID 값입니다.");
        } else {
            JSONObject obj = new JSONObject();

            Activity a = new Activity(resultStr, list, obj);

            a.getStroyBoard(this);

            a.setOverbearing(true);
            list.put(resultStr, a);
            jpan.add(a);

            activityArrayData.add(obj);

            sendJSonData();
            repaint_window();
        }
    }

    public void makeNewActivity(String resultStr, HashMap<String, Activity> list, Point pos) {
        if (list.containsKey(resultStr) == true) {
            JOptionPane.showMessageDialog(null, resultStr + "는 이미 중복되어있는 ID 값입니다.");
        } else {
            JSONObject obj = new JSONObject();

            JSONArray xmlsarray = (JSONArray) jobjRoot.get("xmls");
            Activity a = new Activity(resultStr, list, obj, pos, xmlsarray);

            System.out.println("present activitArrayData : " + activityArrayData);
            System.out.println("create Activity : " + obj);

            activityArrayData.add(xmlsarray);

            a.setOverbearing(true);
            list.put(resultStr, a);
            jpan.add(a);

            sendJSonData();
            repaint_window();

            SaveActivity(jobjRoot);
            AndDraw();
            saveAndDraw();

        }
    }

    public void sendJSonData() {
        System.out.println(jobjRoot);


    }


    //-----------private 접근 함수------------------------------------------
    public String getAppName() {
        return appName;
    }

    public void setAppName(String appName) {
        this.appName = appName;
    }

    //------- 새로운 것을 만들기 위한 창--------------------------------------
    class NewWindow extends JFrame {

        JLabel id_label = new JLabel("id :");
        JTextField id_field = new JTextField();
        JButton okbutton = new JButton("OK");

        public NewWindow() {

            int standard_x = Constant.activitySize_X / 2;

            int standard_y = Constant.activitySize_Y / 2;
            int standard_scale = standard_y / 250;

            this.setSize(standard_x + standard_x / 10, standard_y / 4);          //창 사이즈
            this.setUndecorated(true);      //title bar 제거
            this.setLocation(100, 100);
            this.setVisible(true);
            this.setLayout(null);   // 자유 레이아웃
            this.getRootPane().setBorder(new LineBorder(Color.black));  //테두리 설정

            okbutton.setMargin(new Insets(0, 0, 0, 0));

            id_field.setFont(new Font("Serif", Font.PLAIN, standard_scale * 18));
            id_label.setFont(new Font("Serif", Font.PLAIN, standard_scale * 18));


            id_label.setLocation(standard_scale * 5, standard_scale * 5);
            id_field.setLocation(standard_scale * 55, standard_scale * 5);
            okbutton.setLocation(standard_scale * 100, standard_scale * 35);

            id_label.setSize(standard_scale * 50, standard_scale * 20);
            id_field.setSize(standard_scale * 100, standard_scale * 20);
            okbutton.setSize(standard_scale * 50, standard_scale * 20);

            this.add(id_label);

            this.add(id_field);
            this.add(okbutton);

            // 포커스가 벗어나면 알아서 꺼진다.
            this.addWindowFocusListener(new WindowFocusListener() {
                @Override
                public void windowGainedFocus(WindowEvent e) {
                }

                @Override
                public void windowLostFocus(WindowEvent e) {
                    dispose();
                }
            });
            // 오케이 버튼을 눌렀을때의 위한 함수
            okbutton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    makeNewActivity(id_field.getText(), activity_list);
                    dispose();
                }
            });
            // 엔터시 입력시의 이벤트를 위한 함수
            id_field.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {

                }

                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        makeNewActivity(id_field.getText(), activity_list);
                        dispose();
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            });
        }

        public NewWindow(Point p) {
            int standard_x = Constant.activitySize_X / 2;

            int standard_y = Constant.activitySize_Y / 2;
            int standard_scale = standard_y / 250;

            this.setSize(standard_x + standard_x / 10, standard_y / 4);          //창 사이즈
            this.setUndecorated(true);      //title bar 제거
            this.setLocation(p.x, p.y);
            this.setVisible(true);
            this.setLayout(null);   // 자유 레이아웃
            this.getRootPane().setBorder(new LineBorder(Color.black));  //테두리 설정

            okbutton.setMargin(new Insets(0, 0, 0, 0));

            id_field.setFont(new Font("Serif", Font.PLAIN, standard_scale * 18));
            id_label.setFont(new Font("Serif", Font.PLAIN, standard_scale * 18));


            id_label.setLocation(standard_scale * 5, standard_scale * 5);
            id_field.setLocation(standard_scale * 55, standard_scale * 5);
            okbutton.setLocation(standard_scale * 100, standard_scale * 35);

            id_label.setSize(standard_scale * 50, standard_scale * 20);
            id_field.setSize(standard_scale * 100, standard_scale * 20);
            okbutton.setSize(standard_scale * 50, standard_scale * 20);

            this.add(id_label);

            this.add(id_field);
            this.add(okbutton);

            // 포커스가 벗어나면 알아서 꺼진다.
            this.addWindowFocusListener(new WindowFocusListener() {
                @Override
                public void windowGainedFocus(WindowEvent e) {
                }

                @Override
                public void windowLostFocus(WindowEvent e) {
                    dispose();
                }
            });
            // 오케이 버튼을 눌렀을때의 위한 함수
            okbutton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    makeNewActivity(id_field.getText(), activity_list, new Point(p.x + scroll_p.x, p.y + scroll_p.y));
                    dispose();
                }
            });
            // 엔터시 입력시의 이벤트를 위한 함수
            id_field.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {

                }

                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        makeNewActivity(id_field.getText(), activity_list, p);
                        dispose();
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            });
        }

    }

    class NewXML extends JFrame {

        JLabel xmlName_label = new JLabel("XML name :");
        JTextField xmlName_Field = new JTextField();
        JButton okbutton = new JButton("OK");

        public NewXML() {

            int standard_x = Constant.activitySize_X / 2;

            int standard_y = Constant.activitySize_Y / 2;
            int standard_scale = standard_y / 250;

            this.setSize(standard_x + standard_x / 10, standard_y / 4);          //창 사이즈
            this.setUndecorated(true);      //title bar 제거
            this.setLocation(100, 100);
            this.setVisible(true);
            this.setLayout(null);   // 자유 레이아웃
            this.getRootPane().setBorder(new LineBorder(Color.black));  //테두리 설정

            okbutton.setMargin(new Insets(0, 0, 0, 0));

            xmlName_Field.setFont(new Font("Serif", Font.PLAIN, standard_scale * 18));
            xmlName_label.setFont(new Font("Serif", Font.PLAIN, standard_scale * 18));


            xmlName_label.setLocation(standard_scale * 5, standard_scale * 5);
            xmlName_Field.setLocation(standard_scale * 55, standard_scale * 5);
            okbutton.setLocation(standard_scale * 100, standard_scale * 35);

            xmlName_label.setSize(standard_scale * 50, standard_scale * 20);
            xmlName_Field.setSize(standard_scale * 100, standard_scale * 20);
            okbutton.setSize(standard_scale * 50, standard_scale * 20);

            this.add(xmlName_label);

            this.add(xmlName_Field);
            this.add(okbutton);

            // 포커스가 벗어나면 알아서 꺼진다.
            this.addWindowFocusListener(new WindowFocusListener() {
                @Override
                public void windowGainedFocus(WindowEvent e) {
                }

                @Override
                public void windowLostFocus(WindowEvent e) {
                    dispose();
                }
            });
            // 오케이 버튼을 눌렀을때의 위한 함수
            okbutton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Xml a = new Xml(xmlName_Field.getText(), (JSONArray) jobjRoot.get("xmls"));
                    dispose();
                    setRootJObject(jobjRoot);
                    drawActivity();
                }
            });
            // 엔터시 입력시의 이벤트를 위한 함수
            xmlName_Field.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {

                }

                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        Xml a = new Xml(xmlName_Field.getText(), (JSONArray) jobjRoot.get("xmls"));
                        dispose();
                        setRootJObject(jobjRoot);
                        drawActivity();
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            });
        }

        public NewXML(Point p) {
            int standard_x = Constant.activitySize_X / 2;

            int standard_y = Constant.activitySize_Y / 2;
            int standard_scale = standard_y / 250;

            this.setSize(standard_x + standard_x / 10, standard_y / 4);          //창 사이즈
            this.setUndecorated(true);      //title bar 제거
            this.setLocation(p.x, p.y);
            this.setVisible(true);
            this.setLayout(null);   // 자유 레이아웃
            this.getRootPane().setBorder(new LineBorder(Color.black));  //테두리 설정

            okbutton.setMargin(new Insets(0, 0, 0, 0));

            xmlName_Field.setFont(new Font("Serif", Font.PLAIN, standard_scale * 18));
            xmlName_label.setFont(new Font("Serif", Font.PLAIN, standard_scale * 18));


            xmlName_label.setLocation(standard_scale * 5, standard_scale * 5);
            xmlName_Field.setLocation(standard_scale * 55, standard_scale * 5);
            okbutton.setLocation(standard_scale * 100, standard_scale * 35);

            xmlName_label.setSize(standard_scale * 50, standard_scale * 20);
            xmlName_Field.setSize(standard_scale * 100, standard_scale * 20);
            okbutton.setSize(standard_scale * 50, standard_scale * 20);

            this.add(xmlName_label);

            this.add(xmlName_Field);
            this.add(okbutton);

            // 포커스가 벗어나면 알아서 꺼진다.
            this.addWindowFocusListener(new WindowFocusListener() {
                @Override
                public void windowGainedFocus(WindowEvent e) {
                }

                @Override
                public void windowLostFocus(WindowEvent e) {
                    dispose();
                }
            });
            // 오케이 버튼을 눌렀을때의 위한 함수
            okbutton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Xml a = new Xml(xmlName_Field.getText(), (JSONArray) jobjRoot.get("xmls"));
                    dispose();
                    setRootJObject(jobjRoot);
                    drawActivity();

                }
            });
            // 엔터시 입력시의 이벤트를 위한 함수
            xmlName_Field.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {

                }

                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        Xml a = new Xml(xmlName_Field.getText(), (JSONArray) jobjRoot.get("xmls"));
                        dispose();
                        setRootJObject(jobjRoot);
                        drawActivity();
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            });
        }

    }

    //------- 팝업 메뉴 ------------------
    class PopUpMenu extends JPopupMenu {
        JMenuItem repaint;
        JMenuItem xmlnew;
        JMenuItem removeXml;

        public PopUpMenu() {
            repaint = new JMenuItem("Repaint");
            xmlnew = new JMenuItem("Make Xml");
            removeXml = new JMenuItem("Remove Xml");
            repaint.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    drawActivity();
                    repaint_window();
                    sendJSonData();
                }
            });


            xmlnew.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    NewXML a = new NewXML();

                }
            });
            removeXml.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    Remove_Xml a = new Remove_Xml();
                }
            });
            add(repaint);
            add(xmlnew);
            add(removeXml);
        }

    }

    class CustomJpanel extends JPanel {


        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            drawNextLine(g);

            if (isActivity) {
                g.drawRect(scroll_p.x + x, scroll_p.y + y, Constant.activitySize_X / 2, Constant.activitySize_Y / 2);
            }
        }
    }

    public void drawNextLine(Graphics g) {
        Iterator<String> activityKeyList = activity_list.keySet().iterator();
        while (activityKeyList.hasNext()) {
            ArrayList tempList;
            String key = (String) activityKeyList.next();
            Object o = activity_list.get(key);
            Activity a = (Activity) o;

            tempList = a.getNextActivitylist();


            for (int i = 0; i < tempList.size(); i++) {
                if (activity_list.containsKey(tempList.get(i))) {
                    drawline(a, activity_list.get(tempList.get(i)), g);
                }
            }

        }

    }

    public void drawline(Activity start, Activity end, Graphics g) {
        //     System.out.println(start.getId() +" -> " + end.getId());

        Point startP = new Point((start.getActivity_position().x + start.getActivity_width() / 2), (start.getActivity_position().y + start.getActivity_height() / 2));
        Point endP = new Point((end.getActivity_position().x + end.getActivity_width() / 2), (end.getActivity_position().y + end.getActivity_height() / 2));
        Point centerP = new Point((startP.x + endP.x) / 2, (startP.y + endP.y) / 2);
        Point lightP = new Point((startP.x + endP.x * 2) / 3, (startP.y + endP.y * 2) / 3);

        g.drawLine(startP.x, startP.y, endP.x, endP.y);
        g.fillOval(lightP.x - 10, lightP.y - 10, 20, 20);

    }

    public void getRootObject() {
        System.out.println(jobjRoot);
    }

    public void setRootJObject(JSONObject obj) {
        String jsonString = obj.toJSONString();
        System.out.println("save Object :" + jobjRoot);
        String pathpath;
        pathpath = SharedPreference.PROJECT.get().getBasePath() + ConstantEtc.PROJECT_XML_PATH + "/assets/plugin.txt";

        try {
            File file = new File(pathpath);
            file.delete();

            BufferedWriter out = new BufferedWriter(new FileWriter(pathpath));
            out.write(jsonString);
            out.close();
        } catch (IOException e) {
            System.out.println(e);
        }


    }

    class Remove_Xml extends JFrame {

        JLabel next_label;

        JButton okbutton;
        JButton cancelButton;
        JComboBox<String> combo = new JComboBox<String>();
        int scale_size;

        public Remove_Xml() {

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

            combo.setSize(300, 40);
            combo.setLocation(50, 50);

            next_label.setSize(100, 20);
            next_label.setLocation(10, 10);

            okbutton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (combo.getSelectedItem().equals("NONE")) {

                    } else {
                        int num = 100000;
                        JSONObject tempObject;
                        for (int i = 0; i < xmlArrayData.size(); i++) {
                            String tempString;
                            tempObject = (JSONObject) xmlArrayData.get(i);

                            tempString = (String) tempObject.get("name");
                            if (tempString.equals(combo.getSelectedItem())) {
                                num = i;
                            }
                        }
                        if (num != 100000)
                            xmlArrayData.remove(num);

                    }

                    dispose();
                    setRootJObject(jobjRoot);
                    drawActivity();
                }
            });
            cancelButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    dispose();
                }
            });


            for (int i = 0; i < xmlArrayData.size(); i++) {
                JSONObject tempobj;
                tempobj = (JSONObject) xmlArrayData.get(i);
                combo.addItem((String) tempobj.get("name"));
            }

            combo.addItem("NONE");
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

    public void saveAndDraw() {
        setRootJObject(jobjRoot);

        String pathpath;
        pathpath = SharedPreference.PROJECT.get().getBasePath() + ConstantEtc.PROJECT_XML_PATH + "/assets/plugin.txt";

        JsonToXml jsonToXml = new JsonToXml();
        jsonToXml.make(pathpath);

        XmlToJson xmlToJson = new XmlToJson();
        xmlToJson.make();

        drawActivity();

    }

    public void AndDraw() {
        String pathpath;
        pathpath = SharedPreference.PROJECT.get().getBasePath() + ConstantEtc.PROJECT_XML_PATH + "/assets/plugin.txt";

        File a = new File(pathpath);
        a.delete();

        XmlToJson xmlToJson = new XmlToJson();
        xmlToJson.make();

//        try {
//            Thread.sleep(10000);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        drawActivity_first();

    }

    public void SaveActivity(JSONObject obj) {
        String pathpath;
        pathpath = SharedPreference.PROJECT.get().getBasePath() + ConstantEtc.PROJECT_XML_PATH + "/assets/plugin2.txt";

        File a = new File(pathpath);
        a.delete();

        String jsonString = obj.toJSONString();
        System.out.println("save Object :" + AobjRoot);
        try {
            File file = new File(pathpath);
            file.delete();

            BufferedWriter out = new BufferedWriter(new FileWriter(pathpath));
            out.write(jsonString);
            out.close();
        } catch (IOException e) {
            System.out.println(e);
        }


    }
}