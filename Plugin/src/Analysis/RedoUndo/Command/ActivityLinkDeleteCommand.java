package Analysis.RedoUndo.Command;

import Analysis.RedoUndo.CommandObj.ActivityLink;

/**
 * Created by woong on 2016-02-22.
 */
public class ActivityLinkDeleteCommand implements Command {
    private ActivityLink activityLink;

    public ActivityLinkDeleteCommand(ActivityLink activityLink){
        this.activityLink = activityLink;
    }

    @Override
    public void execute() {
        activityLink.remove();
    }

    @Override
    public void undo() {
        activityLink.create();
    }
}
