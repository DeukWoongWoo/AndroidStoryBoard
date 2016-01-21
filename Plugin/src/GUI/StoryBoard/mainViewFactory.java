package GUI.StoryBoard;

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

/**
 * Created by 우철 on 2016-01-15.
 */
public class mainViewFactory implements ToolWindowFactory, ActionListener{
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

    public mainViewFactory() {
        System.out.println("mainViewFactory!!");
        IconLoader.findIcon("/icon/star.png");

        ///////////////////////////////////////////////
        ///   이벤트 등록하기
        ///////////////////////////////////////////////////
        //                 maintoolbar
        ///////////////////////////////////////////////////
        open_b.addActionListener(this);
        save_b.addActionListener(this);
        refresh_b.addActionListener(this);
        ///////////////////////////////////////////////////
        //                 commandtoolbar
        ///////////////////////////////////////////////////
        undo_b.addActionListener(this);
        redo_b.addActionListener(this);

    }

    public void createToolWindowContent(Project project, ToolWindow toolWindow) {
        System.out.println("createToolWindowContent!!");

        mainViewWindow=toolWindow;
        ContentFactory contentFactory = ContentFactory.SERVICE.getInstance();
        fullContent.setBackground(Color.darkGray);
        Content content = contentFactory.createContent(fullContent, "", false);
        toolWindow.getContentManager().addContent(content);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==open_b){

        }
        else if(e.getSource()==save_b)
        {

        }
        else if(e.getSource()==refresh_b)
        {

        }
        else if(e.getSource()==redo_b)
        {

        }
        else if(e.getSource()==undo_b)
        {

        }

    }
}
