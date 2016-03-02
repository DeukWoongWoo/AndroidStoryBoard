package GUI.StoryBoard;

import GUI.StoryBoard.Object.ObjectCustom;
import org.json.simple.JSONObject;

import java.awt.*;
import java.util.HashMap;

/**
 * Created by 우철 on 2016-02-11.
 */
public interface Constant  {
    int activitySize_X = 300;
    int activitySize_Y = 500;
    int buttonWidth = 100;
    int buttonHeight = 30;
    int layoutWidth = 200;
    int layoutHeight = 200;

    int imageVIewWidth =200;
    int imageViewHeight = 200;

     String FILE_ROUTE ="C:/Users/우철/Documents/AndroidStoryBoard/Plugin/hi.txt";
     String IMAGE_ROUTE ="C:\\Users\\우철\\Pictures\\Screenshots/aaa.jpg";
     String XML_ROUTE = "C:/Users/우철/Pictures/Screenshots";
 //    String FILE_OUT = "C:/Users/우철/Pictures/Screenshots/jsonfile.txt";

    int BUTTON = 4;
    int RADIOBUTTON = 5;
    int LINEARLAYOUT = 2;
    int IMAGEVIEW = 6;
    int TEXTVIEW = 7;
    int CHECKBOX = 8;

    Dimension MENUBUTTONSIZE = new Dimension(50,50);
    int SCALE_SHAPE = 1;
    boolean NEWFILE = true;
    boolean FIXFILE = false;

       class JsonFileStruct{
        String name;
        String color;
        int x;
        int y;
        int width;
        int height;
        String object;
        String type;

        public JsonFileStruct(){
        name = "id 혹은 이름";
        x = 0;
        y= 0;
        width =0;
        height =0;
        type ="type";
        color = "color";
    }
    }
    class ObjectNew{
        public String name;
        public HashMap<String, ObjectCustom> objectList;
        public JSONObject jObject;
        public Point mousep;
        public int parentHeight;
        public int parentWidth;
    }
}
