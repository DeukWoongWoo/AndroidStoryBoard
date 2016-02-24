package Xml;
import com.intellij.icons.AllIcons;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.roots.ProjectRootManager;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.psi.util.PsiTreeUtil;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.*;
import java.util.*;


/**
 * Created by cho on 2016-01-10.
 */
public class GenerateAction extends AnAction {
    private String xml;
    List <String >data = new ArrayList<String>();
    static int depthOfXML = 1;
    String layout_ParentOption [] = {
            "layout_alignParentTop",
            "layout_alignParentBottom",
            "layout_alignParentLeft",
            "layout_alignParentStart",
            "layout_alignParentRight",
            "layout_alignParentEnd"
    };

    String layout_alignOption[] = {
            "layout_alignTop",
            "layout_alignBottom",
            "layout_alignLeft",
            "layout_alignStart",
            "layout_alignRight",
            "layout_alignEnd"
    };
    String layout_toOption[]={
            "layout_toTopOf",
            "layout_toBottomOf",
            "layout_toLeftOf",
            "layout_toStartOf",
            "layout_toRightOf",
            "layout_toEndOf"
    };
    String layout_HeigtOption[] = {
            "layout_above",
            "layout_below"
    };

    String layout_Margin[]={
            "layout_marginTop",
            "layout_marginBottom",
            "layout_marginLeft",
            "layout_marginStart",
            "layout_marginRight",
            "layout_marginEnd"
    };

    int stdTopMargin = 0;
    int stdBottomMargin=0;
    int stdLeftMargin=0;
    int stdRightMargin=0;

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here

        Project project = e.getProject();
        String projectName = project.getName();
        Messages.showInfoMessage("TestParsing","TestParsing");

        try{
            //String Filepath="C:/Users/cho/Desktop/android_project/MyApplication4/app/src/main/res/layout/content_main.xml";
            String Filepath = "C:/Users/cho/Desktop/AndroidStoryboard/Library/android_project/XmlParserActivity/app/src/main/res/layout/testlayout.xml";

            DocumentBuilderFactory f= DocumentBuilderFactory.newInstance();
            DocumentBuilder parser = f.newDocumentBuilder();
            org.w3c.dom.Document  XmlDoc= parser.parse(Filepath);
            org.w3c.dom.Element root = XmlDoc.getDocumentElement();
            XmlDoc.getDocumentElement().normalize();



            File ff = new File(Filepath);
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
                Messages.showInfoMessage("Id"+componentManager.getComponent(i).getId()+"\n"+
                        "width: "+getComponentWidthPoint(componentManager,componentManager.getComponent(i).getId(),"left")
                        +" Height: "+getComponentHeightPoint(componentManager,componentManager.getComponent(i).getId(),"top")
                        ,"Axis");
            }
           // makeFile(makeWebJson(map,componentManager));
            makeFile(makePluginJson(componentArrayList));

            /*
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(XmlDoc);
            StreamResult result = new StreamResult(new File(Filepath));
            transformer.transform(source,result);
*/

        }catch(Exception e2){
            Messages.showInfoMessage("error1","error1");

        }
        StringBuilder sourceRootsList = new StringBuilder();
        VirtualFile[] vFiles = ProjectRootManager.getInstance(project).getContentSourceRoots();
        for (VirtualFile file : vFiles) {
            sourceRootsList.append(file.getUrl()).append("\n");
        }
        PsiClass psiClass=getPsiClassFromContext(e);
        GenerateDialog dlg  = new GenerateDialog(psiClass);
        dlg.show();
        if(dlg.isOK()){
            generateComparable(psiClass, dlg.getFields());
        }
    }

    private void setHashMap(HashMap<String, ArrayList<Component>> map,Component component,String parentId){
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

    private JSONObject makeWebJson(HashMap<String, ArrayList<Component>> map, ComponentManager componentManager){
        JSONObject jsonActivity;
        JSONObject jsonObject;
        JSONArray jsonArrayObject;
        JSONArray jsonArrayActivity=new JSONArray();
        JSONObject jsonApp=new JSONObject();

        Iterator iterator = map.entrySet().iterator();
        jsonApp.put("appName","test");
        while(iterator.hasNext()) {
            Map.Entry entry = (Map.Entry) iterator.next();
            ArrayList<Component> values = (ArrayList<Component>) entry.getValue();
            String parentId = entry.getKey().toString();
            Component component = new Component();

            for(int i=0;i<componentManager.size();i++){
                if(componentManager.getComponent(i).componentId.equals(parentId))
                    component = componentManager.getComponent(i);
            }

            jsonActivity=new JSONObject();
            jsonActivity.put("name",parentId);
            jsonActivity.put("x",component.leftPoint);
            jsonActivity.put("y",component.topPoint);
            jsonActivity.put("width",component.getWidth());
            jsonActivity.put("height",component.getHeight());
            jsonArrayObject = new JSONArray();
            for(int i=0;i<values.size();i++){
                component=values.get(i);
                jsonObject = new JSONObject();
                jsonObject.put("name",component.getId());
                jsonObject.put("x",component.leftPoint);
                jsonObject.put("y",component.topPoint);
                jsonObject.put("height",component.getHeight());
                jsonObject.put("width",component.getWidth());
                jsonObject.put("text",component.text);
                jsonObject.put("textSize",component.textSize);
                jsonObject.put("type",component.tagName);
               //jsonObject.put("next","null");
                //jsonObject.put("image","null");
                jsonArrayObject.add(jsonObject);
            }
            jsonActivity.put("object",jsonArrayObject);
            jsonArrayActivity.add(jsonActivity);
        }
        jsonApp.put("activity",jsonArrayActivity);
        return jsonApp;
    }

    private JSONObject makePluginJson(ArrayList<Component> componentArrayList){
        JSONObject json=new JSONObject();//전체
        JSONArray jsonObjectArray = new JSONArray();
        JSONObject jsonObject=null;
        JSONArray jsonActiArray = new JSONArray();
        JSONArray jsonArray = new JSONArray();

        json.put("appName","test");
        String xmlName = "test.xml";
        //todo : 여기는 xml파일 대로 xmlName이 바뀐다.
        JSONObject jsonActivity = new JSONObject();
        jsonActivity.put("name",xmlName);
        jsonActivity.put("x",0);
        jsonActivity.put("y",0);
        jsonActivity.put("width",768);
        jsonActivity.put("height",1280);

        for(int i=0;i<componentArrayList.size();i++){
            Component component = componentArrayList.get(i);

            if(!component.getTagName().equals("EndTag")){
                jsonObject= new JSONObject();//Object;

                jsonObject.put("name",component.componentId);
                jsonObject.put("x",component.leftPoint);
                jsonObject.put("y",component.topPoint);
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
        jsonActivity.put("object",jsonObject);
        json.put("activity",jsonActivity);
        return json;
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

    private void makeFile(JSONObject jsonObject){
        FileWriter file = null;
        try {
            file = new FileWriter("C:/Users/cho/Desktop/json/uuuu.json");
            file.write(jsonObject.toJSONString());
            file.flush();
            file.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public int getComponentWidthPoint(ComponentManager componentManager, String id, String point){//width를 반환한다(크기도 저장한다)

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
                    leftPoint=parentLeftPoint;
                    if(component.isMarginLeft)
                        leftPoint+=component.marginLeft;
                }

                else
                    leftPoint = rightPoint-stdWidth;
            }else{//left에 물린경우
                if(component.tagName.equals("RelativeLayout")){
                    rightPoint=parentRightPoint;
                    if(component.isMarginRight)
                        rightPoint-=component.marginRight;
                }

                else
                    rightPoint = leftPoint+stdWidth;
            }
        }
        component.setWidth(stdWidth);


            component.leftPoint=leftPoint;
            component.rightPoint=rightPoint;

        if(point.equals("left"))
            return leftPoint;
        else
            return rightPoint;
    }

    public int getComponentHeightPoint(ComponentManager componentManager, String id, String point){//width를 반환한다(크기도 저장한다)

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
                    topPoint = parentTopPoint;
                    if(component.isMarginTop)
                        topPoint+=component.marginTop;
                }
                else
                    topPoint = bottomPoint-stdHeight;
            }else{//top에 물린경우
                if(component.tagName.equals("RelativeLayout")){
                    bottomPoint=parentBottomPoint;
                    if(component.isMarginBottom)
                        bottomPoint-=component.marginBottom;
                }
                else
                    bottomPoint = topPoint+stdHeight;
            }
        }
        component.setHeight(stdHeight);

            component.topPoint=topPoint;
            component.bottomPoint=bottomPoint;

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

    public static int changeDpToInt(String value){
        int dp=0;
        char val[] = value.toCharArray();
        for(int i=0;val[i]!='d';i++)
            dp =dp*10+ val[i]-'0';
        return dp;
    }

    private void generateComparable(PsiClass psiClass, List<PsiField> fields) {
        new WriteCommandAction.Simple(psiClass.getProject(),psiClass.getContainingFile()){

            @Override
            protected void run() throws Throwable {
                generateCompareTo(psiClass, fields);
                generateImplementComparable(psiClass);

            }

        }.execute();
    }

    private void generateImplementComparable(PsiClass psiClass) {
        PsiClassType[] implementsListTypes = psiClass.getImplementsListTypes();
        for(PsiClassType implementsListType : implementsListTypes){
            PsiClass resolved = implementsListType.resolve();
            if(resolved != null && "java.lang.Comparable".equals(resolved.getQualifiedName())){
                return ;
            }
        }

        String implementsType = "Comparable<"+psiClass.getName() + ">";
        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(psiClass.getProject());
        PsiJavaCodeReferenceElement referenceElement = elementFactory.createReferenceFromText(implementsType, psiClass);
        psiClass.getImplementsList().add(referenceElement);

    }

    private void generateCompareTo(PsiClass psiClass, List<PsiField> fields) {
        StringBuilder builder = new StringBuilder("public int compareTo(");
        builder.append(psiClass.getName()).append(" that) {\n");
        builder.append("return com.google.common.collect.ComparisonChain.start()");
        for(PsiField field : fields){
            builder.append(".compare (this.").append(field.getName()).append(", that.");
            builder.append(field.getName()).append(")");
        }
        builder.append(".result();\n}");
        PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(psiClass.getProject());
        PsiMethod compareTo = elementFactory.createMethodFromText(builder.toString(), psiClass);
        PsiElement method= psiClass.add(compareTo);
        JavaCodeStyleManager.getInstance(psiClass.getProject()).shortenClassReferences(method);
    }

    @Override
    public void update(AnActionEvent e) {
        PsiClass psiClass=getPsiClassFromContext(e);
        e.getPresentation().setEnabled(psiClass !=null);

    }

    private PsiClass getPsiClassFromContext(AnActionEvent e) {
        PsiFile psiFile= e.getData(LangDataKeys.PSI_FILE);


        Editor editor= e.getData(PlatformDataKeys.EDITOR);

        if(psiFile ==null || editor == null) {
            return null;
        }

        int offset = editor.getCaretModel().getOffset();
        PsiElement elementAT = psiFile.findElementAt(offset);
        PsiClass psiClass = PsiTreeUtil.getParentOfType(elementAT, PsiClass.class);
        return psiClass;
    }
}