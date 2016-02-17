package Analysis.RedoUndo.Command;

import Analysis.RedoUndo.CommandObj.MemberButtonCreate;

/**
 * Created by woong on 2016-02-15.
 */
public class MemberButtonCreateCommand implements Command{
    private MemberButtonCreate memberButtonCreate = new MemberButtonCreate();

    @Override
    public void execute() {
        memberButtonCreate.create();
    }

    @Override
    public void undo() {
        memberButtonCreate.remove();
    }
}
