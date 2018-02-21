package de.wwu.md2.android.md2library.controller.action.implementation;

import de.wwu.md2.android.md2library.model.contentProvider.implementation.Md2ContentProviderRegistry;

/**
 * Reset content provider to discard changes.
 */

public class Md2ContentProviderResetLocalAction extends AbstractMd2Action {
        protected String contentProvider;

        public Md2ContentProviderResetLocalAction(String contentProvider)
        {
            super("Md2ContentProviderResetLocalAction" + contentProvider);
            this.contentProvider = contentProvider;
        }

    public void execute() {
        if (Md2ContentProviderRegistry.getInstance().getContentProvider(contentProvider) != null) {
            Md2ContentProviderRegistry.getInstance().getContentProvider(contentProvider).load();
        }
        else if (Md2ContentProviderRegistry.getInstance().getContentMultiProvider(contentProvider) != null) {
            Md2ContentProviderRegistry.getInstance().getContentMultiProvider(contentProvider).load();
        }
    }
}