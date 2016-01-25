package Analysis.Database.QueryBuilder.Select;

/**
 * Created by woong on 2016-01-24.
 */
public class SelectFieldBuilder extends SelectBuilder {
    private String query;

    public SelectFieldBuilder(){
        query = "SELECT *";
    }

    public SelectFieldBuilder(String... values){

    }

    @Override
    public String build() {
        return query;
    }
}
