package de.wwu.md2.android.md2library.controller.action.implementation;

import de.wwu.md2.android.md2library.model.contentProvider.implementation.AbstractMd2MultiContentProvider;
import de.wwu.md2.android.md2library.model.contentProvider.implementation.Md2ContentProviderRegistry;
import de.wwu.md2.android.md2library.model.dataStore.Filter;

/**
 * Created by felix_000 on 07.05.2017.
 */

public class Md2ContentProviderRemoveAction {

    private String contentProvider;
    private Filter conditions;

    public Md2ContentProviderRemoveAction(String contentProvider) {
        this.contentProvider=contentProvider;
    }

    public void execute() {
        AbstractMd2MultiContentProvider cp =(AbstractMd2MultiContentProvider) Md2ContentProviderRegistry.getInstance().getContentMultiProvider(contentProvider);
        cp.remove(conditions);
    }

}
