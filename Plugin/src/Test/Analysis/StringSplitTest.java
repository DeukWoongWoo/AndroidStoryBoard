package Test.Analysis;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Ignore;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by woong on 2016-02-16.
 */
public class StringSplitTest {
    private String str1 = "Button button = (Button)findViewById(R.id.button);";
    private String str2 = "button = (Button)findViewById(R.id.button);";

    @Test
    public void findViewByIdSplit(){
//        test(str1);
//        test(str2);
    }

    private void test(String str) {
//        for(String s : str.split("=")) {
//            System.out.println(s);
//        }

        String[] strSplit = str.split("=");

        String[] idStr = strSplit[0].split("\\s");

        if(idStr.length > 1){
            System.out.println("Component Id : " + idStr[idStr.length-1]);
        }else{
            System.out.println("Component Id : " + idStr[0]);
        }

        String findStr = strSplit[1];
        System.out.println("Type : " + findStr.substring(findStr.indexOf("(")+1,findStr.indexOf(")")));
        System.out.println("id : " + findStr.substring(findStr.lastIndexOf("(")+1,findStr.lastIndexOf(")")));

    }

    @Test
    public void intentSplit(){
        String str = "Intent intent = new Intent(this, NextActivity.class);";

        String[] strSplit = str.split("=");

        String[] idStr = strSplit[0].split("\\s");

        if(idStr.length > 1){
            System.out.println("intent Id : " + idStr[idStr.length-1]);
        }else{
            System.out.println("intent Id : " + idStr[0]);
        }

        String intentStr = strSplit[1];
        String content = intentStr.substring(intentStr.indexOf("(")+1,intentStr.indexOf(")"));
        System.out.println("content : " + content);
        System.out.println("nextActivity : " + content.split(",")[1].split("\\.")[0]);
    }

}
