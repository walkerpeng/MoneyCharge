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
public class ModifyNote extends Activity{
	EditText txtNote;// 创建EditText组件对象
	Button btnnoteManageDelete,btnnoteManageEdit;
	int userid,strno;
	public ModifyNote() {
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.modifynote);

		ActivityManager.getInstance().addActivity(this); //管理Activity,退出按钮点击时调用
		txtNote = (EditText) findViewById(R.id.txtNote);// 获取便签文本框
		btnnoteManageDelete = (Button) findViewById(R.id.btnnoteManageDelete);// 获取保存按钮
		btnnoteManageEdit = (Button) findViewById(R.id.btnnoteManageEdit);// 获取取消按钮
	
	}
	@Override
	protected void onStart(){
		super.onStart();
		Intent intentr=getIntent();
		userid=intentr.getIntExtra("grk.id", 100000001);//默认用户 
		strno=intentr.getIntExtra("strno",1);
		NoteDAO noteDAO=new NoteDAO(ModifyNote.this);
		Tb_note tb_note=noteDAO.find(userid, strno);
		txtNote.setText(tb_note.getNote().toString());
		btnnoteManageEdit.setOnClickListener(new OnClickListener() {// 为保存按钮设置监听事件
			@Override
				 public void onClick(View arg0) {
					// TODO Auto-generated method stub
					String strNote = txtNote.getText().toString();// 获取便签文本框的值
					if (!strNote.isEmpty()) {// 判断获取的值不为空
						NoteDAO noteDAO = new NoteDAO(ModifyNote.this);// 创建NoteDAP对象
						Tb_note tb_note = new Tb_note(userid,strno, strNote);// 创建Tb_note对象
						noteDAO.update(tb_note);// 添加便签信息
						// 弹出信息提示
						Toast.makeText(ModifyNote.this, "〖便签信息〗数据修改成功！",
								Toast.LENGTH_SHORT).show();
							Intent intent=new Intent(ModifyNote.this,Note.class);
							intent.putExtra("grk.id",userid);
							startActivity(intent);
					} else {
						Toast.makeText(ModifyNote.this, "请输入便签！",
								Toast.LENGTH_SHORT).show();
					}
				}
			});

			btnnoteManageDelete.setOnClickListener(new OnClickListener() {// 为取消按钮设置监听事件
						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							txtNote.setText("");// 清空便签文本框
							NoteDAO noteDAO=new NoteDAO(ModifyNote.this);
							noteDAO.detele(userid,strno);
							Intent intent=new Intent(ModifyNote.this,Note.class);
							intent.putExtra("grk.id",userid);
							startActivity(intent);
						}
					});
		
	}

}
