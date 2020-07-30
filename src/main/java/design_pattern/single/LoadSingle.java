package design_pattern.single;

/**
 * Created by Niki on 2018/5/30 11:05
 */
public class LoadSingle {
    private LoadSingle(){}

    private static LoadSingle loadSingle = new LoadSingle();

    public static LoadSingle getInstance() {
        return loadSingle;
    }
}
