package GUI.StoryBoard;

import java.awt.*;

/**
 * Created by 우철 on 2016-02-11.
 */
public interface Constant  {
    int activitySize_X = 300;
    int activitySize_Y = 500;
    int buttonWidth = 100;
    int buttonHeight = 30;

     String FILE_ROUTE ="C:/Users/우철/Documents/AndroidStoryBoard/Plugin/jsonfile.txt";
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

}
