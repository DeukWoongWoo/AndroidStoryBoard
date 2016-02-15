package Analysis.RedoUndo.Command;

/**
 * Created by woong on 2016. 2. 11..
 */
public interface Command {
    public void execute();
    public void undo();
}

