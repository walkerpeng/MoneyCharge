package com.peng.cmoneycharge.chart;
import com.cwp.cmoneycharge.R;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewTreeObserver.OnPreDrawListener;

/**
 * 圆弧计分
 * @author Administrator
 *
 */
public class HomeArc extends View { 

	private Paint paint_black, paint_white;//定义画笔
	private RectF rectf;
	private float tb;
	private int blackColor = 0x70000000; // 底黑色
	private int whiteColor = 0xddffffff; // 白色
	private int score;
	private float arc_y = 0f;
	private int score_text;

	public HomeArc(Context context, int score) {
		super(context);
		init(score);
	}

	public void init(int score) {
		this.score = score;
		Resources res = getResources();
		tb = res.getDimension(R.dimen.historyscore_tb);

		paint_black = new Paint();
		paint_black.setAntiAlias(true);//抗锯齿
		paint_black.setColor(blackColor);//画笔颜色
		paint_black.setStrokeWidth(tb * 0.2f);//设置画笔的粗细
		paint_black.setStyle(Style.STROKE);

		paint_white = new Paint();
		paint_white.setAntiAlias(true);
		paint_white.setColor(whiteColor);
		paint_white.setTextSize(tb*6.0f);
		paint_white.setStrokeWidth(tb * 0.2f);
		paint_white.setTextAlign(Align.CENTER);//白圈居中
		paint_white.setStyle(Style.STROKE);

		rectf = new RectF();//声明一个新矩形
		rectf.set(tb * 0.5f, tb * 0.5f, tb * 18.5f, tb * 18.5f);//创建指定坐标的新的矩形

		setLayoutParams(new LayoutParams((int) (tb * 19.5f), (int) (tb * 19.5f)));

		this.getViewTreeObserver().addOnPreDrawListener(
				new OnPreDrawListener() {
					public boolean onPreDraw() {
						new thread();
						getViewTreeObserver().removeOnPreDrawListener(this);
						return false;
					}
				});
	}

	protected void onDraw(Canvas c) {
		super.onDraw(c);
		c.drawArc(rectf, -90, 360, false, paint_black);//黑画笔预先画一个圆弧
		c.drawArc(rectf, -90, arc_y, false, paint_white);
		c.drawText("" + score_text, tb * 9.7f, tb * 11.0f, paint_white);
	}

	//若是初次打开，则另起线程绘制圆弧
	class thread implements Runnable {
		private Thread thread;
		private int statek;
		int count;

		public thread() {
			thread = new Thread(this);
			thread.start();
		}

		public void run() {
			while (true) {
				switch (statek) {
				case 0:
					try {
						Thread.sleep(200);
						statek = 1;
					} catch (InterruptedException e) {
					}
					break;
				case 1:
					try {
						Thread.sleep(15);
						arc_y += 3.6f;//设置绘制增长速率
						score_text++;
						count++;
						postInvalidate();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					break;
				}
				if (count >= score)
					break;
			}
		}
	}

}