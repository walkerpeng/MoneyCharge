package com.peng.cmoneycharge;

import peng.moneycharge.dao.NoteDAO;
import peng.moneycharge.model.ActivityManager;
import peng.moneycharge.model.Tb_note;

import com.cwp.cmoneycharge.R;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

@SuppressLint("NewApi")
public class AddNote extends Activity {
	EditText txtNote;// 创建EditText组件对象
	Button btnNoteSaveButton;// 创建Button组件对象
	Button btnNoteCancelButton;// 创建Button组件对象
	int userid;

	public AddNote() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.addnote);

		ActivityManager.getInstance().addActivity(this); // 管理Activity,退出按钮点击时调用
		txtNote = (EditText) findViewById(R.id.txtNote);// 获取便签文本框
		btnNoteSaveButton = (Button) findViewById(R.id.btnNoteSave);// 获取保存按钮
		btnNoteCancelButton = (Button) findViewById(R.id.btnNoteCancel);// 获取取消按钮

	}

	@Override
	protected void onStart() {
		super.onStart();
		Intent intentr = getIntent();
		userid = intentr.getIntExtra("grk.id", 100000001);// 默认用户
		btnNoteSaveButton.setOnClickListener(new OnClickListener() {// 为保存按钮设置监听事件
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						String strNote = txtNote.getText().toString();// 获取便签文本框的值
						if (!strNote.isEmpty()) {// 判断获取的值不为空
							NoteDAO noteDAO = new NoteDAO(AddNote.this);// 创建NoteDAP对象
							Tb_note tb_note = new Tb_note(userid, noteDAO
									.getMaxNo(userid) + 1, strNote);// 创建Tb_note对象
							noteDAO.add(tb_note);// 添加便签信息
							// 弹出信息提示
							Toast.makeText(AddNote.this, "〖新增便签〗数据添加成功！",
									Toast.LENGTH_SHORT).show();
							Intent intent = new Intent(AddNote.this, Note.class);
							intent.putExtra("grk.id", userid);
							startActivity(intent);
						} else {
							Toast.makeText(AddNote.this, "请输入便签！",
									Toast.LENGTH_SHORT).show();
						}
					}
				});

		btnNoteCancelButton.setOnClickListener(new OnClickListener() {// 为取消按钮设置监听事件
					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						txtNote.setText("");// 清空便签文本框
						Intent intent = new Intent(AddNote.this, Note.class);
						intent.putExtra("grk.id", userid);
						startActivity(intent);
					}
				});

	}

}
