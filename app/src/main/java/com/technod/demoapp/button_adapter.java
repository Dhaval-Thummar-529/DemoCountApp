package com.technod.demoapp;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

class ButtonAdapter extends RecyclerView.Adapter<ButtonAdapter.BtnViewHolder> {

    private List<Integer> listItem;
    private Context context;
    private ButtonClickListener listener;
    private boolean isEnabledFlag = true;
    private List<Integer> randomItems;

    public ButtonAdapter(List<Integer> listItem, Context context, ButtonClickListener listener, boolean isEnabledFlag, List<Integer> randomItems) {
        this.listItem = listItem;
        this.context = context;
        this.listener = listener;
        this.isEnabledFlag = isEnabledFlag;
        this.randomItems = randomItems;
    }

    @NonNull
    @Override
    public BtnViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View btnView
                = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_button, parent, false);

        BtnViewHolder viewHolder
                = new BtnViewHolder(btnView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BtnViewHolder holder, int position) {
        final int btnCount = listItem.get(position);
        holder.btn.setText("" + btnCount);
        if(randomItems != null && randomItems.contains(btnCount)){
            holder.btn.setBackgroundTintList(ColorStateList.valueOf(context.getColor(R.color.blue)));
            holder.btn.setEnabled(false);
        }
        if (!isEnabledFlag) {
            holder.btn.setBackgroundTintList(ColorStateList.valueOf(context.getColor(R.color.green)));
        } else {
            holder.btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Random r = new Random();
                    int res = r.nextInt((listItem.size()+1)-1)+1;
                    boolean check = true;
                    while (check){
                        if(randomItems.contains(res)){
                            res = r.nextInt(listItem.size()-1)+1;
                        } else {
                            check = false;
                        }
                    }
                    notifyItemChanged(res-1);
                    listener.onButtonClick(res);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return listItem.size();
    }

    public class BtnViewHolder extends RecyclerView.ViewHolder {
        public Button btn;

        public BtnViewHolder(@NonNull View itemView) {
            super(itemView);
            this.btn = itemView.findViewById(R.id.btn_item);
        }
    }
}
