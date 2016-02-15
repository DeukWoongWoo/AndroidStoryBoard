package Xml;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cho on 2016-01-24.
 */



public class Component {

    private ArrayList<Attribution> Attributes ;
    private int AttributeCount;
    private String id;//자주 쓰임
    private String tagName;
    private int width;
    private int height;
    private int padding[];

    Component(){
        Attributes =new ArrayList<Attribution>();
        AttributeCount=0;
        padding =new int[]{0,0,0,0,0,0};
    }


    public void setAttributes(XmlPullParser xpp){
        AttributeCount=xpp.getAttributeCount();
        tagName = xpp.getName();
        for(int i=0;i<AttributeCount;i++) {
            Attribution tempAttr=new Attribution();
            tempAttr.setAttribute(xpp.getAttributeName(i));
            tempAttr.setValue(xpp.getAttributeValue(i));
            if(xpp.getAttributeName(i).equals("id"))
                this.id = xpp.getAttributeValue(i);
            if(xpp.getAttributeName(i).equals("text")){
                int width=-1;
                int height=-1;
                for(int j=0;j<AttributeCount;j++){
                    if(xpp.getAttributeName(j).equals("textSize")){
                        width = getComponentSize(xpp.getAttributeValue(i),changeDpToInt(xpp.getAttributeValue(j)));
                        height=(int)(changeDpToInt(xpp.getAttributeValue(j))*2.7);
                    }
                }
                if(height<64)
                    height=64;
                if(width<0)
                    width=getComponentSize(xpp.getAttributeValue(i),14);
                if(xpp.getName().equals("CheckBox"))
                    width+=64;
                else if(xpp.getName().equals("RadioButton"))
                    width+=64;
                else if(xpp.getName().equals("Button"))
                    width+=48;
                this.width = width;
                this.height=height;

            }
            Attributes.add(tempAttr);
        }
    }
    public String getAttributes(int index){
        return Attributes.get(index).getAttribute();
    }
    public String getAttributesValue(int index){
        return Attributes.get(index).getValue();
    }
    public String getId(){
        return id;
    }
    public int getAttributeCount(){
        return AttributeCount;
    }
    public String getTagName(){
        return tagName;
    }
    public int getWidth(){return width;}
    public int getHeight(){return height;}
    private int getComponentSize(String text,int dp){
        int sumDp=0;
        char arr[] = text.toCharArray();
        for(int i=0;i<arr.length;i++){
            if(arr[i]>='a' && arr[i]<='z')
                sumDp+=dp*2.7/2.5;

            else if(arr[i]>='A' && arr[i]<='Z')
                sumDp+=dp*2.7/2.1;

            else if(arr[i]>='0' && arr[i]<='9')
                sumDp+=dp*2.7/2.4;

            else if(arr[i] == ' ')
                sumDp+=dp*2.7/5.5;

            else
                sumDp += dp * 2.7 / 1.5;
        }
        return sumDp;
    }
    private int changeDpToInt(String value){
        int dp=0;
        char val[] = value.toCharArray();
        for(int i=0;val[i]!='d';i++)
            dp =dp*10+ val[i]-'0';
        return dp;
    }
    public  void setPadding(int padding[]){
        for(int i=0;i<this.padding.length;i++)
            this.padding[i]=padding[i];
    }
    public int[] getPadding(){
        return this.padding;
    }


}

