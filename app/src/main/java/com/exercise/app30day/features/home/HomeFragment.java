package com.exercise.app30day.features.home;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.exercise.app30day.base.BaseFragment;
import com.exercise.app30day.base.NoneViewModel;
import com.exercise.app30day.databinding.FragmentHomeBinding;
import com.exercise.app30day.models.User;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel> implements View.OnClickListener {

    UserAdapter userAdapter = new UserAdapter();
    @Override
    protected void initView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.userRcv.setLayoutManager(linearLayoutManager);
        binding.userRcv.setAdapter(userAdapter);
        viewModel.getAllUsers().observe(getViewLifecycleOwner(), userAdapter::setData);
    }

    @Override
    protected void initListener() {
        binding.btnAddUser.setOnClickListener(this);
        binding.btnUpdateUser.setOnClickListener(this);
        userAdapter.setOnUserClickDeleteListener((user, position) -> viewModel.deleteUser(user));
    }

    @Override
    public void onClick(View v) {
        if(v == binding.btnAddUser){
            String txtHeight = binding.edtHeight.getText().toString();
            String txtWeight = binding.edtWeight.getText().toString();
            double height = Double.parseDouble(txtHeight.isEmpty() ? "0" : txtHeight);
            double weight = Double.parseDouble(txtWeight.isEmpty() ? "0" : txtWeight);
            viewModel.insertUser(height, weight);
        }else if(v == binding.btnUpdateUser){
            int id = Integer.parseInt(binding.edtId.getText().toString());
            LiveData<User> userLiveData = viewModel.getUserById(id);
            userLiveData.observe(getViewLifecycleOwner(), new Observer<>() {
                @Override
                public void onChanged(User user) {
                    if(user == null) return;
                    String txtHeight = binding.edtHeight.getText().toString();
                    String txtWeight = binding.edtWeight.getText().toString();
                    user.setHeight(Double.parseDouble(txtHeight.isEmpty() ? "0" : txtHeight));
                    user.setWeight(Double.parseDouble(txtWeight.isEmpty() ? "0" : txtWeight));
                    viewModel.updateUser(user);
                    userLiveData.removeObserver(this);
                }
            });
        }
    }
}