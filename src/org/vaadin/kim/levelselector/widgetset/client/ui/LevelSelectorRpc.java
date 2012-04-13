package org.vaadin.kim.levelselector.widgetset.client.ui;

import com.vaadin.terminal.gwt.client.communication.ServerRpc;

public interface LevelSelectorRpc extends ServerRpc {
	
	public void valueChanged(Integer value);

}
