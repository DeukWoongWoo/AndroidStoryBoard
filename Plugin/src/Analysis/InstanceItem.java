package Analysis;

import com.intellij.openapi.actionSystem.AnActionEvent;

/**
 * Created by woong on 2016-01-14.
 */
public class InstanceItem {
    private final AnActionEvent anActionEvent;
    private final String path;

    public static class Builder{
        private AnActionEvent anActionEvent = null;
        private String path = null;

        public Builder(){}

        public Builder anActionEvent(AnActionEvent e){
            this.anActionEvent = e;
            return this;
        }

        public Builder path(String path){
            this.path = path;
            return this;
        }

        public InstanceItem build(){
            return new InstanceItem(this);
        }
    }

    private InstanceItem(Builder builder){
        this.anActionEvent = builder.anActionEvent;
        this.path = builder.path;
    }

    public AnActionEvent getAnActionEvent(){return anActionEvent;}

    public String getPath(){return path;}
}
