package GUI.StoryBoard.Object;

import GUI.StoryBoard.Constant;
import GUI.StoryBoard.UI.palettePanel;
import org.json.simple.JSONObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 우철 on 2016-02-16.
 */
public class Layout_Linear_Root extends Layout_Linear {

    public Layout_Linear_Root(HashMap<String, ObjectCustom> list , JSONObject obj) {
        super(list, obj);

        //--------- 변수 값 지정---------------
        setPosition(new Point(isPosition().x, isPosition().y+getObject_height()/10));
        //----------창 구성--------------------
        this.setLocation(isPosition().x, isPosition().y);
        this.setBackground(Color.white);
        this.setOpaque(true);
    }
    public Layout_Linear_Root(HashMap<String, ObjectCustom> list , JSONObject obj, palettePanel pan) {
        super(list, obj,pan);

        //--------- 변수 값 지정---------------
        setPosition(new Point(isPosition().x, isPosition().y+getObject_height()/10));
        //----------창 구성--------------------
        this.setLocation(isPosition().x, isPosition().y);
        this.setBackground(Color.white);
        this.setOpaque(true);
    }
    public Layout_Linear_Root(HashMap<String, ObjectCustom> list , JSONObject obj, palettePanel pan, ArrayList nextlist) {
        super(list, obj,pan,nextlist);

        //--------- 변수 값 지정---------------
        setPosition(new Point(isPosition().x, isPosition().y+getObject_height()/10));
        //----------창 구성--------------------
        this.setLocation(isPosition().x, isPosition().y);
        this.setBackground(Color.white);
        this.setOpaque(true);
    }
    public Layout_Linear_Root(HashMap<String, ObjectCustom> list , JSONObject obj, palettePanel pan, ArrayList nextlist,HashMap<String, Activity> actList) {
        super(list, obj,pan,nextlist,actList);

        //--------- 변수 값 지정---------------
        setPosition(new Point(isPosition().x, isPosition().y+getObject_height()/10));
        //----------창 구성--------------------
        this.setLocation(isPosition().x, isPosition().y);
        this.setBackground(Color.white);
        this.setOpaque(true);
    }


    public Layout_Linear_Root(String name_, HashMap<String, ObjectCustom> list , JSONObject obj){
        super(name_,list,obj);
        setSize(Constant.activitySize_X, Constant.activitySize_Y);
        obj.put("width", (long)Constant.activitySize_X);
        obj.put("height", (long)Constant.activitySize_Y);
        obj.put("x",(long)0);
        obj.put("y",(long)0);
        obj.put("type","linear layout");
    }

    @Override
    public void addDragListeners() {

    }
    @Override
    public void addmouseClickEvent() {

    }
}
