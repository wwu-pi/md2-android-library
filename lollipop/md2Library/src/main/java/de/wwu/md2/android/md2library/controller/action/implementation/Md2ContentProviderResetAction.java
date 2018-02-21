package de.wwu.md2.android.md2library.controller.action.implementation;

import de.wwu.md2.android.md2library.model.contentProvider.implementation.Md2ContentProviderRegistry;

/**
 * Used to trigger the reset method of a content provider
 * Represents ContentProviderResetAction element in MD2-DSL
 * <p/>
 * Created on 24/08/2015
 *
 * @author Fabian Wrede
 * @version 1.0
 * @since 1.0
 */
public class Md2ContentProviderResetAction extends AbstractMd2Action {
    /**
     * The Content provider.
     */
    protected String contentProvider;

    /**
     * Instantiates a new Md 2 content provider reset action.
     *
     * @param contentProvider the content provider
     */
    public Md2ContentProviderResetAction(String contentProvider) {
        super("Md2ContentProviderResetAction" + contentProvider);
        this.contentProvider = contentProvider;
    }

    @Override
    public void execute() {
        if(Md2ContentProviderRegistry.getInstance().getContentProvider(contentProvider) != null) {
            Md2ContentProviderRegistry.getInstance().getContentProvider(contentProvider).reset();
        } else if (Md2ContentProviderRegistry.getInstance().getContentMultiProvider(contentProvider) != null){
            Md2ContentProviderRegistry.getInstance().getContentMultiProvider(contentProvider).reset();
        }
    }
}
