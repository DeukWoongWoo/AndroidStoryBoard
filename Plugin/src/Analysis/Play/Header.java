package Analysis.Play;

import Analysis.Main.ProjectAnalysis;
import Analysis.RedoUndo.CommandManager;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by woong on 2016-03-04.
 */
public class Header extends JPanel {
    private CommandManager commandManager;

    public Header(){
        setLayout(new FlowLayout());
        commandManager = CommandManager.getInstance();
        init();
    }

    private void init(){
        JButton analysisButton = new JButton("Analysis");
        analysisButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ProjectAnalysis.getInstance(null).executeAll();
            }
        });

        JButton redoButton = new JButton("Redo");
        redoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commandManager.redo();
            }
        });

        JButton undoButton = new JButton("Undo");
        undoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                commandManager.undo();
            }
        });

        add(analysisButton);
        add(redoButton);
        add(undoButton);
    }
}
