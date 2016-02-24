package Analysis.RedoUndo.Command;

import Analysis.RedoUndo.CommandObj.MemberComponent;

/**
 * Created by woong on 2016-02-15.
 */
public class MemberComponentCreateCommand implements Command{
    private MemberComponent memberComponent;

    public MemberComponentCreateCommand(MemberComponent memberComponent) {
        this.memberComponent = memberComponent;
    }

    @Override
    public void execute() {
        memberComponent.create();
    }

    @Override
    public void undo() {
        memberComponent.remove();
    }
}
