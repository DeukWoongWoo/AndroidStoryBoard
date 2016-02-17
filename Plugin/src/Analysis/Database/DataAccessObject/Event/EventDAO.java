package Analysis.Database.DataAccessObject.Event;

import Analysis.Database.DtatTransferObject.EventDTO;

/**
 * Created by Windows on 2016-02-01.
 */
public interface EventDAO {
    public void create();
    public void insert(EventDTO eventDTO);
    public void select();
}
