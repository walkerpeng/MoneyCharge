package com.peng.cmoneycharge;

import java.util.Calendar;
import java.util.List;

import peng.moneycharge.dao.IncomeDAO;
import peng.moneycharge.dao.ItypeDAO;
import peng.moneycharge.model.ActivityManager;
import peng.moneycharge.model.Tb_income;

import com.cwp.cmoneycharge.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

public class AddIncome extends Activity {
	protected static final int DATE_DIALOG_ID = 0;// 创建日期对话框常量
	EditText txtInMoney, txtInTime, txtInhandler, txtInMark;// 创建4个EditText对象
	Spinner spInType;// 创建Spinner对象
	Button btnInSaveButton;// 创建Button对象“保存”
	Button btnInCancelButton;// 创建Button对象“取消”
	int userid;

	private int mYear;// 年
	private int mMonth;// 月
	private int mDay;// 日
	private ArrayAdapter<String> adapter;
	private String[] spdata;

	public AddIncome() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addincome);// 设置布局文件

		ActivityManager.getInstance().addActivity(this); // 管理Activity,退出按钮点击时调用
		txtInMoney = (EditText) findViewById(R.id.txtInMoney);// 获取金额文本框
		txtInTime = (EditText) findViewById(R.id.txtInTime);// 获取时间文本框
		txtInhandler = (EditText) findViewById(R.id.txtInhandler);// 获取付款方文本框
		txtInMark = (EditText) findViewById(R.id.txtInMark);// 获取备注文本框
		spInType = (Spinner) findViewById(R.id.spInType);// 获取类别下拉列表
		btnInSaveButton = (Button) findViewById(R.id.btnInSave);// 获取保存按钮
		btnInCancelButton = (Button) findViewById(R.id.btnInCancel);// 获取取消按钮

		final Calendar c = Calendar.getInstance();// 获取当前系统日期
		mYear = c.get(Calendar.YEAR);// 获取年份
		mMonth = c.get(Calendar.MONTH);// 获取月份
		mDay = c.get(Calendar.DAY_OF_MONTH);// 获取天数

	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();// 实现基类中的方法
		Intent intentr = getIntent();
		userid = intentr.getIntExtra("grk.id", 100000001);
		updateDisplay();// 显示当前系统时间
		ItypeDAO itypeDAO = new ItypeDAO(AddIncome.this);
		List<String> spdatalist;
		spdatalist = itypeDAO.getItypeName(userid);
		spdata = spdatalist.toArray(new String[spdatalist.size()]);// 在tb_itype中按用户id读取
		adapter = new ArrayAdapter<String>(AddIncome.this,
				android.R.layout.simple_spinner_item, spdata); // 动态生成收入类型列表
		spInType.setAdapter(adapter);

		txtInTime.setOnClickListener(new OnClickListener() {// 为时间文本框设置单击监听事件
					@SuppressWarnings("deprecation")
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						showDialog(DATE_DIALOG_ID);// 显示日期选择对话框
					}
				});

		btnInSaveButton.setOnClickListener(new OnClickListener() {// 为保存按钮设置监听事件
					@SuppressLint("NewApi")
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						String strInMoney = txtInMoney.getText().toString();// 获取金额文本框的值
						if (!strInMoney.isEmpty()) {// 判断金额不为空
							// 创建InaccountDAO对象
							IncomeDAO incomeDAO = new IncomeDAO(AddIncome.this);
							// 创建Tb_inaccount对象
							Tb_income tb_income = new Tb_income(userid,
									incomeDAO.getMaxNo(userid) + 1, Double
											.parseDouble(strInMoney),
									setTimeFormat(), (spInType
											.getSelectedItemPosition() + 1),
									txtInhandler.getText().toString(),
									txtInMark.getText().toString());
							incomeDAO.add(tb_income);// 添加收入信息
							// 弹出信息提示
							Toast.makeText(AddIncome.this, "〖新增收入〗数据添加成功！",
									Toast.LENGTH_SHORT).show();
							Intent intent = new Intent(AddIncome.this,
									Income.class);
							intent.putExtra("grk.id", userid);
							startActivity(intent);
						} else {
							Toast.makeText(AddIncome.this, "请输入收入金额！",
									Toast.LENGTH_SHORT).show();
						}
					}
				});
		btnInCancelButton.setOnClickListener(new OnClickListener() {// 为时间文本框设置单击监听事件
					@Override
					public void onClick(View arg0) {
						txtInMoney.setText("");// 设置金额文本框为空
						txtInMoney.setHint("0.00");// 为金额文本框设置提示
						txtInTime.setText("");// 设置时间文本框为空
						txtInhandler.setText("");// 设置地址文本框为空
						txtInMark.setText("");// 设置备注文本框为空
						spInType.setSelection(0);// 设置类别下拉列表默认选择第一项
						Intent intent = new Intent(AddIncome.this, Income.class);
						intent.putExtra("grk.id", userid);
						startActivity(intent);
					}
				});

	}

	@Override
	protected Dialog onCreateDialog(int id)// 重写onCreateDialog方法
	{
		switch (id) {
		case DATE_DIALOG_ID:// 弹出日期选择对话框
			return new DatePickerDialog(this, mDateSetListener, mYear, mMonth,
					mDay);
		}
		return null;
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			mYear = year;// 为年份赋值
			mMonth = monthOfYear;// 为月份赋值
			mDay = dayOfMonth;// 为天赋值
			updateDisplay();// 显示设置的日期
		}
	};

	private void updateDisplay() {
		// 显示设置的时间
		txtInTime.setText(new StringBuilder().append(mYear).append("-")
				.append(mMonth + 1).append("-").append(mDay));
	}

	private String setTimeFormat() {
		String date = txtInTime.getText().toString();

		int y, m, d;
		String sm, sd;
		int i = 0, j = 0, k = 0;

		for (i = 0; i < date.length(); i++) {
			if (date.substring(i, i + 1).equals("-") && j == 0)
				j = i;
			else if (date.substring(i, i + 1).equals("-"))
				k = i;
		}
		y = Integer.valueOf(date.substring(0, j));
		m = Integer.valueOf(date.substring(j + 1, k));
		d = Integer.valueOf(date.substring(k + 1));
		if (m < 10) {
			sm = "0" + String.valueOf(m);
		} else
			sm = String.valueOf(m);
		if (d < 10) {
			sd = "0" + String.valueOf(d);
		} else
			sd = String.valueOf(d);

		return String.valueOf(y) + "-" + sm + "-" + sd;

	}
}
