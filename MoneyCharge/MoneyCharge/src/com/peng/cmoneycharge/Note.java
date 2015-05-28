package com.peng.cmoneycharge;

import java.util.List;

import peng.moneycharge.dao.NoteDAO;
import peng.moneycharge.model.ActivityManager;
import peng.moneycharge.model.Tb_note;

import com.cwp.cmoneycharge.R;

 
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button; 
import android.widget.ListView;
import android.widget.TextView;
import android.widget.AdapterView.OnItemClickListener;

public class Note extends Activity {

	int userid;
	Button baddnote; 
	NoteDAO noteDAO;
	ListView lvinfo;// 创建ListView对象
	
	public Note() {
		// TODO Auto-generated constructor stub
	}
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.note);// 设置布局文件  
		ActivityManager.getInstance().addActivity(this); //管理Activity,退出按钮点击时调用
		lvinfo = (ListView) findViewById(R.id.lvinnoteinfo);// 获取布局文件中的ListView组件
		baddnote=(Button)findViewById(R.id.baddnote);//添加按钮
		noteDAO=new NoteDAO(Note.this);//  调用自定义方法显示收入信息
	}
	@Override
	protected void onStart(){
		super.onStart();
		
		Intent intentr=getIntent();
		userid=intentr.getIntExtra("grk.id",100000001);
		ShowInfo();
		lvinfo.setOnItemClickListener(new OnItemClickListener()// 为ListView添加项单击事件
		{
			// 覆写onItemClick方法
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String strInfo = String.valueOf(((TextView) view).getText());// 记录收入信息
				String strno = strInfo.substring(0, strInfo.indexOf('|')).trim();// 从收入信息中截取收入编号
				Intent intent = new Intent(Note.this, ModifyNote.class);// 创建Intent对象
				intent.putExtra("grk.id", userid);
				intent.putExtra("strno", Integer.parseInt(strno));
				startActivity(intent);// 执行Intent操作
			}
		});
		baddnote.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0){
				Intent intent = new Intent(Note.this, AddNote.class);// 创建Intent对象
				intent.putExtra("grk.id", userid);
				startActivity(intent);
			}
		});
		
	}
	private void ShowInfo( ) {// 用来根据传入的管理类型，显示相应的信息
		String[] strInfos = null;// 定义字符串数组，用来存储收入信息
		ArrayAdapter<String> arrayAdapter = null;// 创建ArrayAdapter对象 
		NoteDAO Notedao = new NoteDAO(Note.this);// 创建NoteDAO对象
		// 获取所有收入信息，并存储到List泛型集合中
		List<Tb_note> listinfos = Notedao.getScrollData(userid,0,
				(int) Notedao.getCount(userid));
		strInfos = new String[listinfos.size()];// 设置字符串数组的长度
		int m = 0;// 定义一个开始标识
		for (Tb_note tb_note : listinfos) {// 遍历List泛型集合
			// 将收入相关信息组合成一个字符串，存储到字符串数组的相应位置 
			strInfos[m] =tb_note.getNo()+ " |  "   +tb_note.getNote();
			m++;// 标识加1
		}
		// 使用字符串数组初始化ArrayAdapter对象
		arrayAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, strInfos);
		lvinfo.setAdapter(arrayAdapter);// 为ListView列表设置数据源
	}
	public boolean onKeyDown(int keyCode, KeyEvent event) {
	    if(keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
	    	Intent intent=new Intent(Note.this,Index.class);
			intent.putExtra("grk.id",userid);
			startActivity(intent);
	        return true;
	    }
	    return super.onKeyDown(keyCode, event);
	}
	
}
