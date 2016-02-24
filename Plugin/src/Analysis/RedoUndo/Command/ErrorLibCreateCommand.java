package Analysis.RedoUndo.Command;

import Analysis.RedoUndo.CommandObj.ErrorLibCreate;

/**
 * Created by woong on 2016-02-24.
 */
public class ErrorLibCreateCommand implements Command {
    private ErrorLibCreate errorLibCreate;

    public ErrorLibCreateCommand(ErrorLibCreate errorLibCreate) {
        this.errorLibCreate = errorLibCreate;
    }

    @Override
    public void execute() {
        errorLibCreate.create();
    }

    @Override
    public void undo() {
        errorLibCreate.remove();
    }
}
