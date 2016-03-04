package Analysis;

import javax.swing.*;
import java.awt.*;

import static java.awt.BorderLayout.*;

/**
 * Created by woong on 2016-03-04.
 */
public class PluginTest extends JFrame {
    private JPanel container;
    private JButton analysisButton;
    private JButton redoButton;
    private JButton undoButton;
    private JSpinner spinner1;
    private JTextField textField1;
    private JPanel header;
    private JPanel content;
    private JPanel ComponentPanel;

    public PluginTest() {
        setSize(new Dimension(1000, 500));

        container = new JPanel();
        container.setLayout(new BorderLayout());

        header = new JPanel();
        header.setLayout(new FlowLayout());

        analysisButton = new JButton("Analysis");
        header.add(analysisButton);

        redoButton = new JButton("Redo");
        header.add(redoButton);

        undoButton = new JButton("Undo");
        header.add(undoButton);

        content = new JPanel();
        content.setLayout(new GridBagLayout());

        container.add(header, NORTH);
        container.add(content, CENTER);

        ComponentPanel = new JPanel();
        ComponentPanel.setLayout(new GridLayout(1, 4));

        content.add(ComponentPanel);


        add(container);
        setVisible(true);
    }
}
