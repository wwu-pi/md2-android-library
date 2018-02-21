package de.wwu.md2.android.md2library.view.widgets.implementation;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Spinner;

import de.wwu.md2.android.md2library.controller.eventhandler.implementation.Md2OnChangedHandler;
import de.wwu.md2.android.md2library.controller.eventhandler.implementation.Md2OnClickHandler;
import de.wwu.md2.android.md2library.model.type.implementation.Md2String;
import de.wwu.md2.android.md2library.model.type.interfaces.Md2Type;
import de.wwu.md2.android.md2library.view.widgets.interfaces.Md2Content;
import de.wwu.md2.android.md2library.view.widgets.interfaces.Md2Widget;

/**
 * Implementation of TextInput element in MD2-DSL
 * <p/>
 * Created on 10/07/2015
 *
 * @author Fabian Wrede
 * @version 1.0
 * @since 1.0
 */
public class Md2OptionInput extends Spinner implements Md2Content {
    /**
     * The Widget id.
     */
    protected int widgetId;

    /**
     * The On click handler.
     */
    protected Md2OnClickHandler onClickHandler;

    /**
     * The On changed handler.
     */
    protected Md2OnChangedHandler onChangedHandler;

    /**
     * Instantiates a new Md2 text input.
     *
     * @param context the context
     */
    public Md2OptionInput(Context context) {
        super(context);
        this.init();
    }

    /**
     * Instantiates a new Md2 text input.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public Md2OptionInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    /**
     * Instantiates a new Md2 text input.
     *
     * @param context      the context
     * @param attrs        the attrs
     * @param defStyleAttr the def style attr
     */
    public Md2OptionInput(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    /**
     * Instantiates a new Md 2 text input.
     *
     * @param widgetId     the widget id
     * @param context      the context
     * @param attrs        the attrs
     * @param defStyleAttr the def style attr
     * @param defStyleRes  the def style res
     */
    public Md2OptionInput(String widgetId, Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.init();
    }

    /**
     * Copy constructor
     *
     * @param optionInput optionInput to be copied
     */
    private Md2OptionInput(Md2OptionInput optionInput) {
        super(optionInput.getContext());
        this.widgetId = optionInput.getWidgetId();
        super.setEnabled(optionInput.isEnabled());
        this.setAdapter(optionInput.getAdapter());
        this.onChangedHandler = optionInput.getOnChangedHandler();
        this.onClickHandler = optionInput.getOnClickHandler();
    }

    /**
     * Initialization
     */
    protected void init() {
        this.setOnChangedHandler(new Md2OnChangedHandler());
        this.setOnClickHandler(new Md2OnClickHandler());
        this.widgetId = -1;
    }

    @Override
    public Md2Type getValue() {
        return new Md2String(super.getSelectedItem().toString());
    }

    @Override
    public void setValue(Md2Type value) {
        if (value == null) {
            super.setSelection(0);
            return;
        }

        this.setSelection(getIndex(value.toString()));
    }

    private int getIndex(String myString)
    {
        int index = 0;

        for (int i=0;i<this.getCount();i++){
            if (this.getItemAtPosition(i).toString().equalsIgnoreCase(myString)){
                index = i;
                break;
            }
        }
        return index;
    }

    @Override
    public Md2OnClickHandler getOnClickHandler() {
        return this.onClickHandler;
    }

    @Override
    public void setOnClickHandler(Md2OnClickHandler onClickHandler) {
        this.onClickHandler = onClickHandler;
        this.setOnItemSelectedListener(onClickHandler);
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
        return new Md2OptionInput(this);
    }

    @Override
    public boolean isDisabled() {
        return super.isEnabled();//this.disabled;
    }

    @Override
    public void setDisabled(boolean disabled) {
        //this.disabled = disabled;
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

        if (!(otherWidget instanceof Md2TextInput))
            return false;

        Md2TextInput otherMd2TextInput = (Md2TextInput) otherWidget;

        return this.getWidgetId() == otherMd2TextInput.getWidgetId();
    }
}
