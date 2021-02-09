package postermaster.postermaker.crop;

import android.graphics.Rect;

class HorizontalHandleHelper extends HandleHelper {
    private Edge mEdge;

    HorizontalHandleHelper(Edge edge) {
        super(edge, null);
        this.mEdge = edge;
    }

    /* access modifiers changed from: 0000 */
    public void updateCropWindow(float f, float f2, float f3, Rect rect, float f4) {
        this.mEdge.adjustCoordinate(f, f2, rect, f4, f3);
        float coordinate = Edge.LEFT.getCoordinate();
        float coordinate2 = Edge.TOP.getCoordinate();
        float coordinate3 = Edge.RIGHT.getCoordinate();
        float calculateWidth = (AspectRatioUtil.calculateWidth(coordinate2, Edge.BOTTOM.getCoordinate(), f3) - (coordinate3 - coordinate)) / 2.0f;
        float f5 = coordinate3 + calculateWidth;
        Edge.LEFT.setCoordinate(coordinate - calculateWidth);
        Edge.RIGHT.setCoordinate(f5);
        if (Edge.LEFT.isOutsideMargin(rect, f4) && !this.mEdge.isNewRectangleOutOfBounds(Edge.LEFT, rect, f3)) {
            Edge.RIGHT.offset(-Edge.LEFT.snapToRect(rect));
            this.mEdge.adjustCoordinate(f3);
        }
        if (Edge.RIGHT.isOutsideMargin(rect, f4) && !this.mEdge.isNewRectangleOutOfBounds(Edge.RIGHT, rect, f3)) {
            Edge.LEFT.offset(-Edge.RIGHT.snapToRect(rect));
            this.mEdge.adjustCoordinate(f3);
        }
    }
}
