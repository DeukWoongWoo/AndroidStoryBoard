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

//        Stream<XmlTag> activityTag = Stream.of(xmlTag.findSubTags("activity"));
//        activityTag.filter(subTag -> subTag.findSubTags("intent-filter").length != 0)
//                .forEach(subTag -> System.out.println(subTag.findSubTags("intent-filter")[0].findSubTags("action")[0].getAttributeValue("android:name")));
    }

    private void activityTag(XmlTag xmlTag) {
        ActivityDTO activityDTO = new ActivityDTO();

        for(XmlTag subTag : xmlTag.findSubTags("activity")){
//            System.out.println("subTag Content " + subTag.getAttributeValue("android:name"));
            activityDTO.setName(subTag.getAttributeValue("android:name"));

            if(subTag.findSubTags("intent-filter").length != 0){
                String str = subTag.findSubTags("intent-filter")[0].findSubTags("action")[0].getAttributeValue("android:name");
//                System.out.println("action name : "+str);
                activityDTO.setAction(1);
            }else activityDTO.setAction(0);

            FileDocumentManager fdm = FileDocumentManager.getInstance();
            Document doc = fdm.getCachedDocument(xmlFile.getVirtualFile());

            int startLine = doc.getLineNumber(subTag.getTextRange().getStartOffset());
            int endLine = doc.getLineNumber(subTag.getTextRange().getEndOffset());
            int totalLine = endLine - startLine;

//            System.out.println("Start getLineNumber : "+ (startLine+1));
//            System.out.println("total : "+totalLine+1);

            activityDTO.setStartLine((startLine+1));
            activityDTO.setTotalLine(totalLine+1);

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
        ConstantEtc.PROJECT_JAVA_PATH = strPath;
//        System.out.println("XmlParser javaPath : "+ ConstantEtc.PROJECT_JAVA_PATH);
//        Messages.showInfoMessage("javaPath : "+ ConstantEtc.PROJECT_JAVA_PATH,"XmlParser");
    }
}
