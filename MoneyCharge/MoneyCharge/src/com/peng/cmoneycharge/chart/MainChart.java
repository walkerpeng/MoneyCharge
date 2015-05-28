package com.peng.cmoneycharge.chart;

import java.util.ArrayList;
import java.util.List;

import com.cwp.cmoneycharge.R;
import com.peng.cmoneycharge.chart.HomeArc;
import com.peng.cmoneycharge.chart.HomeColumnar;
import com.peng.cmoneycharge.chart.HomeDiagram;
import com.peng.cmoneycharge.chart.Score;

import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.app.Activity;

public class MainChart extends Activity{
	LinearLayout arc;
    RelativeLayout pillars,linear;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_chart);
		//圆弧计分
		arc = (LinearLayout) findViewById(R.id.arc);
		arc.addView(new HomeArc(this, 90)); 
		
		//柱状图，范围10-100
		List<Score> list = new ArrayList<Score>();
		for (int i = 0; i < 28; i++) {
			Score s = new Score();
			s.date = "2015-05-" + i;
			s.score = getRandom(10,100);
			list.add(s);
		}
		pillars= (RelativeLayout) findViewById(R.id.pillars);
		pillars.addView(new HomeColumnar(this,list));
		
		//线性图，范围10-100
		List<Integer> lists = new ArrayList<Integer>();//线性图  范围10-100
		for (int i = 0; i < 48; i++) {
			if (i < 8 || i == 28 || i == 12 || i == 18 || i == 20 || i == 30
					|| i == 34) {
				lists.add(0);
			} else {
				lists.add(getRandom(0, 500));
			}
		}
		linear= (RelativeLayout) findViewById(R.id.linear);
		linear.addView(new HomeDiagram(this,lists));
	}
	
	public int getRandom(int min,int max){
		return (int) Math.round(Math.random()*(max-min)+min);
	}
}
