package Analysis.RedoUndo.Command;

import Analysis.RedoUndo.CommandObj.ActivityCreate;

/**
 * Created by woong on 2016-02-22.
 */
public class ActivityDeleteCommand implements Command{
    private ActivityCreate activityCreate;

    public ActivityDeleteCommand(ActivityCreate activityCreate){
        this.activityCreate = activityCreate;
    }

    @Override
    public void execute() {
        activityCreate.remove();
    }

    @Override
    public void undo() {
        activityCreate.create();
    }
}
