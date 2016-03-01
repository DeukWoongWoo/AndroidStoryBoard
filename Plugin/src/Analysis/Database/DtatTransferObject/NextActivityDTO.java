package Analysis.Database.DtatTransferObject;

import Analysis.Database.QueryBuilder.QueryBuilder;

/**
 * Created by woong on 2016-02-29.
 */
public class NextActivityDTO {
   private final String tableName = "NextActivity";

    private int num;
    private int javaId;
    private String name;
    private String intentName;
    private String intentFuncName;

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public int getJavaId() {
        return javaId;
    }

    public void setJavaId(int javaId) {
        this.javaId = javaId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntentName() {
        return intentName;
    }

    public void setIntentName(String intentName) {
        this.intentName = intentName;
    }

    public String getIntentFuncName() {
        return intentFuncName;
    }

    public void setIntentFuncName(String intentFuncName) {
        this.intentFuncName = intentFuncName;
    }

    public String getInsertQuery(){
        return QueryBuilder.insert().into(tableName).columns("javaId","name","intentName","intentFuncName").values(javaId,name,intentName,intentFuncName).build();
    }
}
