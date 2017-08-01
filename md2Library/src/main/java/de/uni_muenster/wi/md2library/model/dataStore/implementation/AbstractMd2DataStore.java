package de.uni_muenster.wi.md2library.model.dataStore.implementation;
        import de.uni_muenster.wi.md2library.model.contentProvider.interfaces.Md2ContentProvider;
        import de.uni_muenster.wi.md2library.model.dataStore.interfaces.Md2DataStore;
        import de.uni_muenster.wi.md2library.model.type.interfaces.Md2Entity;

/**
 * Abstract super class for data stores.
 * TODO: might be removed if not required.
 * <p/>
 * Created on 08/07/2015.
 *
 * @author Fabian Wrede
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractMd2DataStore<T extends Md2Entity> implements Md2DataStore<T> {
        protected Md2ContentProvider contentProvider;

        public Md2ContentProvider getContentProvider() {
                return contentProvider;
        }

        public void setContentProvider(Md2ContentProvider contentProvider) {
                this.contentProvider = contentProvider;
        }



}