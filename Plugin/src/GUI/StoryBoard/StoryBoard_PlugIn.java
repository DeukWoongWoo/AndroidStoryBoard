package GUI.StoryBoard;

import Analysis.Constant.ConstantEtc;
import Analysis.Constant.SharedPreference;
import Analysis.Main.ProjectAnalysis;
import Analysis.RedoUndo.CodeDriver;
import GUI.StoryBoard.UI.*;
import Xml.XmlToJson;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.wm.ToolWindow;
import com.intellij.openapi.wm.ToolWindowFactory;
import com.intellij.ui.content.Content;
import com.intellij.ui.content.ContentFactory;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created by 우철 on 2016-02-17.
 */
public class StoryBoard_PlugIn implements ToolWindowFactory {
    private JPanel totalPanel;
    private JPanel centerPanel;
    private JPanel eastPanel;
    private JPanel northPanel;

    private ToolWindow mainViewWindow;

    public static Project project;

    public StoryBoard_PlugIn() throws IOException {
        System.out.println("StoryBoard_Plugin...");
        totalPanel = new JPanel();


    }

    @Override
    public void createToolWindowContent(@NotNull Project project, @NotNull ToolWindow toolWindow) {
        System.out.println("createToolWindowContent...");
        mainViewWindow = toolWindow;

        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        Content content = contentFactory.createContent(totalPanel, "", false);
        toolWindow.getContentManager().addContent(content);

        initProjectAnalysis(project);
//        try {
//            init();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }

    private void initProjectAnalysis(Project project) {
        SharedPreference.PROJECT.set(project);
//        ProjectAnalysis projectAnalysis = ProjectAnalysis.getInstance(ConstantEtc.INTELLIJ_PATH);
        ProjectAnalysis projectAnalysis = ProjectAnalysis.getInstance(ConstantEtc.PROJECT_XML_PATH);
        projectAnalysis.executeAll();
    }


    public void init() throws IOException {
        XmlToJson xmlToJson = new XmlToJson();
        xmlToJson.make();
        centerPanel = new storyBoard();
        northPanel = new menuPanel();
        totalPanel.setLayout(new BorderLayout());


        totalPanel.add(centerPanel, "Center");
        totalPanel.add(northPanel, "North");
    }
}
