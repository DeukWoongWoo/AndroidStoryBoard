package Analysis.Database.DtatTransferObject;

/**
 * Created by woong on 2016-01-21.
 */
public class DTO <T> {
    private T t;

    public DTO(T t){
        this.t = t;
    }

    public T getDTO() { return t; }
}
