package Analysis.Database.QueryBuilder.Update;

import Analysis.Database.QueryBuilder.Builder;

/**
 * Created by woong on 2016-02-17.
 */
public class UpdateWhereBuilder implements Builder{
    private UpdateSetBuilder updateSetBuilder;

    private String where;

    public UpdateWhereBuilder(UpdateSetBuilder updateSetBuilder, String where) {
        this.updateSetBuilder = updateSetBuilder;
        this.where = where;
    }

    @Override
    public String build() {
        return updateSetBuilder.build() + " WHERE " + where;
    }
}
