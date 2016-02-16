package GUI.StoryBoard;

import org.json.simple.JSONObject;

import java.awt.*;
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
    @Override
    public void addDragListeners() {

    }
}
