package Analysis.Play;

import Analysis.Play.Contents.PlayContent;

import javax.swing.*;
import java.awt.*;

/**
 * Created by woong on 2016-03-04.
 */
public class Container extends JPanel {
    public Container(){
        setLayout(new BorderLayout());

        init();
    }

    private void init(){
        Header header = new Header();

        add(header, BorderLayout.NORTH);
        add(new PlayContent(), BorderLayout.CENTER);
    }
}
