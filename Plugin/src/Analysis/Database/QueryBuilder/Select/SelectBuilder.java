package Analysis.Database.QueryBuilder.Select;

import Analysis.Database.QueryBuilder.Builder;

/**
 * Created by woong on 2016-01-24.
 */
public abstract class SelectBuilder implements Builder {
    public SelectFromBuilder from(String table){
        return new SelectFromBuilder(this, table);
    }
}
