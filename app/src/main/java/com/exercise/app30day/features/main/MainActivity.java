package com.exercise.app30day.features.main;

import android.content.Intent;
import android.view.View;

import androidx.viewpager2.widget.ViewPager2;

import com.exercise.app30day.R;
import com.exercise.app30day.base.BaseActivity;
import com.exercise.app30day.base.NoneViewModel;
import com.exercise.app30day.databinding.ActivityMainBinding;
import com.exercise.app30day.features.chatbot.ChatbotActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MainActivity extends BaseActivity<ActivityMainBinding, NoneViewModel>
    implements View.OnClickListener
{
    private FloatingActionButton chatbotButton;
    @Override
    protected void initView() {
        chatbotButton = binding.fabChatbot;
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
    }

    @Override
    protected void initListener() {
        chatbotButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v==binding.fabChatbot){
            startChatbotActivity();
        }
    }
    private void startChatbotActivity() {
        // Start the ChatbotActivity
        Intent intent = new Intent(this, ChatbotActivity.class);
        startActivity(intent);
    }
}