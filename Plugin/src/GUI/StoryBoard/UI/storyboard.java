package GUI.StoryBoard.UI;

import com.intellij.util.ui.CenteredIcon;

import javax.swing.*;
import java.awt.*;

/**
 * Created by 우철 on 2016-01-20.
 */
public class storyboard{
    private JPanel storyBoard;
    private JPanel mainContent;
    private JPanel buttonGroup;
    private JButton plus_b;
    private JButton link_b;
    private JButton remove_b;
    private JPanel mainview;

    public storyboard(){
        StoryBoard_function storyboard_p = new StoryBoard_function();

        System.out.println("storyboard : " + storyboard_p);

        mainview.add(storyboard_p);
        plus_b.add(storyboard_p.plus_button);
        System.out.println("After Add ");
    }

}
