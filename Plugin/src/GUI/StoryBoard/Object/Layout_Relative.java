package GUI.StoryBoard.Object;

import GUI.StoryBoard.UI.palettePanel;
import org.json.simple.JSONObject;

import java.util.ArrayList;
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
    public Layout_Relative(HashMap<String, ObjectCustom> list , JSONObject obj, palettePanel pan) {
        super(list,obj,pan);
    }
    public Layout_Relative(HashMap<String, ObjectCustom> list , JSONObject obj, palettePanel pan, ArrayList nextlist) {
        super(list,obj,pan, nextlist);
    }
    public Layout_Relative(HashMap<String, ObjectCustom> list , JSONObject obj, palettePanel pan, ArrayList nextlist, HashMap<String, Activity> actList) {
        super(list,obj,pan, nextlist,actList);
    }


}
