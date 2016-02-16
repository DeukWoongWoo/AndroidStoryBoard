package Analysis.RedoUndo.Command;

import Analysis.RedoUndo.CommandObj.LocalButtonCreate;

/**
 * Created by woong on 2016-02-15.
 */
public class LocalButtonCreateCommand implements Command{
    private LocalButtonCreate localButtonCreate = new LocalButtonCreate();

    @Override
    public void execute() {
        localButtonCreate.create();
    }

    @Override
    public void undo() {
        localButtonCreate.remove();
    }
}
