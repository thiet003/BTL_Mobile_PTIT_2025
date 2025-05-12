package com.exercise.app30day.features.main;

import androidx.viewpager2.widget.ViewPager2;

import com.exercise.app30day.R;
import com.exercise.app30day.base.BaseActivity;
import com.exercise.app30day.base.NoneViewModel;
import com.exercise.app30day.databinding.ActivityMainBinding;
import com.exercise.app30day.utils.SpeechUtils;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends BaseActivity<ActivityMainBinding, NoneViewModel>  {
    @Override
    protected void initView() {
        MainViewPagerAdapter mainViewPagerAdapter = new MainViewPagerAdapter(this);
        binding.mainViewPager.setAdapter(mainViewPagerAdapter);
        binding.mainViewPager.setOffscreenPageLimit(ViewPager2.OFFSCREEN_PAGE_LIMIT_DEFAULT);
        binding.mainViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                binding.bottomNavigationView.getMenu().getItem(position).setChecked(true);
            }
        });
        binding.bottomNavigationView.setOnItemSelectedListener(item -> {
            if(item.getItemId() == R.id.homeFragment) {
                binding.mainViewPager.setCurrentItem(0);
            }else if (item.getItemId() == R.id.reportFragment) {
                binding.mainViewPager.setCurrentItem(1);
            }
            else if (item.getItemId() == R.id.settingFragment) {
                binding.mainViewPager.setCurrentItem(2);
            }
            return true;
        });
        SpeechUtils.init();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SpeechUtils.destroy();
    }
}