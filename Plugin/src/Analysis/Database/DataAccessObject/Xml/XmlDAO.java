package Analysis.Database.DataAccessObject.Xml;

import Analysis.Database.DtatTransferObject.XmlDTO;

/**
 * Created by Windows on 2016-02-01.
 */
public interface XmlDAO {
    public void create();
    public int insert(XmlDTO xmlDTO);
    public void select();
}
