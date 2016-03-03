package Analysis.RedoUndo;

import Analysis.RedoUndo.CodeBuilder.Type;
import Analysis.RedoUndo.Command.*;
import Analysis.RedoUndo.CommandObj.*;

import java.util.HashMap;
import java.util.Stack;

/**
 * Created by woong on 2016. 2. 11..
 */
public class CommandManager{
    public volatile static CommandManager instance = null;

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
        lib.forEach(Command::undo);
    }

    public void linkActivity(String id, String from, String to){
        execute(new ActivityLinkCommand(new ActivityLink(id, from, to)));
    }

    public void deleteLinkActivity(String id, String from, String to){
        execute(new ActivityLinkDeleteCommand(new ActivityLink(id, from, to)));
    }

    public void createActivity(String className){
        execute(new ActivityCreateCommand(new ActivityCreate(className)));
    }

    public void deleteActivity(String className){
        execute(new ActivityDeleteCommand(new ActivityCreate(className)));
    }

    public void createLocalComponent(String id, String xml, Type type){
        execute(new LocalComponentCreateCommand(new LocalComponent(id,xml, type)));
    }

    public void createMemberComponent(String id, String xml, Type type){
        execute(new MemberComponentCreateCommand(new MemberComponent(id,xml,type)));
    }

    public void createFucnComponent(String id, String xml, Type type){
        execute(new FuncComponentCreateCommand(new FuncComponent(id, xml, type)));
    }

    public void deleteLocalComponent(String id, String xml, Type type){
        execute(new LocalComponentDeleteCommand(new LocalComponent(id, xml, type)));
    }

    public void deleteMemberComponent(String id, String xml, Type type){
        execute(new MemberComponentDeleteCommand(new MemberComponent(id, xml, type)));
    }

    public void deleteFuncComponent(String id, String xml, Type type){
        execute(new FuncComponentDeleteCommand(new FuncComponent(id, xml, type)));
    }

//    public void createButton(CommandKey key, String id){
//        System.out.println("Create " + key.name() + " ... / ID : " + id);
//        key.setId("R.id."+id);
//        execute(key);
//    }

    private void execute(Command command){
        command.execute();
        undo.push(command);
    }

    public void redo(){
        System.out.println("CommandManager Redo...");
        execute(redo.pop());
    }

    public void undo() {
        System.out.println("CommandManager Undo...");
        Command undoCommand = undo.pop();
        undoCommand.undo();
        redo.push(undoCommand);
    }
}
