package Analysis.RedoUndo.Command;

import Analysis.RedoUndo.CommandObj.ActivityLibCreate;

/**
 * Created by woong on 2016-02-24.
 */
public class ActivityLibCreateCommand implements Command {
    private ActivityLibCreate activityLibCreate;

    public ActivityLibCreateCommand(ActivityLibCreate activityLibCreate) {
        this.activityLibCreate = activityLibCreate;
    }

    @Override
    public void execute() {
        activityLibCreate.create();
    }

    @Override
    public void undo() {
        activityLibCreate.remove();
    }
}
