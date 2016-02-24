package Xml;

/**
 * Created by cho on 2016-01-24.
 */
public class Attribution {

    private String Attribute;
    private String Value;
    public Attribution(){

    }
    public Attribution(String attr,String value){
        this.Attribute = attr;
        this.Value = value;
    }
    public void setAttribute(String attr){
        Attribute =attr;
    }

    public void setValue(String Value){
        this.Value =Value;
    }
    public String getAttribute(){
        return Attribute;
    }

    public String getValue(){
        return Value;
    }
}
