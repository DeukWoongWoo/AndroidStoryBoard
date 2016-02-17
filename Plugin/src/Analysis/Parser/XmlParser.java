package Analysis.Parser;

import Analysis.Constant.ConstantEtc;
import Analysis.Database.DatabaseManager.DatabaseManager;
import Analysis.Database.DtatTransferObject.ActivityDTO;
import Analysis.Database.DtatTransferObject.ManifestDTO;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.psi.PsiFile;
import com.intellij.psi.xml.XmlFile;
import com.intellij.openapi.ui.Messages;
import com.intellij.psi.xml.XmlTag;


/**
 * Created by woong on 2016-01-14.
 */
public class XmlParser implements FileParser {

    private XmlFile xmlFile;

    private ManifestDTO manifestDTO = new ManifestDTO();

    public XmlParser(PsiFile psiFile){
        this.xmlFile = (XmlFile)psiFile;
    }

    @Override
    public void parsing() {
        makeProjectJavaPath();

        XmlTag xmlTag = xmlFile.getRootTag().findSubTags("application")[0];
        activityTag(xmlTag);

    }

    private void activityTag(XmlTag xmlTag) {
        ActivityDTO activityDTO = new ActivityDTO();

        for(XmlTag subTag : xmlTag.findSubTags("activity")){
            activityDTO.setName(subTag.getAttributeValue("android:name"));

            if(subTag.findSubTags("intent-filter").length != 0){
                String str = subTag.findSubTags("intent-filter")[0].findSubTags("action")[0].getAttributeValue("android:name");
//                System.out.println("action name : "+str);
                activityDTO.setAction(1);
            }else activityDTO.setAction(0);

            FileDocumentManager fdm = FileDocumentManager.getInstance();
            Document doc = fdm.getCachedDocument(xmlFile.getVirtualFile());

            int startLine = doc.getLineNumber(subTag.getTextRange().getStartOffset()) + 1;
            int endLine = doc.getLineNumber(subTag.getTextRange().getEndOffset());
            int totalLine = endLine - startLine + 2;

            activityDTO.setStartLine(startLine);
            activityDTO.setTotalLine(totalLine);

            DatabaseManager.getInstance().insertToManifest(table->table.insertActivity(activityDTO));
        }
    }

    private void makeProjectJavaPath() {
        String packagePath = xmlFile.getRootTag().getAttributeValue("package");

        manifestDTO.setPackageName(packagePath);
        DatabaseManager.getInstance().insertToManifest(table -> table.insertManifest(manifestDTO));

        String str[] = packagePath.split("\\.");
        String strPath = "/java";
        for(int i=0;i<str.length;i++)
            strPath += "/" + str[i];
        if(ConstantEtc.PROJECT_JAVA_PATH == null) ConstantEtc.PROJECT_JAVA_PATH = strPath;
        Messages.showInfoMessage("makeJavaPath : " + ConstantEtc.PROJECT_JAVA_PATH,"test");
    }
}
