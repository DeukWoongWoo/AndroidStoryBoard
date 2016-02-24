package Analysis.RedoUndo.Command;

import Analysis.RedoUndo.CommandObj.ActivityCreate;

/**
 * Created by woong on 2016-02-22.
 */
public class ActivityCreateCommand implements Command{
    private final ActivityCreate activityCreate = new ActivityCreate();

    @Override
    public void execute() {
        activityCreate.create();
    }

    @Override
    public void undo() {
        activityCreate.remove();
    }
}
