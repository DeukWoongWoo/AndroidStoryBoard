package Analysis.RedoUndo;

import Analysis.RedoUndo.Command.CodeCleanCommand;
import Analysis.RedoUndo.Command.CodeWriteCommand;
import Analysis.RedoUndo.Command.Command;
import Analysis.RedoUndo.CommandObj.Code;

import java.util.HashMap;
import java.util.Stack;

/**
 * Created by woong on 2016. 2. 11..
 */
public class CommandManager{

    private HashMap<CommandKey, Command> commandMap = new HashMap<>();

    private Stack<Command> redo = new Stack<>();
    private Stack<Command> undo = new Stack<>();

    // TODO: 2016. 2. 12. singleton 생성하기

    public CommandManager(){
        commandMap.put(CommandKey.WRITE, new CodeWriteCommand(new Code()));
        commandMap.put(CommandKey.CLEAN, new CodeCleanCommand(new Code()));
    }

    public void execute(CommandKey key) {
        System.out.println("Command Execute...");
        Command command = commandMap.get(key);
        command.execute();
        undo.push(command);
    }

    public void execute(CommandKey key, Code code) {
        System.out.println("Command Execute...");
        Command command = commandMap.get(key);
        command.execute();
        undo.push(command);
    }

    public void redo(){
        System.out.println("CommandManager Redo...");
        Command redoCommand = redo.pop();
        redoCommand.execute();
        undo.push(redoCommand);
    }

    public void undo() {
        System.out.println("CommandManager Undo...");
        Command undoCommand = undo.pop();
        undoCommand.undo();
        redo.push(undoCommand);
    }
}
