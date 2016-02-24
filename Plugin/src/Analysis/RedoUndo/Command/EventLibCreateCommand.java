package Analysis.RedoUndo.Command;

import Analysis.RedoUndo.CommandObj.EventLibCreate;

/**
 * Created by woong on 2016-02-24.
 */
public class EventLibCreateCommand implements Command{
    private EventLibCreate eventLibCreate;

    public EventLibCreateCommand(EventLibCreate eventLibCreate){
        this.eventLibCreate = eventLibCreate;
    }

    @Override
    public void execute() {
        eventLibCreate.create();
    }

    @Override
    public void undo() {
        eventLibCreate.remove();
    }
}
