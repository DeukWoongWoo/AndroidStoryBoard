package Analysis.Database.DataAccessObject.Java;

/**
 * Created by woong on 2016-01-24.
 */
public interface JavaDAO {
    public void createJava();
    public void createXml();
    public void createComponent();
    public void createEvent();

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
