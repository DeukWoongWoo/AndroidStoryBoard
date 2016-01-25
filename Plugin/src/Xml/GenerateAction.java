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
import org.w3c.dom.*;
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
    String layout_belowOption = "layout_below";

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
/*
    class Component{
        String id;
        int margin[]=new int[6];
        int stdmargin[]=new int[4];
    }
*/




    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here

        Project project = e.getProject();
        String projectName = project.getName();
        Messages.showInfoMessage("TestParsing","TestParsing");
        try{
            String Filepath="C:/Users/cho/Desktop/android_project/MyApplication4/app/src/main/res/layout/content_main.xml";

            File ff = new File(Filepath);
            XmlPullParserFactory xppf = XmlPullParserFactory.newInstance();
            xppf.setNamespaceAware(true);
            XmlPullParser xpp = xppf.newPullParser();
            FileInputStream fis = new FileInputStream(ff);
            xpp.setInput(fis,null);

            ComponentManager componentManager=new ComponentManager();

            int type = xpp.getEventType();
            while(type != XmlPullParser.END_DOCUMENT){
                if(type == XmlPullParser.START_TAG) {

                    Component component = new Component();
                    component.setAttributes(xpp);
                    componentManager.addComponent(component);
                }
                else  if(type == XmlPullParser.END_TAG) {
                }
                    type = xpp.next();
            }

            for(int i=0;i<componentManager.size();i++){
                String str="";

                for(int j=0;j<componentManager.getComponent(i).getAttributeCount();j++){
                    str+=componentManager.getComponent(i).getAttributes(j)+" : "+componentManager.getComponent(i).getAttributesValue(j)+"\n";
                }
                Messages.showInfoMessage("Id : " + componentManager.getComponent(i).getId()+"\n"+str+" ","Attribute");
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


/*
    public void findMargin(ComponentManager componentManager){
        for(int i=0;i<componentManager.size();i++) {
            checkLayoutMargin(componentManager,componentManager.getComponent(i).getId());

        }
    }

    public Component componentIdCheck(ComponentManager componentManager,String id){
        for(int i=0;i<componentManager.size();i++){
            if(componentManager.getComponent(i).getId().equals(id))
                return componentManager.getComponent(i);
        }
        return null;
    }

    public void checkLayoutMargin(ComponentManager componentManager,String id){
        Component component= new Component();
        component = componentIdCheck(componentManager,id);

        for(int i=0;i<component.getAttributeCount();i++){
            for(int j=0;j<6;j++){
                if(layout_ParentOption[j].equals(component.getAttributes(i))){

                }else if(layout_alignOption[j].equals(component.getAttributes(i))){

                }else if(layout_toOption[j].equals(component.getAttributes(i))){

                }
            }
        }
    }
    public int getLayoutMargin(ComponentManager componentManager,String id,String Attribute){
        Component component= new Component();
        component = componentIdCheck(componentManager,id);

        for(int i=0;i<component.getAttributeCount();i++){
            for(int j=0;j<6;j++){
                if(layout_ParentOption[j].equals(component.getAttributes(i))){
                    //getMargin(component,j,layout_ParentOption[j]);
                }else if(layout_alignOption[j].equals(component.getAttributes(i))){
                   // getMargin(component,j,layout_alignOption[j]);
                }else if(layout_toOption[j].equals(component.getAttributes(i))){
                    //getMargin(component,j,layout_toOption[j]);
                }
            }
        }
    }


    public int checkParent(Component component){
        for(int i=0;i<component.getAttributeCount();i++){
            for(int j=0;j<6;j++){
                if(layout_ParentOption[j].equals(component.getAttributes(i))) {
                    return j;
                }
            }
        }
        return 7;
    }

    public int getRealMargin(ComponentManager componentManager,String id){

        int index =0 ;
        int marginIndex;
        Component component= new Component();
        component = componentIdCheck(componentManager,id);

        //id를 가지고 있지 않는다면... 즉 Parent?
        if((marginIndex=checkParent(component)) <7){
            for(int i=0;i<component.getAttributeCount();i++){
                if(layout_ParentOption[marginIndex].equals(component.getAttributesValue(i))){
                    int dp = changeDpToInt(component.getAttributesValue(i));
                    if(marginIndex==4)
                       dp=384-dp;
                    component.setStdMargin(i,0);
                    component.setMargin(i,dp);
                    component.setRealMargin(i,dp);
                    return dp;
                }
            }
        }
        //id를 가지고 있으면
        else{
            for(int i=0;i<component.getAttributeCount();i++){
                for(int j=0;j<6;j++){
                    if(layout_alignOption[j].equals(component.getAttributes(i))){
                        int dp;
                        dp = getRealMargin(componentManager,component.getAttributesValue(i));

                    }

                }
            }
            getRealMargin(componentManager,)
        }



        component.setStdMargin(index,id);
    }

    public int getMargin(Component component,int index,String type){

        if(type.equals(layout_toOption[index])){
            if(index==2)
                index=4;
            else
                index=2;
        }

        for(int i=0;i<component.getAttributeCount();i++) {
                if (layout_Margin[index].equals(component.getAttributes(i))) {
                    return changeDpToInt(component.getAttributesValue(i));
                }
        }

        return 0;
    }
*/





/*

    public static int changeDpToInt(String value){
        int dp=0;
        char val[] = value.toCharArray();
        for(int i=0;val[i]!='d';i++)
            dp =dp*10+ val[i]-'0';
        return dp;
    }*/
/*
    private int checkLayoutMargin(String value) {
        for(int i=0;i<layout_Margin.length;i++){
            if(layout_Margin[i].equals(value)){
                return changeDpToInt(value);
            }
        }
        return 0;

    }

    private void checkLayoutParentOption(String layout_option,Component component) {
        for(int i=0;i<layout_ParentOption.length;i++) {
            if(layout_ParentOption[i].equals(layout_option)){
                component.stdmargin[i]=0;
                //component.margin[i]=component.stdmargin[i]+checkLayoutMargin(id);
            }
        }
    }

    private void checkLayoutAlignOption(String layout_option,Component component) {
        for(int i=0;i<layout_alignOption.length;i++) {
            if(layout_alignOption[i].equals(layout_option)){
                //component.stdmargin[i]= (component.getAttribute.value).margin;(기준선 재정의)
                component.margin[i]=component.stdmargin[i];


            }
        }
    }

    private void checkLayoutToOption(String layout_option,Component component) {
        for(int i=0;i<layout_toOption.length;i++) {
            if(layout_toOption[i].equals(layout_option)){
                //component.stdmargin[i]= (component.getAttribute.value).margin;(기준선 재정의)
                component.margin[i]=component.stdmargin[i];
            }
        }
    }

    private void checkLayoutBelowOption(String layout_option,Component component) {
            if(layout_belowOption.equals(layout_option)){
                //component.stdmargin[top]=(component.getAttribut.value).margin
                //component.margin[top]=component.stdmargin[top];
            }
    }
*/



    public static void listAllAttributes(Element element)
    {
        //Messages.showInfoMessage("TagName : "+ element.getNodeName(),"TagName");

        NamedNodeMap attributes = element.getAttributes();
        int numAttrs = attributes.getLength();

        for(int i = 0;i<numAttrs;i++){
            Attr attr = (Attr) attributes.item(i);
            String attrName = attr.getNodeName();
            String attrValue = attr.getValue();
            //Messages.showInfoMessage("Attribute : "+attrName+" Value : "+attrValue+" " ,"Attribute");
        }
    }


    private static void printNode(NodeList nodeList, int level) {
        level++;
        if (nodeList != null && nodeList.getLength() > 0) {
            Messages.showInfoMessage("NodeList Legth is  "+nodeList.getLength(),"NodeLength");

            for (int i = 0; i < nodeList.getLength(); i++) {

                Node node = nodeList.item(i);
                Messages.showInfoMessage("Node Type "+node.getNodeType() +" node Name " +node.getNodeName()+" ","NodeType");
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Messages.showInfoMessage(node.getNodeName() + "[" + level + "]","Depth");
                    printNode(node.getChildNodes(), level);

                    // how depth is it?
                    if (level > depthOfXML) {
                        depthOfXML = level;
                    }
                }
            }
        }
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