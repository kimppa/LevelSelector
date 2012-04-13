package org.vaadin.kim.levelselector.widgetset.client.ui;

import org.vaadin.kim.levelselector.LevelSelector;
import org.vaadin.kim.levelselector.widgetset.client.ui.VLevelSelector.ValueChangeListener;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.Widget;
import com.vaadin.terminal.gwt.client.communication.RpcProxy;
import com.vaadin.terminal.gwt.client.communication.StateChangeEvent;
import com.vaadin.terminal.gwt.client.ui.AbstractComponentConnector;
import com.vaadin.terminal.gwt.client.ui.Connect;

@Connect(LevelSelector.class)
public class LevelSelectorConnector extends AbstractComponentConnector implements ValueChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7620343019634514005L;

	private LevelSelectorRpc rpc;

	public LevelSelectorConnector() {
		rpc = RpcProxy.create(LevelSelectorRpc.class, this);
	}

	@Override
	protected Widget createWidget() {
		VLevelSelector w = GWT.create(VLevelSelector.class);
		w.addListener(this);
		return w;
	}

	@Override
	public VLevelSelector getWidget() {
		return (VLevelSelector) super.getWidget();
	}

	@Override
	public LevelSelectorState getState() {
		return (LevelSelectorState) super.getState();
	}

	@Override
	public void onStateChanged(StateChangeEvent stateChangeEvent) {
		super.onStateChanged(stateChangeEvent);
		getWidget().setState(getState().getMaxValue(),
				getState().getMinValue(), getState().getStateValue(), getState().getBlockSize());
	}

	public void valueChanged(Integer value) {
		rpc.valueChanged(value);		
	}

}
