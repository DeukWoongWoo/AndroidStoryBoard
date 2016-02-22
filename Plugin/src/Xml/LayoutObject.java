package Xml;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;

/**
 * Created by cho on 2016-02-22.
 */
public class LayoutObject {


    private ArrayList<Attribution> Attributes ;
    private int AttributeCount;
    private String id=null;//자주 쓰임
    private String tagName;
    private int width;
    private int height;
    private int padding[];
    public int textWidth;
    public int textHeight;
    public int leftPoint;
    public int rightPoint;

    public String leftId="null";
    public String rightId ="null";
    public String left="null";
    public String right="null";

    public String topId="null";
    public String bottomId="null";
    public String top="null";
    public String bottom="null";

    public boolean isMarginLeft=false;
    public boolean isMarginRight=false;
    public int marginLeft;
    public int marginRight;
    public int marginTop;
    public int marginBottom;

    LayoutObject(){
        Attributes =new ArrayList<Attribution>();
        AttributeCount=0;
        padding =new int[]{0,0,0,0,0,0};
    }


    public void setAttributes(XmlPullParser xpp){
        AttributeCount=xpp.getAttributeCount();
        tagName = xpp.getName();
        this.id=tagName;
        for(int i=0;i<AttributeCount;i++) {
            Attribution tempAttr=new Attribution();
            tempAttr.setAttribute(xpp.getAttributeName(i));
            tempAttr.setValue(xpp.getAttributeValue(i));

            if(xpp.getAttributeName(i).equals("layout_alignRight")){
                right=xpp.getAttributeName(i);
                rightId = xpp.getAttributeValue(i);
            }else if(xpp.getAttributeName(i).equals("layout_alignLeft")){
                left= xpp.getAttributeName(i);
                leftId=xpp.getAttributeValue(i);
            }else if(xpp.getAttributeName(i).equals("layout_toRightOf")){
                left = xpp.getAttributeName(i);
                leftId = xpp.getAttributeValue(i);
            }else if(xpp.getAttributeName(i).equals("layout_toLeftOf")){
                right=xpp.getAttributeName(i);
                rightId=xpp.getAttributeValue(i);
            }else if(xpp.getAttributeName(i).equals("layout_alignParentRight")){
                right=xpp.getAttributeName(i);
                rightId ="Parent";
            }else if(xpp.getAttributeName(i).equals("layout_alignParentLeft")){
                left = xpp.getAttributeName(i);
                leftId = "Parent";
            }
            if(xpp.getAttributeName(i).equals("layout_marginRight")){
                isMarginRight=true;
                marginRight= changeDpToInt(xpp.getAttributeValue(i))*2;
            } else if(xpp.getAttributeName(i).equals("layout_marginLeft")){
                isMarginLeft=true;
                marginLeft= changeDpToInt(xpp.getAttributeValue(i))*2;
            } else if(xpp.getAttributeName(i).equals("layout_marginTop")) {
                marginTop = changeDpToInt(xpp.getAttributeValue(i))*2;
            }else if(xpp.getAttributeName(i).equals("layout_marginBottom")) {
                marginBottom = changeDpToInt(xpp.getAttributeValue(i))*2;
            }

            if(xpp.getAttributeName(i).equals("layout_alignTop")){
                top=xpp.getAttributeName(i);
                topId = xpp.getAttributeValue(i);
            }else if(xpp.getAttributeName(i).equals("layout_alignBottom")){
                bottom= xpp.getAttributeName(i);
                bottomId=xpp.getAttributeValue(i);
            }else if(xpp.getAttributeName(i).equals("layout_above")){
                bottom = xpp.getAttributeName(i);
                bottomId = xpp.getAttributeValue(i);
            }else if(xpp.getAttributeName(i).equals("layout_below")){
                top=xpp.getAttributeName(i);
                topId=xpp.getAttributeValue(i);
            }else if(xpp.getAttributeName(i).equals("layout_alignParentTop")){
                top=xpp.getAttributeName(i);
                topId ="Parent";
            }else if(xpp.getAttributeName(i).equals("layout_alignParentBottom")){
                bottom = xpp.getAttributeName(i);
                bottomId = "Parent";
            }



            if(xpp.getAttributeName(i).equals("id"))
                this.id = xpp.getAttributeValue(i);


            if(xpp.getAttributeName(i).equals("layout_width")){
                if(!xpp.getAttributeValue(i).equals("wrap_content"))
                    width=changeDpToInt(xpp.getAttributeValue(i))*2;

            }else if(xpp.getAttributeName(i).equals("layout_width")){
                if(!xpp.getAttributeValue(i).equals("wrap_content"))
                    height=changeDpToInt(xpp.getAttributeValue(i))*2;
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
    public void setWidth(int width){
        this.width=width;
    }
    public void setHeight(int height){
        this.height=height;
    }

    private int getComponentWidthSize(String text,int dp){
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

}
