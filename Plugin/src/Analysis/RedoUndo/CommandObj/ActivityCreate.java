package Analysis.RedoUndo.CommandObj;

import Analysis.Constant.ConstantEtc;
import Analysis.Constant.SharedPreference;
import Analysis.Main.ProjectAnalysis;
import Analysis.RedoUndo.CodeBuilder.Type;
import Analysis.RedoUndo.CommandKey;
import Analysis.RedoUndo.Util.ElementFactory;
import com.intellij.application.options.XmlCodeStyleMainPanel;
import com.intellij.application.options.XmlCodeStyleSettingsProvider;
import com.intellij.application.options.XmlLanguageCodeStyleSettingsProvider;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.CodeStyleManager;
import com.intellij.psi.formatter.xml.XmlCodeStyleSettings;
import com.intellij.psi.xml.XmlFile;

import java.io.File;

/**
 * Created by woong on 2016-02-22.
 */
public class ActivityCreate {

    public void create() {
        ElementFactory elementFactory = new ElementFactory();
        PsiClass psiclass = elementFactory.createPsiClass(CommandKey.ACTIVITY.getId());

        new WriteCommandAction.Simple(psiclass.getProject(), psiclass.getContainingFile()) {
            @Override
            protected void run() throws Throwable {
                psiclass.getExtendsList().add(elementFactory.createPsiJavaCodeReferenceElement("Activity", psiclass));

                PsiJavaFile psiJavaFile = (PsiJavaFile) PsiManager.getInstance(psiclass.getProject()).findFile(psiclass.getContainingFile().getVirtualFile());

                PsiImportStatement activityImport = elementFactory.findPsiImportStatement("import android.app", "Activity");
                if(activityImport != null) psiJavaFile.getImportList().add(activityImport);
                else Messages.showInfoMessage("Cannot find import file...","File Search");

                PsiImportStatement bundleImport = elementFactory.findPsiImportStatement("import android.os", "Bundle");
                if(bundleImport != null) psiJavaFile.getImportList().add(bundleImport);
                else Messages.showInfoMessage("Cannot find import file...","File Search");

                StringBuilder builder = new StringBuilder("@Override\n");
                builder.append("protected void onCreate(Bundle savedInstanceState) {\n");
                builder.append("super.onCreate(savedInstanceState);\n");
                builder.append("setContentView(R.layout."+ CommandKey.ACTIVITY.getId().toLowerCase() +");");
                builder.append("}\n");

                psiclass.add(elementFactory.createPsiMethod(builder.toString(), psiclass));

                File file = new File(psiclass.getProject().getBasePath()+ ConstantEtc.PROJECT_XML_PATH +"/AndroidManifest.xml");
                XmlFile xmlfile = (XmlFile) PsiManager.getInstance(SharedPreference.PROJECT.get()).findFile(LocalFileSystem.getInstance().findFileByIoFile(file));
                xmlfile.getRootTag().findSubTags("application")[0].add(elementFactory.createActivityTag(CommandKey.ACTIVITY.getId()));
            }
        }.execute();

        syncProject();
    }

    private void syncProject() {
        ProjectAnalysis.getInstance(null).executeAll();
    }

    public void remove() {

    }
}
