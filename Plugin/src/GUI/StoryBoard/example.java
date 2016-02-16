package GUI.StoryBoard;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.AffineTransform;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

/**
 * Created by 우철 on 2016-02-11.
 */
public class example extends JFrame {
    JButton abc;
    JPanel jpan;
    JScrollPane scroll;
    listner li;
    AffineTransform newTransform = new AffineTransform();
    JSONObject jobjRoot;
    JSONArray activityArrayData;
    private Point prePoint;
    private int zoom = 0;

    HashMap <String, Activity> activity_list = new HashMap();

    public example() throws IOException {
        abc = new JButton("push");
        li = new listner();
        jpan = new JPanel();
        jpan.setLayout(null);
        scroll = new JScrollPane(jpan , JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        jpan.setPreferredSize(new Dimension(3000,3000));
        this.setSize(1000, 1000);
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
                System.out.println("example Clicked");
            }
        });

        drawActivity();

    }


    class listner implements ActionListener{
        @Override
        public void actionPerformed(ActionEvent e) {
            if(e.getSource()==abc) {
                String resultStr = null;

                resultStr = JOptionPane.showInputDialog("이름을 입력해 주세요.");
                if(resultStr==null )
                {

                }
                else
                {
                    if(activity_list.containsKey(resultStr))
                    {
                        JOptionPane.showMessageDialog(null, "이미 중복되어있는 ID 값입니다.");
                    }
                    else
                    {

                        Activity a = new Activity(resultStr, activity_list);
                        a.setOverbearing(true);
                        activity_list.put(resultStr, a);
                        jpan.add(a);
                        //a.setBounds(a.nowX, a.nowY, Constant.activitySize_X, Constant.activitySize_Y);

                    }



                }

                revalidate();       // 무효화 선언된 화면을 알려줌
                repaint();          // 다시 그려준다.
            }

        }
    }

    //JObjectRoute 파일 경로. text 파일을 읽어온다.
    JSONObject parserJObject(String JObjectRoute) throws IOException {
        JSONParser parser =new JSONParser();
        try {
            Object obj = parser.parse(new FileReader(JObjectRoute));
            return (JSONObject)obj;
        }
        catch(Exception e){
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

    public void drawActivity() throws IOException {
        removeAllActivity();
        jobjRoot = parserJObject(Constant.FILE_ROUTE);
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

}


class abc {

    public static void main(String[] args) throws IOException{
        example f = new example();

    }

}