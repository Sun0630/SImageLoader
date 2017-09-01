package com.sx.simageloader.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

public class RecyclerViewItemDecoration extends RecyclerView.ItemDecoration {

    private Context mContext;
    /**
     * <pre>
     *      方向参数，默认纵向
     *      0x10000000:Vertical纵向
     *      0x20000000:Horizontal横向
     * </pre>
     */
    private int mOrientation = 0x10000000;
    /**
     * <pre>
     *      横向
     * </pre>
     */
    public static final int Horizontal = 0x10000000;
    /**
     * <pre>
     *      纵向
     * </pre>
     */
    public static final int Vertical = 0x20000000;
    private Paint mPaint;
    /**
     * <pre>
     *      颜色参数：分割线颜色
     *      默认颜色：#eeeeee
     * </pre>
     */
    private int mDividerColor =  Color.parseColor("#000000");
    /**
     * <pre>
     *      分割线高度
     *          默认2sp
     * </pre>
     */
    private int mDividerHeight = 2;
    /**
     * <pre>
     *     分割线宽度
     *          默认2sp
     * </pre>
     */
    private int mDividerWidth = 2;
    private static final int[] ATTRS = new int[]{android.R.attr.listDivider};
    private Drawable mDivider;

    private void setDividerColor(int mDividerColor) {
        this.mDividerColor = mDividerColor;
    }

    private void setDividerHeight(int mDividerHeight) {
        this.mDividerHeight = dip2px(mDividerHeight);
    }

    private void setDividerWidth(int mDividerWidth) {
        this.mDividerWidth = dip2px(mDividerWidth);
    }

    private void setDivider(Drawable mDivider) {
        this.mDivider = mDivider;
    }

    public void setOrientation(int mOrientation) {
        this.mOrientation = mOrientation;
    }

    /**
     * 默认分割线：高度为2px，颜色为灰色
     *
     * @param context
     * @param orientation 列表方向
     */
    public RecyclerViewItemDecoration(Context context, int orientation) {
        this.mContext = context;
        if ((orientation & LinearLayoutManager.VERTICAL) == 1
                && (orientation & LinearLayoutManager.HORIZONTAL) == 1) {
            throw new IllegalArgumentException("请输入正确的参数！");
        }
        mOrientation = orientation;

        final TypedArray a = context.obtainStyledAttributes(ATTRS);
        mDivider = a.getDrawable(0);
        a.recycle();
        if(mDivider==null){
            initPaint();
        }
    }


    /**
     * 初始化画笔
     */
    private void initPaint() {
        mPaint = new Paint(ANTI_ALIAS_FLAG);
        mPaint.setColor(mDividerColor);
    }
    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);
        if(getLayoutManagerType(parent) != 2){

            if(mOrientation == (Horizontal|Vertical)){
                drawDividerBothLine(c,parent);
            }else if(mOrientation == Vertical){
                drawDividerVertical(c, parent);
            }else {
                drawDividerHorizontal(c,parent);
            }
        }

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);
        int spanCount = getSpanCount(parent);
        //特殊的item间距
        if(getLayoutManagerType(parent) == 2){//流 折半 每个偏移一半的值 相邻就会偏移一个
            outRect.left = mDividerWidth/2;
            outRect.right = mDividerWidth/2;
            outRect.bottom = mDividerHeight/2;
            outRect.top = mDividerHeight/2;

        }else {//其他分割线
            if(mOrientation == (Horizontal|Vertical)){
                if((parent.getChildAdapterPosition(view)+1)%spanCount == 0){
                    outRect.set(0,0,0,mDividerHeight);
                }else {
                    outRect.set(0,0,mDividerWidth,mDividerHeight);
                }
            }else if(mOrientation == Vertical){
                outRect.set(0,0,0,mDividerHeight);
            }else if(mOrientation == Horizontal){
                outRect.set(0,0,mDividerHeight,0);
            }

        }
    }

    /**
     * 划竖排列表的分割线
     * @param c
     * @param parent
     */
    private void drawDividerVertical(Canvas c,RecyclerView parent){
        int leftParent = parent.getPaddingLeft();
        int rightParent = parent.getWidth()-parent.getPaddingRight();

        int childCount = parent.getChildCount();
        for(int i = 0;i < childCount ; i++){
            View mChildView = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) mChildView.getLayoutParams();

            int left = leftParent+layoutParams.leftMargin;
            int right = rightParent-layoutParams.rightMargin;
            int top = mChildView.getBottom()+layoutParams.bottomMargin;
            if (mDivider != null) {
                mDivider.setBounds(left, top, right, top+mDividerHeight);
                mDivider.draw(c);
            }
            if (mPaint != null) {
//                Log.i("定点", left + ":" + top + ":" + right + ":" + (top + mDividerHeight));
                Rect rect = new Rect(left, top, right, (top + mDividerHeight));
                c.drawRect(rect, mPaint);
            }
        }
    }

    /**
     * 划横排列表间隔线
     * @param canvas
     * @param parent
     */
    private void drawDividerHorizontal(Canvas canvas, RecyclerView parent) {
        int top = parent.getPaddingTop();
        int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom();
        int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            View mChildView = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) mChildView.getLayoutParams();
            int left = mChildView.getRight() + layoutParams.rightMargin;
            int right = left + mDividerHeight;

            if (mDivider != null) {
                mDivider.setBounds(left, top, right, bottom);
                mDivider.draw(canvas);

            }
            if (mPaint != null) {
                canvas.drawRect(left, top, right, bottom, mPaint);
            }
        }
    }

    /**
     * 同时画横向间隔线 竖向分割线
     * @param canvas
     * @param parent
     */
    private void drawDividerBothLine(Canvas canvas, RecyclerView parent) {
        int spanCount = getSpanCount(parent);
        int childSize = parent.getChildCount();
        for (int i = 0; i < childSize; i++) {
            View mChildView = parent.getChildAt(i);
            RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) mChildView.getLayoutParams();

            if((i+1) % spanCount == 0){//划竖向行分界线

                int left = parent.getPaddingLeft()+layoutParams.leftMargin;
                int right = parent.getMeasuredWidth()-parent.getPaddingRight()-layoutParams.rightMargin;
                int top = mChildView.getBottom()+layoutParams.bottomMargin;
                int bottom = top+mDividerHeight;
                if (mDivider != null) {
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(canvas);
                }
                if (mPaint != null) {
                    canvas.drawRect(left, top, right, bottom, mPaint);
                }
            }else{//划横向item间线

                int left = mChildView.getRight() + layoutParams.rightMargin;
                int right = left + mDividerWidth;
                int top = mChildView.getTop()-layoutParams.topMargin;
                int bottom = mChildView.getTop()+mChildView.getMeasuredHeight()+layoutParams.bottomMargin;
                if (mDivider != null) {
                    mDivider.setBounds(left, top, right, bottom);
                    mDivider.draw(canvas);
                }
                if (mPaint != null) {
                    canvas.drawRect(left, top, right, bottom, mPaint);
                }
            }
        }
    }
    /**
     * 获取列数
     *
     * @param parent
     * @return
     */
    private int getSpanCount(RecyclerView parent) {
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        int spanCount = -1;
        switch (getLayoutManagerType(parent)){
            case 1:
                spanCount = ((GridLayoutManager) layoutManager).getSpanCount();
                break;
            case 2:
                spanCount = ((StaggeredGridLayoutManager) layoutManager)
                        .getSpanCount();
                break;

        }
        return spanCount;
    }

    /**
     * 返回manager类型
     * @param parent
     * @return
     */
    private int getLayoutManagerType(RecyclerView parent){
        RecyclerView.LayoutManager layoutManager = parent.getLayoutManager();
        if (layoutManager instanceof GridLayoutManager) {
            return 1;
        } else if (layoutManager instanceof StaggeredGridLayoutManager) {
            return 2;
        } else if (layoutManager instanceof LinearLayoutManager){
            return 3;
        }else{
            return -1;
        }
    }
    public static class Builder{
        private Context mContext;
        RecyclerViewItemDecoration recyclerViewItemDecoration;
        /**
         * <pre>
         *      方向参数，默认纵向
         *      0x10000000:Vertical纵向
         *      0x20000000:Horizontal横向
         * </pre>
         */
        private int mOrientation = 0x10000000;
        /**
         * <pre>
         *      颜色参数：分割线颜色
         *      默认颜色：#eeeeee
         * </pre>
         */
        private int mDividerColor =  Color.parseColor("#000000");
        /**
         * <pre>
         *      分割线高度
         *          默认2sp
         * </pre>
         */
        private int mDividerHeight = 2;
        /**
         * <pre>
         *     分割线宽度
         *          默认2sp
         * </pre>
         */
        private int mDividerWidth = 2;
        /**
         * <pre>
         *     作为分割线的图片资源
         * </pre>
         */
        private Drawable mDivider;

        public Builder(Context mContext) {
            this.mContext = mContext;
            recyclerViewItemDecoration = new RecyclerViewItemDecoration(mContext,mOrientation);
        }

        /**
         * 设置方向
         * @param mOrientation
         * @return
         */
        public Builder setOrientation(int mOrientation) {
            this.mOrientation = mOrientation;
            recyclerViewItemDecoration.setOrientation(mOrientation);
            return this;
        }

        /**
         * 设置颜色
         * @param mDividerColor
         * @return
         */
        public Builder setDividerColor(int mDividerColor) {
            this.mDividerColor = mDividerColor;
            recyclerViewItemDecoration.setDividerColor(mDividerColor);
            return this;
        }

        /**
         * 设置纵向分割线高度
         * @param mDividerHeight dp单位
         * @return
         */
        public Builder setDividerHeight(int mDividerHeight) {
            this.mDividerHeight = mDividerHeight;
            recyclerViewItemDecoration.setDividerHeight(mDividerHeight);
            return this;
        }

        /**
         * 设置分割线宽度
         * @param mDividerWidth dp单位
         * @return
         */
        public Builder setDividerWidth(int mDividerWidth) {
            this.mDividerWidth = mDividerWidth;
            recyclerViewItemDecoration.setDividerWidth(mDividerWidth);
            return this;
        }

        /**
         * 设置分割线图片
         * @param mDivider
         * @return
         */
        public Builder setDivider(Drawable mDivider) {
            this.mDivider = mDivider;
            recyclerViewItemDecoration.setDivider(mDivider);
            return this;
        }
        public RecyclerViewItemDecoration build(){
            return recyclerViewItemDecoration;

        }
    }


    public int dip2px(float dpValue) {
        if(mContext == null){
            return 2;
        }
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public int px2dip(float pxValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }
}