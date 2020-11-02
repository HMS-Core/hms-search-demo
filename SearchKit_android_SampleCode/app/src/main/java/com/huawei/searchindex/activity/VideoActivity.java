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

public class VideoActivity extends AppCompatActivity {
    private static final String TAG = VideoActivity.class.getSimpleName();
    private TextView tvTitle, tvClickUrl, tvThumbnail, tvSiteName, tvLogo, tvPublishTime, tvDuration;
    private ImageView imgThumbnail;
    private String title, clickUrl, thumbnail, siteName, logo, publishTime, duration;
    private String empty = "null";
    private TextView tvProViderPlus, tvThumbnailPlus;
    private LinearLayout llProviderImage;
    private RelativeLayout rlThumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        initView();
        initData();
    }

    private void initView() {
        tvTitle = findViewById(R.id.tv_video_title);
        tvClickUrl = findViewById(R.id.tv_video_click_url);
        tvThumbnail = findViewById(R.id.tv_video_thumbnail_url);
        tvSiteName = findViewById(R.id.tv_video_site_name);
        tvLogo = findViewById(R.id.tv_video_logo);
        tvPublishTime = findViewById(R.id.tv_video_publish_time);
        tvDuration = findViewById(R.id.tv_video_duration);
        imgThumbnail = findViewById(R.id.img_video_thumbnail);
        tvProViderPlus = findViewById(R.id.tv_provider_plus);
        tvThumbnailPlus = findViewById(R.id.tv_thumbnail_plus);
        llProviderImage = findViewById(R.id.linear_provider);
        rlThumbnail = findViewById(R.id.linear_video_thumbnail_url);

        tvProViderPlus.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (tvProViderPlus.getText().equals("+")) {
                            tvProViderPlus.setText("-");
                            AnimationUtils.expand(llProviderImage);
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
            if (getIntent().hasExtra("video_title")) {
                title = getIntent().getStringExtra("video_title");
            }
            if (getIntent().hasExtra("video_click_url")) {
                clickUrl = getIntent().getStringExtra("video_click_url");
            }
            if (getIntent().hasExtra("video_logo")) {
                logo = getIntent().getStringExtra("video_logo");
            }
            if (getIntent().hasExtra("video_site_name")) {
                siteName = getIntent().getStringExtra("video_site_name");
            }
            if (getIntent().hasExtra("video_publish")) {
                publishTime = getIntent().getStringExtra("video_publish");
            }
            if (getIntent().hasExtra("video_duration")) {
                duration = getIntent().getStringExtra("video_duration");
            }
            if (getIntent().hasExtra("video_thumbnail")) {
                thumbnail = getIntent().getStringExtra("video_thumbnail");
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
            if (!TextUtils.isEmpty(duration)) {
                tvDuration.setText(duration);
            } else {
                tvDuration.setText(empty);
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
