package Analysis.Database.DtatTransferObject;

import java.util.ArrayList;

/**
 * Created by woong on 2016-01-21.
 */
public class ManifestDTO {
    private final String tableName = "Manifest";

    private int num;
    private String packageName;
    private String theme;
    private ArrayList<ActivityDTO> activities;

    public ManifestDTO() {
    }

    public ManifestDTO(int num, String packageName, String theme){
        this.num = num;
        this.packageName = packageName;
        this.theme = theme;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public int getNum() {
        return num;
    }

    public String getPackageName() {
        return packageName;
    }

    public String getTheme() {
        return theme;
    }

    public ArrayList<ActivityDTO> getActivities() {
        return activities;
    }

    public void setActivities(ArrayList<ActivityDTO> activities) {
        this.activities = activities;
    }

    public void setActivity(ActivityDTO activity) {
        if(activities == null) activities = new ArrayList<>();
        activities.add(activity);
    }

    public String getInsertQuery(){
        return null;
    }
}
