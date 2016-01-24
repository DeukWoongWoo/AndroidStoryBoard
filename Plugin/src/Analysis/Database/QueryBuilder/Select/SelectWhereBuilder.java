package Analysis.Database.QueryBuilder.Select;

import Analysis.Database.QueryBuilder.Builder;

/**
 * Created by woong on 2016-01-24.
 */
public class SelectWhereBuilder implements Builder {
    private SelectFromBuilder selectFromBuilder;
    private String query;

    public SelectWhereBuilder(SelectFromBuilder selectFromBuilder, String where) {
        this.selectFromBuilder = selectFromBuilder;
        query = "WHERE "+where;
    }

    @Override
    public String build() {
        return selectFromBuilder.build() + " " + query;
    }
}
