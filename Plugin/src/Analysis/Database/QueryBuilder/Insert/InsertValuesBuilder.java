package Analysis.Database.QueryBuilder.Insert;

import Analysis.Database.QueryBuilder.Builder;

/**
 * Created by woong on 2016-01-27.
 */
public class InsertValuesBuilder implements Builder {
    private InsertColumnsBuilder insertColumnsBuilder;
    private Object[] values;

    public InsertValuesBuilder(InsertColumnsBuilder insertColumnsBuilder, Object... values) {
        this.insertColumnsBuilder = insertColumnsBuilder;
        this.values = values;
    }

//    private Object[] check(Object[] values){
//        Object[] check = new Object[values.length];
//        for(Object item : values){
//            if(item instanceof String){
//
//            }
//        }
//        return check;
//    }

    @Override
    public String build() {
        return null;
    }
}
