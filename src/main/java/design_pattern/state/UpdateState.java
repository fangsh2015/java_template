package design_pattern.state;

import org.apache.commons.lang3.Validate;

/**
 * Created by Niki on 2019/2/26 18:17
 */
public enum UpdateState {

    UPDATEABLE(() -> Validate.validState(true)),
    READONLY(() -> Validate.validState(false)),
    ;

    private Runnable action;

    private UpdateState(Runnable action) {
        this.action = action;
    }

    public <T> T set(T value) {
        action.run();
        return value;
    }
}
