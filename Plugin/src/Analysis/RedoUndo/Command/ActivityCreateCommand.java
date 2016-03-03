package Analysis.RedoUndo.Command;

import Analysis.RedoUndo.CommandObj.ActivityCreate;

/**
 * Created by woong on 2016-02-22.
 */
public class ActivityCreateCommand implements Command{
    private ActivityCreate activityCreate;

    public ActivityCreateCommand(ActivityCreate activityCreate){
        this.activityCreate = activityCreate;
    }

    @Override
    public void execute() {
        activityCreate.create();
    }

    @Override
    public void undo() {
        activityCreate.remove();
    }
}
