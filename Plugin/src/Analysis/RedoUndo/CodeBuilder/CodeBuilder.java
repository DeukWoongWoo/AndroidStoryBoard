package Analysis.RedoUndo.CodeBuilder;

/**
 * Created by woong on 2016. 2. 12..
 */
public class CodeBuilder {
    public static FindBuilder Component(Type type){
        return new FindBuilder(type);
    }

    // TODO: 2016. 2. 12. 함수생성 및 함수호출 코드 빌더 추가

}
