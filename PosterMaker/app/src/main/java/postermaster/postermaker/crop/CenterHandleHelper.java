package postermaster.postermaker.crop;

import android.graphics.Rect;

class CenterHandleHelper extends HandleHelper {
    CenterHandleHelper() {
        super(null, null);
    }

    /* access modifiers changed from: 0000 */
    public void updateCropWindow(float f, float f2, Rect rect, float f3) {
        float coordinate = f - ((Edge.LEFT.getCoordinate() + Edge.RIGHT.getCoordinate()) / 2.0f);
        float coordinate2 = f2 - ((Edge.BOTTOM.getCoordinate() + Edge.TOP.getCoordinate()) / 2.0f);
        Edge.LEFT.offset(coordinate);
        Edge.TOP.offset(coordinate2);
        Edge.RIGHT.offset(coordinate);
        Edge.BOTTOM.offset(coordinate2);
        if (Edge.LEFT.isOutsideMargin(rect, f3)) {
            Edge.RIGHT.offset(Edge.LEFT.snapToRect(rect));
        } else if (Edge.RIGHT.isOutsideMargin(rect, f3)) {
            Edge.LEFT.offset(Edge.RIGHT.snapToRect(rect));
        }
        if (Edge.TOP.isOutsideMargin(rect, f3)) {
            Edge.BOTTOM.offset(Edge.TOP.snapToRect(rect));
        } else if (Edge.BOTTOM.isOutsideMargin(rect, f3)) {
            Edge.TOP.offset(Edge.BOTTOM.snapToRect(rect));
        }
    }

    /* access modifiers changed from: 0000 */
    public void updateCropWindow(float f, float f2, float f3, Rect rect, float f4) {
        updateCropWindow(f, f2, rect, f4);
    }
}
