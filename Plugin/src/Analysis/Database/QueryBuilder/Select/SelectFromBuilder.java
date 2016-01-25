package Analysis.Database.QueryBuilder.Select;

import Analysis.Database.QueryBuilder.Builder;

/**
 * Created by woong on 2016-01-24.
 */
public class SelectFromBuilder implements Builder {
    private SelectBuilder selectBuilder;
    private String query;

    public SelectFromBuilder(SelectBuilder selectBuilder, String table) {
        this.selectBuilder = selectBuilder;
        query = "FROM "+table;
    }

    public SelectWhereBuilder where(String where){
        return new SelectWhereBuilder(this, where);
    }

    @Override
    public String build() {
        return selectBuilder.build() + " " +query;
    }
}
