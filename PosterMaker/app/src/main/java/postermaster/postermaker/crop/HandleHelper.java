package postermaster.postermaker.crop;

import android.graphics.Rect;

abstract class HandleHelper {
    private static final float UNFIXED_ASPECT_RATIO_CONSTANT = 1.0f;
    private EdgePair mActiveEdges = new EdgePair(this.mHorizontalEdge, this.mVerticalEdge);
    private Edge mHorizontalEdge;
    private Edge mVerticalEdge;

    /* access modifiers changed from: 0000 */
    public abstract void updateCropWindow(float f, float f2, float f3, Rect rect, float f4);

    HandleHelper(Edge edge, Edge edge2) {
        this.mHorizontalEdge = edge;
        this.mVerticalEdge = edge2;
    }

    /* access modifiers changed from: 0000 */
    public void updateCropWindow(float f, float f2, Rect rect, float f3) {
        EdgePair activeEdges = getActiveEdges();
        Edge edge = activeEdges.primary;
        Edge edge2 = activeEdges.secondary;
        if (edge != null) {
            edge.adjustCoordinate(f, f2, rect, f3, 1.0f);
        }
        if (edge2 != null) {
            edge2.adjustCoordinate(f, f2, rect, f3, 1.0f);
        }
    }

    /* access modifiers changed from: 0000 */
    public EdgePair getActiveEdges() {
        return this.mActiveEdges;
    }

    /* access modifiers changed from: 0000 */
    public EdgePair getActiveEdges(float f, float f2, float f3) {
        if (getAspectRatio(f, f2) > f3) {
            EdgePair edgePair = this.mActiveEdges;
            edgePair.primary = this.mVerticalEdge;
            edgePair.secondary = this.mHorizontalEdge;
        } else {
            EdgePair edgePair2 = this.mActiveEdges;
            edgePair2.primary = this.mHorizontalEdge;
            edgePair2.secondary = this.mVerticalEdge;
        }
        return this.mActiveEdges;
    }

    private float getAspectRatio(float f, float f2) {
        float coordinate = this.mVerticalEdge == Edge.LEFT ? f : Edge.LEFT.getCoordinate();
        float coordinate2 = this.mHorizontalEdge == Edge.TOP ? f2 : Edge.TOP.getCoordinate();
        if (this.mVerticalEdge != Edge.RIGHT) {
            f = Edge.RIGHT.getCoordinate();
        }
        if (this.mHorizontalEdge != Edge.BOTTOM) {
            f2 = Edge.BOTTOM.getCoordinate();
        }
        return AspectRatioUtil.calculateAspectRatio(coordinate, coordinate2, f, f2);
    }
}
