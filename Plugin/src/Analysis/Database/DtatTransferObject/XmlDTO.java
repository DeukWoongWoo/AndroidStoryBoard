package Analysis.Database.DtatTransferObject;

import Analysis.Database.QueryBuilder.QueryBuilder;

/**
 * Created by woong on 2016-01-21.
 */
public class XmlDTO {
    private final String tableName = "Xml";

    private int num;
    private int javaId;
    private String xmlName;
    private String name;

    public XmlDTO(){}

    public XmlDTO(int num, int javaId, String xmlName, String name) {
        this.num = num;
        this.javaId = javaId;
        this.xmlName = xmlName;
        this.name = name;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setJavaId(int javaId) {
        this.javaId = javaId;
    }

    public void setXmlName(String xmlName) {
        this.xmlName = xmlName;
    }

    public void setName(String name) {
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

    public String getInsertQuery(){
        return QueryBuilder.insert().into(tableName).columns("javaId","xmlName").values(javaId,xmlName).build();
    }
}
