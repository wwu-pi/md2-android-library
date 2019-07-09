package de.wwu.md2.android.md2library.model.type.implementation;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URI;

import de.wwu.md2.android.md2library.model.type.interfaces.Md2Type;

/**
 * {@inheritDoc}
 * <p/>
 * Implementation of data type string in MD2-DSL.
 * Uses string as platform representation.
 * <p/>
 * Created on 02/07/2019
 *
 * @author Christoph Rieger
 * @version 1.0
 * @since 1.0
 */
public class Md2File extends AbstractMd2DataType {

    /**
     * Instantiates a new Md2 string.
     */
    public Md2File() {
        super();
    }

    /**
     * Instantiates a new Md2 string.
     *
     * @param value the value
     */
    public Md2File(Object value) {
        if(value instanceof Bitmap){
            // resolve value
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ((Bitmap) value).compress(Bitmap.CompressFormat.PNG, 0 /*ignored for PNG*/, bos);
            this.platformValue= bos.toByteArray();
        } else if (value != null) {
            this.platformValue = value;
            this.isSet = true;
        } else {
            this.platformValue = null;
            this.isSet = false;
        }
    }

    @Override
    public byte[] getPlatformValue() {
        return (byte[]) super.getPlatformValue();
    }

    @Override
    public Md2Type clone() {
        return new Md2File(this.getPlatformValue());
    }

    @Override
    public boolean equals(Md2Type value) {
        if (!super.equals(value))
            return false;

        if (!(value instanceof Md2File) ||this.getPlatformValue()==null) {
            return false;
        }

        Md2File Md2FileValue = (Md2File) value;

        return this.getPlatformValue().equals(Md2FileValue.getPlatformValue());
    }

    @Override
    public Md2String getString() {
        return new Md2String(this.toString());
    }

    @Override
    public java.lang.String toString() {
        return super.toString();
    }
}
