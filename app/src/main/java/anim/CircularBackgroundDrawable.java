package anim;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;

/**
 * 转动时蓝色实心球背景
 * @author chukc
 *
 */
class CircularBackgroundDrawable extends Drawable  {

    public static final int MIN_SWEEP_ANGLE = 30;
    private final RectF fBounds = new RectF();

    private Paint mPaint;
    private float mCurrentGlobalAngle;
    private float mCurrentSweepAngle;
    private float mBorderWidth;
    public CircularBackgroundDrawable(int color, float borderWidth) {
	mBorderWidth = borderWidth;

	mPaint = new Paint();
	mPaint.setAntiAlias(true);
	mPaint.setStrokeWidth(borderWidth);
        mPaint.setColor(color);
    }

    @Override
    public void draw(Canvas canvas) {
	canvas.drawArc(fBounds, 0, 360, false, mPaint);
    }

    @Override
    public void setAlpha(int alpha) {
	mPaint.setAlpha(alpha);
    }

    @Override
    public void setColorFilter(ColorFilter cf) {
	mPaint.setColorFilter(cf);
    }

    @Override
    public int getOpacity() {
	return PixelFormat.TRANSPARENT;
    }

    @Override
    protected void onBoundsChange(Rect bounds) {
	super.onBoundsChange(bounds);
	fBounds.left = bounds.left+ mBorderWidth/2f+ .5f;
	fBounds.right = bounds.right- mBorderWidth/2f- .5f;
	fBounds.top = bounds.top+ mBorderWidth/2f- .5f;
	fBounds.bottom = bounds.bottom-mBorderWidth/2f+.5f;
    }

    public void setCurrentGlobalAngle(float currentGlobalAngle) {
	mCurrentGlobalAngle = currentGlobalAngle;
	invalidateSelf();
    }

    public float getCurrentGlobalAngle() {
	return mCurrentGlobalAngle;
    }

    public void setCurrentSweepAngle(float currentSweepAngle) {
	mCurrentSweepAngle = currentSweepAngle;
	invalidateSelf();
    }

    public float getCurrentSweepAngle() {
	return mCurrentSweepAngle;
    }

}
