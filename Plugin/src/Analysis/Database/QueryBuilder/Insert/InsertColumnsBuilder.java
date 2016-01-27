package Analysis.Database.QueryBuilder.Insert;

import Analysis.Database.QueryBuilder.Builder;

/**
 * Created by woong on 2016-01-27.
 */
public class InsertColumnsBuilder implements Builder{
    private InsertIntoBuilder insertIntoBuilder;
    private String[] columns;

    public InsertColumnsBuilder(InsertIntoBuilder insertIntoBuilder, String... columns) {
        this.insertIntoBuilder = insertIntoBuilder;
        this.columns = columns;
    }

    public InsertValuesBuilder values(Object... values){
        return new InsertValuesBuilder(this, values);
    }

    @Override
    public String build() {
        return null;
    }
}
