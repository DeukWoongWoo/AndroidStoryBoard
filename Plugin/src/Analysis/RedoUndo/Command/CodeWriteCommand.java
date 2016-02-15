package Analysis.RedoUndo.Command;

import Analysis.RedoUndo.Code;

/**
 * Created by woong on 2016. 2. 11..
 */
public class CodeWriteCommand implements Command{
    private Code code;
    public CodeWriteCommand(Code code){
        this.code = code;
    }

    @Override
    public void execute() {
        code.create();
    }

    @Override
    public void undo() {
        code.remove();
    }
}
