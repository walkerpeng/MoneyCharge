package com.peng.cmoneycharge;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import peng.moneycharge.dao.IncomeDAO;
import peng.moneycharge.dao.PayDAO;
import peng.moneycharge.model.ActivityManager;
import peng.moneycharge.model.Datapicker;

import com.cwp.cmoneycharge.R;


import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.text.format.Time;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.Toast;

public class PIData  extends Activity{
	int userid;
	Intent intentr;
	PayDAO payDAO;
	IncomeDAO incomeDAO;
	Time time ;    
	int defaultMonth;
	int defaultYear; 
	LinearLayout chart;
	Button beforet,aftert,anytime;
	Spinner year,month,day,yeare,monthe,daye;//界面上的任意时间 
    List<String> yearlist;
	private GraphicalView lChart;
	Adapter adapter;
	XYSeriesRenderer xyRenderer;
	XYSeries seriesp,seriesi;
	List<Datapicker> datapickerp, datapickeri;//收入，支出数据集
	String date1,date2;//获取用户选择的任意两个时间
    //1 构造显示渲染图
    XYMultipleSeriesRenderer renderer = new XYMultipleSeriesRenderer();
    //2,进行显示
    XYMultipleSeriesDataset dataset = new XYMultipleSeriesDataset();
    //2.1构建数据
    Random r = new Random();

	public PIData() {
		// TODO Auto-generated constructor stub
	}
	 @Override
	    public void onCreate(Bundle savedInstanceState) {
	        super.onCreate(savedInstanceState);
	        setContentView(R.layout.pidata);
			ActivityManager.getInstance().addActivity(this); //管理Activity,退出按钮点击时调用
	        time = new Time("GMT+8");    
	        time.setToNow();   
	        defaultMonth=time.month+1;//设置默认月份
	        defaultYear=time.year;
	        beforet=(Button)findViewById(R.id.before);
	        aftert=(Button)findViewById(R.id.after);
	        anytime=(Button)findViewById(R.id.anytime);
	        year=(Spinner)findViewById(R.id.year);
	        month=(Spinner)findViewById(R.id.month);
	        day=(Spinner)findViewById(R.id.day);
	        yeare=(Spinner)findViewById(R.id.yeare);
	        monthe=(Spinner)findViewById(R.id.monthe);
	        daye=(Spinner)findViewById(R.id.daye);
	        chart=(LinearLayout)findViewById(R.id.chart);
	        
	        yearlist=new ArrayList<String>(); //生成年份列表 spinner
	        
	        //设置年
	        for(int i=0;i<=10;i++){
	        	yearlist.add(String.valueOf(defaultYear-i));
	        } 
	        adapter=new ArrayAdapter<String>(PIData.this,android.R.layout.simple_spinner_item,yearlist);
	        year.setAdapter((SpinnerAdapter) adapter);
	        yeare.setAdapter((SpinnerAdapter) adapter);
	
	 }
	 @Override
	 protected void onStart(){
		 	super.onStart();
		 	intentr=getIntent();
		 	userid=intentr.getIntExtra("grk.id",100000001); 
		 	defaultMonth=intentr.getIntExtra("default", defaultMonth);  
		 	defaultYear=intentr.getIntExtra("defaulty", defaultYear);  
		 	int type=intentr.getIntExtra("type",0);//为0，选择上下月，为1，选择任意时间
	        payDAO=new PayDAO(PIData.this);
	        incomeDAO=new IncomeDAO(PIData.this);
	        
	        //3. 对点的绘制进行设置
	        xyRenderer = new XYSeriesRenderer();
	        //3.1设置颜色
	        xyRenderer.setColor(Color.BLUE);
	        //3.2设置点的样式
	        xyRenderer.setPointStyle(PointStyle.SQUARE);
	        xyRenderer.setLineWidth(10);
	         xyRenderer.setPointStyle(PointStyle.CIRCLE);
	         xyRenderer.setFillPoints(true);
	        renderer.addSeriesRenderer(xyRenderer); 
	         xyRenderer = new XYSeriesRenderer();
	         xyRenderer.setColor(Color.RED);
	         xyRenderer.setPointStyle(PointStyle.SQUARE);
	         xyRenderer.setLineWidth(10);
	         xyRenderer.setPointStyle(PointStyle.CIRCLE);
	         xyRenderer.setFillPoints(true);
	         renderer.addSeriesRenderer(xyRenderer);
	         
	         renderer.setShowGrid(true);
	         renderer.setGridColor(Color.GRAY);
	         renderer.setAxisTitleTextSize(44);// 设置坐标轴标题文本大小
	         renderer.setChartTitleTextSize(54); // 设置图表标题文本大小
	         renderer.setLabelsTextSize(34); // 设置轴标签文本大小
	         renderer.setLegendTextSize(54);
	         renderer.setPointSize(10); 
		     renderer.setXTitle( defaultYear+"年  "+defaultMonth+"月 "+"日期" );//设置X轴名称
		     renderer.setYTitle( "金额" );
		     renderer.setMargins(new int[] { 50, 120, 100,70 }); 
		     renderer.setDisplayChartValues(true); 
		     renderer.setXLabels(15);
		     renderer.setYLabels(10);
		     renderer.setXAxisMin(1);
		     renderer.setYAxisMin(10);
		     renderer.setChartValuesTextSize(34);
		     renderer.setXLabelsAlign(Align.RIGHT);
		     renderer.setYLabelsAlign(Align.RIGHT);  
		     renderer.setRange(new double[]{0d, 30d, 0d, 300d}); //设置chart的视图范围
	        
		     if(type==0){
	        
		        datapickerp=payDAO.getDataMonth(userid, defaultYear, defaultMonth) ;
			 	datapickeri=incomeDAO.getDataMonth(userid, defaultYear,defaultMonth);  
		 	
			 	
		     }else{
		    	 Log.i("========",date1+"  222  "+date2);
				 date1=intentr.getStringExtra("date1");
			     date2=intentr.getStringExtra("date2");
		    	 datapickerp=payDAO.getDataAnytime(userid, date1, date2) ;
			 	 datapickeri=incomeDAO.getDataAnytime(userid, date1, date2);
			     renderer.setXTitle( date1+" 至  " +date2 );//设置X轴名称
		    	 
		     }
		     seriesp = new XYSeries("支出");
	            //填充数据
	            int k=1; 
	            
	            for(Datapicker piker:datapickerp){
	                //填写x,y的值
	                seriesp.add(k,piker.getMoney().doubleValue());
	                k++;
	            }  
	            seriesi = new XYSeries("收入");
	            //填充数据
	            k=1; 
	            for(Datapicker piker:datapickeri){
	                //填写x,y的值
	                seriesi.add(k,piker.getMoney().doubleValue() );
	                k++;
	            }
	            //需要绘制的点放进dataset中
	            dataset.addSeries(seriesi);
	            dataset.addSeries(seriesp);
			     lChart =(GraphicalView) ChartFactory.getLineChartView(PIData.this, dataset, renderer);
		         chart.addView(lChart);
	         
	         aftert.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					clear();
					if(defaultMonth!=12)
					defaultMonth=defaultMonth+1;
					else{
						defaultMonth=1;
						defaultYear=defaultYear+1;
					}
				 	Intent intenti=new Intent(PIData.this,PIData.class);
				    intenti.putExtra("default",defaultMonth);
					intenti.putExtra("defaulty", defaultYear);
				    intenti.putExtra("grk.id", userid);
				    startActivity(intenti);
				}
			});
	         beforet.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						clear();
						if(defaultMonth!=1)
						defaultMonth=defaultMonth-1;
						else{
							defaultMonth=12;
							defaultYear=defaultYear-1;
						}
					 	Intent intenti=new Intent(PIData.this,PIData.class);
					    intenti.putExtra("default",defaultMonth);
						intenti.putExtra("defaulty", defaultYear);
					    intenti.putExtra("grk.id", userid);
					    startActivity(intenti);
					 	
					}
				});
	         
	         anytime.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					getAnyDate();
					clear();

			    	 Log.i("====2====",date1+"  222  "+date2);
				 	Intent intenti=new Intent(PIData.this,PIData.class);
				    intenti.putExtra("type",1);
				    intenti.putExtra("date1", date1);
				    intenti.putExtra("date2", date2);
				 	intenti.putExtra("grk.id", userid);
				    startActivity(intenti);
				}
			});
	         
	         
	    } 
	 	public void clear(){
	 		seriesi.clear();
	 		seriesp.clear();
	 		dataset.removeSeries(seriesi);
	 		dataset.removeSeries(seriesp); 
	 	}
	 	
	 	 
	 	
	 	public void getAnyDate(){
	 		date1=year.getSelectedItem().toString()+"-"+month.getSelectedItem().toString()+"-"+day.getSelectedItem().toString();
	 		date2=yeare.getSelectedItem().toString()+"-"+monthe.getSelectedItem().toString()+"-"+daye.getSelectedItem().toString();

	    	 Log.i("====1====",date1+"  222  "+date2);
	 	}
	 	
		public boolean onKeyDown(int keyCode, KeyEvent event) {
		    if(keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
		    	Intent intent=new Intent(PIData.this,Data.class);
			intent.putExtra("grk.id",userid);
				startActivity(intent);
		        return true;
		    }
		    return super.onKeyDown(keyCode, event);
		}
		public int getday(int month){
			switch(month){
			case 1:return 31; 
			case 2: return 28;					 
			case 3:return 31 ;
			case 4:return 30; 
			case 5:return 31; 
			case 6:return 30; 
			case 7:return 31; 
			case 8:return 31;  
			case 10:return 31; 
			case 11:return 30; 
			case 12:return 31; 
			
		}
			return 31;
}
		
}		

// 3.3, 将要绘制的点添加到坐标绘制中
//重复1-3的步骤绘制第二个系列点

/*  setAxisTitleTextSize(16);// 设置坐标轴标题文本大小
3.    setChartTitleTextSize(20); // 设置图表标题文本大小
4.    setLabelsTextSize(15); // 设置轴标签文本大小
5.    setLegendTextSize(15); // 设置图例文本大小
6.    renderer.setChartTitle( "个人收支表");//设置柱图名称
7.    renderer.setXTitle( "名单" );//设置X轴名称
8.    renderer.setYTitle( "金额" );//设置Y轴名称
9.    renderer.setXAxisMin(0.5);//设置X轴的最小值为0.5
10.  renderer.setXAxisMax(5.5);//设置X轴的最大值为5
11.  renderer.setYAxisMin(0);//设置Y轴的最小值为0
12.  renderer.setYAxisMax(500);//设置Y轴最大值为500
13.  renderer.setDisplayChartValues(true);//设置是否在柱体上方显示值
14.  renderer.setShowGrid(true);//设置是否在图表中显示网格
15.  renderer.setXLabels(0);//设置X轴显示的刻度标签的个数
16.  如果想要在X轴显示自定义的标签，那么首先要设置renderer.setXLabels(0);其次我们要renderer.addTextLabel()循环添加
17.  renderer.setXLabelsAlign(Align.RIGHT);//设置刻度线与X轴之间的相对位置关系
18.  renderer.setYLabelsAlign(Align.RIGHT);//设置刻度线与Y轴之间的相对位置关系
19.  renderer.setZoomButtonsVisible(true);//设置可以缩放
20.  renderer.setPanLimits(newdouble[] { 0, 20, 0, 140 });//设置拉动的范围
21.  renderer.setZoomLimits(newdouble[] { 0.5, 20, 1, 150 });//设置缩放的范围
22.  renderer.setRange(newdouble[]{0d, 5d, 0d, 100d}); //设置chart的视图范围
23.  renderer.setFitLegend(true);// 调整合适的位置
24.  renderer.setClickEnabled(true)//设置是否可以滑动及放大缩小;
25.  Dataset和Render参数介绍：参考手册
26.ChartView.repaint()；是重新绘图的命令
27.关于AChartEngine的点击事件，双击事件，滑动事件均可以用自定义事件解决，但是需要注意的是先设置renderer.setClickEnabled(false);
28.若是添加图形后变形，可以设置 renderer.setInScroll(true);解决，来自于@gupengno1
29.renderer.setGridColor();//设置网格颜色
30.renderer.setAxesColor();//设置坐标轴颜色

*/
