package GUI.StoryBoard;

import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;

import javax.swing.*;

/**
 * Created by 우철 on 2016-01-15.
 */
public class mainViewFactory implements ToolWindowFactory{
    private JPanel mainContent;
    private JButton button1;
    private ToolWindow mainViewWindow;


    public mainViewFactory(){
        System.out.println("mainViewFactory!!");
        IconLoader.findIcon("/icon/star.png");
    }


    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        System.out.println("createToolWindowContent!!");
        mainViewWindow=toolWindow;
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(mainContent, "", false);
        toolWindow.getContentManager().addContent(content);
    }

}
