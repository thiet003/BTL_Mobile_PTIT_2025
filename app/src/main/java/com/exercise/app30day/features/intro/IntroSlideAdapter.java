package com.exercise.app30day.features.intro;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exercise.app30day.R;

import java.util.List;

public class IntroSlideAdapter extends RecyclerView.Adapter<IntroSlideAdapter.IntroSlideViewHolder> {

    private Context context;
    private List<IntroSlide> introSlides;

    public IntroSlideAdapter(Context context, List<IntroSlide> introSlides) {
        this.context = context;
        this.introSlides = introSlides;
    }

    @NonNull
    @Override
    public IntroSlideViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new IntroSlideViewHolder(
                LayoutInflater.from(context).inflate(R.layout.layout_intro_slide, parent, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull IntroSlideViewHolder holder, int position) {
        holder.bind(introSlides.get(position));
    }

    @Override
    public int getItemCount() {
        return introSlides.size();
    }

    public class IntroSlideViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageIntro;
        private TextView textTitle;
        private TextView textDescription;

        public IntroSlideViewHolder(@NonNull View itemView) {
            super(itemView);
            imageIntro = itemView.findViewById(R.id.imageIntro);
            textTitle = itemView.findViewById(R.id.textTitle);
            textDescription = itemView.findViewById(R.id.textDescription);
        }

        void bind(IntroSlide introSlide) {
            imageIntro.setImageResource(introSlide.getImage());
            textTitle.setText(introSlide.getTitle());
            textDescription.setText(introSlide.getDescription());
        }
    }
}