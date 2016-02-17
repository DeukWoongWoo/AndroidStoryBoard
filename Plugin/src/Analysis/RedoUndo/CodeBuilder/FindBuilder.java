package Analysis.RedoUndo.CodeBuilder;

/**
 * Created by woong on 2016. 2. 12..
 */
public class FindBuilder {
    private Type type;

    private String name;
    private String id;

    public FindBuilder(Type type){
        this.type = type;
    }

    public FindBuilder name(String name){
        this.name = name;
        return this;
    }

    public FindBuilder findViewById(String id){
        this.id = id;
        return this;
    }

    public String build(){
        return type.name() + " " + name + " = (" + type.name() + ")findViewById(" + id + ");";
    }
}
