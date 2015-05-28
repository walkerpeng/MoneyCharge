package com.peng.cmoneycharge;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import peng.moneycharge.dao.PayDAO;
import peng.moneycharge.dao.PtypeDAO;
import peng.moneycharge.model.Datapicker;
import peng.moneycharge.model.Tb_pay;

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

public class AddPay extends Activity {
	protected static final int DATE_DIALOG_ID = 0;// 创建日期对话框常量
	EditText txtMoney, txtTime, txtAddress, txtMark;// 创建4个EditText对象
	Spinner spType;// 创建Spinner对象
	Button btnSaveButton;// 创建Button对象“保存”
	Button btnCancelButton;// 创建Button对象“取消”
	int userid;

	private int mYear;// 年
	private int mMonth;// 月
	private int mDay;// 日

	private ArrayAdapter<String> adapter;
	private String[] spdata;

	public AddPay() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addpay);// 设置布局文件

		super.onStart();// 实现基类中的方法
		Intent intentr = getIntent();

		txtMoney = (EditText) findViewById(R.id.txtMoney);// 获取金额文本框
		txtTime = (EditText) findViewById(R.id.txtTime);// 获取时间文本框
		txtAddress = (EditText) findViewById(R.id.txtAddress);// 获取地点文本框
		txtMark = (EditText) findViewById(R.id.txtMark);// 获取备注文本框
		spType = (Spinner) findViewById(R.id.spType);// 获取类别下拉列表
		btnSaveButton = (Button) findViewById(R.id.btnSave);// 获取保存按钮
		btnCancelButton = (Button) findViewById(R.id.btnCancel);// 获取取消按钮

		userid = intentr.getIntExtra("grk.id", 100000001);
		PtypeDAO ptypeDAO = new PtypeDAO(AddPay.this);
		List<String> spdatalist;
		spdatalist = ptypeDAO.getPtypeName(userid);
		spdata = spdatalist.toArray(new String[spdatalist.size()]);// 在tb_itype中按用户id读取
		adapter = new ArrayAdapter<String>(AddPay.this,
				android.R.layout.simple_spinner_item, spdata); // 动态生成收入类型列表
		spType.setAdapter(adapter);

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
		updateDisplay();// 显示当前系统时间
		userid = intentr.getIntExtra("grk.id", 100000001);
		PtypeDAO ptypeDAO = new PtypeDAO(AddPay.this);
		List<String> spdatalist;
		spdatalist = ptypeDAO.getPtypeName(userid);
		spdata = spdatalist.toArray(new String[spdatalist.size()]);// 在tb_itype中按用户id读取
		adapter = new ArrayAdapter<String>(AddPay.this,
				android.R.layout.simple_spinner_item, spdata); // 动态生成收入类型列表
		spType.setAdapter(adapter);

		txtTime.setOnClickListener(new OnClickListener() {// 为时间文本框设置单击监听事件
			@SuppressWarnings("deprecation")
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				showDialog(DATE_DIALOG_ID);// 显示日期选择对话框
			}
		});

		btnSaveButton.setOnClickListener(new OnClickListener() {// 为保存按钮设置监听事件
					@SuppressWarnings("deprecation")
					@SuppressLint("NewApi")
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						String strOutMoney = txtMoney.getText().toString();// 获取金额文本框的值
						if (!strOutMoney.isEmpty()) {// 判断金额不为空
							// 创建InaccountDAO对象
							PayDAO payDAO = new PayDAO(AddPay.this);
							// 创建Tb_inaccount对象
							Tb_pay tb_pay = new Tb_pay(userid, payDAO
									.getMaxNo(userid) + 1, Double
									.parseDouble(strOutMoney), setTimeFormat(),
									(spType.getSelectedItemPosition() + 1),
									txtAddress.getText().toString(), txtMark
											.getText().toString());
							payDAO.add(tb_pay);// 添加收入信息
							Toast.makeText(AddPay.this, "〖新增收入〗数据添加成功！  \n",
									Toast.LENGTH_SHORT).show();
							Intent intent = new Intent(AddPay.this, Pay.class);
							intent.putExtra("grk.id", userid);
							startActivity(intent);
						} else {
							Toast.makeText(AddPay.this, "请输入收入金额！",
									Toast.LENGTH_SHORT).show();
						}
					}
				});
		btnCancelButton.setOnClickListener(new OnClickListener() {// 为时间文本框设置单击监听事件
					@Override
					public void onClick(View arg0) {
						txtMoney.setText("");// 设置金额文本框为空
						txtMoney.setHint("0.00");// 为金额文本框设置提示
						txtTime.setText("");// 设置时间文本框为空
						txtAddress.setText("");// 设置地址文本框为空
						txtMark.setText("");// 设置备注文本框为空
						spType.setSelection(0);// 设置类别下拉列表默认选择第一项
						Intent intent = new Intent(AddPay.this, Pay.class);
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

		txtTime.setText(new StringBuilder().append(mYear).append("-")
				.append(mMonth + 1).append("-").append(mDay));

	}

	private String setTimeFormat() {
		String date = txtTime.getText().toString();

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
