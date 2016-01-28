package Test.Analysis;

import Analysis.Database.QueryBuilder.QueryBuilder;
import org.junit.Test;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


/**
 * Created by woong on 2016-01-27.
 */
public class InsertTest {
    @Test
    public void insertManifestQuery(){
        String str = QueryBuilder.insert().into("Manifest").columns("name","address").values(1,"test").build();
        assertThat(str, is("INSERT INTO Manifest(name,address) VALUES(1,'test')"));
    }
}
