package com.peng.cmoneycharge;

/**
 * 系统主界面菜单
 */
import peng.moneycharge.model.ActivityManager;
import com.cwp.cmoneycharge.R;
import com.peng.cmoneycharge.chart.MainChart;
import com.peng.cmoneycharge.fragment.SimpleChartDemo;
import com.zhy.view.CircleMenuLayout;
import com.zhy.view.CircleMenuLayout.OnMenuItemClickListener;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

public class Index extends Activity {
	int userid;
	int returnflag;
	private CircleMenuLayout mCircleMenuLayout;

	private String[] mItemTexts = new String[] { "我的支出", "我的收入", "我的便签",
			"数据统计", "账户管理", "系统设置" };
	private int[] mItemImgs = new int[] { R.drawable.home_mbank_1_normal,
			R.drawable.home_mbank_2_normal, R.drawable.home_mbank_3_normal,
			R.drawable.home_mbank_4_normal, R.drawable.home_mbank_5_normal,
			R.drawable.home_mbank_6_normal };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main02);

		ActivityManager.getInstance().addActivity(this); // 管理Activity,退出按钮点击时调用

		mCircleMenuLayout = (CircleMenuLayout) findViewById(R.id.id_menulayout);
		mCircleMenuLayout.setMenuItemIconsAndTexts(mItemImgs, mItemTexts);
	}

	@Override
	protected void onStart() {
		super.onStart();
		Intent intentr = getIntent();
		userid = intentr.getIntExtra("grk.id", 100000001);// 默认用户
		returnflag = 0;
		mCircleMenuLayout
				.setOnMenuItemClickListener(new OnMenuItemClickListener() {

					@Override
					public void itemClick(View view, int pos) {
						Intent intent = null;// 创建Intent对象
						switch (pos) {
						case 0:// 我的支出
							intent = new Intent(Index.this, Pay.class);// 使用AddOutaccount窗口初始化Intent
							intent.putExtra("grk.id", userid);
							startActivity(intent);
							break;
						case 1:// 我的收入
							intent = new Intent(Index.this, Income.class);
							intent.putExtra("grk.id", userid);
							startActivity(intent);
							break;
						case 2:// 我的便签
							intent = new Intent(Index.this, Note.class);
							intent.putExtra("grk.id", userid);
							startActivity(intent);
							break;

						case 3:// 数据统计
							intent = new Intent(Index.this, MainChart.class);
							intent.putExtra("grk.id", userid);
							startActivity(intent);
							break;
						case 4:// 账户管理
							intent = new Intent(Index.this, Account.class);
							intent.putExtra("grk.id", userid);
							startActivity(intent);
							break;
						case 5:// 系统设置
							intent = new Intent(Index.this, Setting.class);
							intent.putExtra("grk.id", userid);
							startActivity(intent);
							break;
						}
					}

					@Override
					public void itemCenterClick(View view) {
						// TODO Auto-generated method stub

					}
				});
	}

	/**
	 * Build the desired Dialog CUSTOM or DEFAULT
	 */

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.index, menu);
		return true;
	}

	public void quit() {
		ActivityManager.getInstance().exit();
	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) { // 监控/拦截/屏蔽返回键
			if (returnflag == 0) {
				Toast.makeText(Index.this, "再按一次退出程序！", Toast.LENGTH_SHORT)
						.show();
				returnflag = 1;
			} else {
				quit();
			}
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
