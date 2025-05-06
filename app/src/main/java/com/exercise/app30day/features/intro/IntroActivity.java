package com.exercise.app30day.features.intro;

import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import com.exercise.app30day.R;
import com.exercise.app30day.base.BaseActivity;
import com.exercise.app30day.base.NoneViewModel;
import com.exercise.app30day.databinding.ActivityIntroBinding;
import com.exercise.app30day.features.main.MainActivity;
import com.exercise.app30day.utils.HawkKeys;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

public class IntroActivity extends BaseActivity<ActivityIntroBinding, NoneViewModel> implements View.OnClickListener {

    IntroSlideAdapter introSlideAdapter;

    @Override
    protected void initView() {
        setupIntroSlides();
        setupIndicators();
        setCurrentIndicator(0);
    }

    @Override
    protected void initListener() {
        binding.viewPagerIntro.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentIndicator(position);
                if (position == introSlideAdapter.getItemCount() - 1) {
                    binding.buttonNext.setText(R.string.get_started);
                } else {
                    binding.buttonNext.setText(R.string.next);
                }
            }
        });

        binding.buttonNext.setOnClickListener(this);
        binding.textSkip.setOnClickListener(this);
    }

    private void setupIntroSlides() {
        List<IntroSlide> introSlides = new ArrayList<>();

        introSlides.add(new IntroSlide(R.drawable.img_intro_1,
                getString(R.string.intro_title_1),
                getString(R.string.intro_description_1)));

        introSlides.add(new IntroSlide(R.drawable.img_intro_2,
                getString(R.string.intro_title_2),
                getString(R.string.intro_description_2)));

        introSlides.add(new IntroSlide(R.drawable.img_intro_3,
                getString(R.string.intro_title_3),
                getString(R.string.intro_description_3)));

        introSlideAdapter = new IntroSlideAdapter(this, introSlides);
        binding.viewPagerIntro.setAdapter(introSlideAdapter);
    }

    private void setupIndicators() {
        ImageView[] indicators = new ImageView[introSlideAdapter.getItemCount()];
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT
        );
        layoutParams.setMargins(8, 0, 8, 0);

        for (int i = 0; i < indicators.length; i++) {
            indicators[i] = new ImageView(getApplicationContext());
            indicators[i].setImageDrawable(ContextCompat.getDrawable(
                    getApplicationContext(),
                    R.drawable.indicator_inactive
            ));
            indicators[i].setLayoutParams(layoutParams);
            binding.layoutIndicators.addView(indicators[i]);
        }
    }

    @Override
    public void onClick(View v) {
        if(v == binding.buttonNext){
            if (binding.viewPagerIntro.getCurrentItem() < introSlideAdapter.getItemCount() - 1) {
                binding.viewPagerIntro.setCurrentItem(binding.viewPagerIntro.getCurrentItem() + 1);
            } else {
                Hawk.put(HawkKeys.INTRO_SHOWN_KEY, true);
                startMainActivity();
            }
        }else if(v == binding.textSkip){
            Hawk.put(HawkKeys.INTRO_SHOWN_KEY, true);
            startMainActivity();
        }
    }

    private void setCurrentIndicator(int position) {
        int childCount = binding.layoutIndicators.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) binding.layoutIndicators.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        getApplicationContext(),
                        R.drawable.indicator_active
                ));
            } else {
                imageView.setImageDrawable(ContextCompat.getDrawable(
                        getApplicationContext(),
                        R.drawable.indicator_inactive
                ));
            }
        }
    }

    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        finish();
    }
}