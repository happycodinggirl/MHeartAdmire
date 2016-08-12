package cn.morningtec.mheartadmire;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.PointF;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Random;

/**
 * Created by huangxl on 2016/8/12.
 */
public class FavorLayout extends RelativeLayout {

    int layoutWidth, layoutHeight;

    int heartWidth,heartHeight;

    Random random=new Random();

    Drawable[] drawables=new Drawable[3];
    RelativeLayout.LayoutParams layoutParams;

    Interpolator[] intercaptions=new Interpolator[3];
    private android.content.Context mContext;


    public FavorLayout(Context context) {
        super(context);
        init(context);
    }

    public FavorLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public FavorLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context){
        mContext=context;
        layoutWidth =getResources().getDisplayMetrics().widthPixels;
        layoutHeight =getResources().getDisplayMetrics().heightPixels;
        Drawable drawableRed=getResources().getDrawable(R.mipmap.pl_red);
        Drawable drawableBlue=getResources().getDrawable(R.mipmap.pl_blue);
        Drawable drawableYellow=getResources().getDrawable(R.mipmap.pl_yellow);
        drawables[0]=drawableRed;
        drawables[1]=drawableBlue;
        drawables[2]=drawableYellow;
        heartWidth=drawableRed.getIntrinsicWidth();
        heartHeight=drawableRed.getIntrinsicHeight();
        layoutParams=new RelativeLayout.LayoutParams(heartWidth,heartHeight);
        layoutParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,RelativeLayout.TRUE);
        layoutParams.addRule(RelativeLayout.CENTER_HORIZONTAL,TRUE);


        Interpolator interception1=new AccelerateInterpolator();
        Interpolator interception2=new AccelerateDecelerateInterpolator();
        Interpolator interception3=new DecelerateInterpolator();
        intercaptions[0]=interception1;
        intercaptions[1]=interception2;
        intercaptions[2]=interception3;
      //  addFlavor();



    }

    public void addFlavor() {
        ImageView imageView=new ImageView(mContext);
        imageView.setBackgroundDrawable(drawables[random.nextInt(3)]);

        addView(imageView,layoutParams);
        AnimatorSet animatorSet=new AnimatorSet();
       // animatorSet.playSequentially(genScaleAnim(imageView),genBezierAnimator(imageView));
        animatorSet.playTogether(genScaleAnim(imageView),genBezierAnimator(imageView));
        animatorSet.setDuration(4000);
        animatorSet.setTarget(imageView);
        animatorSet.setInterpolator(intercaptions[random.nextInt(3)]);
        animatorSet.addListener(new CustomAnimationListener(imageView));
        animatorSet.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        layoutWidth=getMeasuredWidth();
        layoutHeight=getMeasuredHeight();

    }

    public ValueAnimator genBezierAnimator(View target){
        BezierEvaluator bezierEvaluator=new BezierEvaluator(genPointF(1),genPointF(2));
        ValueAnimator valueAnimator=ValueAnimator.ofObject(bezierEvaluator,new PointF(layoutWidth/2-heartWidth/2,(layoutHeight-heartHeight)),new PointF(random.nextInt(layoutHeight-100),0));
        BezierUpdateAnimatorListener bezierAnimatorListener=new BezierUpdateAnimatorListener(target);
        valueAnimator.addListener(new CustomAnimationListener(target));
        valueAnimator.addUpdateListener(bezierAnimatorListener);
        valueAnimator.setTarget(target);
        valueAnimator.setInterpolator(intercaptions[random.nextInt(3)]);
       return valueAnimator;
    }

    private PointF genPointF(int scale){
        PointF pointF=new PointF();
        Random random=new Random();
        pointF.x=random.nextInt(layoutWidth -100);
        pointF.y=random.nextInt(layoutHeight -100)/scale;
        return pointF;
    }

    public AnimatorSet genScaleAnim(View view){
        ObjectAnimator scaleXobjectAnimator=ObjectAnimator.ofFloat(view,View.SCALE_X,1,2,1);
        ObjectAnimator scaleYobjectAnimator=ObjectAnimator.ofFloat(view,View.SCALE_Y,1,2,1);
        AnimatorSet animatorSet=new AnimatorSet();
        animatorSet.playTogether(scaleXobjectAnimator,scaleYobjectAnimator);
        animatorSet.setDuration(1000);
        return animatorSet;

    }

    static class BezierUpdateAnimatorListener implements ValueAnimator.AnimatorUpdateListener{

        View view;

        public BezierUpdateAnimatorListener(View view) {
            this.view = view;
        }

        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            PointF pointF= (PointF) animation.getAnimatedValue();
            view.setX(pointF.x);
            view.setY(pointF.y);
            view.setAlpha(1-animation.getAnimatedFraction());
        }
    }

    class CustomAnimationListener implements ValueAnimator.AnimatorListener{

        View view;

        public CustomAnimationListener(View view) {
            this.view = view;
        }

        @Override
        public void onAnimationStart(Animator animation) {

        }

        @Override
        public void onAnimationEnd(Animator animation) {
            removeView(view);

        }

        @Override
        public void onAnimationCancel(Animator animation) {

        }

        @Override
        public void onAnimationRepeat(Animator animation) {

        }
    }




}
