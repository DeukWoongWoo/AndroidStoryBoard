package Analysis.Database.DtatTransferObject;

import Analysis.Database.QueryBuilder.QueryBuilder;

/**
 * Created by woong on 2016-01-21.
 */
public class ActivityDTO{
    private final String tableName = "Activity";

    private int num;
    private int manifestId;
    private String name;
    private int action = 0;
    private int totalLine;
    private int startLine;

    public ActivityDTO(){}

    public ActivityDTO(int num, int manifestId, String name, int action, int totalLine, int startLine) {
        this.num = num;
        this.manifestId = manifestId;
        this.name = name;
        this.action = action;
        this.totalLine = totalLine;
        this.startLine = startLine;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setManifestId(int manifestId) {
        this.manifestId = manifestId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAction(int action) {
        this.action = action;
    }

    public void setTotalLine(int totalLine) {
        this.totalLine = totalLine;
    }

    public void setStartLine(int startLine) {
        this.startLine = startLine;
    }

    public int getNum() {
        return num;
    }

    public int getManifestId() {
        return manifestId;
    }

    public String getName() {
        return name;
    }

    public int getAction() {
        return action;
    }

    public int getTotalLine() {
        return totalLine;
    }

    public int getStartLine() {
        return startLine;
    }

    public String getInsertQuery(){
        return QueryBuilder.insert().into(tableName).columns("manifestId","name","action","totalLine","startLine").values(manifestId, name, action, totalLine, startLine).build();
    }
}
