package Analysis.Play;

import javax.swing.*;
import java.awt.*;

/**
 * Created by woong on 2016-03-04.
 */
public class TestMainFrame extends JFrame {
    public TestMainFrame(){
        setSize(new Dimension(900, 400));

        init();

        setVisible(true);
    }

    private void init(){
        Container container = new Container();

        add(container);
    }
}
