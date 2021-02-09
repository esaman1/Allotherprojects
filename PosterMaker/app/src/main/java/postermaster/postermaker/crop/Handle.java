package postermaster.postermaker.crop;

import android.graphics.Rect;

public enum Handle {
    TOP_LEFT(new CornerHandleHelper(Edge.TOP, Edge.LEFT)),
    TOP_RIGHT(new CornerHandleHelper(Edge.TOP, Edge.RIGHT)),
    BOTTOM_LEFT(new CornerHandleHelper(Edge.BOTTOM, Edge.LEFT)),
    BOTTOM_RIGHT(new CornerHandleHelper(Edge.BOTTOM, Edge.RIGHT)),
    LEFT(new VerticalHandleHelper(Edge.LEFT)),
    TOP(new HorizontalHandleHelper(Edge.TOP)),
    RIGHT(new VerticalHandleHelper(Edge.RIGHT)),
    BOTTOM(new HorizontalHandleHelper(Edge.BOTTOM)),
    CENTER(new CenterHandleHelper());
    
    private HandleHelper mHelper;

    private Handle(HandleHelper handleHelper) {
        this.mHelper = handleHelper;
    }

    public void updateCropWindow(float f, float f2, Rect rect, float f3) {
        this.mHelper.updateCropWindow(f, f2, rect, f3);
    }

    public void updateCropWindow(float f, float f2, float f3, Rect rect, float f4) {
        this.mHelper.updateCropWindow(f, f2, f3, rect, f4);
    }
}
