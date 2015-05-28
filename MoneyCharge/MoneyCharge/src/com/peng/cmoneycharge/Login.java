/**
 * 
 */
package com.peng.cmoneycharge;

/**
 * @author grk
 *
 */
import peng.moneycharge.dao.AccountDAO;
import peng.moneycharge.model.ActivityManager;
import peng.moneycharge.model.Tb_account;

import com.cwp.cmoneycharge.R;


import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("NewApi")
public class Login extends Activity {
	EditText tusername;
	EditText tpwd;
	Button blogin, bcancle;
	Intent intentr;
	int userid;

	public Login() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login);// 设置布局文件

		ActivityManager.getInstance().addActivity(this); // 管理Activity,退出按钮点击时调用
		tusername = (EditText) findViewById(R.id.username);// 获取用户名
		tpwd = (EditText) findViewById(R.id.password);// 获取用户名
		blogin = (Button) findViewById(R.id.btnLogin);// 获取登录按钮
		bcancle = (Button) findViewById(R.id.btnCancle);// 获取取消按钮

	}

	@Override
	protected void onStart() {
		super.onStart();
		intentr = getIntent();
		userid = intentr.getIntExtra("grk.id", 100000001);
		blogin.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(Login.this, Index.class);// 创建Intent对象
				AccountDAO accountDAO = new AccountDAO(Login.this);// 创建AccountDAO对象
				Tb_account account = accountDAO.find(tusername.getText()
						.toString(), tpwd.getText().toString());
				// 判断是否有密码及是否输入了密码
				if (account != null) {
					intent.putExtra("grk.id", account.get_id());
					startActivity(intent);// 启动主Activity
				} else if (tusername.getText().toString().isEmpty()
						|| tpwd.getText().toString().isEmpty()) {
					Toast.makeText(Login.this, "用户名或密码不为空！", Toast.LENGTH_LONG)
							.show();
					tpwd.setText("");
				} else {

					// 弹出信息提示
					Toast.makeText(Login.this, "用户名或密码错误！", Toast.LENGTH_LONG)
							.show();
					tpwd.setText("");
				}

			}

		});
		bcancle.setOnClickListener(new OnClickListener() {// 为取消按钮设置监听事件
			@Override
			public void onClick(View arg0) {
				Intent intent = new Intent(Login.this, Account.class);// 创建Intent对象
				intent.putExtra("grk.id", userid);
				startActivity(intent);// 启动主Activity
			}
		});
		tusername.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				tusername.setText("");
				tpwd.setText("");
			}
		});
		tpwd.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				tpwd.setText("");
			}
		});
	}
}
