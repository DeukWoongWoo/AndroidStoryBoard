package GUI.StoryBoard;

import java.awt.*;

/**
 * Created by 우철 on 2016-02-11.
 */
public interface Constant  {
    int activitySize_X = 300;
    int activitySize_Y = 500;
     String FILE_ROUTE ="C:/Users/우철/Documents/AndroidStoryBoard/Plugin/jsonfile.txt";
    Dimension MENUBUTTONSIZE = new Dimension(50,50);
    int SCALE_SHAPE = 1;


    class JsonFileStruct{
        String activity;
        String parent_object;
        String pri_activity;
        String pri_parent_object;
        String name;
        int x;
        int y;
        int width;
        int height;
        String type;

        public JsonFileStruct(){
            activity = "포함하고 있는 activity";
            parent_object = "포함하고 있는 object";
            pri_activity = "포함했던 activity";
            pri_parent_object= "포함 했던 object";
            name = "id 혹은 이름";
            x = 0;
            y= 0;
            width =0;
            height =0;
            type ="type";
        }
    }

}
