package Analysis.RedoUndo.Command;

import Analysis.RedoUndo.CommandObj.LocalComponent;

/**
 * Created by woong on 2016-02-24.
 */
public class LocalComponentDeleteCommand implements Command {
    private LocalComponent localComponent;

    public LocalComponentDeleteCommand(LocalComponent localComponent) {
        this.localComponent = localComponent;
    }

    @Override
    public void execute() {
        localComponent.remove();
    }

    @Override
    public void undo() {
        localComponent.create();
    }
}
