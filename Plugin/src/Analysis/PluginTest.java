package Analysis;

import com.intellij.ide.util.DefaultPsiElementCellRenderer;
import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.ui.CollectionListModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Created by woong on 2015-12-22.
 */
public class PluginTest extends JFrame implements ActionListener {
    private CollectionListModel<PsiField> myFields;

    private JPanel panel1;
    private JButton button1;
    private JList list1;
    private JLabel label;

    public PluginTest(AnActionEvent e){
        super("Hello World");
        setContentPane(panel1);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//        String AndroidPath = "/app/src/main/java";
//        VirtualFile IntellijPathFile = e.getProject().getBaseDir().findFileByRelativePath(AndroidPath);

//        PsiDirectory psiDirectory = PsiManager.getInstance(e.getProject()).findDirectory(IntellijPathFile);

        String str = "Project Path : "+e.getProject().getProjectFilePath() + "\n";
        str += "GetProjectPackage Name : " + e.getProject().getClass().getPackage().getName() + " \n";
        str += "GetClassPackage Name : " + e.getClass().getPackage().getName() + "\n";

        label.setText(str);

        System.out.println("getUIClassID : "+button1.getUIClassID());
        System.out.println("getDisplayedMnemonicIndex : "+button1.getDisplayedMnemonicIndex());
        System.out.println("getComponentCount : "+button1.getComponentCount());
        System.out.println("getMnemonic : "+button1.getMnemonic());

        button1.addActionListener(this);
 ;
        setVisible(true);
    }

    public PluginTest(PsiClass psiClass){
        super("Hello World");
        setContentPane(panel1);
        pack();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        if(psiClass != null ) {
//        System.out.println("Test : " + psiClass.getTextLength());
            System.out.println("Test : " + psiClass.getAllFields().length);

            myFields = new CollectionListModel<PsiField>(psiClass.getAllFields());
            list1.setModel(myFields);
            list1.setCellRenderer(new DefaultPsiElementCellRenderer());
        }

        button1.addActionListener(this);

        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        System.out.println("getActionCommand : "+e.getActionCommand());
        System.out.println("Click GetId : "+e.getID());
        System.out.println("getModifiers : "+e.getModifiers());
        System.out.println("getSource : "+e.getSource());
        if(e.getSource() == button1){
            System.out.println("Here!!!");
        }
    }
}
