package de.wwu.md2.android.md2library.model.type.implementation;

import de.wwu.md2.android.md2library.model.type.interfaces.Md2NumericType;
import de.wwu.md2.android.md2library.model.type.interfaces.Md2TemporalType;
import de.wwu.md2.android.md2library.model.type.interfaces.Md2Type;

/**
 * Abstract superclass class for numeric types.
 * <p/>
 * Created on 15/03/2018
 *
 * @author Christoph Rieger
 * @version 1.0
 * @since 1.0
 */
public abstract class AbstractMd2TemporalType extends AbstractMd2DataType implements Md2TemporalType {

    /**
     * Instantiates a new Abstract md 2 numeric type.
     */
    public AbstractMd2TemporalType() {
        super();
    }

    /**
     * Instantiates a new Abstract md 2 temporal type.
     *
     * @param platformValue the platform value
     */
    public AbstractMd2TemporalType(Object platformValue) {
        super(platformValue);
    }

    @Override
    public boolean equals(Md2Type value) {
        return super.equals(value);
    }

    @Override
    public Md2String getString() {
        return super.getString();
    }

    @Override
    public java.lang.String toString() {
        return super.toString();
    }
}