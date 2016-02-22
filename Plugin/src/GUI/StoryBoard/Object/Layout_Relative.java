package GUI.StoryBoard.Object;

import org.json.simple.JSONObject;

import java.util.HashMap;

/**
 * Created by 우철 on 2016-02-22.
 */
public class Layout_Relative extends Layout_Root {
    public Layout_Relative(){

    }
    public Layout_Relative(HashMap<String, ObjectCustom> list , JSONObject obj) {
        super(list,obj);
    }

    public Layout_Relative(String name_, HashMap<String, ObjectCustom> list , JSONObject obj){
        super(name_,list, obj);
    }

}
