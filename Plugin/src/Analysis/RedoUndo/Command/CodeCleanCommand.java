package Analysis.RedoUndo.Command;

import Analysis.RedoUndo.CommandObj.Code;

/**
 * Created by woong on 2016. 2. 11..
 */
public class CodeCleanCommand implements Command{

    private Code code;

    public CodeCleanCommand(Code code){
        this.code = code;
    }

    @Override
    public void execute() {
        code.remove();
    }

    @Override
    public void undo() {
        code.create();
    }
}
