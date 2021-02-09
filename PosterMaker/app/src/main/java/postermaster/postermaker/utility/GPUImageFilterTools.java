package postermaster.postermaker.utility;

import android.opengl.Matrix;

import postermaster.postermaker.GPUImageTransformFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImage3x3TextureSamplingFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageBilateralBlurFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageBrightnessFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageBulgeDistortionFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageColorBalanceFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageContrastFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageCrosshatchFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageDissolveBlendFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageEmbossFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageExposureFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageGammaFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageGaussianBlurFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageGlassSphereFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageHazeFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageHighlightShadowFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageHueFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageLevelsFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageMonochromeFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageOpacityFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImagePixelationFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImagePosterizeFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageRGBFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSaturationFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSepiaToneFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSharpenFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSobelEdgeDetectionFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSphereRefractionFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageSwirlFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageVignetteFilter;
import jp.co.cyberagent.android.gpuimage.filter.GPUImageWhiteBalanceFilter;

public class GPUImageFilterTools {

    public static class FilterAdjuster {
        private final Adjuster<? extends GPUImageFilter> adjuster;

        private abstract class Adjuster<T extends GPUImageFilter> {
            private GPUImageFilter filter;

            public abstract void adjust(int i);

            /* access modifiers changed from: protected */
            public float range(int i, float f, float f2) {
                return (((f2 - f) * ((float) i)) / 100.0f) + f;
            }

            private Adjuster() {
            }

            public Adjuster<T> filter(GPUImageFilter gPUImageFilter) {
                this.filter = gPUImageFilter;
                return this;
            }

            public GPUImageFilter getFilter() {
                return this.filter;
            }

            /* access modifiers changed from: protected */
            public int range(int i, int i2, int i3) {
                return (((i3 - i2) * i) / 100) + i2;
            }
        }

        private class BilateralAdjuster extends Adjuster<GPUImageBilateralBlurFilter> {
            private BilateralAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageBilateralBlurFilter) getFilter()).setDistanceNormalizationFactor(range(i, 0.0f, 15.0f));
            }
        }

        private class BrightnessAdjuster extends Adjuster<GPUImageBrightnessFilter> {
            private BrightnessAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageBrightnessFilter) getFilter()).setBrightness(range(i, -1.0f, 1.0f));
            }
        }

        private class BulgeDistortionAdjuster extends Adjuster<GPUImageBulgeDistortionFilter> {
            private BulgeDistortionAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageBulgeDistortionFilter) getFilter()).setRadius(range(i, 0.0f, 1.0f));
                ((GPUImageBulgeDistortionFilter) getFilter()).setScale(range(i, -1.0f, 1.0f));
            }
        }

        private class ColorBalanceAdjuster extends Adjuster<GPUImageColorBalanceFilter> {
            private ColorBalanceAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageColorBalanceFilter) getFilter()).setMidtones(new float[]{range(i, 0.0f, 1.0f), range(i / 2, 0.0f, 1.0f), range(i / 3, 0.0f, 1.0f)});
            }
        }

        private class ContrastAdjuster extends Adjuster<GPUImageContrastFilter> {
            private ContrastAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageContrastFilter) getFilter()).setContrast(range(i, 0.0f, 2.0f));
            }
        }

        private class CrosshatchBlurAdjuster extends Adjuster<GPUImageCrosshatchFilter> {
            private CrosshatchBlurAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageCrosshatchFilter) getFilter()).setCrossHatchSpacing(range(i, 0.0f, 0.06f));
                ((GPUImageCrosshatchFilter) getFilter()).setLineWidth(range(i, 0.0f, 0.006f));
            }
        }

        private class DissolveBlendAdjuster extends Adjuster<GPUImageDissolveBlendFilter> {
            private DissolveBlendAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageDissolveBlendFilter) getFilter()).setMix(range(i, 0.0f, 1.0f));
            }
        }

        private class EmbossAdjuster extends Adjuster<GPUImageEmbossFilter> {
            private EmbossAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageEmbossFilter) getFilter()).setIntensity(range(i, 0.0f, 4.0f));
            }
        }

        private class ExposureAdjuster extends Adjuster<GPUImageExposureFilter> {
            private ExposureAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageExposureFilter) getFilter()).setExposure(range(i, -10.0f, 10.0f));
            }
        }

        private class GPU3x3TextureAdjuster extends Adjuster<GPUImage3x3TextureSamplingFilter> {
            private GPU3x3TextureAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImage3x3TextureSamplingFilter) getFilter()).setLineSize(range(i, 0.0f, 5.0f));
            }
        }

        private class GammaAdjuster extends Adjuster<GPUImageGammaFilter> {
            private GammaAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageGammaFilter) getFilter()).setGamma(range(i, 0.0f, 3.0f));
            }
        }

        private class GaussianBlurAdjuster extends Adjuster<GPUImageGaussianBlurFilter> {
            private GaussianBlurAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageGaussianBlurFilter) getFilter()).setBlurSize(range(i, 0.0f, 5.0f));
            }
        }

        private class GlassSphereAdjuster extends Adjuster<GPUImageGlassSphereFilter> {
            private GlassSphereAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageGlassSphereFilter) getFilter()).setRadius(range(i, 0.0f, 1.0f));
            }
        }

        private class HazeAdjuster extends Adjuster<GPUImageHazeFilter> {
            private HazeAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageHazeFilter) getFilter()).setDistance(range(i, -0.3f, 0.3f));
                ((GPUImageHazeFilter) getFilter()).setSlope(range(i, -0.3f, 0.3f));
            }
        }

        private class HighlightShadowAdjuster extends Adjuster<GPUImageHighlightShadowFilter> {
            private HighlightShadowAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageHighlightShadowFilter) getFilter()).setShadows(range(i, 0.0f, 1.0f));
                ((GPUImageHighlightShadowFilter) getFilter()).setHighlights(range(i, 0.0f, 1.0f));
            }
        }

        private class HueAdjuster extends Adjuster<GPUImageHueFilter> {
            private HueAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageHueFilter) getFilter()).setHue(range(i, 0.0f, 360.0f));
            }
        }

        private class LevelsMinMidAdjuster extends Adjuster<GPUImageLevelsFilter> {
            private LevelsMinMidAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageLevelsFilter) getFilter()).setMin(0.0f, range(i, 0.0f, 1.0f), 1.0f);
            }
        }

        private class MonochromeAdjuster extends Adjuster<GPUImageMonochromeFilter> {
            private MonochromeAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageMonochromeFilter) getFilter()).setIntensity(range(i, 0.0f, 1.0f));
            }
        }

        private class OpacityAdjuster extends Adjuster<GPUImageOpacityFilter> {
            private OpacityAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageOpacityFilter) getFilter()).setOpacity(range(i, 0.0f, 1.0f));
            }
        }

        private class PixelationAdjuster extends Adjuster<GPUImagePixelationFilter> {
            private PixelationAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImagePixelationFilter) getFilter()).setPixel(range(i, 1.0f, 100.0f));
            }
        }

        private class PosterizeAdjuster extends Adjuster<GPUImagePosterizeFilter> {
            private PosterizeAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImagePosterizeFilter) getFilter()).setColorLevels(range(i, 1, 50));
            }
        }

        private class RGBAdjuster extends Adjuster<GPUImageRGBFilter> {
            private RGBAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageRGBFilter) getFilter()).setRed(range(i, 0.0f, 1.0f));
            }
        }

        private class RotateAdjuster extends Adjuster<GPUImageTransformFilter> {
            private RotateAdjuster() {
                super();
            }

            public void adjust(int i) {
                float[] fArr = new float[16];
                Matrix.setRotateM(fArr, 0, (float) ((i * 360) / 100), 0.0f, 0.0f, 1.0f);
                ((GPUImageTransformFilter) getFilter()).setTransform3D(fArr);
            }
        }

        private class SaturationAdjuster extends Adjuster<GPUImageSaturationFilter> {
            private SaturationAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageSaturationFilter) getFilter()).setSaturation(range(i, 0.0f, 2.0f));
            }
        }

        private class SepiaAdjuster extends Adjuster<GPUImageSepiaToneFilter> {
            private SepiaAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageSepiaToneFilter) getFilter()).setIntensity(range(i, 0.0f, 2.0f));
            }
        }

        private class SharpnessAdjuster extends Adjuster<GPUImageSharpenFilter> {
            private SharpnessAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageSharpenFilter) getFilter()).setSharpness(range(i, -4.0f, 4.0f));
            }
        }

        private class SobelAdjuster extends Adjuster<GPUImageSobelEdgeDetectionFilter> {
            private SobelAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageSobelEdgeDetectionFilter) getFilter()).setLineSize(range(i, 0.0f, 5.0f));
            }
        }

        private class SphereRefractionAdjuster extends Adjuster<GPUImageSphereRefractionFilter> {
            private SphereRefractionAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageSphereRefractionFilter) getFilter()).setRadius(range(i, 0.0f, 1.0f));
            }
        }

        private class SwirlAdjuster extends Adjuster<GPUImageSwirlFilter> {
            private SwirlAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageSwirlFilter) getFilter()).setAngle(range(i, 0.0f, 2.0f));
            }
        }

        private class VignetteAdjuster extends Adjuster<GPUImageVignetteFilter> {
            private VignetteAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageVignetteFilter) getFilter()).setVignetteStart(range(i, 0.0f, 1.0f));
            }
        }

        private class WhiteBalanceAdjuster extends Adjuster<GPUImageWhiteBalanceFilter> {
            private WhiteBalanceAdjuster() {
                super();
            }

            public void adjust(int i) {
                ((GPUImageWhiteBalanceFilter) getFilter()).setTemperature(range(i, 2000.0f, 8000.0f));
            }
        }

        public FilterAdjuster(GPUImageFilter gPUImageFilter) {
            if (gPUImageFilter instanceof GPUImageSharpenFilter) {
                this.adjuster = new SharpnessAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageSepiaToneFilter) {
                this.adjuster = new SepiaAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageContrastFilter) {
                this.adjuster = new ContrastAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageGammaFilter) {
                this.adjuster = new GammaAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageBrightnessFilter) {
                this.adjuster = new BrightnessAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageSobelEdgeDetectionFilter) {
                this.adjuster = new SobelAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageEmbossFilter) {
                this.adjuster = new EmbossAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImage3x3TextureSamplingFilter) {
                this.adjuster = new GPU3x3TextureAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageHueFilter) {
                this.adjuster = new HueAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImagePosterizeFilter) {
                this.adjuster = new PosterizeAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImagePixelationFilter) {
                this.adjuster = new PixelationAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageSaturationFilter) {
                this.adjuster = new SaturationAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageExposureFilter) {
                this.adjuster = new ExposureAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageHighlightShadowFilter) {
                this.adjuster = new HighlightShadowAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageMonochromeFilter) {
                this.adjuster = new MonochromeAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageOpacityFilter) {
                this.adjuster = new OpacityAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageRGBFilter) {
                this.adjuster = new RGBAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageWhiteBalanceFilter) {
                this.adjuster = new WhiteBalanceAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageVignetteFilter) {
                this.adjuster = new VignetteAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageDissolveBlendFilter) {
                this.adjuster = new DissolveBlendAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageGaussianBlurFilter) {
                this.adjuster = new GaussianBlurAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageCrosshatchFilter) {
                this.adjuster = new CrosshatchBlurAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageBulgeDistortionFilter) {
                this.adjuster = new BulgeDistortionAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageGlassSphereFilter) {
                this.adjuster = new GlassSphereAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageHazeFilter) {
                this.adjuster = new HazeAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageSphereRefractionFilter) {
                this.adjuster = new SphereRefractionAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageSwirlFilter) {
                this.adjuster = new SwirlAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageColorBalanceFilter) {
                this.adjuster = new ColorBalanceAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageLevelsFilter) {
                this.adjuster = new LevelsMinMidAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageBilateralBlurFilter) {
                this.adjuster = new BilateralAdjuster().filter(gPUImageFilter);
            } else if (gPUImageFilter instanceof GPUImageTransformFilter) {
                this.adjuster = new RotateAdjuster().filter(gPUImageFilter);
            } else {
                this.adjuster = null;
            }
        }

        public boolean canAdjust() {
            return this.adjuster != null;
        }

        public void adjust(int i) {
            Adjuster<? extends GPUImageFilter> adjuster2 = this.adjuster;
            if (adjuster2 != null) {
                adjuster2.adjust(i);
            }
        }
    }
}
