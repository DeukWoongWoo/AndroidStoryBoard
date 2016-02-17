package Analysis.Database.DtatTransferObject;

import Analysis.Database.QueryBuilder.QueryBuilder;

/**
 * Created by woong on 2016-01-21.
 */
public class EventDTO {
    private final String tableName = "Event";

    private int num;
    private int componentId;
    private String methodName;
    private String componentName;
    private int type;
    private int startLine;
    private int totalLine;

    public EventDTO(){}

    public EventDTO(int num, int componentId, String methodName, int type, int startLine, int totalLine) {
        this.num = num;
        this.componentId = componentId;
        this.methodName = methodName;
        this.type = type;
        this.startLine = startLine;
        this.totalLine = totalLine;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setComponentId(int componentId) {
        this.componentId = componentId;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setComponentName(String componentName) {
        this.componentName = componentName;
    }

    public void setType(int type) {
        this.type = type;
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

    public int getComponentId() {
        return componentId;
    }

    public String getMethodName() {
        return methodName;
    }

    public String getComponentName() {
        return componentName;
    }

    public int getType() {
        return type;
    }

    public int getStartLine() {
        return startLine;
    }

    public int getTotalLine() {
        return totalLine;
    }

    public String getInsertQuery(){
        return QueryBuilder.insert().into(tableName).columns("componentId","methodName","type","totalLine","startLine").values(componentId,methodName,type,totalLine,startLine).build();
    }
}
