package com.peng.cmoneycharge;

/**
 * 系统设置界面
 */
import peng.moneycharge.dao.DBOpenHelper;
import peng.moneycharge.dao.IncomeDAO;
import peng.moneycharge.dao.ItypeDAO;
import peng.moneycharge.dao.NoteDAO;
import peng.moneycharge.dao.PayDAO;
import peng.moneycharge.dao.PtypeDAO;
import peng.moneycharge.model.ActivityManager;
import peng.moneycharge.model.CustomDialog;

import com.cwp.cmoneycharge.R;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import android.view.KeyEvent;
import android.view.View;

public class Setting extends Activity {
	int userid;
	Intent intentr;
	private ListView listview;

	public Setting() {
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setting);
		ActivityManager.getInstance().addActivity(this); // 管理Activity,退出按钮点击时调用
		listview = (ListView) findViewById(R.id.settinglisv);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
				this, R.array.settingtype,
				android.R.layout.simple_expandable_list_item_1);

		listview.setAdapter(adapter);
	}

	@Override
	protected void onStart() {

		super.onStart();
		intentr = getIntent();
		userid = intentr.getIntExtra("grk.id", 100000001);
		listview.setOnItemClickListener(new OnItemClickListener() {// 为GridView设置项单击事件
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int pos,
					long arg3) {
				String result = arg0.getItemAtPosition(pos).toString();
				Intent intent = getIntent();// 创建Intent对象
				userid = intent.getIntExtra("grk.id", 100000001);
				switch (pos) {
				case 0:
					alarmDialog(pos);// 清空收入数据
					break;
				case 1:
					alarmDialog(pos); // 清空支出数据
					break;
				case 2:
					alarmDialog(pos); // 清空便签数据
					break;
				case 3://收入类型管理
					intentr = new Intent(Setting.this, InPtypeManager.class);
					intentr.putExtra("grk.id", userid);
					intentr.putExtra("type", 0);
					startActivity(intentr);
					break;
				case 4:
					intentr = new Intent(Setting.this, InPtypeManager.class);
					intentr.putExtra("grk.id", userid);
					intentr.putExtra("type", 1);
					startActivity(intentr);
					break;
				case 5:
					alarmDialog(pos); // 数据初始化
					break;
				case 6:
					// 关于系统
					intentr = new Intent(Setting.this, About.class);
					intentr.putExtra("grk.id", userid);
					startActivity(intentr);
					break;
				}
			}

		});
	}

	private void alarmDialog(int type) { // 退出程序的方法
		Dialog dialog = null;
		String ps = "收入数据", is = "支出数据", ns = "便签数据";
		CustomDialog.Builder customBuilder = new CustomDialog.Builder(
				Setting.this);

		customBuilder.setTitle("警告"); // 创建标题
		switch (type) {
		case 0:
			customBuilder
					.setMessage("将删除当前的用户所有" + is)
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									IncomeDAO incomeDAO = new IncomeDAO(
											Setting.this);
									incomeDAO.deleteUserData(userid);
									Toast.makeText(Setting.this, "已清空~！！",
											Toast.LENGTH_LONG).show();
								}

							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();

								}
							});
			break;

		case 1:
			customBuilder
					.setMessage("将删除当前的用户所有" + ps)
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									PayDAO payDAO = new PayDAO(Setting.this);
									payDAO.deleteUserData(userid);
									Toast.makeText(Setting.this, "已清空~！！",
											Toast.LENGTH_LONG).show();
									dialog.dismiss();
								}

							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();

								}
							});
			break;
		case 2:
			customBuilder
					.setMessage("将删除当前的用户所有" + ps)
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									NoteDAO noteDAO = new NoteDAO(Setting.this);
									noteDAO.deleteUserData(userid);
									Toast.makeText(Setting.this, "已清空~！！",
											Toast.LENGTH_LONG).show();
									dialog.dismiss();
								}

							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();

								}
							});
			break;
		case 5:
			customBuilder
					.setMessage("此操作将重置当前用户的收入、支出类型，确定还原吗？")
					.setPositiveButton("确定",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									ItypeDAO itypedao = new ItypeDAO(
											Setting.this);
									PtypeDAO ptypedao = new PtypeDAO(
											Setting.this);
									itypedao.initData(userid);
									ptypedao.initData(userid);
									Toast.makeText(Setting.this, "已还原~！！",
											Toast.LENGTH_LONG).show();
									dialog.dismiss();
								}

							})
					.setNegativeButton("取消",
							new DialogInterface.OnClickListener() {

								public void onClick(DialogInterface dialog,
										int which) {
									dialog.dismiss();

								}
							});
			break;

		}

		dialog = customBuilder.create();// 创建对话框
		dialog.show(); // 显示对话框

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) { // 监控/拦截/屏蔽返回键
			Intent intent = new Intent(Setting.this, Index.class);
			intent.putExtra("grk.id", userid);
			startActivity(intent);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
