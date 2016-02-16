package Analysis.Database.DtatTransferObject;

import Analysis.Database.QueryBuilder.QueryBuilder;

/**
 * Created by woong on 2016-01-21.
 */
public class ComponentDTO {
    private final String tableName = "Component";

    private int num;
    private int xmlId;
    private String name;
    private String type;
    private String methodName;
    private String xmlName;
    private int startLine;
    private int totalLine;

    public ComponentDTO(){}

    public ComponentDTO(int num, int xmlId, String name, String type, String xmlName, int startLine, int totalLine) {
        this.num = num;
        this.xmlId = xmlId;
        this.name = name;
        this.type = type;
        this.xmlName = xmlName;
        this.startLine = startLine;
        this.totalLine = totalLine;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setXmlId(int xmlId) {
        this.xmlId = xmlId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setXmlName(String xmlName) {
        this.xmlName = xmlName;
    }

    public void setStartLine(int startLine) {
        this.startLine = startLine;
    }

    public void setTotalLine(int totalLine) {
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

    public String getType() {
        return type;
    }

    public String getMethodName() {
        return methodName;
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

    public String getInsertQuery(){
        return QueryBuilder.insert().into(tableName).columns("xmlId","name","type","methodName","xmlName","totalLine","startLine").values(xmlId,name,type,methodName,xmlName,totalLine,startLine).build();
    }
}
