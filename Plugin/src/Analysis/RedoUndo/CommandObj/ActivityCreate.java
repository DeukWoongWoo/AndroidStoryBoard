package Analysis.RedoUndo.CommandObj;

import Analysis.Constant.ConstantEtc;
import Analysis.Constant.SharedPreference;
import Analysis.Database.DatabaseManager.DatabaseManager;
import Analysis.Main.ProjectAnalysis;
import Analysis.RedoUndo.Util.ElementFactory;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.psi.*;
import com.intellij.psi.xml.XmlFile;
import com.intellij.psi.xml.XmlTag;

import java.io.File;

/**
 * Created by woong on 2016-02-22.
 */
public class ActivityCreate {
    private XmlFile xmlFile;

    private String activityName;

    private ElementFactory elementFactory = new ElementFactory();

    public ActivityCreate(String activityName){
        this.activityName = activityName;
        checkXmlFile();
    }

    public void create() {
        PsiClass psiclass = elementFactory.createPsiClass(activityName);

        new WriteCommandAction.Simple(psiclass.getProject(), psiclass.getContainingFile()) {
            @Override
            protected void run() throws Throwable {
                psiclass.getExtendsList().add(elementFactory.createPsiJavaCodeReferenceElement("Activity", psiclass));

                PsiJavaFile psiJavaFile = (PsiJavaFile) PsiManager.getInstance(psiclass.getProject()).findFile(psiclass.getContainingFile().getVirtualFile());

                PsiImportStatement activityImport = elementFactory.findPsiImportStatement("android.app", "Activity");
                if(activityImport != null) psiJavaFile.getImportList().add(activityImport);
                else Messages.showInfoMessage("Cannot find import file...","File Search");

                PsiImportStatement bundleImport = elementFactory.findPsiImportStatement("android.os", "Bundle");
                if(bundleImport != null) psiJavaFile.getImportList().add(bundleImport);
                else Messages.showInfoMessage("Cannot find import file...","File Search");

                StringBuilder builder = new StringBuilder("@Override\n");
                builder.append("protected void onCreate(Bundle savedInstanceState) {\n");
                builder.append("super.onCreate(savedInstanceState);\n");
                builder.append("setContentView(R.layout."+ activityName.toLowerCase() +");");
                builder.append("}\n");

                psiclass.add(elementFactory.createPsiMethod(builder.toString(), psiclass));


                xmlFile.getRootTag().findSubTags("application")[0].add(elementFactory.createActivityTag(activityName));
            }
        }.execute();

        syncProject();
    }

    private void checkXmlFile(){
        File file = new File(SharedPreference.PROJECT.get().getBasePath()+ ConstantEtc.PROJECT_XML_PATH +"/AndroidManifest.xml");
        xmlFile = (XmlFile) PsiManager.getInstance(SharedPreference.PROJECT.get()).findFile(LocalFileSystem.getInstance().findFileByIoFile(file));
    }

    private void syncProject() {
        ProjectAnalysis.getInstance(null).executeAll();
    }

    public void remove() {
        String path = DatabaseManager.getInstance().selectToJava(table->table.selectJava("name='"+activityName+"'")).get(0).getPath();
        File file = new File(path);
        for(XmlTag xmlTag : xmlFile.getRootTag().findSubTags("application")[0].findSubTags("activity")){
            String str = xmlTag.getAttributeValue("android:name");
            if(str.equals("."+activityName)){
                new WriteCommandAction.Simple(xmlFile.getProject(), xmlFile.getContainingFile()) {
                    @Override
                    protected void run() throws Throwable {
                        xmlTag.delete();
                        file.delete();
                    }
                }.execute();
            }
        }
    }
}
