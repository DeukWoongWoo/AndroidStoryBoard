package Analysis.RedoUndo.Command;

import Analysis.RedoUndo.CommandObj.FuncButtonCreate;

/**
 * Created by woong on 2016-02-15.
 */
public class FuncButtonCreateCommand implements Command{
    private FuncButtonCreate funcButtonCreate = new FuncButtonCreate();

    @Override
    public void execute() {
        funcButtonCreate.create();
    }

    @Override
    public void undo() {
        funcButtonCreate.remove();
    }
}
