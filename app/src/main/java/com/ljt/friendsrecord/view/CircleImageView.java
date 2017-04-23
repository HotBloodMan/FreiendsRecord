package com.ljt.friendsrecord.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.widget.ImageView;

import com.ljt.friendsrecord.ui.R;

/**
 * Created by 1 on 2017/4/23.
 */

public class CircleImageView extends ImageView {

    int borderColor;
    int borderWidth;

    public CircleImageView(Context context) {
        this(context,null);
    }

    public CircleImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context,attrs);
    }

    public CircleImageView(Context context, AttributeSet attrs) {
        this(context,attrs,0);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray t = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView);
        borderColor=t.getColor(R.styleable.CircleImageView_border_color,
                Color.WHITE);
        borderWidth=t.getDimensionPixelSize(R.styleable.CircleImageView_border_width,2);
        t.recycle();
    }

/**
 * 将作为参数传入的方形的Bitmap对象
 * 变成圆形的Bitmap对象
 * 然后，“画到”组件上
 * @param bitmap 传入的默认方形图片
 */
    public void setCircleImageBitmap(Bitmap bitmap){
        int width=getWidth();
        int height=getHeight();
        if(width==0||height==0){
            width= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,80,getResources().getDisplayMetrics());
            height= (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,80,getResources().getDisplayMetrics());
        }
        bitmap=Bitmap.createScaledBitmap(bitmap,width,height,true);

        Bitmap bm = Bitmap.createBitmap(width, height, bitmap.getConfig());
        Canvas c = new Canvas(bm);
        Paint p = new Paint(Paint.ANTI_ALIAS_FLAG);
        //画圆
        float radius=Math.min(width,height);
        c.drawCircle(width/2,height/2,radius/2-borderWidth,p);
        p.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        c.drawBitmap(bitmap,0,0,p);

        //画边
        p.reset();
        p.setAntiAlias(true);
        p.setStyle(Paint.Style.STROKE);
        p.setColor(borderColor);
        p.setStrokeWidth(borderWidth);

        c.drawCircle(width/2,height/2,radius/2-borderWidth,p);

        setScaleType(ScaleType.CENTER);
        super.setImageBitmap(bm);
    }
}
