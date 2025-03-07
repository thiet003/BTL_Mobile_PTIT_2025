package com.exercise.app30day.base;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import java.lang.reflect.ParameterizedType;
import java.util.Objects;

public class ViewModelHelper {
    @SuppressWarnings("unchecked")
    public static <VM extends ViewModel> VM createViewModel(ViewModelStoreOwner owner, Class<?> aClass) {
        Class<VM> viewModelClass = (Class<VM>) ((ParameterizedType) Objects.requireNonNull(aClass.getGenericSuperclass())).getActualTypeArguments()[1];
        return new ViewModelProvider(owner).get(viewModelClass);
    }
}
