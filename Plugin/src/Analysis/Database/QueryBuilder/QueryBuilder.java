package Analysis.Database.QueryBuilder;

import Analysis.Database.QueryBuilder.Insert.InsertBuilder;
import Analysis.Database.QueryBuilder.Select.SelectFieldBuilder;

/**
 * Created by woong on 2016-01-24.
 */
public class QueryBuilder {
    public static SelectFieldBuilder select(String... values){
        return new SelectFieldBuilder(values);
    }

    public static SelectFieldBuilder selectAll(){
        return new SelectFieldBuilder();
    }

    public static InsertBuilder insert(){
        return new InsertBuilder();
    }
}
