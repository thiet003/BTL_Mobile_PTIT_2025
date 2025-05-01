package com.exercise.app30day.features.setting.workout;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exercise.app30day.R;
import com.exercise.app30day.items.MusicItem;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

import java.util.List;

public class MusicBottomDialog extends BottomSheetDialogFragment {

    private RecyclerView recyclerView;
    private MusicAdapter musicAdapter;
    private int selectedMusicPosition;

    private List<MusicItem> musicItems;
    private MusicSelectionListener musicSelectionListener;

    public interface MusicSelectionListener {
        void onMusicSelected(MusicItem music);
    }

    public MusicBottomDialog(List<MusicItem> musicItems, int selectedMusicPosition) {
        this.musicItems = musicItems;
        this.selectedMusicPosition = selectedMusicPosition;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.bottom_dialog_music, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        FrameLayout parent = (FrameLayout) view.getParent();
        parent.setBackgroundResource(R.drawable.bg_primary_top_corner);

        recyclerView = view.findViewById(R.id.recyclerViewMusic);

        musicAdapter = new MusicAdapter();
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(musicAdapter);
        musicAdapter.setData(musicItems);
        musicAdapter.setSelectedPosition(selectedMusicPosition);
        musicAdapter.setOnItemClickListener((data, position) -> {
            if(musicSelectionListener != null){
                musicSelectionListener.onMusicSelected(data);
            }
            musicAdapter.setSelectedPosition(position);
            MusicBottomDialog.this.dismiss();
        });
    }

    public void setMusicSelectionListener(MusicSelectionListener listener) {
        this.musicSelectionListener = listener;
    }
}