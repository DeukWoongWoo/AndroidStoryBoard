package GUI.StoryBoard.UI;

import GUI.StoryBoard.storyBoard_func;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.IconLoader;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

/**
 * Created by 우철 on 2016-01-15.
 */
public class mainViewFactory implements ToolWindowFactory{
    private JPanel fullContent;
    private JToolBar maintoolbar;
    private JButton save_b;
    private JPanel toobarPanel;
    private JButton open_b;
    private JButton refresh_b;
    private JButton redo_b;
    private JButton undo_b;
    private JToolBar commandtoolbar;
    private JPanel mainContent;
    private JPanel rightContent;
    private JPanel leftContent;
    private JPanel topContent;
    private JPanel bottomContent;
    private JPanel centerContent;
    private ToolWindow mainViewWindow;

    public mainViewFactory() throws IOException {
        System.out.println("mainViewFactory!!");
        centerContent = new storyBoard_func();
        System.out.println(centerContent);
        centerContent.setVisible(true);

    }

    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        System.out.println("createToolWindowContent!!");

        mainViewWindow=toolWindow;
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        fullContent.setBackground(Color.darkGray);
        Content content = contentFactory.createContent(fullContent, "", false);
        toolWindow.getContentManager().addContent(content);

    }

}
