package Analysis.Parser;

import Analysis.Constant;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.PsiFile;
import com.intellij.psi.xml.XmlFile;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.xml.XmlTag;

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
        XmlTag xmlTag = xmlFile.getRootTag().findSubTags("application")[0];
        for(XmlTag subTag : xmlTag.findSubTags("activity")){
            System.out.println("subTag Content " + subTag.getAttributeValue("android:name"));
            if(subTag.findSubTags("intent-filter").length != 0){
                String str = subTag.findSubTags("intent-filter")[0].findSubTags("action")[0].getAttributeValue("android:name");
                System.out.println("action name : "+str);
            }
            System.out.println("getStartOffsetInParent : " + subTag.getStartOffsetInParent());
            System.out.println("getTextRange().getLength() : "+subTag.getTextRange().getLength());
            System.out.println("getTextRange().getStartOffset()() : "+subTag.getTextRange().getStartOffset());
            System.out.println("getTextRange().getEndOffset()() : "+subTag.getTextRange().getEndOffset());
            FileDocumentManager fdm = FileDocumentManager.getInstance();
            Document doc = fdm.getCachedDocument(xmlFile.getVirtualFile());
            int startLine = doc.getLineNumber(subTag.getTextRange().getStartOffset());
            int endLine = doc.getLineNumber(subTag.getTextRange().getEndOffset());
            System.out.println("Start getLineNumber : "+ (startLine+1));
            System.out.println("total : "+(endLine - startLine));
            System.out.println("col : "+(subTag.getTextRange().getStartOffset() - doc.getLineStartOffset(startLine)));
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
