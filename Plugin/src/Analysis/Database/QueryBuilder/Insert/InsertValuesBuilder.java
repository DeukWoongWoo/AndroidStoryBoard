package Analysis.Database.QueryBuilder.Insert;

import Analysis.Database.QueryBuilder.Builder;
import Analysis.Database.QueryBuilder.StringUtils;

/**
 * Created by woong on 2016-01-27.
 */
public class InsertValuesBuilder implements Builder {
    private InsertColumnsBuilder insertColumnsBuilder;
    private Object[] values;

    public InsertValuesBuilder(InsertColumnsBuilder insertColumnsBuilder, Object... values) {
        this.insertColumnsBuilder = insertColumnsBuilder;
        this.values = check(values);
    }

    private Object[] check(Object[] values){
        Object[] check = new Object[values.length];
        for(int i=0;i<values.length;i++){
            if(values[i] instanceof String){
                check[i] = "'" + values[i] +"'";
            }else check[i] = values[i];
        }
        return check;
    }

    @Override
    public String build() {
        return insertColumnsBuilder.build() + " VALUES(" + StringUtils.join(",",values) + ")";
    }
}
