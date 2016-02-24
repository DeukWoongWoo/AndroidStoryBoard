package Analysis.RedoUndo.Command;

import Analysis.RedoUndo.CommandObj.LocalButton;

/**
 * Created by woong on 2016-02-24.
 */
public class LocalButtonDeleteCommand implements Command {
    private LocalButton localButton;

    public LocalButtonDeleteCommand(LocalButton localButton) {
        this.localButton = localButton;
    }

    @Override
    public void execute() {
        localButton.remove();
    }

    @Override
    public void undo() {
        localButton.create();
    }
}
