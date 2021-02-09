package postermaster.postermaker.crop;

import android.graphics.Rect;

class VerticalHandleHelper extends HandleHelper {
    private Edge mEdge;

    VerticalHandleHelper(Edge edge) {
        super(null, edge);
        this.mEdge = edge;
    }

    /* access modifiers changed from: 0000 */
    public void updateCropWindow(float f, float f2, float f3, Rect rect, float f4) {
        this.mEdge.adjustCoordinate(f, f2, rect, f4, f3);
        float coordinate = Edge.LEFT.getCoordinate();
        float coordinate2 = Edge.TOP.getCoordinate();
        float coordinate3 = Edge.RIGHT.getCoordinate();
        float coordinate4 = Edge.BOTTOM.getCoordinate();
        float calculateHeight = (AspectRatioUtil.calculateHeight(coordinate, coordinate3, f3) - (coordinate4 - coordinate2)) / 2.0f;
        float f5 = coordinate4 + calculateHeight;
        Edge.TOP.setCoordinate(coordinate2 - calculateHeight);
        Edge.BOTTOM.setCoordinate(f5);
        if (Edge.TOP.isOutsideMargin(rect, f4) && !this.mEdge.isNewRectangleOutOfBounds(Edge.TOP, rect, f3)) {
            Edge.BOTTOM.offset(-Edge.TOP.snapToRect(rect));
            this.mEdge.adjustCoordinate(f3);
        }
        if (Edge.BOTTOM.isOutsideMargin(rect, f4) && !this.mEdge.isNewRectangleOutOfBounds(Edge.BOTTOM, rect, f3)) {
            Edge.TOP.offset(-Edge.BOTTOM.snapToRect(rect));
            this.mEdge.adjustCoordinate(f3);
        }
    }
}
