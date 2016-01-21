package Analysis.Database.DtatTransferObject;

/**
 * Created by woong on 2016-01-21.
 */
public class ComponentDTO {
    private int num;
    private int xmlId;
    private String name;
    private String xmlName;
    private int startLine;
    private int totalLine;

    public ComponentDTO(int num, int xmlId, String name, String xmlName, int startLine, int totalLine) {
        this.num = num;
        this.xmlId = xmlId;
        this.name = name;
        this.xmlName = xmlName;
        this.startLine = startLine;
        this.totalLine = totalLine;
    }

    public int getNum() {
        return num;
    }

    public int getXmlId() {
        return xmlId;
    }

    public String getName() {
        return name;
    }

    public String getXmlName() {
        return xmlName;
    }

    public int getStartLine() {
        return startLine;
    }

    public int getTotalLine() {
        return totalLine;
    }
}
