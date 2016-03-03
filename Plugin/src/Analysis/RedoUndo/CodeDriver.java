package Analysis.RedoUndo;

import Analysis.RedoUndo.CodeBuilder.Type;

/**
 * Created by woong on 2016. 2. 11..
 */
public class CodeDriver {
    public CodeDriver(){
        CommandManager commandManager = CommandManager.getInstance();

//        commandManager.linkActivity("button","activity_main","SecondActivity");
//        commandManager.undo();
//        commandManager.redo();
//        commandManager.deleteLinkActivity("button","activity_main","SecondActivity");

//        commandManager.createLocalComponent("textView1", "activity_main", Type.TextView);
        commandManager.createLocalComponent("button11", "activity_main", Type.Button);
//        commandManager.deleteLocalComponent("button1", "activity_main", Type.Button);

//        commandManager.createMemberComponent("button1","activity_main", Type.Button);
//        commandManager.deleteMemberComponent("button1","activity_main", Type.Button);

//        commandManager.createFucnComponent("button1","activity_main", Type.Button);
//        commandManager.createFucnComponent("textView1","activity_main", Type.TextView);

//        commandManager.createActivity("TestActivity");
//        commandManager.deleteActivity("TestActivity");

//        commandManager.linkActivity("button","activity_main","TestActivity");

//        commandManager.addLibEvent("button","activity_main");
//        commandManager.addLibError("button","activity_main");
//        commandManager.addLibActivity("activity_main");

//        commandManager.deleteLib();
    }
}