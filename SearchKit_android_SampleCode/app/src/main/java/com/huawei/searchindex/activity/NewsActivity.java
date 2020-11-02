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
package com.huawei.searchindex.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.huawei.searchindex.R;
import com.huawei.searchindex.utils.AnimationUtils;

import com.bumptech.glide.Glide;

public class NewsActivity extends AppCompatActivity {
    private static final String TAG = NewsActivity.class.getSimpleName();
    private TextView tvTitle, tvClickUrl, tvThumbnail, tvSiteName, tvLogo, tvPublishTime;
    private ImageView imgThumbnail;
    private String title, clickUrl, thumbnail, siteName, logo, publishTime;
    private String empty = "null";
    private TextView tvProViderPlus, tvThumbnailPlus;
    private LinearLayout llProviderImage;
    private RelativeLayout rlThumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        initView();
        initData();
    }

    private void initView() {
        tvTitle = findViewById(R.id.tv_news_title);
        tvClickUrl = findViewById(R.id.tv_news_click_url);
        tvThumbnail = findViewById(R.id.tv_news_thumbnail_url);
        tvSiteName = findViewById(R.id.tv_news_site_name);
        tvLogo = findViewById(R.id.tv_news_logo);
        tvPublishTime = findViewById(R.id.tv_news_publish_time);
        imgThumbnail = findViewById(R.id.img_news_thumbnail);
        tvProViderPlus = findViewById(R.id.tv_provider_plus);
        tvThumbnailPlus = findViewById(R.id.tv_thumbnail_plus);
        llProviderImage = findViewById(R.id.linear_provider);
        rlThumbnail = findViewById(R.id.linear_news_thumbnail_url);

        tvProViderPlus.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (tvProViderPlus.getText().equals("+")) {
                            AnimationUtils.expand(llProviderImage);
                            tvProViderPlus.setText("-");
                        } else if (tvProViderPlus.getText().equals("-")) {
                            tvProViderPlus.setText("+");
                            AnimationUtils.collapse(llProviderImage);
                        }
                    }
                });

        tvThumbnailPlus.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (tvThumbnailPlus.getText().equals("+")) {
                            tvThumbnailPlus.setText("-");
                            AnimationUtils.expand(rlThumbnail);
                        } else if (tvThumbnailPlus.getText().equals("-")) {
                            tvThumbnailPlus.setText("+");
                            AnimationUtils.collapse(rlThumbnail);
                        }
                    }
                });
    }

    private void initData() {
        try {
            if (getIntent().hasExtra("news_title")) {
                title = getIntent().getStringExtra("news_title");
            }
            if (getIntent().hasExtra("news_click_url")) {
                clickUrl = getIntent().getStringExtra("news_click_url");
            }
            if (getIntent().hasExtra("news_logo")) {
                logo = getIntent().getStringExtra("news_logo");
            }
            if (getIntent().hasExtra("news_site_name")) {
                siteName = getIntent().getStringExtra("news_site_name");
            }
            if (getIntent().hasExtra("news_publish")) {
                publishTime = getIntent().getStringExtra("news_publish");
            }
            if (getIntent().hasExtra("news_thumbnail")) {
                thumbnail = getIntent().getStringExtra("news_thumbnail");
            }

            if (!TextUtils.isEmpty(title)) {
                tvTitle.setText(title);
            } else {
                tvTitle.setText(empty);
            }
            if (!TextUtils.isEmpty(clickUrl)) {
                tvClickUrl.setText(clickUrl);
            } else {
                tvClickUrl.setText(empty);
            }
            if (!TextUtils.isEmpty(logo)) {
                tvLogo.setText(logo);
            } else {
                tvLogo.setText(empty);
            }
            if (!TextUtils.isEmpty(siteName)) {
                tvSiteName.setText(siteName);
            } else {
                tvSiteName.setText(empty);
            }
            if (!TextUtils.isEmpty(publishTime)) {
                tvPublishTime.setText(publishTime);
            } else {
                tvPublishTime.setText(empty);
            }
            if (!TextUtils.isEmpty(thumbnail)) {
                tvThumbnail.setText(thumbnail);
                imgThumbnail.setVisibility(View.VISIBLE);
                Glide.with(this).load(thumbnail).dontAnimate().into(imgThumbnail);
            } else {
                tvThumbnail.setText(empty);
                imgThumbnail.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            Log.e(TAG, "initData error: " + e.getMessage());
        }
    }
}
