package postermaster.postermaker.crop;

import android.graphics.Rect;

class CornerHandleHelper extends HandleHelper {
    CornerHandleHelper(Edge edge, Edge edge2) {
        super(edge, edge2);
    }

    /* access modifiers changed from: 0000 */
    public void updateCropWindow(float f, float f2, float f3, Rect rect, float f4) {
        EdgePair activeEdges = getActiveEdges(f, f2, f3);
        Edge edge = activeEdges.primary;
        Edge edge2 = activeEdges.secondary;
        edge.adjustCoordinate(f, f2, rect, f4, f3);
        edge2.adjustCoordinate(f3);
        if (edge2.isOutsideMargin(rect, f4)) {
            edge2.snapToRect(rect);
            edge.adjustCoordinate(f3);
        }
    }
}
