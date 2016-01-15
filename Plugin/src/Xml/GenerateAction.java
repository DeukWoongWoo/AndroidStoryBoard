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
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlFile;
import org.w3c.dom.*;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
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

    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here

        Project project = e.getProject();
        String projectName = project.getName();
        Messages.showInfoMessage("TestParsing","TestParsing");

        try{

            /* 파일 경로 찾는 소스코드
            String IntellijPath = "/src";
            VirtualFile IntellijPathFile = e.getProject().getBaseDir().findFileByRelativePath(IntellijPath);

            PsiDirectory psiDirectory = PsiManager.getInstance(e.getProject()).findDirectory(IntellijPathFile);

            Messages.showInfoMessage( e.getProject().getBasePath(),"getProject Path");
            Messages.showInfoMessage( psiDirectory.getName(),"Directory Path");
            PsiFile psiFile[] = psiDirectory.getFiles();
            for(int i=0;i<psiFile.length;i++) {
                Messages.showInfoMessage("PsiFileName !! : " + psiFile[i].getName(),"Project Path");
            }
            */

            String Filepath="C:/Users/cho/Desktop/android_project/MyApplication4/app/src/main/res/layout/content_main.xml";

            File ff = new File(Filepath);

            /*
            VirtualFile virtualFile = LocalFileSystem.getInstance().findFileByIoFile(ff);
            Messages.showInfoMessage("Success1","Success1");
            PsiFile psiff = PsiManager.getInstance(project).findFile(virtualFile);
            Messages.showInfoMessage("Success2","Success2");

            XmlFile xf = (XmlFile) psiff;

            if(xf == null)
                Messages.showInfoMessage("Xml null","Xml null");
            */
            DocumentBuilderFactory f= DocumentBuilderFactory.newInstance();
            DocumentBuilder parser = f.newDocumentBuilder();
            Document  XmlDoc= parser.parse(ff);
            Element root = XmlDoc.getDocumentElement();
            int level=1;

            XmlDoc.getDocumentElement().normalize();
            NodeList nList = XmlDoc.getElementsByTagName("*");


            Messages.showInfoMessage(nList.getLength()+" ","nList Length");


            for(int temp =0;temp<nList.getLength();temp++)
            {
                Element nNode = (Element) nList.item(temp);
                //Messages.showInfoMessage(nNode.getTagName(),"TagName");
                listAllAttributes(nNode);

                //추가방법1

                if(temp ==3)
                {
                    Node staff = nList.item(temp);
                    Element bt= XmlDoc.createElement("ButtonA");
                    Attr asd = XmlDoc.createAttribute("android:id");
                    asd.setValue("123");
                    bt.setAttributeNode(asd);
                    staff.appendChild(bt);
                }
            }
            Messages.showInfoMessage(nList.getLength()+" ","new nList Length");
/*
            NodeList nodeList = root.getChildNodes();
            printNode(nodeList,level);
            Messages.showInfoMessage(depthOfXML+"","Deepest Level");
*/

/*
            Node staff = XmlDoc.getElementsByTagName("RelativeLayout").item(0);
            Element bt= XmlDoc.createElement("ButtonA");
            Attr asd = XmlDoc.createAttribute("android:id");
            asd.setValue("123");
            bt.setAttributeNode(asd);
            staff.appendChild(bt);
*/
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(XmlDoc);
            StreamResult result = new StreamResult(new File(Filepath));
            transformer.transform(source,result);

        }catch(Exception e2){

            Messages.showInfoMessage("error1","error1");
        }

        StringBuilder sourceRootsList = new StringBuilder();
        VirtualFile[] vFiles = ProjectRootManager.getInstance(project).getContentSourceRoots();

        for (VirtualFile file : vFiles) {
            sourceRootsList.append(file.getUrl()).append("\n");

        }


        PsiClass psiClass=getPsiClassFromContext(e);
        GenerateDialog dlg=new GenerateDialog(psiClass );
        dlg.show();
        if(dlg.isOK()){
            generateComparable(psiClass, dlg.getFields());
        }


    }

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