package Analysis.RedoUndo;

import Analysis.RedoUndo.Command.*;
import Analysis.RedoUndo.CommandObj.ActivityLibCreate;
import Analysis.RedoUndo.CommandObj.ActivityLink;
import Analysis.RedoUndo.CommandObj.ErrorLibCreate;
import Analysis.RedoUndo.CommandObj.EventLibCreate;

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
    private Stack<Command> lib = new Stack<>();

    public static CommandManager getInstance(){
        if(instance == null) {
            synchronized (CommandManager.class) {
                if (instance == null) instance = new CommandManager();
            }
        }
        return instance;
    }

    public CommandManager(){
        commandMap.put(CommandKey.LOCALBUTTON, new LocalButtonCreateCommand());
        commandMap.put(CommandKey.MEMBERBUTTON, new MemberButtonCreateCommand());
        commandMap.put(CommandKey.FUNCBUTTON, new FuncButtonCreateCommand());
        commandMap.put(CommandKey.ACTIVITY, new ActivityCreateCommand());
    }

    public void addLibError(String id, String xml){
        Command command = new ErrorLibCreateCommand(new ErrorLibCreate(id, xml));
        command.execute();
        lib.add(command);
    }

    public void addLibEvent(String id, String xml){
        Command command = new EventLibCreateCommand(new EventLibCreate(id, xml));
        command.execute();
        lib.add(command);
    }

    public void addLibActivity(String xml){
        Command command = new ActivityLibCreateCommand(new ActivityLibCreate(xml));
        command.execute();
        lib.add(command);
    }

    public void deleteLib(){
        
    }

    public void linkActivity(String id, String from, String to){
        commandMap.put(CommandKey.LINK, new ActivityLinkCommand(new ActivityLink(id, from, to)));
        execute(CommandKey.LINK);
    }

    public void createActivity(String className){
        CommandKey.ACTIVITY.setId(className);
        execute(CommandKey.ACTIVITY);
    }

    public void createButton(CommandKey key, String id){
        System.out.println("Create " + key.name() + " ... / ID : " + id);
        key.setId("R.id."+id);
        execute(key);
    }

    private void execute(CommandKey key) {
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
