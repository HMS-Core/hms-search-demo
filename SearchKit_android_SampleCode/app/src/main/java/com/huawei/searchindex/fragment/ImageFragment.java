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
package com.huawei.searchindex.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.huawei.hms.searchkit.bean.ImageItem;
import com.huawei.searchindex.R;
import com.huawei.searchindex.activity.ImageActivity;
import com.huawei.searchindex.adapter.WebAdapter;
import com.huawei.searchindex.bean.ListBean;

import java.util.ArrayList;
import java.util.List;

public class ImageFragment extends Fragment {
    private static final String TAG = WebFragment.class.getSimpleName();
    private View view;
    private RecyclerView recyclerView;
    private WebAdapter adapter;
    private TextView tvEmpty;

    /**
     * New instance main fragment.
     *
     * @return the main fragment
     */
    public static ImageFragment newInstance() {
        return new ImageFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_image, container, false);

        initView();

        return view;
    }

    private void initView() {
        if (view != null) {
            recyclerView = view.findViewById(R.id.recycler_view);
            tvEmpty = view.findViewById(R.id.tv_empty);

            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(layoutManager);
        } else {
            Log.e(TAG, "view is null");
        }
    }

    public void setValue(List<ImageItem> list) {
        List<ListBean> listBeans = new ArrayList<>();
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                ListBean bean = new ListBean();
                if (list.get(i).getTitle() != null) {
                    bean.setTitle(list.get(i).getTitle());
                }
                if (list.get(i).getThumbnail() != null && list.get(i).getThumbnail().getUrl() != null) {
                    bean.setUrl(list.get(i).getThumbnail().getUrl());
                } else {
                    bean.setUrl("");
                }
                if (list.get(i).getClickUrl() != null) {
                    bean.setClick_url(list.get(i).getClickUrl());
                }
                listBeans.add(bean);
            }
            if (adapter != null) {
                adapter.refresh(listBeans);
                if (recyclerView != null && tvEmpty != null) {
                    tvEmpty.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            } else {
                adapter = new WebAdapter(getActivity(), listBeans);
                if (recyclerView != null && tvEmpty != null) {
                    recyclerView.setAdapter(adapter);
                    tvEmpty.setVisibility(View.GONE);
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
            onClick(list);
        } else {
            if (recyclerView != null && tvEmpty != null) {
                tvEmpty.setVisibility(View.VISIBLE);
                recyclerView.setVisibility(View.GONE);
            }
        }
    }

    private void onClick(final List<ImageItem> list) {
        if (adapter != null) {
            adapter.setOnClickListener(
                    new WebAdapter.OnItemClickListener() {
                        @Override
                        public void click(int position) {
                            Intent intent = new Intent(getActivity(), ImageActivity.class);
                            if (list.get(position).getTitle() != null) {
                                intent.putExtra("img_title", list.get(position).getTitle());
                            } else {
                                intent.putExtra("img_title", "");
                            }
                            if (list.get(position).getClickUrl() != null) {
                                intent.putExtra("img_click_url", list.get(position).getClickUrl());
                            } else {
                                intent.putExtra("img_click_url", "");
                            }
                            if (list.get(position).getThumbnail() != null
                                    && list.get(position).getThumbnail().getUrl() != null) {
                                intent.putExtra("img_thumbnail", list.get(position).getThumbnail().getUrl());
                            } else {
                                intent.putExtra("img_thumbnail", "");
                            }
                            if (list.get(position).getSourceImage() != null
                                    && list.get(position).getSourceImage().getImageHostpageUrl() != null) {
                                intent.putExtra(
                                        "img_host_url", list.get(position).getSourceImage().getImageHostpageUrl());
                            } else {
                                intent.putExtra("img_host_url", "");
                            }
                            if (list.get(position).getSourceImage() != null
                                    && list.get(position).getSourceImage().getImageContentUrl() != null) {
                                intent.putExtra(
                                        "img_content_url", list.get(position).getSourceImage().getImageContentUrl());
                            } else {
                                intent.putExtra("img_content_url", "");
                            }
                            if (list.get(position).getSourceImage() != null
                                    && list.get(position).getSourceImage().getHeight() != null) {
                                intent.putExtra("img_height", list.get(position).getSourceImage().getHeight());
                            } else {
                                intent.putExtra("img_height", "");
                            }
                            if (list.get(position).getSourceImage() != null
                                    && list.get(position).getSourceImage().getWidth() != null) {
                                intent.putExtra("img_width", list.get(position).getSourceImage().getWidth());
                            } else {
                                intent.putExtra("img_width", "");
                            }
                            if (list.get(position).getSourceImage() != null
                                    && list.get(position).getSourceImage().getPublishTime() != null) {
                                intent.putExtra("img_publish", list.get(position).getSourceImage().getPublishTime());
                            } else {
                                intent.putExtra("img_publish", "");
                            }
                            startActivity(intent);
                        }
                    });
        }
    }
}
