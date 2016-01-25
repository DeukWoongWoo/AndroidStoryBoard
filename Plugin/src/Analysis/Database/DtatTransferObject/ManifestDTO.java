package Analysis.Database.DtatTransferObject;

import Analysis.Database.QueryBuilder.QueryBuilder;

/**
 * Created by woong on 2016-01-21.
 */
public class ManifestDTO implements DTO {
    private final String tableName = "Manifest";
    private int num;
    private String packageName;
    private String theme;

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

    @Override
    public String selectPortionQuery(){
        return QueryBuilder.selectAll().from(tableName).build();
    }

    public boolean isNull(String str){
        return str == null ? true : false;
    }
}
