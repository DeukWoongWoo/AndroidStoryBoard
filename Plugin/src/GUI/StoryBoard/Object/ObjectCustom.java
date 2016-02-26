package GUI.StoryBoard.Object;

import com.sun.java.swing.plaf.windows.WindowsBorders;
import org.json.simple.JSONObject;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.MouseInputAdapter;
import javax.swing.event.MouseInputListener;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
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

    private int cursor;
    private Point startPos = null;

    public JSONObject objectJObject;
    public HashMap<String, ObjectCustom> objectList;
    public HashMap<String, Activity> activityList;
    public ArrayList nextActivitylist;


    public ObjectCustom() {

        addDragListeners();
        addmouseClickEvent();
        addFocusEvent();
        setBorder(new ResizableBorder(8));
    }


    // 객체를 움직이기 위한 값들

    public void addDragListeners(){

        ObjectCustom handle = this;
        addMouseMotionListener(new MouseAdapter() {

            @Override
            public void mouseMoved(MouseEvent e) {
                anchorPoint = e.getPoint();
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
                                }resize();
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

                ResizableBorder border = (ResizableBorder) getBorder();
                cursor = border.getCursor(e);
                startPos = e.getPoint();
                requestFocus();
                repaint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                startPos = null;
                repaint();
                System.out.println("mouse Released");
            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {
                setCursor(Cursor.getDefaultCursor());
            }
        });
    }
    public void addFocusEvent(){
        addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {

            }

            @Override
            public void focusLost(FocusEvent e) {
                startPos = null;
                repaint();
                System.out.println("focus out");
                //setBorder(new EmptyBorder(1,1,1,1));
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
}
