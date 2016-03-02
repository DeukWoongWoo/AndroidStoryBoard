package GUI.StoryBoard.Object.Replace;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by woong on 2016-03-03.
 */
public class XmlFactory {
    private HashMap<String, XmlLayout> layouts = new HashMap<>();

    public void setLayout(String name, XmlLayout xmlLayout){
        layouts.put(name, xmlLayout);
    }

    public XmlLayout getLayout(String name){
        return layouts.get(name);
    }
}
