package Analysis.Constant;

import com.intellij.openapi.actionSystem.AnActionEvent;
import com.intellij.openapi.project.Project;

/**
 * Created by woong on 2016-02-17.
 */
public enum SharedPreference {
    PROJECT(null);

    private Project project;
    SharedPreference(Project project){
        this.project = project;
    }

    public void set(Project project){
        this.project = project;
    }

    public Project get(){
        return project;
    }
}
