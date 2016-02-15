package Analysis.RedoUndo;

import Analysis.RedoUndo.CodeBuilder.CodeBuilder;
import Analysis.RedoUndo.CodeBuilder.Type;

/**
 * Created by woong on 2016. 2. 11..
 */
public class Code {
    public void create(){
        System.out.println("Code Create...");

        String str = CodeBuilder.Component(Type.Button).findViewById("R.id.button").build();
        System.out.println(">> code : " + str);
    }
    public void remove(){
        System.out.println("Code Remove...");
    }
}
