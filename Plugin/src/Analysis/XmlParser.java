package Analysis;

import com.intellij.psi.PsiFile;
import com.intellij.psi.xml.XmlFile;
import com.intellij.openapi.ui.Messages;

/**
 * Created by woong on 2016-01-14.
 */
public class XmlParser implements Parser {

    private XmlFile xmlFile;

    public XmlParser(PsiFile psiFile){
        this.xmlFile = (XmlFile)psiFile;
    }

    @Override
    public void parsing() {
        if(Constant.PROJECT_JAVA_PATH==null) {
            makeProjectJavaPath();
        }
    }

    private void makeProjectJavaPath() {
        String packagePath = xmlFile.getRootTag().getAttributeValue("package");
        String str[] = packagePath.split("\\.");
        String strPath = "/java";
        for(int i=0;i<str.length;i++)
            strPath += "/" + str[i];
        Constant.PROJECT_JAVA_PATH = strPath;
        System.out.println("XmlParser javaPath : "+Constant.PROJECT_JAVA_PATH);
        Messages.showInfoMessage("javaPath : "+Constant.PROJECT_JAVA_PATH,"XmlParser");
    }
}
