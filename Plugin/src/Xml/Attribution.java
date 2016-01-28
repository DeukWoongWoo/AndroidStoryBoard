package Xml;

/**
 * Created by cho on 2016-01-24.
 */
public class Attribution {

    private String Attribute;
    private String Value;

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
