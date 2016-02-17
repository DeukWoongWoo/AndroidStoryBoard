package Analysis.RedoUndo;

/**
 * Created by woong on 2016. 2. 11..
 */
public enum CommandKey{
    WRITE(null), CLEAN(null), LOCALBUTTON(null);

    String id;
    CommandKey(String id){
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
