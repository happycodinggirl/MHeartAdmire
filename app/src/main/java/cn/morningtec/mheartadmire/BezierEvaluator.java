package cn.morningtec.mheartadmire;

import android.animation.TypeEvaluator;
import android.graphics.PointF;

/**
 * Created by huangxl on 2016/8/12.
 */
public class BezierEvaluator implements TypeEvaluator<PointF> {

    PointF pointF1,pointF2;

    public BezierEvaluator(PointF pointF1, PointF pointF2) {
        this.pointF1 = pointF1;
        this.pointF2 = pointF2;
    }

    @Override
    public PointF evaluate(float fraction, PointF startValue, PointF endValue) {
        PointF pointF0=startValue;
        PointF pointF3=endValue;
        float timeLeft=1-fraction;
        float time=fraction;
        PointF resultF=new PointF();
        float timeLeft3More=timeLeft*timeLeft*timeLeft;
        resultF.x=timeLeft3More*pointF0.x+3*pointF1.x*time*timeLeft*timeLeft+3*pointF2.x*time*time*timeLeft+pointF3.x*time*time*time;
        resultF.y=timeLeft3More*pointF0.y+3*pointF1.y*time*timeLeft*timeLeft+3*pointF2.y*time*time*timeLeft+pointF3.y*time*time*time;


        return resultF;
    }
}
