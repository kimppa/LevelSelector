package com.vaadin.incubator.levelselector;

import java.util.Map;

import com.vaadin.terminal.PaintException;
import com.vaadin.terminal.PaintTarget;
import com.vaadin.ui.AbstractField;

/**
 * 
 * @author Kim Leppänen
 * 
 */
@SuppressWarnings("unchecked")
public class LevelSelector extends AbstractField {

    private static final long serialVersionUID = 6173115029929032332L;

    protected int minValue = 1;

    protected int maxValue = 1;

    protected Integer blockSize = null;

    @Override
    public String getTag() {
        return "levelselector";
    }

    /**
     * Set the maximum value the user can choose, simultaneously this decides
     * the number of blocks displayed.
     * 
     * @param maxValue
     */
    public void setMaxValue(int maxValue) {
        this.maxValue = maxValue;
        if (blockSize != null) {
            super.setWidth(maxValue * blockSize + "px");
        }
        requestRepaint();
    }

    /**
     * Get the current maximum value the user can choose.
     * 
     * @return
     */
    public int getMaxValue() {
        return maxValue;
    }

    @Override
    public Class<?> getType() {
        return Integer.class;
    }

    @Override
    public void setValue(Object newValue) throws ReadOnlyException,
            ConversionException {
        if (newValue instanceof Integer) {
            super.setValue(newValue);
        } else if (newValue == null) {
            throw new ConversionException();
        } else {
            try {
                super.setValue(Integer.valueOf(newValue.toString()));
            } catch (Exception e) {
                throw new ConversionException();
            }
        }
    }

    @Override
    public void changeVariables(Object source, Map variables) {
        super.changeVariables(source, variables);
        if (variables.containsKey("value") && !isReadOnly()) {
            int value = Integer.valueOf(variables.get("value").toString());
            if (value >= minValue && value <= maxValue) {
                super.setValue(value, false);
            }
        }
    }

    @Override
    public void paintContent(PaintTarget target) throws PaintException {
        super.paintContent(target);

        float width = 0;
        if (blockSize != null) {
            width = maxValue * blockSize;
        } else {
            width = getWidth();
        }

        target.startTag("level");
        target.addAttribute("width", width);
        target.addAttribute("height", getHeight());
        Object value = getValue();
        int intValue = value == null ? 0 : Integer.valueOf(value.toString());
        target.addAttribute("value", intValue);
        target.addAttribute("minLevel", minValue);
        target.addAttribute("maxValue", maxValue);
        target.endTag("level");
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
        if (minValue >= 1 && minValue != this.minValue) {
            this.minValue = minValue;

            Object value = getValue();
            int intValue = value == null ? 0 : Integer
                    .valueOf(value.toString());
            if (intValue < minValue) {
                setValue(minValue, false);
            }
            requestRepaint();
        }
    }

    /**
     * Get the current minimum value that can be selected.
     * 
     * @return
     */
    public int getMinValue() {
        return minValue;
    }

    /**
     * Helper method for calculating the block size. setWidth() method will
     * override this value and vica versa.
     * 
     * @param size
     */
    public void setBlockSize(int size) {
        blockSize = size;
        super.setWidth(maxValue * size + "px");
    }

    @Override
    public void setWidth(float width, int unit) {
        super.setWidth(width, unit);
        blockSize = null;
    }

    @Override
    @Deprecated
    public void setWidth(float width) {
        super.setWidth(width);
        blockSize = null;
    }

    @Override
    public void setWidth(String width) {
        super.setWidth(width);
        blockSize = null;
    }
}
