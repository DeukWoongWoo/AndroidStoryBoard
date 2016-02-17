package GUI.StoryBoard;

import GUI.StoryBoard.UI.*;
import javax.swing.*;
import java.io.IOException;

/**
 * Created by 우철 on 2016-02-17.
 */
public class example extends JFrame {

    public example() throws IOException {
        palettePanel a = new palettePanel();
        this.setSize(1000, 1000);
        this.add(a);
        this.setVisible(true);
    }
    public static void main(String[] args) throws IOException {
        example a =new example();

    }

}
