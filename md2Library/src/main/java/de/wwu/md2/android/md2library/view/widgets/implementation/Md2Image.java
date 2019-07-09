package de.wwu.md2.android.md2library.view.widgets.implementation;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

import de.wwu.md2.android.md2library.controller.eventhandler.implementation.Md2OnChangedHandler;
import de.wwu.md2.android.md2library.controller.eventhandler.implementation.Md2OnClickHandler;
import de.wwu.md2.android.md2library.controller.eventhandler.implementation.Md2OnLongClickHandler;
import de.wwu.md2.android.md2library.model.type.implementation.Md2File;
import de.wwu.md2.android.md2library.model.type.implementation.Md2String;
import de.wwu.md2.android.md2library.model.type.interfaces.Md2Type;
import de.wwu.md2.android.md2library.view.widgets.interfaces.Md2Content;
import de.wwu.md2.android.md2library.view.widgets.interfaces.Md2Widget;

/**
 * Created by Christoph on 08.07.2019.
 */

/**
 * Implementation of Label element in MD2-DSL
 * <p/>
 * Created on 08/07/2019
 *
 * @author Fabian Wrede
 * @version 1.0
 * @since 1.0
 */
public class Md2Image extends ImageView implements Md2Content {

    /**
     * The Widget id.
     */
    protected int widgetId;

    /**
     * The On click handler.
     */
    protected Md2OnClickHandler onClickHandler;

    /**
     * The On click handler.
     */
    protected Md2OnLongClickHandler onLongClickHandler;

    /**
     * The On changed handler.
     */
    protected Md2OnChangedHandler onChangedHandler;

    /**
     * Instantiates a new Md2 label.
     *
     * @param context the context
     */
    public Md2Image(Context context) {
        super(context);
        this.init();
    }

    /**
     * Instantiates a new Md2 label.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public Md2Image(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    /**
     * Instantiates a new Md2 label.
     *
     * @param context      the context
     * @param attrs        the attrs
     * @param defStyleAttr the def style attr
     */
    public Md2Image(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    /**
     * Instantiates a new Md2 label.
     *
     * @param context      the context
     * @param attrs        the attrs
     * @param defStyleAttr the def style attr
     * @param defStyleRes  the def style res
     */
    public Md2Image(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.init();
    }

    /**
     * Copy constructor
     *
     * @param image label to be copied
     */
    private Md2Image(Md2Image image) {
        super(image.getContext());
        this.setWidgetId(image.getWidgetId());
        this.setValue(image.getValue());
        this.setOnChangedHandler(image.getOnChangedHandler());
        this.setOnClickHandler(image.getOnClickHandler());
        this.setOnLongClickHandler(image.getOnLongClickHandler());
        this.setDisabled(image.isDisabled());

    }

    /**
     * Initialization
     */
    protected void init() {
        this.setOnChangedHandler(new Md2OnChangedHandler());
        this.setOnClickHandler(new Md2OnClickHandler());
        this.setOnLongClickHandler(new Md2OnLongClickHandler());
        this.widgetId = -1;
    }

    @Override
    public Md2Type getValue() {
        Bitmap bitmap = ((BitmapDrawable)this.getDrawable()).getBitmap();
        int size = bitmap.getRowBytes() * bitmap.getHeight();
        ByteBuffer byteBuffer = ByteBuffer.allocate(size);
        bitmap.copyPixelsToBuffer(byteBuffer);
        return new Md2File(byteBuffer.array());
    }

    @Override
    public void setValue(Md2Type value) {
        if(value instanceof Md2File){
            byte[] bytes = ((Md2File) value).getPlatformValue();
            Bitmap bitmap = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
            super.setImageBitmap(bitmap);
        }
    }

    @Override
    public Md2OnClickHandler getOnClickHandler() {
        return this.onClickHandler;
    }

    @Override
    public void setOnClickHandler(Md2OnClickHandler onClickHandler) {
        this.onClickHandler = onClickHandler;
        this.setOnClickListener(onClickHandler);
    }

    @Override
    public Md2OnLongClickHandler getOnLongClickHandler() {
        return this.onLongClickHandler;
    }

    @Override
    public boolean setOnLongClickHandler(Md2OnLongClickHandler onLongClickHandler) {
        this.onLongClickHandler = onLongClickHandler;
        this.setOnLongClickListener(onLongClickHandler);
        return true;
    }

    @Override
    public Md2OnChangedHandler getOnChangedHandler() {
        return this.onChangedHandler;
    }

    @Override
    public void setOnChangedHandler(Md2OnChangedHandler onChangedHandler) {
        this.onChangedHandler = onChangedHandler;
    }

    @Override
    public Md2Widget copy() {
        return new Md2Image(this);
    }

    @Override
    public boolean isDisabled() {
        return !isEnabled();
    }

    @Override
    public void setDisabled(boolean disabled) {
        this.setEnabled(!disabled);
    }

    @Override
    public int getWidgetId() {
        return this.widgetId;
    }

    @Override
    public void setWidgetId(int widgetId) {
        this.widgetId = widgetId;
    }

    @Override
    public boolean equals(Object otherWidget) {
        if (otherWidget == null)
            return false;

        if (!(otherWidget instanceof Md2Image))
            return false;

        Md2Image otherMd2Image = (Md2Image) otherWidget;

        return this.getWidgetId() == otherMd2Image.getWidgetId();
    }
}
