package GUI.StoryBoard.Object;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by 우철 on 2016-02-16.
 */
public class Layout_Root extends ObjectCustom {

    public Layout_Root() {

    }
    public Layout_Root(HashMap<String, ObjectCustom> list , JSONObject obj) {
        long width, height, x, y ;
        String name;
        JSONArray objectArray;

        objectJObject=obj;
        objectList =list;

        name =(String) objectJObject.get("name");
        height=(long) objectJObject.get("height");
        width=(long) objectJObject.get("width");
        x=(long) objectJObject.get("x");
        y=(long) objectJObject.get("y");

        //--------- 변수 값 지정---------------
        setId(name);
        setPosition(new Point((int)x, (int)y));
        setObject_height((int)height);
        setObject_width((int)width);

        //----------창 구성--------------------
        this.setSize((int)width, (int)height);
        this.setLocation((int)x, (int)y);
        this.setLayout(null);
        this.setVisible(true);
        this.setOpaque(false);

        makeAllObject(objectJObject);
    }


    public ObjectCustom CreateObjectCustom(String type, JSONObject jobj){
        if(type.equals("linear layout")){
            Layout_Linear linear = new Layout_Linear(objectList, jobj);
            return linear;
        }
        else if(type.equals("button")){
            Button_Click b = new Button_Click(objectList, jobj);
            return b;
        }
        else if(type.equals("radio button")){
            Button_Radio b= new Button_Radio(objectList, jobj);
            return b;
        }
        else
        {
            ObjectCustom obj = new ObjectCustom();
            obj=null;
            return obj;
        }

    }

    //jobj 는 layout들이 들어있는 obj
    public void makeAllObject(JSONObject jobj){

        JSONArray objectArray;
        objectArray = (JSONArray)jobj.get("object");

        for(int i=0; i<objectArray.size(); i++){
            JSONObject tempJsonObject;
            tempJsonObject = (JSONObject)objectArray.get(i);
            if(tempJsonObject.isEmpty())
            {
                System.out.println(objectArray.get(i)+"이 지워졌습니다");
                objectArray.remove(i);
                i=-1;
            }
        }
        for(int i=0; i<objectArray.size(); i++){
            String type;
            ObjectCustom temp;
            JSONObject tempJsonObject;
            tempJsonObject = (JSONObject)objectArray.get(i);

            type =(String)tempJsonObject.get("type");
            temp=CreateObjectCustom(type,tempJsonObject);
            objectList.put((String)tempJsonObject.get("name"),temp);
            add(temp);

        }
    }




}
