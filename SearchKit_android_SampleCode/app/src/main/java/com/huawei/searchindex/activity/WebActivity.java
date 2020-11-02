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
import android.widget.RelativeLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.huawei.searchindex.R;
import com.huawei.searchindex.utils.AnimationUtils;

import com.bumptech.glide.Glide;

public class WebActivity extends AppCompatActivity {
    private static final String TAG = WebActivity.class.getSimpleName();
    private TextView tvTitle, tvClickUrl, tvSnippet, tvSiteName, tvThumbnail;
    private ImageView imgThumbnail;
    private String title, clickUrl, snippet, siteName, thumbnail;
    private String empty = "null";
    private TextView tvThumbnailPlus;
    private RelativeLayout rlThumbnail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web);

        initView();
        initData();
    }

    private void initView() {
        tvTitle = findViewById(R.id.tv_web_title);
        tvClickUrl = findViewById(R.id.tv_web_click_url);
        tvSnippet = findViewById(R.id.tv_web_snippet);
        tvSiteName = findViewById(R.id.tv_web_site_name);
        tvThumbnail = findViewById(R.id.tv_web_thumbnail_url);
        imgThumbnail = findViewById(R.id.img_web_thumbnail);
        tvThumbnailPlus = findViewById(R.id.tv_thumbnail_plus);
        rlThumbnail = findViewById(R.id.linear_web_thumbnail_url);

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
            if (getIntent().hasExtra("web_title")) {
                title = getIntent().getStringExtra("web_title");
            }
            if (getIntent().hasExtra("web_click_url")) {
                clickUrl = getIntent().getStringExtra("web_click_url");
            }
            if (getIntent().hasExtra("web_site_name")) {
                snippet = getIntent().getStringExtra("web_site_name");
            }
            if (getIntent().hasExtra("web_snippet")) {
                siteName = getIntent().getStringExtra("web_snippet");
            }
            if (getIntent().hasExtra("web_thumbnail")) {
                thumbnail = getIntent().getStringExtra("web_thumbnail");
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
            if (!TextUtils.isEmpty(snippet)) {
                tvSnippet.setText(snippet);
            } else {
                tvSnippet.setText(empty);
            }
            if (!TextUtils.isEmpty(siteName)) {
                tvSiteName.setText(siteName);
            } else {
                tvSiteName.setText(empty);
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
