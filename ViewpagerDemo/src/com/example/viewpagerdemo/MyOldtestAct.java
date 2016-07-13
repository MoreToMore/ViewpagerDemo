package com.example.viewpagerdemo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;

import com.example.adapter.VPAdapter;
import com.example.db.DBManager;
import com.example.model.Questions;
import com.example.view.MyViewPager;

public class MyOldtestAct extends Activity implements OnClickListener {
	private ImageView btnBack;
	private MyViewPager viewPager;
	// �������view
	private List<View> viewItems = new ArrayList<View>();
	// adapter
	private VPAdapter viewpagerAdpter;
	// ��������ļ���
	List<Questions> questionsList;
	private List<String> questionList;
	private LinearLayout mViewpagef;
	private LinearLayout vpLinparentnow; // ��ǰ��viewpagerǶ��������linerlayout
	private LinearLayout vpLinparentnext; // ��һ����viewpagerǶ��������linerlayout
	private LinearLayout vpLinparenttop; // ��һ����viewpagerǶ��������linerlayout

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.act_oldtest);
		setView();
		initData();
	}

	protected void setView() {
		mViewpagef = (LinearLayout) findViewById(R.id.vpfather);
		btnBack = (ImageView) findViewById(R.id.btn_back);
		viewPager = (MyViewPager) findViewById(R.id.vote_submit_viewpager);
		btnBack.setOnClickListener(this);
		for (int i = 0; i < 10; i++) {
			viewItems.add(getLayoutInflater().inflate(R.layout.act_oldtesting,
					null));
		}
		// ��һ�ν����ʱ��ִ�и÷���������͸����
		vpLinparentnext = (LinearLayout) viewItems.get(1).findViewById(
				R.id.vp_parent);
		vpLinparentnext.setAlpha(0.5f);// ��ʼ����ʱ��Ĭ�ϰѵڶ�ҳ͸��������Ϊ0.5
	}

	protected void initData() {
		// ��ȡ���ݿ�
		SQLiteDatabase db = DBManager.getdb(getApplication());
		questionsList = new ArrayList<Questions>();
		questionList = new ArrayList<String>();
		// �����ݿ�����ݲ�ѯ����
		Cursor cursor = db.query("test_bank", null, null, null, null, null,
				null);
		while (cursor.moveToNext()) {
			String questionId = cursor.getString(0);
			String test_cotent = cursor.getString(1);
			Questions Q = new Questions(questionId, test_cotent);
			questionsList.add(Q);
		}
		// ��1-5����һ���������sqlite����5�����
		Random rand = new Random();
		int randNum = rand.nextInt(5) + 1;
		Log.w("randNum", String.valueOf(randNum));
		// ���ݲ������������ȡ��Ŀ
		switch (randNum) {
		case 1:
			for (int i = 1; i <= 10; i++) {
				questionList.add(i + "��"
						+ questionsList.get(i - 1).getTest_cotent());
			}
			break;
		case 2:
			for (int i = 1; i <= 10; i++) {
				questionList.add(i + "��"
						+ questionsList.get(i + 9).getTest_cotent());
			}
			break;
		case 3:
			for (int i = 1; i <= 10; i++) {
				questionList.add(i + "��"
						+ questionsList.get(i + 19).getTest_cotent());
			}
			break;
		case 4:
			for (int i = 1; i <= 10; i++) {
				questionList.add(i + "��"
						+ questionsList.get(i + 29).getTest_cotent());
			}
			break;
		case 5:
			for (int i = 1; i <= 10; i++) {
				questionList.add(i + "��"
						+ questionsList.get(i + 39).getTest_cotent());
			}
			break;
		}
		viewpagerAdpter = new VPAdapter(this, viewItems, questionList);
		viewPager.setOffscreenPageLimit(3);// viewPager���û����ҳ����
		viewPager.setPageMargin(-20);// ����2��ͼ֮ǰ�ļ�࣬-20���ǻ��б߾��Ч��
		MyOnPageChangeListener myOnPageChangeListener = new MyOnPageChangeListener();
		viewPager.setOnPageChangeListener(myOnPageChangeListener);
		viewPager.setAdapter(viewpagerAdpter);
	}

	public class MyOnPageChangeListener implements OnPageChangeListener {
		@Override
		public void onPageSelected(int position) {
			vpLinparentnow = (LinearLayout) viewItems.get(position)
					.findViewById(R.id.vp_parent);
			vpLinparentnow.setAlpha(1f); // ���õ�ǰ��alphaΪ1
			if (position == 0) {
				vpLinparentnext = (LinearLayout) viewItems.get(position + 1)
						.findViewById(R.id.vp_parent);
				vpLinparentnext.setAlpha(0.5f);
			} else if (position == viewItems.size() - 1) {
				vpLinparenttop = (LinearLayout) viewItems.get(position - 1)
						.findViewById(R.id.vp_parent);
				vpLinparenttop.setAlpha(0.5f);
			} else {
				vpLinparentnext = (LinearLayout) viewItems.get(position + 1)
						.findViewById(R.id.vp_parent);
				vpLinparenttop = (LinearLayout) viewItems.get(position - 1)
						.findViewById(R.id.vp_parent);
				vpLinparentnext.setAlpha(0.5f);
				vpLinparenttop.setAlpha(0.5f);
			}
			// �жϵ�ǰ��ѡ���Ƿ���ѡ�е�
			RadioButton chkNothave = (RadioButton) viewItems.get(position)
					.findViewById(R.id.chk_nothave);
			RadioButton chkSometimes = (RadioButton) viewItems.get(position)
					.findViewById(R.id.chk_sometimes);
			RadioButton chkUsual = (RadioButton) viewItems.get(position)
					.findViewById(R.id.chk_usual);
			// �����ǰ��ѡ�е������Ի���
			if (chkNothave.isChecked() || chkSometimes.isChecked()
					|| chkUsual.isChecked()) {
				viewPager.isCompleteable(true);
			} else {
				viewPager.isCompleteable(false);
			}
		}

		// ��ˢ�²�������ȻView���ص�����Ӱ
		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			// to refresh frameLayout
			if (mViewpagef != null) {
				mViewpagef.invalidate();
			}
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}
	}

	/**
	 * @param index
	 *  ��������ֵ�л�ҳ��
	 */
	public void setCurrentView(int index) {
		viewPager.setCurrentItem(index);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_back:
			finish();
			break;
		}
	}

}
