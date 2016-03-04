package GUI.StoryBoard.Object;

import Analysis.Main.ProjectAnalysis;
import Analysis.RedoUndo.CodeBuilder.Type;
import GUI.StoryBoard.Constant;
import GUI.StoryBoard.storyBoard;
import org.json.simple.JSONObject;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 우철 on 2016-02-29.
 */
public class Image_View extends ObjectCustom {
    protected HashMap<String, ObjectCustom> checkkey;
    String pathStirng=null;
    String xmlPath=Constant.XML_ROUTE;
    private Image img;
    ImagePanel centerPanel =new ImagePanel();

    //----------- 생성자들 --------------
    public Image_View() {
        //----------창 구성--------------------
        this.setSize(Constant.imageVIewWidth,Constant.imageViewHeight);
        this.setLayout(new BorderLayout());
        this.setVisible(true);
        this.setBackground(Color.LIGHT_GRAY);
        centerPanel =new ImagePanel();
        add(centerPanel, "Center");
    }
    public Image_View(String name_ , HashMap<String,  ObjectCustom> list, JSONObject obj, Point p) {
        long width, height, x, y ;

        String name, color;
        typeObject= Type.ImageView;
        System.out.println(obj);
        name = "@+id/"+"imageView"+ name_;
        width = Constant.imageVIewWidth;
        height = Constant.imageViewHeight;
        x = p.x;
        y = p.y;
        color = "gray";

        setId(name);

        centerPanel =new ImagePanel();

        setPosition(new Point((int)x, (int)y));
        setObject_height((int)height);
        setObject_width((int)width);
        setColor(color);

        this.setSize((int)width, (int)height);
        this.setLocation((int)x, (int)y);
        this.setLayout(null);
        this.setVisible(true);
        this.setBackground(Color.WHITE);

        obj.put("name",getId());
        obj.put("x",x);
        obj.put("y",y);
        obj.put("width",width*2);
        obj.put("height",height*2);
        obj.put("color",color);
        obj.put("type","ImageVIew");

        setId(name);
        objectJObject=obj;

        checkkey=list;

        this.setLayout(new BorderLayout());
        this.setVisible(true);
        this.setBackground(Color.LIGHT_GRAY);

        add(centerPanel, "Center");

        addMouseListener();

        repaint();
    }
    public Image_View(HashMap<String, ObjectCustom> list, JSONObject obj, ArrayList nextlist, HashMap<String, Activity> actList, storyBoard stroy, String ActivitName) {
        super(list,obj,nextlist,actList,stroy,ActivitName);
        long width, height, x, y;
        String name;
        typeObject= Type.ImageView;

        nextActivitylist = nextlist;
        objectJObject = obj;
        objectList = list;
        checkkey = list;
        activityList = actList;
        this.XmlName = ActivitName;

        getStroyBoard(stroy);


        if (objectJObject.containsKey("src")) {
//            ProjectAnalysis projectAnalysis = ProjectAnalysis.getInstance(null);
//            xmlPath = projectAnalysis. findDrawablePath();
           pathStirng=xmlPath+"/"+objectJObject.get("src")+".png";
            centerPanel =new ImagePanel(pathStirng);


        }
        else
        {
            centerPanel =new ImagePanel();
        }

        if (objectJObject.containsKey("next")) {
            nextActivitylist.add(objectJObject.get("next"));
        }




        //----------창 구성--------------------
        this.setSize((int) getObject_width(), (int) getObject_height());
        this.setLocation((int) isPosition().x, (int) isPosition().y);
        this.setLayout(new BorderLayout());
        this.setVisible(true);
        this.setBackground(Color.LIGHT_GRAY);

        add(centerPanel, "Center");


        //---------메소드----------------------
        addMouseListener();

    }

    //-----------메소드들---------------
    private void addMouseListener() {

        addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2 && !e.isConsumed()) {
                    Change_Window c = new Change_Window(getId(), e.getLocationOnScreen(), e.getPoint());
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

    //--------변경을 위한 창------------
    class Change_Window extends JFrame {

        String srcname ="newone";

        JLabel id_label;
        JLabel path;
        JLabel srcName;
        JTextField srcName_field;
        JTextField path_field;
        JTextField id_field;
        JButton openbutton;
        JButton okbutton;
        int scale_size;

        String id;
        Point screenP;
        Point mouseP;

        public Change_Window(String id_, Point screenPosition, Point mouse) {
            path = new JLabel("open Image :");
            srcName = new JLabel("src name :");
            srcName_field = new JTextField();
            path_field = new JTextField();
            openbutton = new JButton("open");
            id_label = new JLabel("id :");
            id_field = new JTextField(getId());
            okbutton = new JButton("OK");
            scale_size = getObject_width() / 20;
            id=id_;
            screenP=screenPosition;
            mouseP=mouse;

            if(pathStirng!=null)
                path_field.setText(pathStirng);

            if (objectJObject.containsKey("src")) {
                srcname = (String)objectJObject.get("src");

            }
            this.setSize(370, 200);          //창 사이즈

            this.setUndecorated(true);      //title bar 제거
            this.setLocation(screenPosition.x - mouse.x, screenPosition.y - mouse.y);   // 현재 버튼의 위에 덮기 위한 것
            this.setVisible(true);
            this.setLayout(null);   // 자유 레이아웃
            this.getRootPane().setBorder(new LineBorder(Color.black));  // JFrame 테두리 설정


//           ProjectAnalysis projectAnalysis = ProjectAnalysis.getInstance(null);
//           xmlPath = projectAnalysis. findDrawablePath();

            openbutton.setMargin(new Insets(0,0,0,0));
            okbutton.setMargin(new Insets(0, 0, 0, 0));

            srcName_field.setText(srcname);

            path.setLocation(10,10);

            openbutton.setLocation(320,10);


            path_field.setLocation(100,10);
            id_label.setLocation(10, 110);

            id_field.setLocation(100, 110);
            okbutton.setLocation(220, 150);

            srcName.setLocation(10, 60);
            srcName_field.setLocation(100, 60);

            srcName.setSize(150, 40);
            srcName_field.setSize(200,40);

            id_label.setSize(100, 40);


            path.setSize(200,40);
            path_field.setSize(200,40);
            openbutton.setSize(40,40);

            id_field.setSize(200, 40);
            okbutton.setSize(100, 40);

            add(path);
            add(path_field);
            add(openbutton);
            add(srcName);
            add(srcName_field);
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

            openbutton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    JFileChooser fileChooser = new JFileChooser();
                    if (fileChooser.showOpenDialog(getParent()) == JFileChooser.APPROVE_OPTION) {
                        path_field.setText(fileChooser.getSelectedFile().getAbsolutePath());
                        pathStirng=fileChooser.getSelectedFile().getAbsolutePath();
                    }
                    Change_Window c = new Change_Window(id,screenP,mouseP);
                }
            });

            okbutton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    result();
                    fixObject(1);
                }
            });

            id_field.addKeyListener(new KeyListener() {
                @Override
                public void keyTyped(KeyEvent e) {

                }

                @Override
                public void keyPressed(KeyEvent e) {
                    if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                        result();
                        fixObject(1);
                    }
                }

                @Override
                public void keyReleased(KeyEvent e) {

                }
            });


            System.out.println(xmlPath);
        }

        // 확인 키
        public void result() {
            if (checkObjectId(id_field.getText()) == true) {
                JOptionPane.showMessageDialog(null, id_field.getText() + "는 이미 중복되어있는 ID 값입니다.");
            } else {
                Image temp_img;
                String temp_last[];
                String last;
                setId(id_field.getText());
                pathStirng=path_field.getText();
                repaint();
                objectJObject.put("name", getId());
                objectJObject.put("src", srcName_field.getText());
                temp_img=new ImageIcon(pathStirng).getImage();
                String temp;
                temp_last=pathStirng.split("\\.");
                last = temp_last[temp_last.length-1];
                System.out.println(last);
                temp = xmlPath+"/"+srcName_field.getText()+"."+last;


                try {
                    temp_img=ImageIO.read(new File(pathStirng));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                System.out.println(xmlPath);
                try {
                    ImageIO.write((RenderedImage) temp_img, last, new File(temp));
                }
                catch(Exception e){
                    System.out.println(e);
                }
            }

            dispose();
        }
    }

    //--------이미지 페널을 위한 것-----
    class ImagePanel extends JPanel
    {

        private Image img;

        public ImagePanel(){

        }
        public ImagePanel(String img) {
            this(new ImageIcon(img).getImage());
        }

        public ImagePanel(Image img) {
            this.img = img;
        }

        public void paintComponent(Graphics g) {
            g.drawImage(img, 0, 0, getParent().getWidth(),getParent().getHeight(),this);
        }

        public Image getImageView(){
            return this.img;
        }
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
