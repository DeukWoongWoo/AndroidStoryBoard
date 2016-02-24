package GUI.StoryBoard.Object;

import GUI.StoryBoard.Constant;
import GUI.StoryBoard.UI.palettePanel;
import org.json.simple.JSONObject;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by 우철 on 2016-02-22.
 */
public class Layout_Relative_Root extends Layout_Relative {
    public Layout_Relative_Root(HashMap<String, ObjectCustom> list , JSONObject obj) {
        super(list, obj);

        //--------- 변수 값 지정---------------
        setPosition(new Point(isPosition().x, isPosition().y+getObject_height()/10));
        //----------창 구성--------------------
        this.setLocation(isPosition().x, isPosition().y);
        this.setBackground(Color.white);
        this.setOpaque(true);
    }
    public Layout_Relative_Root(HashMap<String, ObjectCustom> list , JSONObject obj, palettePanel pan) {
        super(list, obj,pan);

        System.out.println(this);
        setPosition(new Point(0,0));
        setObject_width(768);
        setObject_height(1280);


        setSize(getObject_width(),getObject_height());

        //--------- 변수 값 지정---------------
        setPosition(new Point(isPosition().x, isPosition().y+getObject_height()/10));
        //----------창 구성--------------------
        this.setLocation(isPosition().x, isPosition().y);
        this.setBackground(Color.white);
        this.setOpaque(true);
    }

    public Layout_Relative_Root(String name_, HashMap<String, ObjectCustom> list , JSONObject obj){
        super(name_,list,obj);
        setSize(Constant.activitySize_X, Constant.activitySize_Y);
        obj.put("width", (long)Constant.activitySize_X);
        obj.put("height", (long)Constant.activitySize_Y);
        obj.put("x",(long)0);
        obj.put("y",(long)0);
        obj.put("type","relative layout");
    }

    @Override
    public void addDragListeners() {

    }
}
