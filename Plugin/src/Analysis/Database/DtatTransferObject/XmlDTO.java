package Analysis.Database.DtatTransferObject;

/**
 * Created by woong on 2016-01-21.
 */
public class XmlDTO {
    private int num;
    private int javaId;
    private String xmlName;
    private String name;

    public XmlDTO(int num, int javaId, String xmlName, String name) {
        this.num = num;
        this.javaId = javaId;
        this.xmlName = xmlName;
        this.name = name;
    }

    public int getNum() {
        return num;
    }

    public int getJavaId() {
        return javaId;
    }

    public String getXmlName() {
        return xmlName;
    }

    public String getName() {
        return name;
    }
}
