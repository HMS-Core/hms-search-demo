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

public class ImageActivity extends AppCompatActivity {
    private static final String TAG = ImageActivity.class.getSimpleName();
    private TextView tvTitle, tvClickUrl, tvHostUrl, tvContentUrl, tvThumbnail, tvHeigth, tvWidth, tvPublishTime;
    private ImageView imgThumbnail;
    private String title, clickUrl, hostUrl, contentUrl, thumbnail, heigth, width, publishTime;
    private String empty = "null";
    private TextView tvImagePlus, tvThumbnailPlus;
    private LinearLayout llSourceImage;
    private RelativeLayout rlThumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image);
        initView();
        initData();
    }

    private void initView() {
        tvTitle = findViewById(R.id.tv_image_title);
        tvClickUrl = findViewById(R.id.tv_image_click_url);
        tvHostUrl = findViewById(R.id.tv_image_host_url);
        tvContentUrl = findViewById(R.id.tv_image_content_url);
        tvThumbnail = findViewById(R.id.tv_image_thumbnail_url);
        tvHeigth = findViewById(R.id.tv_image_height);
        tvWidth = findViewById(R.id.tv_image_width);
        tvPublishTime = findViewById(R.id.tv_image_public_time);
        imgThumbnail = findViewById(R.id.img_image_thumbnail);
        tvImagePlus = findViewById(R.id.tv_image_plus);
        tvThumbnailPlus = findViewById(R.id.tv_thumbnail_plus);
        rlThumbnail = findViewById(R.id.linear_image_thumbnail_url);
        llSourceImage = findViewById(R.id.linear_source_image);

        tvImagePlus.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (tvImagePlus.getText().equals("+")) {
                            tvImagePlus.setText("-");
                            AnimationUtils.expand(llSourceImage);
                        } else if (tvImagePlus.getText().equals("-")) {
                            tvImagePlus.setText("+");
                            AnimationUtils.collapse(llSourceImage);
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
            if (getIntent().hasExtra("img_title")) {
                title = getIntent().getStringExtra("img_title");
            }
            if (getIntent().hasExtra("img_click_url")) {
                clickUrl = getIntent().getStringExtra("img_click_url");
            }
            if (getIntent().hasExtra("img_host_url")) {
                hostUrl = getIntent().getStringExtra("img_host_url");
            }
            if (getIntent().hasExtra("img_content_url")) {
                contentUrl = getIntent().getStringExtra("img_content_url");
            }
            if (getIntent().hasExtra("img_thumbnail")) {
                thumbnail = getIntent().getStringExtra("img_thumbnail");
            }
            if (getIntent().hasExtra("img_height")) {
                heigth = getIntent().getStringExtra("img_height");
            }
            if (getIntent().hasExtra("img_width")) {
                width = getIntent().getStringExtra("img_width");
            }
            if (getIntent().hasExtra("img_publish")) {
                publishTime = getIntent().getStringExtra("img_publish");
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
            if (!TextUtils.isEmpty(hostUrl)) {
                tvHostUrl.setText(hostUrl);
            } else {
                tvHostUrl.setText(empty);
            }
            if (!TextUtils.isEmpty(contentUrl)) {
                tvContentUrl.setText(contentUrl);
            } else {
                tvContentUrl.setText(empty);
            }
            if (!TextUtils.isEmpty(heigth)) {
                tvHeigth.setText(heigth);
            } else {
                tvHeigth.setText(empty);
            }
            if (!TextUtils.isEmpty(width)) {
                tvWidth.setText(width);
            } else {
                tvWidth.setText(empty);
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
