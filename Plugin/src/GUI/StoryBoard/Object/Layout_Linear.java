package GUI.StoryBoard.Object;

import org.json.simple.JSONObject;

import java.util.HashMap;

/**
 * Created by 우철 on 2016-02-16.
 */
public class Layout_Linear extends Layout_Root {

    public Layout_Linear(){

    }
    public Layout_Linear(HashMap<String, ObjectCustom> list , JSONObject obj) {
       super(list,obj);
    }
    public Layout_Linear(String name_, HashMap<String, ObjectCustom> list , JSONObject obj){
        super(name_,list,obj);
        obj.put("type","linear layout");
    }

}
