package com.example.viewpagerdemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends Activity {
	private TextView tvStart;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		tvStart = (TextView) findViewById(R.id.tv_start);
		initData();
		tvStart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent it = new Intent(MainActivity.this, MyOldtestAct.class);
				startActivity(it);
			}
		});
	}

	private void initData() {
		// 1��ֱ��new һ���߳��࣬�������ʵ��Runnable�ӿڵĶ���new Runnable�����൱�ڷ�����
		new Thread(new Runnable() {
			@Override
			public void run() {
				// д���߳��еĲ���
				 
			}
		}).start();

		// 2��ͨ��ʵ��Runnable�ӿ�
		Thread t = new Thread(new myRunnable());
		t.start();

		// 3��ͨ���̳��߳���ʵ��
		new myThread().start();

	}

	// Thread��һ���࣬����̳�
	public class myThread extends Thread {
		@Override
		public void run() {
			super.run();
			// д���߳��еĲ���
		}
	}

	// ʵ��Runnable�ǽӿ�
	public class myRunnable implements Runnable {
		@Override
		public void run() {
			// д���߳��еĲ���

		}

	}

}
