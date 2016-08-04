package anim;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.drawable.GradientDrawable;
import android.widget.TextView;

/**
 * 动画控制类
 *
 * @author chukc
 *
 */
class MorphingAnimation {

    public static final int DURATION_NORMAL = 200;
    public static final int DURATION_INSTANT = 1;

    private OnAnimationEndListener mListener;

    private int mDuration;

    private int mFromWidth;
    private int mToWidth;

    private int mFromColor;
    private int mToColor;

    private int mFromStrokeColor;
    private int mToStrokeColor;

    private float mFromCornerRadius;
    private float mToCornerRadius;

    private float mPadding;

    private TextView mView;
    private StrokeGradientDrawable mDrawable;

    public MorphingAnimation(TextView viewGroup, StrokeGradientDrawable drawable) {
	mView = viewGroup;
	mDrawable = drawable;
    }

    public void setDuration(int duration) {
	mDuration = duration;
    }

    public void setListener(OnAnimationEndListener listener) {
	mListener = listener;
    }

    public void setFromWidth(int fromWidth) {
	mFromWidth = fromWidth;
    }

    public void setToWidth(int toWidth) {
	mToWidth = toWidth;
    }

    public void setFromColor(int fromColor) {
	mFromColor = fromColor;
    }

    public void setToColor(int toColor) {
	mToColor = toColor;
    }

    public void setFromStrokeColor(int fromStrokeColor) {
	mFromStrokeColor = fromStrokeColor;
    }

    public void setToStrokeColor(int toStrokeColor) {
	mToStrokeColor = toStrokeColor;
    }

    public void setFromCornerRadius(float fromCornerRadius) {
	mFromCornerRadius = fromCornerRadius;
    }

    public void setToCornerRadius(float toCornerRadius) {
	mToCornerRadius = toCornerRadius;
    }

    public void setPadding(float padding) {
	mPadding = padding;
    }

    public void start() {
	ValueAnimator widthAnimation = ValueAnimator.ofInt(mFromWidth, mToWidth);
	final GradientDrawable gradientDrawable = mDrawable.getGradientDrawable();  //颜色渐变
	widthAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
	    @Override
	    public void onAnimationUpdate(ValueAnimator animation) {
		Integer value = (Integer) animation.getAnimatedValue();  //每一帧返回的值 此处为 当前宽度 与ofInt设置属性类型相对应
		int leftOffset;
		int rightOffset;
		int padding;
		if (mFromWidth > mToWidth) {   //缩小
		    leftOffset = (mFromWidth - value) / 2;
		    rightOffset = mFromWidth - leftOffset;
		    padding = (int) (mPadding * animation.getAnimatedFraction());  //getAnimatedFraction执行动画的百分比
		} else {  //放大
		    leftOffset = (mToWidth - value) / 2;
		    rightOffset = mToWidth - leftOffset;
		    padding = (int) (mPadding - mPadding * animation.getAnimatedFraction());
		}
		gradientDrawable.setBounds(leftOffset + padding, padding, rightOffset - padding, mView.getHeight() - padding);  //表明需要绘制在哪个矩形区域内
	    }
	});
	final ValueAnimator widthAnimation1 = ValueAnimator.ofInt(mToWidth, mToWidth - 60);  //添加伸缩动画
	final GradientDrawable gradientDrawable1 = mDrawable.getGradientDrawable();
	widthAnimation1.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
	    @Override
	    public void onAnimationUpdate(ValueAnimator animation) {
		Integer value = (Integer) animation.getAnimatedValue();
		int leftOffset;
		int rightOffset;
		int padding;

		if (mFromWidth > mToWidth) {
		    leftOffset = (mFromWidth - value) / 2;
		    rightOffset = mFromWidth - leftOffset;
		    padding = (int) (mPadding * animation.getAnimatedFraction());
		} else {
		    leftOffset = (mToWidth - value) / 2;
		    rightOffset = mToWidth - leftOffset;
		    padding = (int) (mPadding - mPadding * animation.getAnimatedFraction());
		}

		gradientDrawable1.setBounds(leftOffset + padding, padding, rightOffset - padding, mView.getHeight() - padding);
	    }
	});

	final ObjectAnimator bgColorAnimation = ObjectAnimator.ofInt(gradientDrawable, "color", mFromColor, mToColor);
	bgColorAnimation.setEvaluator(new ArgbEvaluator());

	ObjectAnimator strokeColorAnimation = ObjectAnimator.ofInt(mDrawable, "strokeColor", mFromStrokeColor, mToStrokeColor);
	strokeColorAnimation.setEvaluator(new ArgbEvaluator());

	ObjectAnimator cornerAnimation = ObjectAnimator.ofFloat(gradientDrawable, "cornerRadius", mFromCornerRadius, mToCornerRadius);

	final AnimatorSet animatorSet = new AnimatorSet();
	animatorSet.setDuration(mDuration);
	animatorSet.playTogether(widthAnimation, strokeColorAnimation, cornerAnimation);
	// animatorSet.playTogether(widthAnimation, bgColorAnimation,
	// strokeColorAnimation);
	animatorSet.addListener(new Animator.AnimatorListener() {
	    @Override
	    public void onAnimationStart(Animator animation) {

	    }

	    @Override
	    public void onAnimationEnd(Animator animation) {
		if (mListener != null) {
		    mListener.onAnimationEnd();
		}
	    }

	    @Override
	    public void onAnimationCancel(Animator animation) {

	    }

	    @Override
	    public void onAnimationRepeat(Animator animation) {

	    }
	});
	animatorSet.play(widthAnimation1).after(widthAnimation);
	animatorSet.start();

    }
}