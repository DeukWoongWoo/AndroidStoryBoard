package Analysis.RedoUndo.Command;

import Analysis.RedoUndo.CommandObj.LocalComponent;

/**
 * Created by woong on 2016-02-15.
 */
public class LocalComponentCreateCommand implements Command{
    private LocalComponent localComponent;

    public LocalComponentCreateCommand(LocalComponent localComponent) {
        this.localComponent = localComponent;
    }

    @Override
    public void execute() {
        localComponent.create();
    }

    @Override
    public void undo() {
        localComponent.remove();
    }
}
