package Analysis;

import Analysis.Database.StoryBoardDAO;
import com.intellij.openapi.actionSystem.AnAction;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.vfs.VirtualFile;
import com.intellij.psi.*;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.xml.XmlFile;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by woong on 2015-12-22.
 */
public class MyActionClass extends AnAction {
    private String intellijPath = "/src";
    @Override
    public void actionPerformed(AnActionEvent e) {
        // TODO: insert action logic here
//        ProjectAnalysis projectAnalysis = ProjectAnalysis.getInstance(e, intellijPath);
//        projectAnalysis.execute(intellijPath, Constant.XML_PATTERN);

        StoryBoardDAO storyBoardDAO = new StoryBoardDAO();
        storyBoardDAO.onUpdate("test");

//        ProjectAnalysis projectAnalysis1 = ProjectAnalysis.getInstance(null,null);
//        projectAnalysis.execute(Constant.PROJECT_JAVA_PATH, Constant.JAVA_PATTERN);

//        PluginTest test = new PluginTest(e);

    }

}
