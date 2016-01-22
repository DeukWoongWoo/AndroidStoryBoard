package Analysis.Database.DtatTransferObject;

/**
 * Created by woong on 2016-01-21.
 */
public class ActivityDTO {
    private int num;
    private int manifestId;
    private String name;
    private int action;
    private int totalLine;
    private int startLine;

    public ActivityDTO(int num, int manifestId, String name, int action, int totalLine, int startLine) {
        this.num = num;
        this.manifestId = manifestId;
        this.name = name;
        this.action = action;
        this.totalLine = totalLine;
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
}
