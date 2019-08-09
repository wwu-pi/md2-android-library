package de.wwu.md2.android.md2library.model.contentProvider.implementation;

import java.sql.Timestamp;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import de.wwu.md2.android.md2library.controller.eventhandler.implementation.Md2OnAttributeChangedHandler;
import de.wwu.md2.android.md2library.model.contentProvider.interfaces.Md2ContentProvider;
import de.wwu.md2.android.md2library.model.dataStore.Filter;
import de.wwu.md2.android.md2library.model.dataStore.interfaces.Md2DataStore;
import de.wwu.md2.android.md2library.model.dataStore.interfaces.Md2LocalStore;
import de.wwu.md2.android.md2library.model.type.interfaces.Md2Entity;
import de.wwu.md2.android.md2library.model.type.interfaces.Md2Type;

/**
 * Abstract super class for content providers.
 * Concrete sub-classes are generated.
 * Represents the ContentProvider element in MD2-DSL
 * <p/>
 * Created on 10/07/2015
 *
 * @author Fabian Wrede
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractMd2ContentProvider implements Md2ContentProvider {
    protected Filter filter;
    /**
     * The Key.
     */
    protected String key;
    /**
     * The SyncTimestamp
     *
     */
    protected Timestamp syncTimestamp;

    /**
     * The Content.
     */
    protected Md2Entity content;
    /**
     * The Backup.
     */
    protected Md2Entity backup;
    /**
     * The Md 2 data store.
     */
    protected Md2DataStore md2DataStore;
    /**
     * The Attribute changed event handlers.
     */
//protected HashMap<String, Md2Type> observedAttributes;
    protected HashMap<String, Md2OnAttributeChangedHandler> attributeChangedEventHandlers;

    /**
     * Instantiates a new Abstract md 2 content provider.
     *
     * @param key          the key
     * @param content      the content
     * @param md2DataStore the md 2 data store
     */
    public AbstractMd2ContentProvider(String key, Md2Entity content, Md2DataStore md2DataStore) {
        this.key = key;
        attributeChangedEventHandlers = new HashMap<>();
        //observedAttributes = new HashMap<>();

        this.md2DataStore = md2DataStore;
        md2DataStore.setContentProvider(this);

        setContent(content);
    }

    public String getKey() {
        return this.key;
    }

    @Override
    public Md2Entity getContent() {
        return content;
    }

    @Override
    public void setContent(Md2Entity content) {
        if (content != null) {
            this.content = content;
            this.backup = (Md2Entity) content.clone();
            this.load();
        }
    }

    @Override
    public void overwriteContent(Md2Entity content){
        if (content != null) {
            this.content = content;
        }
        callAllHandlers();
    }

    @Override
    public void overwriteContent(List<Md2Entity> content){
        if ((content != null)&&(content.isEmpty()==false)) {
            this.content = content.get(0);
        }
        callAllHandlers();
    }

    @Override
    public void updateContent(List<Md2Entity> updates) {

        if ((updates != null)&&(updates.isEmpty() == false)) {

            for (Md2Entity entity1 : updates) {
                Timestamp updateTimestamp = entity1.getModifiedDate();

                if(updateTimestamp.after(content.getModifiedDate())){
                    this.content = entity1;
                }
            }
        }


    }
    public void purgeContent(List<Md2Entity> deleted){
        if ((deleted != null) && (deleted.isEmpty() == false)) {

            for(Md2Entity entity2 : deleted) {

                Timestamp contentTimestamp = entity2.getModifiedDate();

                if (contentTimestamp.after(syncTimestamp)) {
                    this.content = null;
                }
            }
        }
    }

    /** Notify all handlers about changed attributes */
    protected void callAllHandlers(){
        for (String key: this.attributeChangedEventHandlers.keySet()){
            Md2OnAttributeChangedHandler handler = getOnAttributeChangedHandler(key);
            handler.onChange(key);
        }
    }
    @Override
    public void registerAttributeOnChangeHandler(String attribute, Md2OnAttributeChangedHandler onAttributeChangedHandler) {
        //observedAttributes.put(attribute, this.getValue(attribute));
        attributeChangedEventHandlers.put(attribute, onAttributeChangedHandler);
    }

    @Override
    public void unregisterAttributeOnChangeHandler(String attribute) {
        //this.observedAttributes.remove(attribute);
        this.attributeChangedEventHandlers.remove(attribute);
    }

    @Override
    public Md2OnAttributeChangedHandler getOnAttributeChangedHandler(String attribute) {
        return this.attributeChangedEventHandlers.get(attribute);
    }

    @Override
    public Md2Type getValue(String attribute) {
        if (content != null) {
            return this.content.get(attribute);
        }
        return null;
    }

    @Override
    public void setValue(String attribute, Md2Type value) {
        if (content == null) {
            return;
        }

        // set only if value is different to current value
        if ((this.getValue(attribute) == null && value != null) || !this.getValue(attribute).equals(value)) {
            this.content.set(attribute, value);
            Md2OnAttributeChangedHandler handler = this.attributeChangedEventHandlers.get(attribute);
            if (handler != null) {
                handler.onChange(attribute);
            }
        }
    }

    @Override
    public void reset() {
        newEntity();
    }

    @Override
    public void resetLocal(){
        newEntity();
    }

    @Override
    public void load() {
//        if (content == null | md2DataStore == null)
//            return;
//
//        // case: entity has id
//        if (this.content.getId() > 0) {
//            existsInDataStore = true;
//            internalId = this.content.getId();
//            return;
//        }
//
//        // case: entity has no id
//       // long id = md2DataStore.getInternalId(this.content);
//        long id = -1;
//        if (id == -1) {
//            existsInDataStore = false;
//            internalId = -1;
//        } else {
//            existsInDataStore = true;
//            internalId = id;
//            this.content.setId(id);
//
//        }
        //this.syncTimestamp = new Timestamp(System.currentTimeMillis());
        //md2DataStore.query(this.filter);
        md2DataStore.load(content.getId(), content.getClass());
    }

    @Override
    public void save() {
        if (content == null || md2DataStore == null)
            return;
        if (content.getId()>0)
            // update existing element
            md2DataStore.put(content.getId(), this.content);
        else {
            // new element
            md2DataStore.put(this.content);
        }
        this.backup = (Md2Entity) content.clone();
    }

    @Override
    public void update(){
        // Nothing to load for local stores
        if(this.md2DataStore instanceof Md2LocalStore) return;

        Timestamp oldStamp = syncTimestamp;
        this.syncTimestamp = new Timestamp(System.currentTimeMillis());
        md2DataStore.query(this.filter, oldStamp);
    }

    @Override
    public void remove() {
        if (content == null || md2DataStore == null) return;

        md2DataStore.remove(content.getId(), content);
    }

    public abstract void newEntity();


    public void notfiyOnChangeHandlers(){
        Collection<Md2OnAttributeChangedHandler> coll = attributeChangedEventHandlers.values();
        for(Md2OnAttributeChangedHandler c : coll){
            c.execute();
        }
    }

    public void notifyChangeHandler(String name){
        Md2OnAttributeChangedHandler handler = this.attributeChangedEventHandlers.get(name);
        if (handler != null) {
            handler.onChange(name);
        }
    }
}
