package de.wwu.md2.android.md2library.controller.action.implementation;

import de.wwu.md2.android.md2library.model.contentProvider.implementation.AbstractMd2ContentProvider;
import de.wwu.md2.android.md2library.model.contentProvider.implementation.AbstractMd2MultiContentProvider;
import de.wwu.md2.android.md2library.model.contentProvider.implementation.Md2ContentProviderRegistry;
import de.wwu.md2.android.md2library.model.type.interfaces.Md2Entity;

/**
 * Created by Alexander on 25.06.2017.
 */

public class Md2ContentProviderGetActiveAction extends AbstractMd2Action{


    private String target;
    private String source;

    public Md2ContentProviderGetActiveAction(String target, String source){
        super("Md2ContentProviderGetActiveAction"+target);
        this.target = target;
        this.source = source;
    }

    public void execute(){
        AbstractMd2MultiContentProvider scp = (AbstractMd2MultiContentProvider) Md2ContentProviderRegistry.getInstance().getContentMultiProvider(source);
        AbstractMd2ContentProvider tcp = (AbstractMd2ContentProvider) Md2ContentProviderRegistry.getInstance().getContentProvider(target);
        Md2Entity item = scp.getContentsList().get(scp.getCurrentIndex());
        tcp.overwriteContent(item); // includes notifying handlers
    }

}