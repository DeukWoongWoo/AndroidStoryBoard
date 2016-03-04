package Analysis.Play;

import javax.swing.*;
import java.awt.*;

/**
 * Created by woong on 2016-03-04.
 */
public class Header extends JPanel {
    public Header(){
        setLayout(new FlowLayout());

        init();
    }

    private void init(){
        JButton analysisButton = new JButton("Analysis");
        JButton redoButton = new JButton("Redo");
        JButton undoButton = new JButton("Undo");

        add(analysisButton);
        add(redoButton);
        add(undoButton);
    }
}
