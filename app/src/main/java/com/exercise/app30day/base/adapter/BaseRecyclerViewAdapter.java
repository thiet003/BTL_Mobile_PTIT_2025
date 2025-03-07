package com.exercise.app30day.base.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

import com.exercise.app30day.base.BindingReflex;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerViewAdapter<T, VB extends ViewBinding> extends RecyclerView.Adapter<BaseViewHolder<VB>> {

    private RecyclerView recyclerView;


    private final List<T> dataList = new ArrayList<>();

    private OnItemClickListener<T> onItemClickListener;

    private OnItemLongClickListener<T> onItemLongClickListener;

    public interface OnItemClickListener<T> {
        void onItemClick(T data, int position);
    }

    public interface OnItemLongClickListener<T> {
        void onItemLongClick(T data, int position);
    }

    protected abstract void bindData(VB binding, T item, int position);

    public void bindViewClickListener(BaseViewHolder<VB> viewHolder, int viewType){
        viewHolder.itemView.setOnClickListener(v->{
            if(!isCheckClickItem()) return;
            int position = viewHolder.getAdapterPosition();
            if(position == RecyclerView.NO_POSITION) return;
            T item = getItem(position);
            if (onItemClickListener != null && item != null) {
                onItemClickListener.onItemClick(getItem(position), viewHolder.getAdapterPosition());
            }
        });
        viewHolder.itemView.setOnLongClickListener(v -> {
            int position = viewHolder.getAdapterPosition();
            T item = getItem(position);
            if (onItemLongClickListener != null && item != null) {
                onItemLongClickListener.onItemLongClick(getItem(position), viewHolder.getAdapterPosition());
            }
            return true;
        });
    }

    private long timeClickItem = 0L;

    private static final long DURATION_CLICK = 100L;
    protected boolean isCheckClickItem(){
        if (System.currentTimeMillis() - timeClickItem > DURATION_CLICK) {
            timeClickItem = System.currentTimeMillis();
            return true;
        }
        return false;
    }

    public RecyclerView getRecyclerView() {
        return recyclerView;
    }

    public Context getContext() {
        return recyclerView.getContext();
    }

    public List<T> getDataList() {
        return dataList;
    }

    public T getItem(int position){
        return dataList.get(position);
    }

    public int getItemPosition(T item){
        return dataList.indexOf(item);
    }

    public void setData(List<T> dataList) {
        DiffUtilCallBack<T> diffUtilCallBack = new DiffUtilCallBack<>(this.dataList, dataList);
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffUtilCallBack);
        this.dataList.clear();
        this.dataList.addAll(dataList);
        diffResult.dispatchUpdatesTo(this);
    }

    public void addData(List<T> dataList) {
        int oldSize = this.dataList.size();
        this.dataList.addAll(dataList);
        notifyItemRangeInserted(oldSize, dataList.size());
    }

    public void clearData() {
        int oldSize = this.dataList.size();
        this.dataList.clear();
        notifyItemRangeRemoved(0, oldSize);
    }

    public void removeItem(int position) {
        if (position < 0 || position >= dataList.size()) return;
        dataList.remove(position);
        notifyItemRemoved(position);
    }

    public void removeItem(T item) {
        int position = dataList.indexOf(item);
        if (position < 0) return;
        dataList.remove(position);
        notifyItemRemoved(position);
    }

    public void addItem(T item) {
        dataList.add(item);
        notifyItemInserted(dataList.size() - 1);
    }

    public void addItem(T item, int position) {
        dataList.add(position, item);
        notifyItemInserted(position);
    }

    public void updateItem(T item) {
        int position = dataList.indexOf(item);
        if (position < 0) return;
        dataList.set(position, item);
        notifyItemChanged(position);
    }

    public void setOnItemClickListener(OnItemClickListener<T> onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener<T> onItemLongClickListener) {
        this.onItemLongClickListener = onItemLongClickListener;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.recyclerView = recyclerView;
    }

    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onDetachedFromRecyclerView(recyclerView);
        this.recyclerView = null;
    }

    @NonNull
    @Override
    public BaseViewHolder<VB> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        BaseViewHolder<VB> viewHolder = new BaseViewHolder<>(
                BindingReflex.reflexViewBinding(
                        getClass(),
                        LayoutInflater.from(parent.getContext()),
                        parent,
                        false
                )
        );
        bindViewClickListener(viewHolder, viewType);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder<VB> holder, int position) {
        bindData(holder.getBinding(), dataList.get(position), position);
    }


    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
