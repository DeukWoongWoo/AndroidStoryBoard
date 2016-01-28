package Analysis.Database.QueryBuilder.Insert;

import Analysis.Database.QueryBuilder.Builder;

/**
 * Created by woong on 2016-01-27.
 */
public class InsertBuilder implements Builder{
    public InsertIntoBuilder into(String table){
        return new InsertIntoBuilder(this, table);
    }

    @Override
    public String build() {
        return null;
    }
}
