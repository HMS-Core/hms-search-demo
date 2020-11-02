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

import android.content.Context;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.huawei.searchindex.R;
import com.huawei.searchindex.fragment.ImageFragment;
import com.huawei.searchindex.fragment.NewsFragment;
import com.huawei.searchindex.fragment.VideoFragment;
import com.huawei.searchindex.fragment.WebFragment;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentPagerAdapter {
    private List<Fragment> fragments = new ArrayList<>();

    private Context context;

    private int[] titleids = {
        R.string.fragment_web, R.string.fragment_image, R.string.fragment_video, R.string.fragment_news
    };

    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        fragments.add(WebFragment.newInstance());
        fragments.add(ImageFragment.newInstance());
        fragments.add(VideoFragment.newInstance());
        fragments.add(NewsFragment.newInstance());
        this.context = context;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return context.getString(titleids[position]);
    }

    public List<Fragment> getFragments() {
        return fragments;
    }

    public int getPageTitleSize() {
        return titleids.length;
    }
}
