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
    private int margin [];
    private int stdMargin [];
    private int realMargin[];

    Component(){
        Attributes =new ArrayList<Attribution>();
        AttributeCount=0;
        margin = new int[]{-1, -1, -1, -1, -1, -1};
        stdMargin = new int[]{-1,-1,-1,-1,-1,-1};
        realMargin=new int[]{-1,-1,-1,-1,-1,-1};
    }

    public void setMargin(int index,int value){
        margin[index]=value;
    }
    public  void setStdMargin(int index,int value){
        stdMargin[index]=value;
    }
    public  void setRealMargin(int index,int value){
        realMargin[index]=value;
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



}

