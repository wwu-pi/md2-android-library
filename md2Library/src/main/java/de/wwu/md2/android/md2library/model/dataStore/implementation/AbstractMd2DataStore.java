package de.wwu.md2.android.md2library.model.dataStore.implementation;
        import java.sql.Timestamp;

        import de.wwu.md2.android.md2library.model.contentProvider.interfaces.Md2ContentProvider;
        import de.wwu.md2.android.md2library.model.dataStore.AtomicExpression;
        import de.wwu.md2.android.md2library.model.dataStore.CombinedExpression;
        import de.wwu.md2.android.md2library.model.dataStore.Filter;
        import de.wwu.md2.android.md2library.model.dataStore.Junction;
        import de.wwu.md2.android.md2library.model.dataStore.Operator;
        import de.wwu.md2.android.md2library.model.dataStore.interfaces.Md2DataStore;
        import de.wwu.md2.android.md2library.model.type.interfaces.Md2Entity;

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


        public void query(Filter filter, Timestamp modifiedDate){
                if(modifiedDate!=null){
                    AtomicExpression atomicExpression=   new AtomicExpression("MODIFIED_TIMESTAMP", Operator.GREATER, "'"+modifiedDate.toString()+"'");

                if(filter!=null){
                 filter.setFilterTree( new CombinedExpression(filter.getFilterTree(), Junction.AND,atomicExpression ));
                }
                else{
                     filter = new Filter(atomicExpression);

                }}
             this.query(filter);
        }

}