package de.wwu.md2.android.md2library.controller.action.implementation.customCode;

import de.wwu.md2.android.md2library.controller.action.implementation.customCode.interfaces.Md2CustomCodeTask;
import de.wwu.md2.android.md2library.controller.action.interfaces.Md2Action;
import de.wwu.md2.android.md2library.exception.Md2WidgetNotCreatedException;

/**
 * Calls an action.
 * Represents CallTask element in MD2-DSL.
 * <p/>
 * Created on 24/08/2015
 *
 * @author Fabian Wrede
 * @version 1.0
 * @since 1.0
 */
public class Md2CallTask implements Md2CustomCodeTask {
    /**
     * The Action.
     */
    Md2Action action;

    /**
     * Instantiates a new Md 2 call task.
     *
     * @param action the action
     */
    public Md2CallTask(Md2Action action) {
        this.action = action;
    }

    @Override
    public void execute() throws Md2WidgetNotCreatedException {
        action.execute();
    }
}
