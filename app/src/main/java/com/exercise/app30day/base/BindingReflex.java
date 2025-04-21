package com.exercise.app30day.base;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.viewbinding.ViewBinding;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Objects;

public class BindingReflex {
    public static <V extends ViewBinding> V reflexViewBinding(Class<?> aClass, LayoutInflater from){
        try{
            Type[] actualTypeArguments = ((ParameterizedType) Objects.requireNonNull(aClass.getGenericSuperclass())).getActualTypeArguments();
            for(Type actualTypeArgument : actualTypeArguments){
                Class<?> tClass = null;
                if(actualTypeArgument instanceof Class<?>){
                    tClass = (Class<?>) actualTypeArgument;
                }
                if(tClass != null && ViewBinding.class.isAssignableFrom(tClass)){
                    Method inflate = tClass.getMethod("inflate", LayoutInflater.class);
                    return (V) inflate.invoke(null, from);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RuntimeException("Cannot initialize ViewBinding");
    }

    public static <V extends ViewBinding> V reflexViewBinding(
            Class<?> aClass,
            LayoutInflater from,
            ViewGroup viewGroup,
            boolean b
    ){
        try{
            Type[] actualTypeArguments = ((ParameterizedType) Objects.requireNonNull(aClass.getGenericSuperclass())).getActualTypeArguments();
            for (Type actualTypeArgument : actualTypeArguments) {
                Class<?> tClass;
                try {
                    tClass = (Class<?>) actualTypeArgument;
                } catch (Exception e) {
                    continue;
                }
                if (tClass != null && ViewBinding.class.isAssignableFrom(tClass)) {
                    Method inflate = tClass.getMethod("inflate", LayoutInflater.class, ViewGroup.class, boolean.class);
                    return (V) inflate.invoke(null, from, viewGroup, b);
                }
            }
            return reflexViewBinding(aClass.getSuperclass(), from, viewGroup, b);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
