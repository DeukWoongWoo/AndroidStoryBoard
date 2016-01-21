package Analysis.Database.DtatTransferObject;

/**
 * Created by woong on 2016-01-21.
 */
public class JavaDTO {
    private int num;
    private String name;
    private String extendsValue;
    private String implementsValue;
    private String nextActivity;

    public JavaDTO(int num, String name, String extendsValue, String implementsValue, String nextActivity) {
        this.num = num;
        this.name = name;
        this.extendsValue = extendsValue;
        this.implementsValue = implementsValue;
        this.nextActivity = nextActivity;
    }

    public int getNum() {
        return num;
    }

    public String getName() {
        return name;
    }

    public String getExtendsValue() {
        return extendsValue;
    }

    public String getImplementsValue() {
        return implementsValue;
    }

    public String getNextActivity() {
        return nextActivity;
    }
}
