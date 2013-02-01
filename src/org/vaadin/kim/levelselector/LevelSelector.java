package org.vaadin.kim.levelselector;

import org.vaadin.kim.levelselector.widgetset.client.ui.LevelSelectorRpc;
import org.vaadin.kim.levelselector.widgetset.client.ui.LevelSelectorState;

import com.vaadin.ui.AbstractField;

/**
 * 
 * @author Kim Leppänen
 * 
 */
public class LevelSelector extends AbstractField<Integer> {

	private static final long serialVersionUID = 6173115029929032332L;

	protected Integer blockSize = null;

	public LevelSelector() {
		LevelSelectorRpc rpc = new LevelSelectorRpc() {

			private static final long serialVersionUID = -6056647151709185L;

			public void valueChanged(Integer value) {
				if (value >= getState().getMinValue()
						&& value <= getState().getMaxValue()) {
					LevelSelector.this.setValue(value, false);
				}
			}
		};
		registerRpc(rpc);
	}

	/**
	 * Set the maximum value the user can choose, simultaneously this decides
	 * the number of blocks displayed.
	 * 
	 * @param maxValue
	 */
	public void setMaxValue(int maxValue) {
		getState().setMaxValue(maxValue);
	}

	/**
	 * Get the current maximum value the user can choose.
	 * 
	 * @return
	 */
	public int getMaxValue() {
		return getState().getMaxValue();
	}

	@Override
	public Class<Integer> getType() {
		return Integer.class;
	}

	@Override
	public LevelSelectorState getState() {
		return (LevelSelectorState) super.getState();
	}

	/**
	 * Set the minimum number of blocks the user can choose. All blocks before
	 * this value will be disabled in the selection. Default value is 1. Smaller
	 * values than 1 are not valid.
	 * 
	 * @param minValue
	 *            An integer value between 1 and maxValue
	 */
	public void setMinValue(int minValue) {
		if (minValue >= 1 && minValue != getState().getMinValue()) {
			getState().setMinValue(minValue);

			Integer intValue = getValue();
			if (intValue < minValue) {
				setValue(minValue, false);
			}
		} else if (minValue < 1) {
			throw new IllegalArgumentException(
					"Minimum value cannot be below 1");
		}
	}

	/**
	 * Get the current minimum value that can be selected.
	 * 
	 * @return
	 */
	public int getMinValue() {
		return getState().getMinValue();
	}

	/**
	 * Defines a width for the an individual block in the selector. This will
	 * make the component's width undefined thus overriding any previously set
	 * values.
	 * 
	 * @param size
	 */
	public void setBlockSize(Integer size) {
		super.setWidth(null);
		getState().setBlockSize(size);
	}

	@Override
	public void setWidth(float width, Unit unit) {
		super.setWidth(width, unit);
		getState().setBlockSize(null);
	}

	@Override
	public void setWidth(String width) {
		if (width == null && blockSize == null) {
			throw new IllegalArgumentException(
					"You may not define a null width. If you want to use an undefined width, please "
							+ "specify a block size instead.");
		}
		super.setWidth(width);
		getState().setBlockSize(null);
	}
}
