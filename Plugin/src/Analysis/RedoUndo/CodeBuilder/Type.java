package Analysis.RedoUndo.CodeBuilder;

/**
 * Created by woong on 2016. 2. 12..
 */
public enum Type {
    Button("button"), TextView("textView"), EditText("editText");

    private String value;
    Type(String value){
        this.value = value;
    }
    String getValue(){
        return value;
    }
}
