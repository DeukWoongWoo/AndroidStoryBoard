package Analysis.RedoUndo.CommandObj;

import Analysis.Constant.ConstantEtc;
import Analysis.Constant.SharedPreference;
import Analysis.Main.ProjectAnalysis;
import Analysis.RedoUndo.CodeBuilder.Type;
import Analysis.RedoUndo.CommandKey;
import Analysis.RedoUndo.Util.ElementFactory;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.ui.Messages;
import com.intellij.openapi.vfs.LocalFileSystem;
import com.intellij.psi.*;
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

                PsiImportStatement psiImportStatement = elementFactory.findPsiImportStatement("import android.app.Activity;", Type.Button.name());
                if(psiImportStatement != null) psiJavaFile.getImportList().add(psiImportStatement);
                else Messages.showInfoMessage("Cannot find import file...","File Search");

                StringBuilder builder = new StringBuilder("@Override\n");
                builder.append("protected void onCreate(Bundle savedInstanceState) {\n");
                builder.append("super.onCreate(savedInstanceState);\n");
                builder.append("setContentView(R.layout."+ CommandKey.ACTIVITY.getId().toLowerCase() +");");
                builder.append("}\n");

                psiclass.add(elementFactory.createPsiMethod(builder.toString(), psiclass));

                File file = new File(psiclass.getProject().getBasePath()+ ConstantEtc.INTELLIJ_PATH +"/AndroidManifest.xml");
                XmlFile xmlfile = (XmlFile) PsiManager.getInstance(SharedPreference.ACTIONEVENT.getData().getProject()).findFile(LocalFileSystem.getInstance().findFileByIoFile(file));
                xmlfile.getRootTag().findSubTags("application")[0].add(elementFactory.createActivityTag(CommandKey.ACTIVITY.getId()));
            }
        }.execute();

        syncProject();
    }

    private void syncProject() {
        ProjectAnalysis.getInstance(null,null).executeAll();
    }

    public void remove() {

    }
}
