package Analysis.RedoUndo;

/**
 * Created by woong on 2016. 2. 11..
 */
public class CodeDriver {
    public CodeDriver(){
        CommandManager commandManager = new CommandManager();

        commandManager.execute(CommandKey.WRITE);
        commandManager.undo();
        commandManager.execute(CommandKey.CLEAN);
        commandManager.undo();
        commandManager.redo();
    }
}