package Analysis.Database.QueryBuilder.Update;

import Analysis.Database.QueryBuilder.Builder;

/**
 * Created by woong on 2016-02-17.
 */
public class UpdateBuilder implements Builder {
    private String table;

    public UpdateBuilder(String table){
        this.table = table;
    }

    public UpdateSetBuilder set(String... sets){
        return new UpdateSetBuilder(this, sets);
    }

    @Override
    public String build() {
        return "UPDATE " + table;
    }
}
