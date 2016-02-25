package Analysis.RedoUndo.Command;

import Analysis.RedoUndo.CommandObj.MemberComponent;

/**
 * Created by woong on 2016-02-25.
 */
public class MemberComponentDeleteCommand implements Command {
    private MemberComponent memberComponent;

    public MemberComponentDeleteCommand(MemberComponent memberComponent) {
        this.memberComponent = memberComponent;
    }

    @Override
    public void execute() {
        memberComponent.remove();
    }

    @Override
    public void undo() {
        memberComponent.create();
    }
}
