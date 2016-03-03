package GUI.StoryBoard.Object;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 * Created by 우철 on 2016-03-03.
 */
public class Xml  {

    public Xml(String name, JSONArray xmlarray){
        JSONObject tempXmlObj = new JSONObject();
        JSONObject tempLayoutObj = new JSONObject();
        JSONArray clearobjArray = new JSONArray();
        JSONObject attibuteObj = new JSONObject();
        JSONArray clearArray = new JSONArray();

        tempXmlObj.put("name",name);


        tempLayoutObj.put("name","RelativeLayout");
        tempLayoutObj.put("x",(long)0);
        tempLayoutObj.put("y",(long)48);
        tempLayoutObj.put("width",(long)768);
        tempLayoutObj.put("height",(long)1136);
        tempLayoutObj.put("type","RelativeLayout");


        attibuteObj.put("layout_width","match_parent");
        attibuteObj.put("layout_height","match_parent");
        attibuteObj.put("paddingBottom","@dimen/activity_vertical_margin");
        attibuteObj.put("paddingRight","@dimen/activity_horizontal_margin");
        attibuteObj.put("paddingTop","@dimen/activity_vertical_margin");
        attibuteObj.put("paddingLeft","@dimen/activity_horizontal_margin");

        tempLayoutObj.put("attribute", attibuteObj);
        tempLayoutObj.put("object",clearArray);

        clearobjArray.add(tempLayoutObj);

        tempXmlObj.put("object", clearobjArray);
        xmlarray.add(tempXmlObj);


    }


}
