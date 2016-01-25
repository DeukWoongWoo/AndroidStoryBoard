package Analysis.Database.DataAccessObject.Java;

/**
 * Created by woong on 2016-01-24.
 */
public interface JavaDAO {
    public int insertJava();
    public int insertXml();
    public int insertComponent();
    public int insertEvent();

    public void selectAll();
    public void selectJava();
    public void selectXml();
    public void selectComponent();
    public void selectEvent();
}
