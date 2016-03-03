package Xml;

import Analysis.Database.DataAccessObject.Java.JavaDAO;
import Analysis.Database.DatabaseManager.DatabaseManager;
import Analysis.Database.DtatTransferObject.JavaDTO;
import Analysis.Database.DtatTransferObject.NextActivityDTO;
import Analysis.Main.ProjectAnalysis;
import com.intellij.openapi.ui.Messages;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by cho on 2016-02-25.
 */
public class XmlToJson {
    private JSONObject webJsonObject;
    private JSONArray webJsonArray;
    private JSONObject pluginJsonObject;
    private JSONArray pluginJsonArray;
    private ArrayList<JavaDTO> javaDTOArray = DatabaseManager.getInstance().selectToJava(JavaDAO::selectAll);

    public XmlToJson(){
        webJsonObject=new JSONObject();
        pluginJsonObject = new JSONObject();
        webJsonArray=new JSONArray();
        pluginJsonArray=new JSONArray();
        Messages.showInfoMessage("Contructor!!","filePath");
    }

    private void addWebObject(JSONObject jsonObject){
        webJsonArray.add(jsonObject);
    }
    private void addPluginObject(JSONObject jsonObject){
        pluginJsonArray.add(jsonObject);
    }

    private JSONObject makeWebJsonObject(){
        webJsonObject.put("appName","test");
        webJsonObject.put("xmls",webJsonArray);


        return webJsonObject;
    }
    private JSONObject makePluginJsonObject(){
        pluginJsonObject.put("appName","test");
        pluginJsonObject.put("xmls",pluginJsonArray);
        pluginJsonObject.put("activities", makePluginActivityJsonObject());
        return pluginJsonObject;
    }

    private JSONArray makePluginActivityJsonObject(){
        String activityName;
        String xmlName;
        UseLibraryParser useLibraryParser = new UseLibraryParser();
        useLibraryParser.parse();
        JSONArray activityJsonArray = new JSONArray();
        for(int i=0;i<javaDTOArray.size();i++ ){
            JSONObject jsonObject = new JSONObject();
            JavaDTO javaDTO=javaDTOArray.get(i);
            xmlName=javaDTO.getXml(0).getXmlName();

            char[] pastxmlName =xmlName.toCharArray();
            String changexmlName = new String(pastxmlName,9,xmlName.length()-9);
            changexmlName=changexmlName+".xml";
            activityName=javaDTO.getName();
            jsonObject.put("xmlName",changexmlName);
            jsonObject.put("name",activityName);
            jsonObject.put("x",0);
            jsonObject.put("y",0);
            jsonObject.put("width",768);
            jsonObject.put("height",1280);
            for(int j=0;j<useLibraryParser.activityLength();j++)
                if(useLibraryParser.getComponentName("activity",j).equals(activityName)&&
                        useLibraryParser.getXmlName("activity",j).equals(changexmlName))
                    jsonObject.put("library","activity");
            JSONArray jsonArray = new JSONArray();
            ArrayList<NextActivityDTO> javaNextAct = javaDTO.getNextActivitys();

            for(int k=0;k<javaNextAct.size();k++)
                jsonArray.add(javaNextAct.get(k).getName());
            if (javaNextAct.size()>0)
                jsonObject.put("next",jsonArray);
            activityJsonArray.add(jsonObject);
        }
        return activityJsonArray;
    }

    public void make(){
        ProjectAnalysis projectAnalysis = ProjectAnalysis.getInstance(null);
        String[][] filePath=projectAnalysis.findResourcePath();
        XmlToJson xmlToJson = new XmlToJson();
        for(int i=0;i<filePath.length;i++)
        {
            String fp[] = filePath[i];
            Messages.showInfoMessage(fp[0],"filePath");
            XmlToJsonObject xmlToJsonObject=xmlToJson.makeObject(fp);
            addWebObject(xmlToJsonObject.getWebJson());
            addPluginObject(xmlToJsonObject.getPluginJson());
        }
        makeFile(makeWebJsonObject(),"C:/Users/cho/Desktop/json/web.json");
        makeFile(makePluginJsonObject(),"C:/Users/cho/Desktop/json/plugin.json");
         //makeFile(makePluginJsonObject(), Constant.FILE_OUT);

    }

    private XmlToJsonObject makeObject(String Filepath[]){
        XmlToJsonObject xmlToJsonObject = null;
        String xmlName=Filepath[1];
        try{
            File ff = new File(Filepath[0]);
            XmlPullParserFactory xppf = XmlPullParserFactory.newInstance();
            xppf.setNamespaceAware(true);
            XmlPullParser xpp = xppf.newPullParser();
            FileInputStream fis = new FileInputStream(ff);
            xpp.setInput(fis,null);

            ComponentManager componentManager=new ComponentManager();
            int type = xpp.getEventType();
            int layoutCount=0;
            String parentId=null;
            Push(0);
            int componentCount=0;
            ArrayList<Component> key =new ArrayList<Component>();
            HashMap<String, ArrayList<Component>> map = new HashMap<String ,ArrayList<Component>>();
            ArrayList<Component> componentArrayList = new ArrayList<>();

            while(type != XmlPullParser.END_DOCUMENT){

                if(type == XmlPullParser.START_TAG) {

                    Component component = new Component();
                    component.setAttributes(xpp);
                    if(component.contentWidth<0)
                        component.setContentWidthSize();
                    if(component.contentHeight<0)
                        component.setContentHeightSize();

                    if(xpp.getName().equals("RelativeLayout")){//layout
                        component.parentId="RelativeLayout"+isTop();
                        Push(++layoutCount);
                        parentId ="RelativeLayout"+isTop();
                        component.setComponentId(parentId);
                        if(component.getId().equals(component.tagName)){
                            component.setId(parentId);
                        }
                    }else{//component
                        component.parentId=parentId;
                        component.setComponentId(component.getId());
                        setHashMap(map,component,parentId);
                    }
                    componentManager.addComponent(component);
                    componentCount++;
                    componentArrayList.add(component);
                }
                else  if(type == XmlPullParser.END_TAG) {
                    if(xpp.getName().equals("RelativeLayout")){
                        int pop = Pop();
                        parentId ="RelativeLayout"+isTop();

                        Component component = new Component();
                        String Id = "End"+"RelativeLayout"+pop;
                        component.tagName="EndTag";
                        component.setId(Id);
                        component.setComponentId(Id);
                        component.parentId=parentId;
                        componentArrayList.add(component);
                    }
                }
                type = xpp.next();
            }

            for(int i=0;i<componentManager.size();i++){
                /*
                Messages.showInfoMessage("Id"+componentManager.getComponent(i).getId()+"\n"+
                                "width: "+getComponentWidthPoint(componentManager,componentManager.getComponent(i).getId(),"left")
                                +" Height: "+getComponentHeightPoint(componentManager,componentManager.getComponent(i).getId(),"top")
                        ,"Axis");*/
                getComponentWidthPoint(componentManager,componentManager.getComponent(i).getId(),"left");
                getComponentHeightPoint(componentManager,componentManager.getComponent(i).getId(),"top");
            }



            JSONObject webJson = makeWebJsonObject(componentManager,xmlName);
            JSONObject pluginJson = makePluginJsonObject(componentArrayList,xmlName);

            xmlToJsonObject=new XmlToJsonObject(webJson,pluginJson);
        }catch(Exception e2){
            Messages.showInfoMessage("error1222","error1222");

        }
        return xmlToJsonObject;
    }


    private void makeFile(JSONObject jsonObject,String filePath){
        FileWriter file = null;
        try {
            file = new FileWriter(filePath);
            file.write(jsonObject.toJSONString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String makeId(String id){
        char[] charId = id.toCharArray();
        String makeId=new String(charId,5,id.length()-5);
        return makeId;
    }



    private JSONObject makeWebJsonObject(ComponentManager componentManager, String xmlName){

        ArrayList<String> nextActivity = new ArrayList<>();
        xmlName="R.layout."+xmlName;
        char[] pastxmlName =xmlName.toCharArray();
        String changexmlName = new String(pastxmlName,0,xmlName.length()-4);
        String activityName=null;

        for(int i=0;i<javaDTOArray.size();i++ ){
            JavaDTO javaDTO=javaDTOArray.get(i);
            for(int j=0;j<javaDTO.getXmls().size();j++){
                if(javaDTO.getXmls().get(j).getXmlName().equals(changexmlName)) {
                    activityName = javaDTO.getName();
                    ArrayList<NextActivityDTO> javaNextAct = javaDTO.getNextActivitys();
                    for(int k=0;k<javaNextAct.size();k++){
                        nextActivity.add(javaNextAct.get(k).getName());
                    }

                }
            }
        }


        JSONObject jsonActivity;
        JSONObject jsonObject;
        JSONArray jsonArrayObject;
        Component component;

        jsonActivity=new JSONObject();
        jsonActivity.put("name",activityName);
        jsonActivity.put("x","0");
        jsonActivity.put("y","0");
        jsonActivity.put("width",768);
        jsonActivity.put("height",1280);
        if(nextActivity.size()==1)
            jsonActivity.put("next",nextActivity.get(0));
        else{
            JSONArray jr = new JSONArray();
            for(int i=0;i<nextActivity.size();i++){
                jr.add(nextActivity.get(i));
            }
            if(nextActivity.size()>0)
                jsonActivity.put("next",jr);
        }

        jsonArrayObject = new JSONArray();
        for(int i=0;i<componentManager.size();i++){
            component= componentManager.getComponent(i);
            if(!component.getTagName().equals("RelativeLayout")){//오브젝트인경우
                jsonObject = new JSONObject();
                jsonObject.put("name",makeId(component.getId()));
                jsonObject.put("x",component.leftPoint);
                jsonObject.put("y",component.topPoint);
                jsonObject.put("height",component.getHeight());
                jsonObject.put("width",component.getWidth());
                jsonObject.put("text",component.text);
                jsonObject.put("textSize",component.textSize);
                jsonObject.put("textColor",component.textColor);
                jsonObject.put("type",component.tagName);
                if(component.image == null)
                    jsonObject.put("color",component.color);
                else
                    jsonObject.put("image",component.image);
                //jsonObject.put("next","null");
                //jsonObject.put("image","null");
                jsonArrayObject.add(jsonObject);
            }else{//레이아웃인경우
                if(component.isBackground) {
                    jsonObject = new JSONObject();
                    jsonObject.put("height",component.getHeight());
                    jsonObject.put("width",component.getWidth());
                    jsonObject.put("type",component.tagName);
                    jsonObject.put("x",component.leftPoint);
                    jsonObject.put("y",component.topPoint);
                    if(component.image !=null)//이미지가 있는 경우
                        jsonObject.put("image",component.image);
                    else
                        jsonObject.put("color",component.color);
                    jsonArrayObject.add(jsonObject);
                }

            }
        }
        jsonActivity.put("object",jsonArrayObject);

        return jsonActivity;
    }
    private JSONObject makePluginJsonObject(ArrayList<Component> componentArrayList,String xmlName){



        UseLibraryParser useLibraryParser = new UseLibraryParser();
        useLibraryParser.parse();

        JSONArray jsonObjectArray = new JSONArray();
        JSONObject jsonObject=null;
        //String xmlName = "test.xml";
        //todo : 여기는 xml파일 대로 xmlName이 바뀐다.
        JSONObject jsonActivity = new JSONObject();
        jsonActivity.put("name",xmlName);

        for(int i=0;i<componentArrayList.size();i++){
            Component component = componentArrayList.get(i);

            if(!component.getTagName().equals("EndTag")){
                jsonObject= new JSONObject();//Object;

                jsonObject.put("name",component.componentId);
                jsonObject.put("x",component.leftPoint-component.parentLeftPoint);
                jsonObject.put("y",component.topPoint-component.parentTopPoint);
                jsonObject.put("width",component.getWidth());
                jsonObject.put("height",component.getHeight());
                jsonObject.put("type",component.tagName);
                JSONObject jsonAttribute = new JSONObject();
                for(int j = 0;j<component.getSize();j++){
                    jsonAttribute.put(component.getAttributes(j),
                            component.getAttributesValue(j));
                }
                jsonObject.put("attribute",jsonAttribute);
                if(component.tagName.equals("RelativeLayout")){//레이아웃!
                    pushJsonArray(jsonObjectArray);
                    pushJson(jsonObject);
                    jsonObjectArray= new JSONArray();
                }else{//컴포넌트!
                    for(int j=0;j<useLibraryParser.eventLength();j++){
                        if(xmlName.equals(useLibraryParser.getXmlName("event",j)) &&
                                makeId(component.getId()).equals(useLibraryParser.getComponentName("event",j))){
                            jsonObject.put("library","event");
                            break;
                        }
                    }for(int j=0;j<useLibraryParser.errorLength();j++) {
                        if (xmlName.equals(useLibraryParser.getXmlName("error", j)) &&
                                makeId(component.getId()).equals(useLibraryParser.getComponentName("error", j))) {
                            jsonObject.put("library", "error");
                            break;
                        }
                    }

                    jsonObjectArray.add(jsonObject);
                }
            }
            else{//endTag!

                //endTag의 앞부분 속성
                //jsonObjectArray endTag가 걸린 컴포넌트들
                jsonObject =popJson();
                jsonObject.put("object",jsonObjectArray);
                //jsonObject는 이번 ObjectArray에 에드를 해야할듯?
                jsonObjectArray = popJsonArray();
                jsonObjectArray.add(jsonObject);
            }
        }

        JSONArray ucherSibal = new JSONArray();
        ucherSibal.add(jsonObject);
        jsonActivity.put("object",ucherSibal);

        return jsonActivity;
    }
    private void setHashMap(HashMap<String, ArrayList<Component>> map, Component component, String parentId){
        Iterator iterator = map.entrySet().iterator();
        while(iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            if (entry.getKey().equals(parentId)) {//존재하면
                ArrayList<Component> value = (ArrayList<Component>) entry.getValue();
                value.add(component);
                map.put(entry.getKey().toString(), value);
                return ;
            }
        }
        //존재하지 않으면
        ArrayList <Component> value = new ArrayList<Component>();
        value.add(component);
        map.put(parentId,value);
    }


    private int jsonArrayTop=0;
    private JSONArray[] jsonArrayStack = new JSONArray[255];
    private void pushJsonArray(JSONArray jsonArray){
        jsonArrayStack[jsonArrayTop++] = jsonArray;
    }
    private JSONArray popJsonArray(){
        return jsonArrayStack[--jsonArrayTop];
    }
    private JSONArray currentJsonArray(){
        return jsonArrayStack[jsonArrayTop-1];
    }



    private int jsonTop=0;
    private JSONObject[] jsonStack = new JSONObject[255];
    private void pushJson(JSONObject jsonObject){
        jsonStack[jsonTop++] = jsonObject;
    }
    private JSONObject popJson(){
        return jsonStack[--jsonTop];
    }

    private JSONObject currentJson(){
        return jsonStack[jsonTop-1];
    }

    private int getComponentWidthPoint(ComponentManager componentManager, String id, String point){//width를 반환한다(크기도 저장한다)

        int leftPoint=0;
        int rightPoint=0;
        int stdWidth=0;
        int parentLeftPoint=-1;
        int parentRightPoint=-1;

        Component component = new Component();
        for(int i=0;i<componentManager.size();i++){
            if(componentManager.getComponent(i).getId().equals(id)){
                component = componentManager.getComponent(i);
                break;
            }
        }
        if(component.parentId.equals("RelativeLayout0")) {//최상위 레이아웃
            component.leftPoint = 0;
            component.rightPoint= 768;
            component.setWidth(768);
            return component.leftPoint;
        }else{
            for(int i=0;i<componentManager.size();i++){
                if(componentManager.getComponent(i).componentId.equals(component.parentId)){
                    parentLeftPoint=componentManager.getComponent(i).leftPoint;
                    parentRightPoint=componentManager.getComponent(i).rightPoint;

                }
            }
        }

        if(component.leftId.equals("Parent")){//parent
            leftPoint=parentLeftPoint;
            leftPoint+=component.marginLeft;

        }else if(component.left.equals("layout_alignLeft")) {
            leftPoint = getComponentWidthPoint(componentManager,component.leftId,"left");
            leftPoint+=component.marginLeft;
        }else if(component.left.equals("layout_toRightOf")){
            leftPoint = getComponentWidthPoint(componentManager,component.leftId,"right");
            leftPoint+=component.marginLeft;
        }

        if(component.rightId.equals("Parent")){
            rightPoint=parentRightPoint;
            rightPoint-=component.marginRight;
        }else if(component.right.equals("layout_alignRight")){
            rightPoint= getComponentWidthPoint(componentManager,component.rightId,"right");
            rightPoint-=component.marginRight;
        }else if(component.right.equals("layout_toLeftOf")){
            rightPoint= getComponentWidthPoint(componentManager,component.rightId,"left");
            rightPoint-=component.marginRight;
        }



        if((!component.rightId.equals("null"))&&(!component.leftId.equals("null")))//양쪽으로 물려 있는 경우
        {
            stdWidth = rightPoint - leftPoint;

        }else if(component.rightId.equals("null") || component.leftId.equals("null")){//한쪽으로 물린경우
            stdWidth = component.contentWidth;
            if(!component.rightId.equals("null")){//right에 물린경우
                if(component.tagName.equals("RelativeLayout")){
                    if(component.isWidth)
                        leftPoint=rightPoint-stdWidth;
                    else
                        leftPoint=parentLeftPoint;
                    if(component.isMarginLeft)
                        leftPoint+=component.marginLeft;
                } else {
                    if(component.rightId.equals("Center")){
                    int midlePoint=(parentRightPoint+parentLeftPoint)/2;
                    leftPoint = midlePoint-stdWidth/2;
                    rightPoint=midlePoint+stdWidth/2;
                }else
                    leftPoint = rightPoint-stdWidth;

                }
            }else{//left에 물린경우
                if(component.tagName.equals("RelativeLayout")){
                    if(component.isWidth)
                        rightPoint=stdWidth+leftPoint;
                    else
                        rightPoint=parentRightPoint;
                    if(component.leftId.equals("Center"))
                        leftPoint=parentLeftPoint;
                    if(component.isMarginRight)
                        rightPoint-=component.marginRight;
                }
                else{
                    if(component.leftId.equals("Center")){
                        int middlePoint=(parentRightPoint+parentLeftPoint)/2;
                        leftPoint = middlePoint-stdWidth/2;
                        rightPoint=middlePoint+stdWidth/2;
                    }else
                        rightPoint = leftPoint+stdWidth;
                }
            }
        }
        component.leftPoint=leftPoint;
        component.rightPoint=rightPoint;
        component.parentLeftPoint=parentLeftPoint;
        if(component.tagName.equals("RelativeLayout")) {
            stdWidth = rightPoint-leftPoint;
        }
        component.setWidth(stdWidth);

        if(point.equals("left"))
            return leftPoint;
        else
            return rightPoint;
    }
    private int getComponentHeightPoint(ComponentManager componentManager, String id, String point){//width를 반환한다(크기도 저장한다)

        int topPoint=0;
        int bottomPoint=0;
        int stdHeight=0;
        int parentTopPoint=-1;
        int parentBottomPoint=-1;

        Component component = new Component();
        for(int i=0;i<componentManager.size();i++){
            if(componentManager.getComponent(i).getId().equals(id)){
                component = componentManager.getComponent(i);
                break;
            }
        }

        if(component.parentId.equals("RelativeLayout0")) {//최상위 레이아웃
            component.topPoint = 48;
            component.bottomPoint= 1184;
            component.setHeight(1184-48);
            return component.topPoint;
        }else{
            for(int i=0;i<componentManager.size();i++){
                if(componentManager.getComponent(i).componentId.equals(component.parentId)){
                    parentTopPoint=componentManager.getComponent(i).topPoint;
                    parentBottomPoint=componentManager.getComponent(i).bottomPoint;

                }
            }
        }

        if(component.topId.equals("Parent")){//parent
            topPoint=parentTopPoint;
            topPoint+=component.marginTop;

        }else if(component.top.equals("layout_alignTop")) {
            topPoint = getComponentHeightPoint(componentManager,component.topId,"top");
            topPoint+=component.marginTop;
        }else if(component.top.equals("layout_below")){
            topPoint= getComponentHeightPoint(componentManager,component.topId,"bottom");
            topPoint+=component.marginTop;
        }

        if(component.bottomId.equals("Parent")){
            bottomPoint=parentBottomPoint;
            bottomPoint-=component.marginBottom;
        }else if(component.bottom.equals("layout_alignBottom")){//
            bottomPoint= getComponentHeightPoint(componentManager,component.bottomId,"bottom");
            bottomPoint-=component.marginBottom;
        } else if(component.bottom.equals("layout_above")){//
            bottomPoint = getComponentHeightPoint(componentManager,component.bottomId,"top");
            bottomPoint-=component.marginBottom;
        }

        if((!component.bottomId.equals("null"))&&(!component.topId.equals("null")))//양쪽으로 물려 있는 경우
        {
            stdHeight = bottomPoint - topPoint;

        }else if(component.bottomId.equals("null") || component.topId.equals("null")){//한쪽으로 물린경우
            stdHeight = component.contentHeight;
            if(!component.bottomId.equals("null")){//bottom에 물린경우
                if(component.tagName.equals("RelativeLayout")){
                    if(component.isHeight)
                        topPoint=bottomPoint-stdHeight;
                    else
                        topPoint=parentTopPoint;
                    if(component.isMarginTop)
                        topPoint+=component.marginTop;
                }
                else
                    topPoint = bottomPoint-stdHeight;
            }else{//top에 물린경우
                if(component.tagName.equals("RelativeLayout")){
                    if(component.isHeight)
                        bottomPoint=topPoint+stdHeight;
                    else
                        bottomPoint=parentBottomPoint;
                    if(component.topId.equals("Center"))
                        topPoint = parentTopPoint;
                    if(component.isMarginBottom)
                        bottomPoint-=component.marginBottom;
                }
                else
                if(component.topId.equals("Center")){
                    int middlePoint=(parentTopPoint+parentBottomPoint)/2;
                    topPoint = middlePoint-stdHeight/2;
                    bottomPoint=middlePoint+stdHeight/2;
                }else
                    bottomPoint = topPoint+stdHeight;
            }
        }

        component.topPoint=topPoint;
        component.bottomPoint=bottomPoint;
        if(component.tagName.equals("RelativeLayout")) {
            stdHeight = bottomPoint-topPoint;
        }
        component.setHeight(stdHeight);

        if(point.equals("top"))
            return topPoint;
        else
            return bottomPoint;
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
