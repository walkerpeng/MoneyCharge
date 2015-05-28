package com.peng.cmoneycharge;

import peng.moneycharge.dao.AccountDAO;
import peng.moneycharge.model.ActivityManager;
import peng.moneycharge.model.Tb_account;

import com.cwp.cmoneycharge.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class ChangePwd extends Activity {
	EditText cptusername, cpusername;
	EditText cptpwd, cppwd;
	Button bcpsure, bcpcancle, bcptchange, bcptcancle;
	String suserneme, spwd;
	LinearLayout L1, L2;
	Intent intentr;
	int userid;

	public ChangePwd() {
		// TODO Auto-generated constructor stub
	}

	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.changepwd);// 设置布局文件

		ActivityManager.getInstance().addActivity(this); // 管理Activity,退出按钮点击时调用
		cptusername = (EditText) findViewById(R.id.cptusername);
		cpusername = (EditText) findViewById(R.id.cpusername);
		cptpwd = (EditText) findViewById(R.id.cptpassword);
		cppwd = (EditText) findViewById(R.id.cppassword);
		bcpcancle = (Button) findViewById(R.id.btncpCancle);
		bcptcancle = (Button) findViewById(R.id.btncptCancle);
		bcpsure = (Button) findViewById(R.id.btncpsure);
		bcptchange = (Button) findViewById(R.id.btncptsure);
		L1 = (LinearLayout) findViewById(R.id.cpyanzheng);
		L2 = (LinearLayout) findViewById(R.id.cphint);
		L1.setVisibility(View.VISIBLE);
		L2.setVisibility(View.GONE);

	}

	@Override
	protected void onStart() {
		super.onStart();
		intentr = getIntent();
		userid = intentr.getIntExtra("grk.id", 100000001);
		bcpsure.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				AccountDAO accountDAO = new AccountDAO(ChangePwd.this);// 创建AccountDAO对象
				suserneme = cpusername.getText().toString();
				spwd = cppwd.getText().toString();
				Tb_account tb_account = accountDAO.find(suserneme, spwd);
				if (tb_account.get_id() == 100000001) {
					Toast.makeText(ChangePwd.this,
							"默认用户不允许修改用户名密码！/n 若需要保存私密信息，请建新账户！",
							Toast.LENGTH_LONG).show();
				} else if (accountDAO.find(suserneme, spwd) == null) {
					Toast.makeText(ChangePwd.this, "用户名或密码错误！",
							Toast.LENGTH_LONG).show();
				} else {

					// 弹出信息提示

					cptusername.setText(suserneme);
					cptpwd.setText(spwd);
					L1.setVisibility(View.GONE);
					L2.setVisibility(View.VISIBLE);
				}
			}
		});
		bcptchange.setOnClickListener(new OnClickListener() {
			@SuppressLint("NewApi")
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(ChangePwd.this, Index.class);// 创建Intent对象
				AccountDAO accountDAO = new AccountDAO(ChangePwd.this);// 创建AccountDAO对象
				Tb_account tb_account = accountDAO.find(suserneme, spwd);
				if (cptusername.getText().toString().trim().isEmpty()
						|| cptpwd.getText().toString().trim().isEmpty()) {
					Toast.makeText(ChangePwd.this, "请将信息填写完整~！",
							Toast.LENGTH_LONG).show();
				} else {
					accountDAO.update(tb_account.get_id(), cptusername
							.getText().toString(), cptpwd.getText().toString());
					Toast.makeText(ChangePwd.this,
							"修改成功！已自动登陆！" + cptusername.getText().toString(),
							Toast.LENGTH_LONG).show();
					intent.putExtra("grk.id", tb_account.get_id());
					startActivity(intent);
				}
			}
		});
		bcpcancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(ChangePwd.this, Account.class);// 创建Intent对象
				intent.putExtra("grk.id", userid);
				startActivity(intent);// 启动主Activity
			}
		});
		bcptcancle.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(ChangePwd.this, Account.class);// 创建Intent对象
				intent.putExtra("grk.id", userid);
				startActivity(intent);// 启动主Activity
			}
		});
	}
}
