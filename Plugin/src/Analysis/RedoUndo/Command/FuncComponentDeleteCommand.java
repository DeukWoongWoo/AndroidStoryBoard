package Analysis.RedoUndo.Command;

import Analysis.RedoUndo.CommandObj.FuncComponent;

/**
 * Created by woong on 2016-02-25.
 */
public class FuncComponentDeleteCommand implements Command {
    private FuncComponent funcComponent;

    public FuncComponentDeleteCommand(FuncComponent funcComponent) {
        this.funcComponent = funcComponent;
    }

    @Override
    public void execute() {
        funcComponent.remove();
    }

    @Override
    public void undo() {
        funcComponent.create();
    }
}
