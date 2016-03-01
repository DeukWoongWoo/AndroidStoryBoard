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
    int layoutWidth = 200;
    int layoutHeight = 200;

    int imageVIewWidth =200;
    int imageViewHeight = 200;

<<<<<<< HEAD
     String FILE_ROUTE ="C:/Users/cho/Documents/AndroidStoryBoard/Plugin/우철이 라이브러리 포함된것.txt";
     String IMAGE_ROUTE ="C:\\Users\\cho\\Pictures\\Screenshots/aaa.jpg";
=======
     String FILE_ROUTE ="C:/Users/woong/Documents/AndroidStoryBoard/Plugin/우철이 라이브러리 포함된것.txt";
     String IMAGE_ROUTE ="C:\\Users\\우철\\Pictures\\Screenshots/aaa.jpg";
     String XML_ROUTE = "C:/Users/우철/Pictures/Screenshots";
>>>>>>> 0be9f2e92589c92e1a3f2dc333d12aa7f2baeca7
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
