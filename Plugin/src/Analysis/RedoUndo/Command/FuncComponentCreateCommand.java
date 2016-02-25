package Analysis.RedoUndo.Command;

import Analysis.RedoUndo.CommandObj.FuncComponent;

/**
 * Created by woong on 2016-02-15.
 */
public class FuncComponentCreateCommand implements Command{
    private FuncComponent funcComponent;

    public FuncComponentCreateCommand(FuncComponent funcComponent) {
        this.funcComponent = funcComponent;
    }

    @Override
    public void execute() {
        funcComponent.create();
    }

    @Override
    public void undo() {
        funcComponent.remove();
    }
}
