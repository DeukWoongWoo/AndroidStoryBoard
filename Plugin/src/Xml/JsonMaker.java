package Xml;


import com.google.gson.JsonObject;
import com.sun.jna.platform.win32.COM.COMUtils;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.HashMap;
import java.util.Set;

/**
 * Created by cho on 2016-02-22.
 */
public class JsonMaker {


    public JSONObject jsonObject;

    JsonMaker(){

    }


    public JSONObject WebmMake(HashMap<String, Integer> map, ComponentManager componentManager){
        JSONObject jsonObject = new JSONObject();
        //ToDO: appname 받기
        jsonObject.put("appName","test");
        Set <String> layoutList = map.keySet();


        /*
        try {
            int  type = xpp.getEventType();
            int layoutCount=0;
            int componentCount=0;
            String parentId;
            String currentId;
            while(type != XmlPullParser.END_DOCUMENT){

                if(type == XmlPullParser.START_TAG) {
                    Component component = componentManager.getComponent(componentCount);

                    if(xpp.getName().equals("RelativeLayout")){//layout
                        parentId="RelativeLayout"+isTop();
                        Push(++layoutCount);
                        currentId ="RelativeLayout"+isTop();
                        jsonArrayObject = new JSONArray();
                        jsonActivity = new JSONObject();
                        jsonActivity.put("name",currentId);
                        jsonActivity.put("x",component.leftPoint);
                        jsonActivity.put("y",component.topPoint);
                        jsonActivity.put("width",component.getWidth());
                        jsonActivity.put("height",component.getHeight());

                    }else{//component
                        //parentId;
                        jsonObject = new JSONObject();
                        jsonObject.put("name",component.getId());
                        jsonObject.put("x",component.leftPoint);
                        jsonObject.put("y",component.topPoint);
                        jsonObject.put("height",component.getHeight());
                        jsonObject.put("width",component.getWidth());
                        jsonObject.put("text",component.text);
                        jsonObject.put("textSize",component.textSize);
                        jsonObject.put("type",component.tagName);
                        jsonObject.put("next","null");
                        jsonObject.put("image","null");
                        jsonArrayObject.add(jsonObject);
                    }
                    componentCount++;
                }
                else  if(type == XmlPullParser.END_TAG) {
                    if(xpp.getName().equals("RelativeLayout")){
                        int pop=Pop();
                        parentId ="RelativeLayout"+isTop();
                        jsonObject.put("RelativeLayout"+pop,jsonArrayObject);
                    }

                }
                type = xpp.next();
            }
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

*/


        return jsonObject;
    }
    private int top=0;
    private int[] stack =new int[255];
    private void Push(int data){
        stack[top++]=data;
    }
    private int Pop(){
        top--;
        return stack[top];
    }
    private int isTop(){
        return stack[top-1];
    }
}
