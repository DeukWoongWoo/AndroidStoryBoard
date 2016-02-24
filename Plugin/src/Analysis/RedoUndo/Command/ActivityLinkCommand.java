package Analysis.RedoUndo.Command;

import Analysis.RedoUndo.CommandObj.ActivityLink;

/**
 * Created by woong on 2016-02-22.
 */
public class ActivityLinkCommand implements Command {
    private ActivityLink activityLink;

    public ActivityLinkCommand(ActivityLink activityLink){
        this.activityLink = activityLink;
    }

    @Override
    public void execute() {
        activityLink.create();
    }

    @Override
    public void undo() {
        activityLink.remove();
    }
}
