package Analysis.Database.DtatTransferObject;

/**
 * Created by woong on 2016-01-21.
 */
public class ManifestDTO {
    private int num;
    private String packageName;
    private String theme;

    public ManifestDTO(int num, String packageName, String theme){
        this.num = num;
        this.packageName = packageName;
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
}
