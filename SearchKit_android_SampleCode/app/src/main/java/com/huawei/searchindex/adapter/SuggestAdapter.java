/*
 * Copyright 2020. Huawei Technologies Co., Ltd. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.huawei.searchindex.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.huawei.searchindex.R;

import java.util.ArrayList;
import java.util.List;

public class SuggestAdapter extends RecyclerView.Adapter<SuggestAdapter.SuggestViewHolder> {
    private List<String> suggestList = new ArrayList<>();

    public SuggestAdapter(List<String> list) {
        this.suggestList = list;
    }

    @NonNull
    @Override
    public SuggestViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_suggest, parent, false);
        return new SuggestViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull SuggestViewHolder holder, final int position) {
        if (suggestList != null && !suggestList.isEmpty()) {
            holder.tv_suggest.setText(suggestList.get(position));

            holder.itemView.setOnClickListener(
                    new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onItemClickListener.click(suggestList.get(position));
                        }
                    });
        }
    }

    @Override
    public int getItemCount() {
        return suggestList.size();
    }

    public static class SuggestViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_suggest;

        public SuggestViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_suggest = itemView.findViewById(R.id.tv_suggest);
        }
    }

    public void clear() {
        if (suggestList != null && !suggestList.isEmpty()) {
            suggestList.clear();
            notifyDataSetChanged();
        }
    }

    private OnItemClickListener onItemClickListener;

    public void setOnClickListener(OnItemClickListener onClickListener) {
        this.onItemClickListener = onClickListener;
    }

    public interface OnItemClickListener {
        void click(String text);
    }
}
