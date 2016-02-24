package Analysis.RedoUndo;

/**
 * Created by woong on 2016. 2. 11..
 */
public enum CommandKey{
   LOCALBUTTON(null), MEMBERBUTTON(null), FUNCBUTTON(null), ACTIVITY(null), LINK(null), LIB_EVENT(null), LIB_ERROR(null), LIB_ACTIVITY(null);

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
