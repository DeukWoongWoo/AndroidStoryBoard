package Xml;

import com.intellij.ide.util.DefaultPsiElementCellRenderer;
import com.intellij.openapi.ui.DialogWrapper;
import com.intellij.openapi.ui.LabeledComponent;
import com.intellij.psi.PsiClass;
import com.intellij.psi.PsiField;
import com.intellij.ui.CollectionListModel;
import com.intellij.ui.ToolbarDecorator;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;
import java.util.List;

/**
 * Created by cho on 2016-01-10.
 */
public class GenerateDialog extends DialogWrapper {
    private CollectionListModel<PsiField> myField;
    private final LabeledComponent<JPanel> myComponent;

    protected GenerateDialog(PsiClass psiClass) {
        super(psiClass.getProject());

        setTitle("Select Filed for ComparisonChain");

        myField = new CollectionListModel<PsiField>(psiClass.getAllFields());
        JList fieldlist = new JList(myField);
        fieldlist.setCellRenderer(new DefaultPsiElementCellRenderer());
        ToolbarDecorator decorator=  ToolbarDecorator.createDecorator(fieldlist);
        decorator.disableAddAction();
        JPanel panel =decorator.createPanel();
        myComponent = LabeledComponent.create(panel,"Fields to include in Compare To():");

        init();
    }

    @Nullable
    @Override
    protected JComponent createCenterPanel() {
        return myComponent;
    }

    public List<PsiField> getFields() {
        return myField.getItems();
    }
}