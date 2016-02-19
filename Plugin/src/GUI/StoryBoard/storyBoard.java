package GUI.StoryBoard;

import GUI.StoryBoard.Object.Activity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by 우철 on 2016-02-11.
 */
public class storyBoard extends JPanel {
    JButton abc;
    JPanel jpan;
    JScrollPane scroll;
    listner li;
    AffineTransform newTransform = new AffineTransform();
    JSONObject jobjRoot;
    JSONArray activityArrayData;
    private Point prePoint;
    private int zoom = 0;
    private String appName;
    HashMap <String, Activity> activity_list = new HashMap();

    // 생성자
    public storyBoard() throws IOException {
        abc = new JButton("push");
        li = new listner();
        jpan = new JPanel();
        jpan.setLayout(null);
        scroll = new JScrollPane(jpan , JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jpan.setPreferredSize(new Dimension(3000,3000));
       // this.setSize(1000, 1000);
        this.setLayout(new BorderLayout());
        this.add(abc, "North" );
//        this.add(jpan , "Center") ;
        this.add(scroll , "Center") ;

        abc.addActionListener(li);

        this.setVisible(true);

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


        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                System.out.println("dfexample Clicked");
            }
        });

        drawActivity();
        System.out.println("StroyBoard Add");
    }
    public void repaint_window()
    {
        revalidate();       // 무효화 선언된 화면을 알려줌
        repaint();
    }

    //리스너
    class listner implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==abc) {
                NewWindow a = new  NewWindow();

                revalidate();       // 무효화 선언된 화면을 알려줌
                repaint();          // 다시 그려준다.
            }

        }
    }

    //JObjectRoute 파일 경로. text 파일을 읽어온다.
    JSONObject parserJObject(String JObjectRoute) {
        JSONParser par =new JSONParser();

        try {
            System.out.println("JsonObjectRoute : " + JObjectRoute);
            FileReader in = new FileReader(JObjectRoute);
            System.out.println(in);
            Object obj = par.parse(in);
            System.out.println(obj);
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
        System.out.println(jobjRoot);

        setAppName((String)jobjRoot.get("appName"));
        activityArrayData = (JSONArray)jobjRoot.get("activity");

        // 가지고 있는 액티비티를 만든다.
        for(int i=0; i<activityArrayData.size();i++){
            JSONObject activity_jobj;
            activity_jobj=(JSONObject)activityArrayData.get(i);
            Activity a = new Activity(activity_list, activity_jobj);
            activity_list.put((String)activity_jobj.get("name"),a);
            a.setOverbearing(true);
            jpan.add(a);
        }

        revalidate();       // 무효화 선언된 화면을 알려줌
        repaint();          // 다시 그려준다.
    }


    class NewWindow extends JFrame{

        JLabel name_label = new JLabel("name :");
        JLabel id_label = new JLabel("id :");
        JTextField id_field = new JTextField();
        JTextField name_field =new JTextField();
        JButton okbutton = new JButton("OK");

        public NewWindow() {

            int standard_x =Constant.activitySize_X;
            int standard_y =Constant.activitySize_Y;
            int standard_scale = standard_y/250;

            this.setSize(standard_x+standard_x/10, standard_y/3);          //창 사이즈
            this.setUndecorated(true);      //title bar 제거
            this.setLocation(100,100);
            this.setVisible(true);
            this.setLayout(null);   // 자유 레이아웃
            this.getRootPane().setBorder(new LineBorder(Color.black));  //테두리 설정
            System.out.println("make new window");


            okbutton.setMargin(new Insets(0, 0, 0, 0));

            id_field.setFont(new Font("Serif", Font.PLAIN, standard_scale*18 ));
            name_field.setFont(new Font("Serif", Font.PLAIN, standard_scale*18 ));
            id_label.setFont(new Font("Serif", Font.PLAIN, standard_scale*18 ));
            name_label.setFont(new Font("Serif", Font.PLAIN, standard_scale*18 ));


            name_label.setLocation(standard_scale*5, standard_scale*5);
            id_label.setLocation(standard_scale*5, standard_scale*30);
            name_field.setLocation(standard_scale*55, standard_scale*5);
            id_field.setLocation(standard_scale*55, standard_scale*30);
            okbutton.setLocation(standard_scale*100, standard_scale*55);

            name_label.setSize(standard_scale*50, standard_scale*20);
            id_label.setSize(standard_scale*50, standard_scale*20);
            name_field.setSize(standard_scale*100, standard_scale*20);
            id_field.setSize(standard_scale*100, standard_scale*20);
            okbutton.setSize(standard_scale*50, standard_scale*20);

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
                    result(id_field.getText(), activity_list);
                    dispose();
                }
            });

            id_field.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {

                }

                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        result(id_field.getText(), activity_list);
                        dispose();
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
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        result(id_field.getText(), activity_list);
                        dispose();
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            });

        }


        public void result(String resultStr ,HashMap<String, Activity> list)
        {
            if(list.containsKey(resultStr)==true)
            {
                JOptionPane.showMessageDialog(null, resultStr+"는 이미 중복되어있는 ID 값입니다.");
            }
            else {
                System.out.println(resultStr +"Activity 가 생성되었습니다.");
                Constant.JsonFileStruct file = new Constant.JsonFileStruct();
                JSONObject newjobj = new JSONObject();


                Activity a = new Activity(resultStr, list);
                a.setOverbearing(true);
                list.put(resultStr, a);
                jpan.add(a);

                file.height=a.getActivity_height();
                file.width =a.getActivity_width();
                file.x = a.getActivity_position().x;
                file.y = a.getActivity_position().y;
                file.name=a.getName();
                file.type=a.getType();


                newjobj =makeSendData(newjobj, file, "new");
                System.out.println(newjobj);
                repaint_window();
            }
        }
    }

    // json 형식을 만들어준다.

    public JSONObject makeSendData(JSONObject jobj, Constant.JsonFileStruct file, String newandfix)
    {
        JSONObject temp = jobj;
        JSONObject newone = new JSONObject();
        JSONObject fixone = new JSONObject();
        temp.put("status", newandfix);
            newone.put("activity", file.activity);
            newone.put("parent object", file.parent_object);
            newone.put("name", file.name);
            newone.put("x", file.x);
            newone.put("y", file.y);
            newone.put("width", file.width);
            newone.put("height", file.height);
            newone.put("type",file.type);
        temp.put("new", newone);
            fixone.put("activity", file.activity);
            fixone.put("parent object", file.parent_object);
            fixone.put("pri activity", file.pri_activity);
            fixone.put("pri parent object", file.pri_parent_object);
            fixone.put("name", file.name);
            fixone.put("x", file.x);
            fixone.put("y", file.y);
            fixone.put("width", file.width);
            fixone.put("height", file.height);
            fixone.put("type",file.type);
        temp.put("fix", fixone);

        return temp;
    }

    //-----------private 접근 함수--------------------------
    public String getAppName() {
        return appName;
    }
    public void setAppName(String appName) {
        this.appName = appName;
    }


}
