package de.uni_muenster.wi.md2library.controller.action.implementation;

import de.uni_muenster.wi.md2library.model.contentProvider.implementation.Md2ContentProviderRegistry;

/**
 * Created by c_rieg01 on 13.10.2017.
 */

/**
 * reset to state of data that is permanently stored locally or in a remote location and discard temporary changes
 */
public class Md2ContentProviderResetLocalAction extends AbstractMd2Action {
    /**
     * The Content provider.
     */
    protected String contentProvider;

    /**
     * Instantiates a new Md 2 content provider reset action.
     *
     * @param contentProvider the content provider
     */
    public Md2ContentProviderResetLocalAction(String contentProvider) {
        super("Md2ContentProviderResetAction" + contentProvider);
        this.contentProvider = contentProvider;
    }

    @Override
    public void execute() {
        if(Md2ContentProviderRegistry.getInstance().getContentProvider(contentProvider) != null) {
            Md2ContentProviderRegistry.getInstance().getContentProvider(contentProvider).load();

        } else if (Md2ContentProviderRegistry.getInstance().getContentMultiProvider(contentProvider) != null){
            Md2ContentProviderRegistry.getInstance().getContentMultiProvider(contentProvider).load();
        }
    }
}