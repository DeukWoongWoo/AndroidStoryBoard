package Xml;

import org.xmlpull.v1.XmlPullParser;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * Created by cho on 2016-01-24.
 */

public class Component {

    public String nextActivity=null;
    public boolean isNext=false;
    public String library="null";
    public String componentId=null;
    private ArrayList<Attribution> Attributes ;
    private int AttributeCount;
    private String id=null;//자주 쓰임
    public String tagName;
    private int width;
    private int height;
    public int paddingTop=0;
    public int paddingBottom=0;
    public int paddingLeft=0;
    public int paddingRight=0;
    public int textWidth;
    public int textHeight;
    public int leftPoint=-1;
    public int rightPoint=-1;
    public int topPoint=-1;
    public int bottomPoint=-1;

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
    public boolean isMarginTop=false;
    public boolean isMarginBottom=false;

    public int marginLeft;
    public int marginRight;
    public int marginTop;
    public int marginBottom;

    public String parentId;
    public int contentWidth=-1;
    public int contentHeight=-1;


    public String image = null;
    public boolean isBackground=false;
    public String color="none";
    public String text="null";
    public String textSize="14";
    public String textColor="#000000";

    public int parentLeftPoint;
    public int parentTopPoint;
    public boolean isWidth=false;
    public boolean isHeight=false;

    //linearLayout


    Component(){
        Attributes =new ArrayList<Attribution>();
        AttributeCount=0;
    }
    public void setComponentId(String id){
        this.componentId = id;
    }
    public void setId(String id){
        this.id=id;
    }
    public void setAttributes(XmlPullParser xpp){
        AttributeCount=xpp.getAttributeCount();
        tagName = xpp.getName();
        if(tagName.equals("Button")){
            paddingLeft=24;
            paddingRight=24;
            paddingBottom=20;
            paddingTop=20;
            color = "gray";
        }else if(tagName.equals("EditText")){
            paddingLeft=8;
            paddingRight=8;
            paddingBottom=22;
            paddingTop=20;
        }
        this.id=tagName;
        for(int i=0;i<AttributeCount;i++) {
            Attribution tempAttr=new Attribution();
            tempAttr.setAttribute(xpp.getAttributeName(i));
            tempAttr.setValue(xpp.getAttributeValue(i));

            if(xpp.getAttributeName(i).equals("background")) {
                isBackground= true;
                String background=xpp.getAttributeValue(i);
                char[] backArray =  background.toCharArray();
                String comp=null;
                if(backArray[0]!='#'){
                    comp = new String(backArray,0,10);
                    if(comp.equals("@drawable/")) {
                        String img = new String(backArray, 10, background.length() - 10);
                        image = img;
                    }
                }
                else
                    color=background;
            }
            if(xpp.getAttributeName(i).equals("textColor")){
                textColor=xpp.getAttributeValue(i);
            }

            else if(xpp.getAttributeName(i).equals("layout_width")){
                if((!xpp.getAttributeValue(i).equals("wrap_content")) &&
                        (!xpp.getAttributeValue(i).equals("match_parent"))){
                    contentWidth=changeDpToInt(xpp.getAttributeValue(i))*2;
                    isWidth=true;
                }
            }else  if(xpp.getAttributeName(i).equals("layout_height")){
                if((!xpp.getAttributeValue(i).equals("wrap_content"))&&
                        (!xpp.getAttributeValue(i).equals("match_parent"))){
                    contentHeight=changeDpToInt(xpp.getAttributeValue(i))*2;
                    isHeight=true;
                }
            }

            //RelativeLayout
            else if(xpp.getAttributeName(i).equals("layout_centerHorizontal")){
                leftId = "Center";
                left=xpp.getAttributeName(i);
            }else if(xpp.getAttributeName(i).equals("layout_centerVertical")){
                topId="Center";
                top = xpp.getAttributeName(i);
            }
            else if(xpp.getAttributeName(i).equals("layout_alignRight")){
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
            else if(xpp.getAttributeName(i).equals("layout_marginRight")){
                isMarginRight=true;
                marginRight= changeDpToInt(xpp.getAttributeValue(i))*2;
            } else if(xpp.getAttributeName(i).equals("layout_marginLeft")){
                isMarginLeft=true;
                marginLeft= changeDpToInt(xpp.getAttributeValue(i))*2;
            } else if(xpp.getAttributeName(i).equals("layout_marginTop")) {
                isMarginTop=true;
                marginTop = changeDpToInt(xpp.getAttributeValue(i))*2;
            }else if(xpp.getAttributeName(i).equals("layout_marginBottom")) {
                isMarginBottom=true;
                marginBottom = changeDpToInt(xpp.getAttributeValue(i))*2;
            }
            else if(xpp.getAttributeName(i).equals("layout_alignTop")){
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


            else if(xpp.getAttributeName(i).equals("paddingTop")){
                paddingTop = changeDpToInt(xpp.getAttributeValue(i));
            }else if(xpp.getAttributeName(i).equals("paddingBottom")){
                paddingBottom=changeDpToInt(xpp.getAttributeValue(i));
            }else if(xpp.getAttributeName(i).equals("paddingLeft")){
                paddingLeft=changeDpToInt(xpp.getAttributeValue(i));
            }else if(xpp.getAttributeName(i).equals("paddingRight")){
                paddingRight=changeDpToInt(xpp.getAttributeValue(i));
            }else if(xpp.getAttributeName(i).equals("padding")) {
                int padding;
                padding = changeDpToInt(xpp.getAttributeValue(i));
                paddingLeft=padding;
                paddingRight=padding;
                paddingBottom=padding;
                paddingTop=padding;
            }

            else if(xpp.getAttributeName(i).equals("id"))
                this.id = xpp.getAttributeValue(i);
            else if(xpp.getAttributeName(i).equals("text")){
                text = xpp.getAttributeValue(i);
                for(int j=0;j<AttributeCount;j++){
                    if(xpp.getAttributeName(j).equals("textSize")){
                        textSize= String.valueOf((changeDpToInt(xpp.getAttributeValue(j))));
                        textWidth = getComponentWidthSize(xpp.getAttributeValue(i),changeDpToInt(xpp.getAttributeValue(j)));
                        textHeight=(int)(changeDpToInt(xpp.getAttributeValue(j))*2.7);
                    }
                }
                if(textWidth<=0)
                    textWidth=getComponentWidthSize(xpp.getAttributeValue(i),14);
            }
            Attributes.add(tempAttr);
        }
        if(textSize.equals("14")){
            textWidth = getComponentWidthSize(text,14);
            textHeight=(int)(14*2.7);
        }


    }
    public void setContentWidthSize(){
            contentWidth = textWidth+paddingLeft+paddingRight;

            if(tagName.equals("CheckBox") ||
                    tagName.equals("RadioButton")){
               contentWidth+=64;
            }else if(tagName.equals("EditText")){
                contentWidth+=25;
            }
            else if(tagName.equals("Button")){
                if(contentWidth<176){
                    contentWidth=176;
                }
            }
    }
    public void setContentHeightSize(){
        contentHeight=textHeight+paddingBottom+paddingTop;

        if(tagName.equals("CheckBox") ||
                tagName.equals("RadioButton")){
            if(contentHeight<64)
                contentHeight=64;
        }
        else if(tagName.equals("Button")){
            if(contentHeight<96)
                contentHeight=96;
        }



    }
    public void setAttributeCount(){
        AttributeCount=Attributes.size();
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
    public int getSize(){return AttributeCount;}
    public void setAttributes(String attr,String value){
        Attribution attribution = new Attribution(attr,value);
        Attributes.add(attribution);
    }
}

