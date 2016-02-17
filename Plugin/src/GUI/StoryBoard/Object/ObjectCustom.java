package GUI.StoryBoard.Object;

import com.sun.java.swing.plaf.windows.WindowsBorders;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.*;
import java.util.HashMap;

/**
 * Created by 우철 on 2016-02-13.
 */
public class ObjectCustom extends JPanel {

    private String id , color;
    private Point object_position;
    private int object_width, object_height;
    private Point anchorPoint;
    private boolean overbearing = true;

    public JSONObject objectJObject;
    public HashMap<String, ObjectCustom> objectList;


    public ObjectCustom() {
        addDragListeners();
        addmouseClickEvent();
        addFocusEvent();
    }


    // 객체를 움직이기 위한 값들

    public void addDragListeners(){

        ObjectCustom handle = this;
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
                Dimension parent;
                Point parentOnScreen = getParent().getLocationOnScreen();
                Point mouseOnScreen = e.getLocationOnScreen();
                Point position_ = new Point(mouseOnScreen.x - parentOnScreen.x - anchorX, mouseOnScreen.y - parentOnScreen.y - anchorY);

                parent=getParent().getSize();

                if(parent.getWidth() < position_.x + object_width )
                    position_.x=(int)parent.getWidth()-object_width;
                if(position_.x <0)
                    position_.x=0;
                if(parent.getHeight() < position_.y + object_height )
                    position_.y=(int)parent.getHeight()-object_height;
                if(position_.y <0)
                    position_.y=0;

                setPosition(position_);
                    setLocation(object_position);
                if (overbearing) {
                    getParent().setComponentZOrder(handle, 0);
                    repaint();
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

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBorder(new WindowsBorders.DashedBorder(Color.black));
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBorder(new EmptyBorder(1,1,1,1));
            }
        });
    }
    public void addFocusEvent(){

        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                setBorder(new LineBorder(Color.black));
            }

            @Override
            public void focusLost(FocusEvent e) {
                setBorder(new EmptyBorder(1,1,1,1));
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


}
