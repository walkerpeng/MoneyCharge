package com.peng.cmoneycharge;

import peng.moneycharge.dao.AccountDAO;
import peng.moneycharge.dao.IncomeDAO;
import peng.moneycharge.dao.ItypeDAO;
import peng.moneycharge.dao.NoteDAO;
import peng.moneycharge.dao.PayDAO;
import peng.moneycharge.dao.PtypeDAO;
import peng.moneycharge.model.ActivityManager;
import peng.moneycharge.model.CustomDialog;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.cwp.cmoneycharge.R;

@SuppressLint("NewApi")
public class Account extends Activity {
	Button register, login, modify, adminlogin, alldelete;
	TextView usernow;
	int userid;

	public Account() {
		// TODO Auto-generated constructor stub
	}

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.account);// 设置布局文件

		ActivityManager.getInstance().addActivity(this); // 管理Activity,退出按钮点击时调用
		register = (Button) findViewById(R.id.accregister);
		login = (Button) findViewById(R.id.acclogin);
		modify = (Button) findViewById(R.id.accchange);
		usernow = (TextView) findViewById(R.id.usernow);
		adminlogin = (Button) findViewById(R.id.accadminlogin);
		alldelete = (Button) findViewById(R.id.alldelete);

	}

	@Override
	public void onStart() {
		super.onStart();
		Intent intentr = getIntent();
		userid = intentr.getIntExtra("grk.id", 100000001);
		AccountDAO accountdao = new AccountDAO(Account.this);
		ColorDrawable drawable;
		if (userid == 100000001) {
			drawable = new ColorDrawable(0xff6e6e6e);
			adminlogin.setBackground(drawable);
			adminlogin.setEnabled(false);
		} else {
			drawable = new ColorDrawable(0xff21a0a0);
			adminlogin.setBackground(drawable);
			adminlogin.setEnabled(true);
		}
		usernow.setText(accountdao.find(userid));
		register.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Account.this, Register.class);
				intent.putExtra("grk.id", userid);
				startActivity(intent);
			}
		});
		login.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Account.this, Login.class);
				intent.putExtra("grk.id", userid);
				startActivity(intent);
			}
		});
		modify.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Account.this, ChangePwd.class);
				intent.putExtra("grk.id", userid);
				startActivity(intent);
			}
		});
		adminlogin.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Account.this, Index.class);
				intent.putExtra("grk.id", 100000001);
				Toast.makeText(Account.this, "已切换为默认用户", Toast.LENGTH_LONG)
						.show();
				startActivity(intent);
			}
		});
		alldelete.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				deleteDialog();

			}
		});
	}

	private void deleteDialog() { // 退出程序的方法
		Dialog dialog = null;

		CustomDialog.Builder customBuilder = new CustomDialog.Builder(
				Account.this);

		customBuilder
				.setTitle("警告")
				// 创建标题

				.setMessage("此操作将删除当前用户及该用户所有数据，确定删除吗？")
				// 表示对话框的内容

				.setPositiveButton("确定", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						if (userid != 100000001) {
							AccountDAO accountdao = new AccountDAO(Account.this);
							PayDAO paydao = new PayDAO(Account.this);
							IncomeDAO incomedao = new IncomeDAO(Account.this);
							ItypeDAO itypedao = new ItypeDAO(Account.this);
							PtypeDAO ptypedao = new PtypeDAO(Account.this);
							NoteDAO notedao = new NoteDAO(Account.this);
							notedao.deleteUserData(userid);
							ptypedao.deleteById(userid);
							itypedao.deleteById(userid);
							paydao.deleteUserData(userid);
							incomedao.deleteUserData(userid);
							accountdao.deleteById(userid);

							Intent intent = new Intent(Account.this,
									Index.class);
							intent.putExtra("grk.id", 100000001);
							Toast.makeText(Account.this, "删除成功，已登陆默认用户",
									Toast.LENGTH_LONG).show();
							startActivity(intent);
						} else {

							Toast.makeText(Account.this, "默认用户不允许删除！",
									Toast.LENGTH_LONG).show();
							dialog.dismiss();
						}
					}

				})
				.setNegativeButton("取消", new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();

					}
				});
		dialog = customBuilder.create();// 创建对话框
		dialog.show(); // 显示对话框

	}

	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) { // 监控/拦截/屏蔽返回键
			Intent intent = new Intent(Account.this, Index.class);
			intent.putExtra("grk.id", userid);
			startActivity(intent);
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
