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
import com.intellij.psi.xml.XmlDocument;
import com.sun.jna.platform.win32.COM.COMUtils;
import org.w3c.dom.*;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
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
            String Filepath="C:/Users/cho/Desktop/android_project/MyApplication4/app/src/main/res/layout/content_main.xml";

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
            while(type != XmlPullParser.END_DOCUMENT){

                if(type == XmlPullParser.START_TAG) {

                    Component component = new Component();
                    component.setAttributes(xpp);
                    component.setPadding(padding);
                    componentManager.addComponent(component);
                }
                else  if(type == XmlPullParser.END_TAG) {
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
                Messages.showInfoMessage("TagNme : " + componentManager.getComponent(i).getTagName()+"\n"+str+" ","Attribute");
            }


            for(int i=0;i<componentManager.size();i++){
                if(componentManager.getComponent(i).getTagName().equals("Button"))
               Messages.showInfoMessage("TagNme : " + componentManager.getComponent(i).getTagName()+"\n"+
                        "Axis:"+getHeightRealValue(componentManager,componentManager.getComponent(i).getId())+" \n","Axis");
               // Messages.showInfoMessage("TagNme : " + componentManager.getComponent(i).getTagName()+"\n"+
                //"Width:"+componentManager.getComponent(i).getWidth()+" \n","Axis");
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
    public int getRealValue(ComponentManager componentManager,String id){
        int stdValue=0;
        int dp =0;
        int margin =0;
        Component component = new Component();
        for(int i=1;i<componentManager.size();i++){
            if(componentManager.getComponent(i).getId().equals(id)){
                component = componentManager.getComponent(i);
                break;
            }
        }

        for(int i=0;i<component.getAttributeCount();i++){
            if(layout_ParentOption[2].equals(component.getAttributes(i)))
                stdValue=0;
            else if(layout_ParentOption[4].equals(component.getAttributes(i)))
                stdValue=768;
        }

        for(int i=0;i<component.getAttributeCount();i++){
            if(layout_alignOption[2].equals(component.getAttributes(i))){
                String getId = component.getAttributesValue(i);
                stdValue=getRealValue(componentManager,getId);
            }
            else if(layout_alignOption[4].equals(component.getAttributes(i))){
                String getId = component.getAttributesValue(i);
                stdValue=getRealValue(componentManager,getId);

                for(int j=1;j<componentManager.size();j++){
                    if(componentManager.getComponent(j).getId().equals(getId)){
                        stdValue=stdValue+componentManager.getComponent(j).getWidth();
                        break;
                    }
                }
            }
        }

        for(int i=0;i<component.getAttributeCount();i++){
            if(layout_toOption[2].equals(component.getAttributes(i))){
                String getId = component.getAttributesValue(i);
                stdValue=getRealValue(componentManager,getId);
            }
            else if(layout_toOption[4].equals(component.getAttributes(i))){
                String getId = component.getAttributesValue(i);
                stdValue=getRealValue(componentManager,getId);

                for(int j=1;j<componentManager.size();j++){
                    if(componentManager.getComponent(j).getId().equals(getId)){
                        stdValue=stdValue+componentManager.getComponent(j).getWidth();
                        break;
                    }
                }
            }
        }


        for(int i=0;i<component.getAttributeCount();i++){
            if(layout_Margin[2].equals(component.getAttributes(i))){
                margin = changeDpToInt(component.getAttributesValue(i))*2;
            }
            if(layout_Margin[4].equals(component.getAttributes(i))){
                margin = (changeDpToInt(component.getAttributesValue(i))*2)+component.getWidth();
                margin = -1*margin;
            }
        }
        dp = stdValue+margin;
        return dp;
    }


    public int getHeightRealValue(ComponentManager componentManager,String id){
        int stdValue=0;
        int dp =0;
        int margin =0;
        Component component = new Component();
        for(int i=1;i<componentManager.size();i++){
            if(componentManager.getComponent(i).getId().equals(id)){
                component = componentManager.getComponent(i);
                break;
            }
        }

        for(int i=0;i<component.getAttributeCount();i++){
                if(layout_ParentOption[0].equals(component.getAttributes(i)))
                    stdValue=0;
                else if(layout_ParentOption[1].equals(component.getAttributes(i)))
                    stdValue=1280;
        }

        for(int i=0;i<component.getAttributeCount();i++){
            if(layout_HeigtOption[0].equals(component.getAttributes(i))
                    || layout_alignOption[0].equals(component.getAttributes(i))){//above & alignTop
                String getId = component.getAttributesValue(i);
                stdValue=getRealValue(componentManager,getId);

            }else if(layout_HeigtOption[1].equals(component.getAttributes(i))
                    || layout_alignOption[1].equals(component.getAttributes(i))){//below & alignBottom
                String getId = component.getAttributesValue(i);
                stdValue=getRealValue(componentManager,getId);
                for(int j=1;j<componentManager.size();j++){
                    if(componentManager.getComponent(j).getId().equals(getId)){
                        stdValue=stdValue+componentManager.getComponent(j).getHeight();
                        break;
                    }
                }
            }
        }


        for(int i=0;i<component.getAttributeCount();i++){
                if(layout_Margin[0].equals(component.getAttributes(i))){
                    margin = changeDpToInt(component.getAttributesValue(i))*2;
                }
                if(layout_Margin[1].equals(component.getAttributes(i))){
                    margin = (changeDpToInt(component.getAttributesValue(i))*2)+component.getHeight();
                    margin = -1*margin;
                }
        }
        dp = stdValue+margin;
        return dp;
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