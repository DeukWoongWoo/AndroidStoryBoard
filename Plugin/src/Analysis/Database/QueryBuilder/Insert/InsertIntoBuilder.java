package Analysis.Database.QueryBuilder.Insert;

import Analysis.Database.QueryBuilder.Builder;

/**
 * Created by woong on 2016-01-27.
 */
public class InsertIntoBuilder implements Builder {
    private InsertBuilder insertBuilder;
    private String tableName;

    public InsertIntoBuilder(InsertBuilder insertBuilder, String table) {
        this.insertBuilder = insertBuilder;
        this.tableName = table;
    }

    public InsertColumnsBuilder columns(String... columns){
        return new InsertColumnsBuilder(this, columns);
    }

    @Override
    public String build() {
        return null;
    }
}
