package Analysis.RedoUndo.CodeBuilder;

/**
 * Created by woong on 2016. 2. 12..
 */
public enum Type {
    Button("button"), RadioButton("radioButton"), CheckBox("checkBox"), TextView("textView"), EditText("editText"), ImageView("imageView");

    private String value;
    Type(String value){
        this.value = value;
    }
    public String getValue(){
        return value;
    }
}
