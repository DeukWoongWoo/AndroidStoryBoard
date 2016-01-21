package Analysis.Database.DtatTransferObject;

/**
 * Created by woong on 2016-01-21.
 */
public class EventDTO {
    private int num;
    private int componentId;
    private String name;
    private int type;
    private int startLine;
    private int totalLine;

    public EventDTO(int num, int componentId, String name, int type, int startLine, int totalLine) {
        this.num = num;
        this.componentId = componentId;
        this.name = name;
        this.type = type;
        this.startLine = startLine;
        this.totalLine = totalLine;
    }

    public int getNum() {
        return num;
    }

    public int getComponentId() {
        return componentId;
    }

    public String getName() {
        return name;
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
}
