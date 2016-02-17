package Analysis.Constant;

import com.intellij.openapi.actionSystem.AnActionEvent;

/**
 * Created by woong on 2016-02-17.
 */
public enum SharedPreference {
    ACTIONEVENT(null);

    AnActionEvent actionEvent;
    SharedPreference(AnActionEvent actionEvent){
        this.actionEvent = actionEvent;
    }

    public void setData(AnActionEvent actionEvent) {
        this.actionEvent = actionEvent;
    }

    public AnActionEvent getData(){
        return actionEvent;
    }
}
