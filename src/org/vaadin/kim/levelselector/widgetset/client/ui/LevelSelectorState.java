package org.vaadin.kim.levelselector.widgetset.client.ui;

import com.vaadin.shared.AbstractFieldState;

public class LevelSelectorState extends AbstractFieldState {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4566960360890517027L;

	private int maxValue = 1;

	private int value = 0;

	private int minValue = 1;
	
	private Integer blockSize = null;

	public int getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(int maxValue) {
		this.maxValue = maxValue;
	}

	public int getStateValue() {
		return value;
	}

	public void setStateValue(int value) {
		this.value = value;
	}

	public int getMinValue() {
		return minValue;
	}

	public void setMinValue(int minValue) {
		this.minValue = minValue;
	}

	public Integer getBlockSize() {
		return blockSize;
	}

	public void setBlockSize(Integer blockSize) {
		this.blockSize = blockSize;
	}

}
