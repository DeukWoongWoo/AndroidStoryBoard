package GUI.StoryBoard;

import GUI.StoryBoard.Object.Activity;
import GUI.StoryBoard.Object.Layout_Relative_Root;
import GUI.StoryBoard.UI.palettePanel;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by 우철 on 2016-02-11.
 */
public class storyBoard extends JPanel {
    JButton createActivityB;
    JPanel jpan;
    JScrollPane scroll;
    listner li;
    JSONObject jobjRoot;

    JSONArray activityArrayData;

    palettePanel parlPanel = new palettePanel();
    private Point prePoint;
    private int zoom = 0;
    private String appName;
    HashMap <String, Activity> activity_list = new HashMap();

    // 생성자----------------------------------------------------------------
    public storyBoard() throws IOException {
        createActivityB = new JButton("push");


        li = new listner();
        jpan = new JPanel();
        jpan.setLayout(null);
        scroll = new JScrollPane(jpan , JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

        System.out.println(parlPanel);

        jpan.setPreferredSize(new Dimension(3000,3000));
        this.setLayout(new BorderLayout());
        this.add(createActivityB, "North" );
        this.add(scroll , "Center") ;

        createActivityB.addActionListener(li);

        this.setVisible(true);

        // 이동을 위한 마우스 이벤트
        scroll.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseDragged( MouseEvent e) {

                int preX = prePoint.x;
                int preY = prePoint.y;
                int temp_Vertical;
                int temp_Horizon;
                temp_Vertical=scroll.getVerticalScrollBar().getValue();
                temp_Horizon=scroll.getHorizontalScrollBar().getValue();

                scroll.getVerticalScrollBar().setValue(temp_Vertical+(preY - e.getY()));
                scroll.getHorizontalScrollBar().setValue(temp_Horizon+(preX - e.getX()));

                setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
                prePoint=e.getPoint();
            }

            @Override
            public void mouseMoved(MouseEvent e) {
                prePoint=e.getPoint();
                setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

            }


        });
        scroll.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getModifiers() == MouseEvent.BUTTON3_MASK)
                {
                    PopUpMenu menu = new PopUpMenu();
                    menu.show(e.getComponent(), e.getX(), e.getY());

                }

                else if(parlPanel.getChoice()==1)
                {
                    NewWindow a = new  NewWindow();

                    revalidate();       // 무효화 선언된 화면을 알려줌
                    repaint();          // 다시 그려준다.
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
        // 버튼 클릭을 위한 마우스 이벤트
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("dfexample Clicked");
            }
        });
        // 전부 그린다.
        add(parlPanel,"West");

        drawActivity();
        System.out.println("StroyBoard Add");
    }
    // 다시 그리기 위한 함수-------------------------------------------------
    public void repaint_window() {
        revalidate();       // 무효화 선언된 화면을 알려줌
        repaint();
    }
    //리스너-----------------------------------------------------------------
    class listner implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()== createActivityB) {
                NewWindow a = new  NewWindow();

                revalidate();       // 무효화 선언된 화면을 알려줌
                repaint();          // 다시 그려준다.
            }

        }
    }

    //-------------Json 받아서 그려주는 함수---------------------------------
    //JObjectRoute 파일 경로. text 파일을 읽어온다.
    JSONObject parserJObject(String JObjectRoute) {
        JSONParser par =new JSONParser();

        try {
            FileReader in = new FileReader(JObjectRoute);
            System.out.println(in);
            Object obj = par.parse(in);
            return (JSONObject)obj;
        }
        catch(Exception e){
            System.out.println("error : "  + e);
            return null;
        }

    }
    /// Activity key 값과 전부 제거
    public void removeAllActivity() {
        Iterator<String> activityKeyList = activity_list.keySet().iterator();

        System.out.println(activityKeyList.hasNext());

        while (activityKeyList.hasNext()) {
            String key = (String) activityKeyList.next();
            Object o = activity_list.get(key);
            Activity a = (Activity) o;
            a.removeActivity();
        }
        activity_list.clear();   // 현재까지 key 값들 모두 제거
    }
    // JSON 파일을 받은 것을 전부 그려준다.
    public void drawActivity() {
        removeAllActivity();
        jobjRoot = parserJObject(Constant.FILE_ROUTE);

        setAppName((String)jobjRoot.get("appName"));
        activityArrayData = (JSONArray)jobjRoot.get("activity");

        // 가지고 있는 액티비티를 만든다.
        for(int i=0; i<activityArrayData.size();i++){
            JSONObject activity_jobj;
            activity_jobj=(JSONObject)activityArrayData.get(i);
            Activity a = new Activity(activity_list, activity_jobj, parlPanel);
            activity_list.put((String)activity_jobj.get("name"),a);
            a.setOverbearing(true);
            jpan.add(a);
        }

        revalidate();       // 무효화 선언된 화면을 알려줌
        repaint();          // 다시 그려준다.
    }

    public void drawActivity_temp() {
        removeAllActivity();

        setAppName((String)jobjRoot.get("appName"));
        activityArrayData = (JSONArray)jobjRoot.get("activity");
        System.out.println(jobjRoot);
        // 가지고 있는 액티비티를 만든다.
        for(int i=0; i<activityArrayData.size();i++){
            JSONObject activity_jobj;
            activity_jobj=(JSONObject)activityArrayData.get(i);
            if(activity_jobj.isEmpty())
            {
                activityArrayData.remove(i);
                i=-1;
            }
        }
        for(int i=0; i<activityArrayData.size();i++){
            JSONObject activity_jobj;
            activity_jobj=(JSONObject)activityArrayData.get(i);

            Activity a = new Activity(activity_list, activity_jobj, parlPanel);
            activity_list.put((String)activity_jobj.get("name"),a);
            a.setOverbearing(true);
            jpan.add(a);
        }

        revalidate();       // 무효화 선언된 화면을 알려줌
        repaint();          // 다시 그려준다.
    }
    //--------------새로운 Activity를 만들기 위해 필요함---------------------
    // 새로운 activity를 만드는 함수
    public void makeNewActivity(String resultStr ,HashMap<String, Activity> list) {
        if(list.containsKey(resultStr)==true)
        {
            JOptionPane.showMessageDialog(null, resultStr+"는 이미 중복되어있는 ID 값입니다.");
        }
        else {
            System.out.println(resultStr +"Activity 가 생성되었습니다.");
            JSONObject obj = new JSONObject();

            Activity a = new Activity(resultStr, list, obj);

            a.setOverbearing(true);
            list.put(resultStr, a);
            jpan.add(a);

            activityArrayData.add(obj);
            System.out.println(activityArrayData);
            sendData();
            repaint_window();
        }
    }

    public void sendData(){
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
    class NewWindow extends JFrame{

        JLabel id_label = new JLabel("id :");
        JTextField id_field = new JTextField();
        JButton okbutton = new JButton("OK");

        public NewWindow() {

            int standard_x =Constant.activitySize_X;

            int standard_y =Constant.activitySize_Y;
            int standard_scale = standard_y/250;

            this.setSize(standard_x+standard_x/10, standard_y/4);          //창 사이즈
            this.setUndecorated(true);      //title bar 제거
            this.setLocation(100,100);
            this.setVisible(true);
            this.setLayout(null);   // 자유 레이아웃
            this.getRootPane().setBorder(new LineBorder(Color.black));  //테두리 설정
            System.out.println("make new window");


            okbutton.setMargin(new Insets(0, 0, 0, 0));

            id_field.setFont(new Font("Serif", Font.PLAIN, standard_scale*18 ));
            id_label.setFont(new Font("Serif", Font.PLAIN, standard_scale*18 ));


            id_label.setLocation(standard_scale*5, standard_scale*5);
            id_field.setLocation(standard_scale*55, standard_scale*5);
            okbutton.setLocation(standard_scale*100, standard_scale*35);

            id_label.setSize(standard_scale*50, standard_scale*20);
            id_field.setSize(standard_scale*100, standard_scale*20);
            okbutton.setSize(standard_scale*50, standard_scale*20);

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

        public NewWindow(Point p){
        }

    }

    //------- 팝업 메뉴 ------------------
    class PopUpMenu extends JPopupMenu{
        JMenuItem repaint;
        public PopUpMenu() {
            repaint = new JMenuItem("Repaint");

            repaint.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    drawActivity_temp();
                    repaint_window();
                }
            });

            add(repaint);
        }

    }
}
