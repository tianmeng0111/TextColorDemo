package com.tm.example.textcolordemo;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tm.example.textcolordemo.view.TextSwitchButton;

public class SwitchActivity extends AppCompatActivity implements View.OnClickListener {

    private ViewPager vp;
    private TextSwitchButton textSwitchBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_switch);
        initView();
    }

    private void initView() {
        vp = (ViewPager) findViewById(R.id.vp);
        vp.setAdapter(new MyAdapter());
        vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position == 0) {
                    textSwitchBtn.setChangeCheckedState(TextSwitchButton.CheckedState.Left);
                } else {
                    textSwitchBtn.setChangeCheckedState(TextSwitchButton.CheckedState.Right);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        textSwitchBtn = (TextSwitchButton) findViewById(R.id.textSwitchBtn);
        textSwitchBtn.setCheckedStateChangeListener(new TextSwitchButton.CheckedStateChangeListener() {
            @Override
            public void stateChangeListener(TextSwitchButton.CheckedState checkedState) {
                if (checkedState == TextSwitchButton.CheckedState.Left)
                    vp.setCurrentItem(0);
                else
                    vp.setCurrentItem(1);
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.textSwitchBtn:

                break;
        }
    }

    private static class MyAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            TextView tv = new TextView(container.getContext());
            tv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewPager.LayoutParams.MATCH_PARENT));
            tv.setGravity(Gravity.CENTER);
//            tv.setBackgroundColor(Color.parseColor("#" + Integer.toHexString(-16777216)));
            tv.setText("第" + position + "页");
            tv.setTextSize(40);
            container.addView(tv);
            return tv;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView(container.getChildAt(position));
//            super.destroyItem(container, position, object);
        }
    }
}
