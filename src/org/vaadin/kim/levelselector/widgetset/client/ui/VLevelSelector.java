package org.vaadin.kim.levelselector.widgetset.client.ui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.terminal.gwt.client.ApplicationConnection;
import com.vaadin.terminal.gwt.client.Paintable;
import com.vaadin.terminal.gwt.client.UIDL;

public class VLevelSelector extends FlowPanel implements Paintable {

    /** Set the tagname used to statically resolve widget from UIDL. */
    public static final String TAGNAME = "levelselector";

    /** Set the CSS class name to allow styling. */
    public static final String CLASSNAME = "v-" + TAGNAME;

    protected int maxValue = 1;

    protected int value = 0;

    protected int minValue = 1;

    protected int elementWidth = 0;

    protected String id;

    protected boolean immediate = false;

    protected List<SelectionBox> boxes = new ArrayList<SelectionBox>();

    /** Component identifier in UIDL communications. */
    String uidlId;

    /** Reference to the server connection object. */
    ApplicationConnection client;

    /**
     * The constructor should first call super() to initialize the component and
     * then handle any initialization relevant to Vaadin.
     */
    public VLevelSelector() {
        // This method call of the Paintable interface sets the component
        // style name in DOM tree
        setStyleName(CLASSNAME);
    }

    public void updateFromUIDL(UIDL uidl, ApplicationConnection client) {
        // This call should be made first. Ensure correct implementation,
        // and let the containing layout manage caption, etc.
        if (client.updateComponent(this, uidl, true)) {
            return;
        }

        // Save reference to server connection object to be able to send
        // user interaction later
        this.client = client;

        // Save the UIDL identifier for the component
        id = uidl.getId();

        UIDL child = uidl.getChildUIDL(0);

        maxValue = child.getIntAttribute("maxValue");
        minValue = child.getIntAttribute("minLevel");
        value = child.getIntAttribute("value");
        elementWidth = getElement().getClientWidth();

        immediate = uidl.getBooleanAttribute("immediate");

        if (value > maxValue) {
            value = maxValue;
        }

        // TODO: update size
        if (boxes.size() > maxValue) {
            for (int i = maxValue; i < boxes.size(); i++) {
                SelectionBox box = boxes.get(maxValue - 1);
                remove(box);
                boxes.remove(box);
            }
        }

        for (int i = boxes.size(); i < maxValue; i++) {
            SelectionBox box = new SelectionBox(i + 1);
            boxes.add(box);
            add(box);
        }

        for (SelectionBox box : boxes) {
            box.resetStyles();
            box.resetSize();
            box.resetEvents();
        }
    }

    protected void hoveringAt(int pos) {
        for (int i = minValue - 1; i < pos; i++) {
            boxes.get(i).addStyleName("box-selected");
        }
        for (int i = pos; i < boxes.size(); i++) {
            boxes.get(i).removeStyleName("box-selected");
        }
    }

    protected void hoverOut() {
        for (SelectionBox box : boxes) {
            box.resetStyles();
        }
    }

    protected void setValue(int value) {
        this.value = value;
        if (value <= maxValue && value >= minValue) {
            client.updateVariable(id, "value", value, immediate);
        }
    }

    protected class SelectionBox extends Widget {

        protected int pos;

        public SelectionBox(int pos) {
            // DivElement box = Document.get().createDivElement();
            SpanElement box = Document.get().createSpanElement();
            setElement(box);
            addStyleName("box");

            int width = (int) Math.floor(elementWidth / maxValue) - 1;
            setWidth(width + "px");

            if (minValue > pos) {
                addStyleName("box-disabled");
            } else {
                sinkEvents(Event.ONCLICK);
                sinkEvents(Event.ONMOUSEOUT);
                sinkEvents(Event.ONMOUSEOVER);
            }

            if (value >= pos && pos >= minValue) {
                addStyleName("box-selected");
            }

            this.pos = pos;
        }

        public void resetStyles() {
            if (minValue > pos) {
                removeStyleName("box-selected");
                addStyleName("box-disabled");
            } else if (value >= pos) {
                removeStyleName("box-disabled");
                addStyleName("box-selected");
            } else {
                removeStyleName("box-disabled");
                removeStyleName("box-selected");
            }
        }

        @Override
        public void onBrowserEvent(Event event) {
            super.onBrowserEvent(event);
            switch (DOM.eventGetType(event)) {
            case Event.ONMOUSEOVER:
                hoveringAt(pos);
                break;
            case Event.ONMOUSEOUT:
                hoverOut();
                break;
            case Event.ONCLICK:
                setValue(pos);
                break;

            default:
                break;
            }
        }

        public void resetSize() {
            int width = (int) Math.floor(elementWidth / maxValue) - 1;
            setWidth(width + "px");
        }

        public void resetEvents() {
            if (minValue > pos) {
                unsinkEvents(Event.ONCLICK);
                unsinkEvents(Event.ONMOUSEOUT);
                unsinkEvents(Event.ONMOUSEOVER);
            } else {
                sinkEvents(Event.ONCLICK);
                sinkEvents(Event.ONMOUSEOUT);
                sinkEvents(Event.ONMOUSEOVER);
            }
        }

    }
}
