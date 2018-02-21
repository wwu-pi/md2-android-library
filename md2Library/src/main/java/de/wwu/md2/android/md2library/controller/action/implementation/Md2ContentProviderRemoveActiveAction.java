package de.wwu.md2.android.md2library.controller.action.implementation;

import de.wwu.md2.android.md2library.model.contentProvider.implementation.AbstractMd2MultiContentProvider;
import de.wwu.md2.android.md2library.model.contentProvider.implementation.Md2ContentProviderRegistry;

/**
 * Created by Alexander on 25.06.2017.
 */

public class Md2ContentProviderRemoveActiveAction extends AbstractMd2Action{

    String contentProvider;

    public Md2ContentProviderRemoveActiveAction(String cp){
        super("Md2ContentProviderRemoveActiveAction"+cp);
        contentProvider = cp;
    }

    public void execute(){
        AbstractMd2MultiContentProvider cp = (AbstractMd2MultiContentProvider) Md2ContentProviderRegistry.getInstance().getContentMultiProvider(contentProvider);
        cp.remove(cp.getCurrentIndex());
    }

}