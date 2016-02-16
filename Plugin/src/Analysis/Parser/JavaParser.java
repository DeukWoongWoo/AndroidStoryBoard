package Analysis.Parser;

import Analysis.Database.DatabaseManager.DatabaseManager;
import Analysis.Database.DtatTransferObject.ComponentDTO;
import Analysis.Database.DtatTransferObject.EventDTO;
import Analysis.Database.DtatTransferObject.JavaDTO;
import Analysis.Database.DtatTransferObject.XmlDTO;
import com.intellij.codeInsight.template.JavaCodeContextType;
import com.intellij.openapi.actionSystem.LangDataKeys;
import com.intellij.openapi.actionSystem.PlatformDataKeys;
import com.intellij.openapi.command.WriteCommandAction;
import com.intellij.openapi.editor.Document;
import com.intellij.openapi.editor.Editor;
import com.intellij.openapi.fileEditor.FileDocumentManager;
import com.intellij.psi.*;
import com.intellij.psi.codeStyle.JavaCodeStyleManager;
import com.intellij.psi.util.PsiExpressionTrimRenderer;
import com.intellij.psi.util.PsiTreeUtil;
import com.intellij.psi.util.PsiTypesUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by woong on 2016-01-14.
 */
public class JavaParser implements FileParser {
    private PsiJavaFile psiJavaFile;

    private String curPath;
    private String curMethod;

    private FileDocumentManager fdm;
    private Document doc;

    private JavaDTO javaDTO;
    private EventDTO eventDTO;

    private boolean isOnClick;

    public JavaParser(PsiFile psiFile, String path) {
        psiJavaFile = (PsiJavaFile) psiFile;
        curPath = path;

        fdm = FileDocumentManager.getInstance();
        doc = fdm.getCachedDocument(psiJavaFile.getVirtualFile());
    }

    @Override
    public void parsing() {
        javaDTO = new JavaDTO();
        javaDTO.setPath(psiJavaFile.getProject().getBasePath() + curPath + "/" + psiJavaFile.getName());

//        PsiClass psiClass = PsiTreeUtil.getParentOfType(psiJavaFile.getClasses()[0].getFirstChild(), PsiClass.class);
//
//        System.out.println("MakePsiClass : " + psiClass);
//
//        new WriteCommandAction.Simple(psiClass.getProject(), psiClass.getContainingFile()){
//            @Override
//            protected void run() throws Throwable {
//                StringBuilder builder = new StringBuilder("private Button button;");
//                PsiElementFactory elementFactory = JavaPsiFacade.getElementFactory(psiClass.getProject());
////                PsiMethod compareTo = elementFactory.createMethodFromText(builder.toString(), psiClass);
////                PsiElement el = psiClass.add(compareTo);
////                JavaCodeStyleManager.getInstance(psiClass.get,.Project()).shortenClassReferences(el);
//
//                PsiField field = elementFactory.createFieldFromText(builder.toString(), psiClass);
////                PsiField field = elementFactory.createField(builder.toString(), psiClass.getAllFields()[0].getType());
//
//                psiClass.add(field);
//
//            }
//        }.execute();

        for (PsiClass p : psiJavaFile.getClasses()) {
            javaDTO.setName(p.getName());

            PsiJavaCodeReferenceElement[] extendsElement = p.getExtendsList().getReferenceElements();
            if (extendsElement.length != 0) {
                javaDTO.setExtendsValue(extendsElement[0].getText());
            }

            PsiJavaCodeReferenceElement[] implementsElement = p.getImplementsList().getReferenceElements();
            if (implementsElement.length != 0) {
                javaDTO.setImplementsValue(implementsElement[0].getText());
                if (implementsElement[0].getText().equals("View.OnClickListener")) isOnClick = true;
            }

            DatabaseManager.getInstance().insertToJava(table -> table.insertJava(javaDTO));

//            System.out.println("Methods length : "+p.getMethods().length);
            for (PsiMethod method : p.getMethods()) {
                curMethod = method.getName();
//                System.out.println(">> Method Name : "+method.getMethodName());
                if (isOnClick) if (method.getName().equals("onClick")) {
                    eventDTO.setMethodName(method.getName());
                    eventDTO.setStartLine(getStartLine(method.getTextRange().getStartOffset()));
                    eventDTO.setTotalLine(getTotalLine(method.getTextRange().getEndOffset(),getStartLine(method.getTextRange().getStartOffset())));
                    DatabaseManager.getInstance().insertToJava(table->table.insertEvent(eventDTO));
                }
//                if(method.getMethodName().equals("onCreate")){
                PsiCodeBlock body = method.getBody();

                for (PsiStatement statement : body.getStatements()) {
                    if (findPattern(statement, "setContentView")) continue;
                    if (findPattern(statement, "findViewById")) continue;
                    if (findPattern(statement, "setOnClickListener")) continue;
                    if (findPattern(statement, "Intent")) continue;
                }
//                }
            }
        }
    }

    private boolean findPattern(PsiStatement statement, String type) {
        Pattern pattern = Pattern.compile(type);
        Matcher matcher = pattern.matcher(statement.getText());

        if (matcher.find()) {
            if (type.equals("setContentView")) makeXmlDTO(statement);
            else if (type.equals("findViewById")) makeComponentDTO(statement);
            else if (type.equals("setOnClickListener")) {
                eventDTO = new EventDTO();

                PsiElement[] element = ((PsiExpressionStatement) statement).getExpression().getChildren();
                eventDTO.setComponentName(element[0].getText().split("\\.")[0]);
                if (element[1].getChildren()[1].getChildren().length > 2) {
                    eventDTO.setType(3);
                    eventDTO.setStartLine(getStartLine(statement.getTextRange().getStartOffset()));
                    eventDTO.setTotalLine(getTotalLine(statement.getTextRange().getEndOffset(),getStartLine(statement.getTextRange().getStartOffset())));
                    eventDTO.setMethodName("Local");

                    DatabaseManager.getInstance().insertToJava(table->table.insertEvent(eventDTO));

                    for (PsiElement el : element[1].getChildren()[1].getChildren()[3].getChildren()[5].getChildren()[9].getChildren()) {
                        if (el instanceof PsiStatement) {
                            PsiStatement psiStat = (PsiStatement) el;
                            if (findPattern(psiStat, "Intent")) continue;
                        }
                    }
                } else {
                    if(element[1].getChildren()[1].getChildren()[1].getText().equals("this")){
                        eventDTO.setType(1);
                        eventDTO.setMethodName("onClick");
                    }else eventDTO.setType(2);
                }

            } else if (type.equals("Intent")) {
                String[] strSplit = statement.getText().split("=");
                String[] idStr = strSplit[0].split("\\s");

                String intentName = null;
                if(idStr.length > 1) intentName = idStr[idStr.length-1];
                else intentName = idStr[0];
                javaDTO.setIntentName(intentName);
                javaDTO.setNextActivity(strSplit[1].substring(strSplit[1].indexOf("(")+1, strSplit[1].indexOf(")")).split(",")[1].split("\\.")[0]);
                javaDTO.setIntentName(((PsiMethod)statement.getParent().getParent()).getName());
                // TODO: 2016-02-17 JavaDAO update
            }
            return true;
        }
        return false;
    }

    private void makeXmlDTO(PsiStatement statement) {
        XmlDTO xmlDTO = new XmlDTO();
        xmlDTO.setXmlName(statement.getText().substring((statement.getText().indexOf("(") + 1), statement.getText().indexOf(")")));
        DatabaseManager.getInstance().insertToJava(table -> table.insertXml(xmlDTO));
    }

    private void makeComponentDTO(PsiStatement statement) {
        ComponentDTO componentDTO = new ComponentDTO();

        componentDTO.setMethodName(curMethod);

        String[] strSplit = statement.getText().split("=");
        String[] idStr = strSplit[0].split("\\s");

        String componentName = null;
        if (idStr.length > 1) componentName = idStr[idStr.length - 1];
        else componentName = idStr[0];

        componentDTO.setName(componentName);

        String findStr = strSplit[1];
        componentDTO.setType(findStr.substring(findStr.indexOf("(") + 1, findStr.indexOf(")")));
        componentDTO.setXmlName(findStr.substring(findStr.lastIndexOf("(") + 1, findStr.lastIndexOf(")")));

        componentDTO.setStartLine(getStartLine(statement.getTextRange().getStartOffset()));
        componentDTO.setTotalLine(getTotalLine(statement.getTextRange().getEndOffset(),getStartLine(statement.getTextRange().getStartOffset())));

        DatabaseManager.getInstance().insertToJava(table -> table.insertComponent(componentDTO));
    }

    private int getTotalLine(int offset, int startLine) {
        return (doc.getLineNumber(offset) - startLine) + 2;
    }

    private int getStartLine(int offset) {
        return doc.getLineNumber(offset) + 1;
    }
}
