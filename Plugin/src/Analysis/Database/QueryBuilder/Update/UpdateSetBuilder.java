package Analysis.Database.QueryBuilder.Update;

import Analysis.Database.QueryBuilder.Builder;
import Analysis.Database.QueryBuilder.StringUtils;

/**
 * Created by woong on 2016-02-17.
 */
public class UpdateSetBuilder implements Builder{
    private UpdateBuilder updateBuilder;

    private String[] sets;

    public UpdateSetBuilder(UpdateBuilder updateBuilder, String... set) {
        this.updateBuilder = updateBuilder;
        this.sets= set;
    }

    public UpdateWhereBuilder where(String where){
        return new UpdateWhereBuilder(this, where);
    }

    @Override
    public String build() {
        return updateBuilder.build() + " SET " + StringUtils.join(",",sets);
    }
}
