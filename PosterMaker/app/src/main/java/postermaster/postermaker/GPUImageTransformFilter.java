package postermaster.postermaker;

import android.opengl.GLES20;
import android.opengl.Matrix;

import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;

import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;

public class GPUImageTransformFilter extends GPUImageFilter {
    public static final String TRANSFORM_VERTEX_SHADER = "attribute vec4 position;\n attribute vec4 inputTextureCoordinate;\n \n uniform mat4 transformMatrix;\n uniform mat4 orthographicMatrix;\n \n varying vec2 textureCoordinate;\n \n void main()\n {\n     gl_Position = transformMatrix * vec4(position.xyz, 1.0) * orthographicMatrix;\n     textureCoordinate = inputTextureCoordinate.xy;\n }";
    private boolean anchorTopLeft;
    private boolean ignoreAspectRatio;
    private float[] orthographicMatrix = new float[16];
    private int orthographicMatrixUniform;
    private float[] transform3D;
    private int transformMatrixUniform;

    public GPUImageTransformFilter() {
        super(TRANSFORM_VERTEX_SHADER, GPUImageFilter.NO_FILTER_FRAGMENT_SHADER);
        Matrix.orthoM(this.orthographicMatrix, 0, -1.0f, 1.0f, -1.0f, 1.0f, -1.0f, 1.0f);
        this.transform3D = new float[16];
        Matrix.setIdentityM(this.transform3D, 0);
    }

    public void onInit() {
        super.onInit();
        this.transformMatrixUniform = GLES20.glGetUniformLocation(getProgram(), "transformMatrix");
        this.orthographicMatrixUniform = GLES20.glGetUniformLocation(getProgram(), "orthographicMatrix");
        setUniformMatrix4f(this.transformMatrixUniform, this.transform3D);
        setUniformMatrix4f(this.orthographicMatrixUniform, this.orthographicMatrix);
    }

    public void onInitialized() {
        super.onInitialized();
    }

    public void onOutputSizeChanged(int i, int i2) {
        super.onOutputSizeChanged(i, i2);
        if (!this.ignoreAspectRatio) {
            float f = (float) i2;
            float f2 = (float) i;
            Matrix.orthoM(this.orthographicMatrix, 0, -1.0f, 1.0f, (-1.0f * f) / f2, (f * 1.0f) / f2, -1.0f, 1.0f);
            setUniformMatrix4f(this.orthographicMatrixUniform, this.orthographicMatrix);
        }
    }

    public void onDraw(int i, FloatBuffer floatBuffer, FloatBuffer floatBuffer2) {
        if (!this.ignoreAspectRatio) {
            float[] fArr = new float[8];
            floatBuffer.position(0);
            floatBuffer.get(fArr);
            float outputHeight = ((float) getOutputHeight()) / ((float) getOutputWidth());
            fArr[1] = fArr[1] * outputHeight;
            fArr[3] = fArr[3] * outputHeight;
            fArr[5] = fArr[5] * outputHeight;
            fArr[7] = fArr[7] * outputHeight;
            floatBuffer = ByteBuffer.allocateDirect(fArr.length * 4).order(ByteOrder.nativeOrder()).asFloatBuffer();
            floatBuffer.put(fArr).position(0);
        }
        super.onDraw(i, floatBuffer, floatBuffer2);
    }

    public void setTransform3D(float[] fArr) {
        this.transform3D = fArr;
        setUniformMatrix4f(this.transformMatrixUniform, fArr);
    }

    public float[] getTransform3D() {
        return this.transform3D;
    }

    public void setIgnoreAspectRatio(boolean z) {
        this.ignoreAspectRatio = z;
        if (z) {
            Matrix.orthoM(this.orthographicMatrix, 0, -1.0f, 1.0f, -1.0f, 1.0f, -1.0f, 1.0f);
            setUniformMatrix4f(this.orthographicMatrixUniform, this.orthographicMatrix);
            return;
        }
        onOutputSizeChanged(getOutputWidth(), getOutputHeight());
    }

    public boolean ignoreAspectRatio() {
        return this.ignoreAspectRatio;
    }

    public void setAnchorTopLeft(boolean z) {
        this.anchorTopLeft = z;
        setIgnoreAspectRatio(this.ignoreAspectRatio);
    }

    public boolean anchorTopLeft() {
        return this.anchorTopLeft;
    }
}
