package Analysis.RedoUndo;

import Analysis.RedoUndo.Command.*;
import Analysis.RedoUndo.CommandObj.Code;

import java.util.HashMap;
import java.util.Stack;

/**
 * Created by woong on 2016. 2. 11..
 */
public class CommandManager{
    public volatile static CommandManager instance = null;

    private HashMap<CommandKey, Command> commandMap = new HashMap<>();

    private Stack<Command> redo = new Stack<>();
    private Stack<Command> undo = new Stack<>();

    public static CommandManager getInstance(){
        if(instance == null) {
            synchronized (CommandManager.class) {
                if (instance == null) instance = new CommandManager();
            }
        }
        return instance;
    }

    public CommandManager(){
        commandMap.put(CommandKey.WRITE, new CodeWriteCommand(new Code()));
        commandMap.put(CommandKey.CLEAN, new CodeCleanCommand(new Code()));
        commandMap.put(CommandKey.LOCALBUTTON, new LocalButtonCreateCommand());
        commandMap.put(CommandKey.MEMBERBUTTON, new MemberButtonCreateCommand());
        commandMap.put(CommandKey.FUNCBUTTON, new FuncButtonCreateCommand());
    }

    public void createButton(CommandKey key, String id){
        System.out.println("Create " + key.name() + " ... / ID : " + id);
        key.setId("R.id."+id);
        execute(key);
    }

    public void execute(CommandKey key) {
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
