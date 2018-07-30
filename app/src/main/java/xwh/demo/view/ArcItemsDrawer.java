package xwh.demo.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/**
 * 绘制圆弧，分段
 * Created by XWH on 2016/3/17.
 */
public class ArcItemsDrawer extends SurfaceView implements SurfaceHolder.Callback {

    private boolean isSurfaceCreated = false;
    private Canvas canvas;
    private SurfaceHolder surfaceHolder;

    private int mSelectedColor = Color.parseColor("#24ca72");
    private int mBgColor = Color.WHITE;
    private RectF mRect;
    private RectF mRectCircle;
    private Paint mPaintSelected;
    private Paint mPaintBg;

    private float selectedArcWidth = 32f;

    public ArcItemsDrawer(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }
    public ArcItemsDrawer(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        isSurfaceCreated = true;
        updateView();
    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        isSurfaceCreated = false;
    }

    public void updateView() {
        if (isSurfaceCreated) {
            canvas = surfaceHolder.lockCanvas(null);
            doDraw();
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }


    private void init() {

        this.setZOrderOnTop(true);//设置画布  背景透明
        this.getHolder().setFormat(PixelFormat.TRANSLUCENT);

        surfaceHolder = getHolder();
        surfaceHolder.addCallback(this); //设置Surface生命周期回调


        //创建画笔
        mPaintSelected = new Paint();
        mPaintSelected.setAntiAlias(true);
        mPaintSelected.setStyle(Paint.Style.STROKE);
        mPaintSelected.setStrokeWidth(selectedArcWidth);
        mPaintSelected.setStrokeCap(Paint.Cap.SQUARE);
        mPaintSelected.setColor(mSelectedColor);

        mPaintBg = new Paint();
        mPaintBg.setAntiAlias(true);
        mPaintBg.setStyle(Paint.Style.STROKE);
        mPaintBg.setStrokeWidth(1f);
        mPaintBg.setStrokeCap(Paint.Cap.SQUARE);
        mPaintBg.setColor(mBgColor);

    }

    float left = 0f;
    float top = 0f;
    float right = 640f;
    float bottom = 640f;

    protected void doDraw() {
        // 清空画布
        canvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.SRC);


        this.mRect = new RectF(left, top, right, bottom);

        mPaintBg.setStrokeWidth(1f);
        canvas.drawRect(mRect, mPaintBg);
        canvas.drawLine(left, bottom/2, right, bottom/2, mPaintBg);
        canvas.drawLine(right/2, top, right/2, bottom, mPaintBg);

        this.mRectCircle = new RectF(left+ selectedArcWidth /2, top+ selectedArcWidth /2, right- selectedArcWidth /2, bottom- selectedArcWidth /2);

        mPaintBg.setStrokeWidth(selectedArcWidth);

        canvas.drawArc(mRectCircle, 0f, 360f, false, mPaintBg);



        if (mRectAnim == null) {
            mRectAnim = new RectF(left+ selectedArcWidth /2, top+ selectedArcWidth /2, right- selectedArcWidth /2, bottom- selectedArcWidth /2);
        }


        float offset = selectedArcWidth/2;
        float a = (float) (360f * offset / (2 * Math.PI * (mRectCircle.bottom - mRectCircle.top - selectedArcWidth) /2 ));

        float offsetMargin = selectedArcWidth/2;
        float b = (float) (360f * offsetMargin / (2 * Math.PI * (mRectCircle.bottom - mRectCircle.top - selectedArcWidth) /2 ));


        canvas.drawArc(mRectAnim, 0f + a, 90f- 2*a -b, false, mPaintSelected);

        canvas.drawArc(mRectAnim, 90f + a, 90f- 2*a, false, mPaintSelected);


    }



    private boolean isAnimStart;
    private RectF mRectAnim;

    public void updateViewByAnim() {
        if (isAnimStart) {
            return;
        }

        new Thread(new Runnable() {
            @Override
            public void run() {
                isAnimStart = true;
                /*for(int i=0; i<=10; i++) {
                    mPaintSelected.setAlpha((int) (255 * 0.1 * i));
                    updateView();
                    try {
                        Thread.sleep(30);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }*/

                for(int i=0; i<=10; i++) {
                    mRectAnim = new RectF(left+ selectedArcWidth /2 + i*4, top+ selectedArcWidth /2 + i*4, right- selectedArcWidth /2 - i*4, bottom- selectedArcWidth /2 - i*4);

                    //mPaintSelected.setAlpha((int) (255 * 0.01 * i));

                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    updateView();
                }

                isAnimStart = false;
            }
        }).start();
    }





}
