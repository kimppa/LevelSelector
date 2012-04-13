package org.vaadin.kim.levelselector.widgetset.client.ui;

import java.util.ArrayList;
import java.util.List;

import com.google.gwt.dom.client.Document;
import com.google.gwt.dom.client.SpanElement;
import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Widget;

public class VLevelSelector extends FlowPanel {

	/** Set the tagname used to statically resolve widget from UIDL. */
	public static final String TAGNAME = "levelselector";

	/** Set the CSS class name to allow styling. */
	public static final String CLASSNAME = "v-" + TAGNAME;

	private int maxValue = 1;

	private int value = 0;

	private int minValue = 1;

	protected int elementWidth = 0;

	protected boolean immediate = false;

	protected List<SelectionBox> boxes = new ArrayList<SelectionBox>();

	private List<ValueChangeListener> listeners = new ArrayList<ValueChangeListener>();

	private Integer blockSize;

	/**
	 * The constructor should first call super() to initialize the component and
	 * then handle any initialization relevant to Vaadin.
	 */
	public VLevelSelector() {
		setStyleName(CLASSNAME);
	}

	public void setState(int maxValue, int minValue, int value, Integer blockSize) {
		setMaxValue(maxValue);
		setMinValue(minValue);
		setBlockSize(blockSize);
		this.value = value;
		requestRepaint();
	}

	private void setBlockSize(Integer blockSize) {
		this.blockSize = blockSize;
	}

	protected void requestRepaint() {
		elementWidth = getElement().getClientWidth();
		
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
		for (int i = getMinValue() - 1; i < pos; i++) {
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
		if (value <= getMaxValue() && value >= getMinValue()) {
			for(ValueChangeListener listener : listeners) {
				listener.valueChanged(value);
			}
		}
	}

	public int getMaxValue() {
		return maxValue;
	}

	protected void setMaxValue(int maxValue) {
		if (value > maxValue) {
			value = maxValue;
		}

		this.maxValue = maxValue;
	}

	public int getMinValue() {
		return minValue;
	}

	protected void setMinValue(int minValue) {
		this.minValue = minValue;
	}

	public Integer getValue() {
		return value;
	}

	protected class SelectionBox extends Widget {

		protected int pos;

		public SelectionBox(int pos) {
			SpanElement box = Document.get().createSpanElement();
			setElement(box);
			addStyleName("box");

			if (getMinValue() > pos) {
				addStyleName("box-disabled");
			} else {
				sinkEvents(Event.ONCLICK);
				sinkEvents(Event.ONMOUSEOUT);
				sinkEvents(Event.ONMOUSEOVER);
			}

			if (value >= pos && pos >= getMinValue()) {
				addStyleName("box-selected");
			}

			this.pos = pos;
		}

		public void resetSize() {
			if(blockSize == null) {
				float size = 100f/boxes.size();
				setWidth(size + "%");
			} else {
				setWidth(blockSize + "px");
			}
		}

		public void resetStyles() {
			if (getMinValue() > pos) {
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

		public void resetEvents() {
			if (getMinValue() > pos) {
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

	public static interface ValueChangeListener {
		public void valueChanged(Integer value);
	}
	
	public void addListener(ValueChangeListener listener) {
		listeners .add(listener);
	}
}
