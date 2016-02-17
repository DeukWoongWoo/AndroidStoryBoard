package Analysis.RedoUndo;

/**
 * Created by woong on 2016. 2. 11..
 */
public class CodeDriver {
    public CodeDriver(){
        CommandManager commandManager = CommandManager.getInstance();

//        CommandKey.LOCALBUTTON.setId("R.id.button");
//        commandManager.execute(CommandKey.LOCALBUTTON);

        CommandKey.LOCALBUTTON.setId("R.id.button1");
        commandManager.execute(CommandKey.LOCALBUTTON);

        CommandKey.MEMBERBUTTON.setId("R.id.button2");
        commandManager.execute(CommandKey.MEMBERBUTTON);

//        commandManager.undo();
//        commandManager.redo();
//        commandManager.execute(CommandKey.WRITE);
//        commandManager.undo();
//        commandManager.execute(CommandKey.CLEAN);
//        commandManager.undo();
//        commandManager.redo();

    }
}