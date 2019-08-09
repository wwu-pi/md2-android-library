package de.wwu.md2.android.md2library.model.contentProvider.interfaces;

import java.util.List;

import de.wwu.md2.android.md2library.controller.eventhandler.implementation.Md2OnAttributeChangedHandler;
import de.wwu.md2.android.md2library.model.type.interfaces.Md2Entity;
import de.wwu.md2.android.md2library.model.type.interfaces.Md2Type;

/**
 * Interface definition for content providers.
 * Represents the ContentProvider element in MD2-DSL
 * <p/>
 * Created on 10/07/2015
 *
 * @author Fabian Wrede
 * @version 1.0
 * @since 1.0
 */
public interface Md2ContentProvider {
    /**
     * Gets key.
     *
     * @return the key
     */
    String getKey();

    /**
     * Gets content.
     *
     * @return the content
     */
    Md2Entity getContent();

    /**
     * Sets content.
     *
     * @param content the content
     */
    void setContent(Md2Entity content);

    /** set content directly without loading it from the database */
    void overwriteContent(Md2Entity content);
    void overwriteContent(List<Md2Entity> content);

    /**
     * Register attribute on change handler.
     *
     * @param attribute                 the attribute
     * @param onAttributeChangedHandler the on attribute changed handler
     */
    void registerAttributeOnChangeHandler(String attribute, Md2OnAttributeChangedHandler onAttributeChangedHandler);

    /**
     * Unregister attribute on change handler.
     *
     * @param attribute the attribute
     */
    void unregisterAttributeOnChangeHandler(String attribute);

    /**
     * Gets on attribute changed handler.
     *
     * @param attribute the attribute
     * @return the on attribute changed handler
     */
    Md2OnAttributeChangedHandler getOnAttributeChangedHandler(String attribute);

    /**
     * Gets value.
     *
     * @param attribute the attribute
     * @return the value
     */
    Md2Type getValue(String attribute);

    /**
     * Sets value.
     *
     * @param attribute the attribute
     * @param value     the value
     */
    void setValue(String attribute, Md2Type value);

    /**
     * Reset content provider to empty element.
     */
    void reset();

    /**
     * Reset local content provider to the remote element.
     */
    void resetLocal();

    /**
     * Load from the database.
     */
    void load();

    /**
     * Save a new or existing element to the database.
     */
    void save();

    /**
     * Remove from database.
     */
    void remove();

    /** Replace current version of content from the most recent version from the datastore */
    void update();

    /** Replace current version of content from the most recent version in the list of elements */
    void updateContent(List<Md2Entity> updates);
    /** Remove current version of content if it has been removed in the meantime */
    void purgeContent(List<Md2Entity> updates);
}
