package Analysis.RedoUndo;

/**
 * Created by woong on 2016. 2. 11..
 */
public class CodeDriver {
    public CodeDriver(){
        CommandManager commandManager = CommandManager.getInstance();

//        commandManager.createButton(CommandKey.LOCALBUTTON,"button1");
        commandManager.createButton(CommandKey.MEMBERBUTTON, "button2");
//        commandManager.createButton(CommandKey.FUNCBUTTON, "button3");

        commandManager.createActivity("TestActivity");

        commandManager.linkActivity("button","activity_main","TestActivity");

//        commandManager.undo();
//        commandManager.redo();
//        commandManager.execute(CommandKey.WRITE);
//        commandManager.undo();
//        commandManager.execute(CommandKey.CLEAN);
//        commandManager.undo();
//        commandManager.redo();

    }
}