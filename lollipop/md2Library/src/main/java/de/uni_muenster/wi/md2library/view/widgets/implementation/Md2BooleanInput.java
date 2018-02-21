package de.uni_muenster.wi.md2library.view.widgets.implementation;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.Switch;

import de.uni_muenster.wi.md2library.controller.eventhandler.implementation.Md2OnChangedHandler;
import de.uni_muenster.wi.md2library.controller.eventhandler.implementation.Md2OnClickHandler;
import de.uni_muenster.wi.md2library.model.type.implementation.Md2String;
import de.uni_muenster.wi.md2library.model.type.interfaces.Md2Type;
import de.uni_muenster.wi.md2library.view.widgets.interfaces.Md2Content;
import de.uni_muenster.wi.md2library.view.widgets.interfaces.Md2Widget;

/**
 * Implementation of BooleanInput element in MD2-DSL
 * <p/>
 * Created on 20.02.2018
 *
 * @author Christoph Rieger
 * @version 1.0
 * @since 1.0
 */
public class Md2BooleanInput extends Switch implements Md2Content {
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
     * Instantiates a new Md2 boolean input.
     *
     * @param context the context
     */
    public Md2BooleanInput(Context context) {
        super(context);
        this.init();
    }

    /**
     * Instantiates a new Md2 boolean input.
     *
     * @param context the context
     * @param attrs   the attrs
     */
    public Md2BooleanInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.init();
    }

    /**
     * Instantiates a new Md2 boolean input.
     *
     * @param context      the context
     * @param attrs        the attrs
     * @param defStyleAttr the def style attr
     */
    public Md2BooleanInput(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.init();
    }

    /**
     * Instantiates a new Md 2 boolean input.
     *
     * @param widgetId     the widget id
     * @param context      the context
     * @param attrs        the attrs
     * @param defStyleAttr the def style attr
     * @param defStyleRes  the def style res
     */
    public Md2BooleanInput(String widgetId, Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.init();
    }

    /**
     * Copy constructor
     *
     * @param booleanInput booleanInput to be copied
     */
    private Md2BooleanInput(Md2BooleanInput booleanInput) {
        super(booleanInput.getContext());
        this.widgetId = booleanInput.getWidgetId();
        super.setEnabled(booleanInput.isEnabled());
        this.setText(booleanInput.getText());
        this.onChangedHandler = booleanInput.getOnChangedHandler();
        this.onClickHandler = booleanInput.getOnClickHandler();
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
        return new Md2String(super.getText().toString());
    }

    @Override
    public void setValue(Md2Type value) {
        if (value == null) {
            super.setChecked(false);
            return;
        }

        String checked = "false";
        if(isChecked()) checked = "true";

        if (!checked.equals(value.toString().toLowerCase())) {
            if(value.toString().toLowerCase().equals("false")){
                super.setChecked(false);
            } else if (value.toString().toLowerCase().equals("true")) {
                super.setChecked(true);
            }
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
    public Md2OnChangedHandler getOnChangedHandler() {
        return this.onChangedHandler;
    }

    @Override
    public void setOnChangedHandler(Md2OnChangedHandler onChangedHandler) {
        this.onChangedHandler = onChangedHandler;
        this.addTextChangedListener(onChangedHandler);
    }

    @Override
    public Md2Widget copy() {
        return new Md2BooleanInput(this);
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

        if (!(otherWidget instanceof Md2BooleanInput))
            return false;

        Md2BooleanInput otherMd2BooleanInput = (Md2BooleanInput) otherWidget;

        return this.getWidgetId() == otherMd2BooleanInput.getWidgetId();
    }
}
