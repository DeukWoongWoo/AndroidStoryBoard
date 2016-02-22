package Xml;
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
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.*;
import java.util.ArrayList;
import java.util.List;



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
            /*
            DocumentBuilderFactory f= DocumentBuilderFactory.newInstance();
            DocumentBuilder parser = f.newDocumentBuilder();
            org.w3c.dom.Document  XmlDoc= parser.parse(Filepath);
            Element root = XmlDoc.getDocumentElement();
            XmlDoc.getDocumentElement().normalize();
            */
            File ff = new File(Filepath);
            XmlPullParserFactory xppf = XmlPullParserFactory.newInstance();
            xppf.setNamespaceAware(true);
            XmlPullParser xpp = xppf.newPullParser();
            FileInputStream fis = new FileInputStream(ff);
            xpp.setInput(fis,null);

            ComponentManager componentManager=new ComponentManager();
            int padding []=new int[]{0,0,0,0,0,0};
            int type = xpp.getEventType();
            int layoutCount=0;
            int allLayoutCount=0;
            String parentId=null;
            Push(0);
            while(type != XmlPullParser.END_DOCUMENT){

                if(type == XmlPullParser.START_TAG) {

                    Component component = new Component();
                    component.setAttributes(xpp);
                    component.setPadding(padding);
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
                    }


                    componentManager.addComponent(component);
                }
                else  if(type == XmlPullParser.END_TAG) {
                   // Messages.showInfoMessage("EndTag : "+xpp.getName(),"EndTag");
                    if(xpp.getName().equals("RelativeLayout")){
                        Pop();
                        parentId ="RelativeLayout"+isTop();
                    }




                    String paddingArr[] ={
                            "paddingTop",
                            "paddingBottom",
                            "paddingLeft",
                            "paddingStart",
                            "paddingRight",
                            "paddingEnd"
                    };
                    if(xpp.getName().equals("RelativeLayout"))
                        for(int i=0;i<xpp.getAttributeCount();i++)
                            for(int j=0;j<6;j++)
                                if(xpp.getAttributeName(i).equals(paddingArr[j]))
                                    padding[j] = changeDpToInt(xpp.getAttributeValue(i))*2;
                }
                type = xpp.next();
            }

            for(int i=0;i<componentManager.size();i++){
                String str="";

                for(int j=0;j<componentManager.getComponent(i).getAttributeCount();j++){
                    str+=componentManager.getComponent(i).getAttributes(j)+" : "+componentManager.getComponent(i).getAttributesValue(j)+"\n";
                }
                //Messages.showInfoMessage("TagNme : " + componentManager.getComponent(i).getTagName()+"\n"+str+" ","Attribute");
/*
                Messages.showInfoMessage("TagNme : " + componentManager.getComponent(i).getTagName()+"\n" +
                        "Id: "+componentManager.getComponent(i).componentId+"\n"+
                        "parentId: "+componentManager.getComponent(i).parentId,"Attribute");*/

            }


            for(int i=0;i<componentManager.size();i++){

                Messages.showInfoMessage("Id"+componentManager.getComponent(i).getId()+"\n"+
                        "width: "+getComponentWidthPoint(componentManager,componentManager.getComponent(i).getId(),"left")
                        +" Height: "+getComponentHeightPoint(componentManager,componentManager.getComponent(i).getId(),"top")
                        ,"Axis");
/*
                Messages.showInfoMessage("Id: "+componentManager.getComponent(i).componentId+"\n"+
                                " width: "+(getComponentWidthPoint(componentManager,componentManager.getComponent(i).getId(),"left"))
                        +" width length: "+componentManager.getComponent(i).getWidth()
                        ,"Axis");*/
/*
                Messages.showInfoMessage("Id"+componentManager.getComponent(i).componentId+"\n"+
                                "height: "+(getComponentHeightPoint(componentManager,componentManager.getComponent(i).getId(),"top"))
                                +"height length: "+componentManager.getComponent(i).getHeight()
                        ,"Axis");*/
            }

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
            stdWidth = component.textWidth;
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

        if(component.tagName.equals("RelativeLayout")){
            component.leftPoint=leftPoint;
            component.rightPoint=rightPoint;
        }
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
            stdHeight = component.textHeight;
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
        if(component.tagName.equals("RelativeLayout")){
            component.topPoint=topPoint;
            component.bottomPoint=bottomPoint;
        }
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