package Analysis.Play.Contents;

import com.intellij.ide.util.importProject.LibrariesLayoutPanel;

import javax.swing.*;
import java.awt.*;

/**
 * Created by woong on 2016-03-04.
 */
public class PlayContent extends JPanel{
    public PlayContent(){
        setLayout(new GridBagLayout());

        init();
    }

    private void init(){
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;

        add(new ComponentPanel(), gbc);
        add(new ActivityCreatePanel());
        add(new ActivityLinkPanel());
        add(new LibraryPanel());
    }
}
