package Analysis.Database.DataAccessObject.Java;

import Analysis.Database.DtatTransferObject.ComponentDTO;
import Analysis.Database.DtatTransferObject.EventDTO;
import Analysis.Database.DtatTransferObject.JavaDTO;
import Analysis.Database.DtatTransferObject.XmlDTO;

import java.util.ArrayList;

/**
 * Created by woong on 2016-01-24.
 */
public interface JavaDAO {
    public void createJava();
    public void createXml();
    public void createComponent();
    public void createEvent();

    public void insertJava(JavaDTO javaDTO);
    public void insertXml(XmlDTO xmlDTO);
    public void insertComponent(ComponentDTO componentDTO);
    public void insertEvent(EventDTO eventDTO);

    public ArrayList<JavaDTO>  selectAll();
    public ArrayList<JavaDTO> selectJava(String... col);
    public ArrayList<JavaDTO> selectXml(String... col);
    public ArrayList<JavaDTO> selectComponent(String... col);
    public ArrayList<JavaDTO> selectEvent(String... col);

    public void updateJava(JavaDTO javaDTO);
}
