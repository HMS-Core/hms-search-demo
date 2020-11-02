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

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.huawei.hms.searchkit.SearchKitInstance;
import com.huawei.hms.searchkit.bean.AutoSuggestResponse;
import com.huawei.hms.searchkit.bean.BaseSearchResponse;
import com.huawei.hms.searchkit.bean.CommonSearchRequest;
import com.huawei.hms.searchkit.bean.ImageItem;
import com.huawei.hms.searchkit.bean.NewsItem;
import com.huawei.hms.searchkit.bean.SpellCheckResponse;
import com.huawei.hms.searchkit.bean.VideoItem;
import com.huawei.hms.searchkit.bean.WebItem;
import com.huawei.hms.searchkit.bean.WebSearchRequest;
import com.huawei.hms.searchkit.utils.Language;
import com.huawei.hms.searchkit.utils.Region;
import com.huawei.searchindex.R;
import com.huawei.searchindex.adapter.SuggestAdapter;
import com.huawei.searchindex.adapter.ViewPagerAdapter;
import com.huawei.searchindex.bean.TokenResponse;
import com.huawei.searchindex.fragment.ImageFragment;
import com.huawei.searchindex.fragment.NewsFragment;
import com.huawei.searchindex.fragment.VideoFragment;
import com.huawei.searchindex.fragment.WebFragment;
import com.huawei.searchindex.network.NetworkManager;
import com.huawei.searchindex.network.QueryService;

import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = SearchActivity.class.getSimpleName();
    private EditText editSearch;
    private LinearLayout linearSpellCheck, linearViewPager;
    private RecyclerView recyclerSuggest, recyclerContent;
    private ViewPager viewPager;
    private TabLayout tabLayout;
    private ViewPagerAdapter viewPagerAdapter;
    private SuggestAdapter suggestAdapter;
    private TextView tvSpellCheck;
    public static int mCurrentPage = 0;
    public static final WebSearchRequest webRequest = new WebSearchRequest();
    public static final CommonSearchRequest commonRequest = new CommonSearchRequest();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        SearchKitInstance.enableLog();
        SearchKitInstance.init(this, "103039265");
        // SearchKitInstance.init(this, "101513199");

        initRetrofit();

        initView();
    }

    private void initView() {
        editSearch = findViewById(R.id.search_view);
        linearSpellCheck = findViewById(R.id.linear_spell_check);
        recyclerSuggest = findViewById(R.id.suggest_list);
        recyclerContent = findViewById(R.id.content_list);
        tvSpellCheck = findViewById(R.id.tv_spell_check);
        viewPager = findViewById(R.id.view_pager);
        tabLayout = findViewById(R.id.tab_layout);
        linearViewPager = findViewById(R.id.linear_view_pager);

        LinearLayoutManager layoutManager = new LinearLayoutManager(SearchActivity.this);
        recyclerSuggest.setLayoutManager(layoutManager);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SearchActivity.this);
        recyclerContent.setLayoutManager(linearLayoutManager);

        FragmentManager fm = getSupportFragmentManager();
        if (null == viewPagerAdapter) {
            viewPagerAdapter = new ViewPagerAdapter(fm, this);
        }
        viewPager.setAdapter(viewPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        viewPager.setOffscreenPageLimit(3);

        initViewPagerListener();
        onEditTextListener();
    }

    private void initViewPagerListener() {
        viewPager.addOnPageChangeListener(
                new ViewPager.OnPageChangeListener() {
                    @Override
                    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

                    @Override
                    public void onPageSelected(int position) {
                        mCurrentPage = position;
                    }

                    @Override
                    public void onPageScrollStateChanged(int state) {}
                });
    }

    private void onEditTextListener() {
        editSearch.addTextChangedListener(
                new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {
                        recyclerSuggest.setVisibility(View.VISIBLE);
                        linearSpellCheck.setVisibility(View.GONE);

                        linearViewPager.setVisibility(View.GONE);

                        if (!TextUtils.isEmpty(s.toString())) {
                            getSuggest(s.toString());
                        } else {
                            recyclerSuggest.setVisibility(View.GONE);
                            linearViewPager.setVisibility(View.VISIBLE);
                            if (TextUtils.isEmpty(tvSpellCheck.getText().toString())) {
                                linearSpellCheck.setVisibility(View.GONE);
                            } else {
                                linearSpellCheck.setVisibility(View.VISIBLE);
                            }
                            if (suggestAdapter != null) {
                                suggestAdapter.clear();
                            }
                        }
                    }

                    @Override
                    public void afterTextChanged(Editable s) {}
                });

        editSearch.setOnEditorActionListener(
                new TextView.OnEditorActionListener() {
                    @Override
                    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                            recyclerSuggest.setVisibility(View.GONE);
                            linearViewPager.setVisibility(View.VISIBLE);

                            hintSoftKeyboard();
                            getSpellCheck(v.getText().toString());
                            return true;
                        }
                        return false;
                    }
                });
    }

    private void getSpellCheck(final String query) {
        Observable.create(
                        new ObservableOnSubscribe<String>() {
                            @Override
                            public void subscribe(ObservableEmitter<String> emitter) throws Exception {
                                SpellCheckResponse response =
                                        SearchKitInstance.getInstance()
                                                .getSearchHelper()
                                                .spellCheck(query, Language.ENGLISH);
                                if (response != null && response.getCorrectedQuery() != null) {
                                    emitter.onNext(response.getCorrectedQuery());
                                } else {
                                    Log.e(TAG, "spell error");
                                    emitter.onNext("");
                                }
                            }
                        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<String>() {
                            @Override
                            public void accept(String s) throws Exception {
                                if (TextUtils.isEmpty(s)) {
                                    linearSpellCheck.setVisibility(View.GONE);
                                } else {
                                    linearSpellCheck.setVisibility(View.VISIBLE);
                                    tvSpellCheck.setText(s);
                                }
                                doSearch(query);
                            }
                        },
                        StaticUtils.consumer);
    }

    private void getSuggest(final String query) {
        Observable.create(
                        new ObservableOnSubscribe<List<String>>() {
                            @Override
                            public void subscribe(ObservableEmitter<List<String>> emitter) throws Exception {
                                AutoSuggestResponse response =
                                        SearchKitInstance.getInstance()
                                                .getSearchHelper()
                                                .suggest(query, Language.ENGLISH);
                                List<String> list = new ArrayList<String>();
                                if (response != null) {
                                    if (response.getSuggestions() != null && !response.getSuggestions().isEmpty()) {
                                        for (int i = 0; i < response.getSuggestions().size(); i++) {
                                            list.add(response.getSuggestions().get(i).getName());
                                        }
                                        emitter.onNext(list);
                                    }
                                }
                                emitter.onComplete();
                            }
                        })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<List<String>>() {
                            @Override
                            public void accept(List<String> list) throws Exception {
                                if (suggestAdapter != null) {
                                    suggestAdapter.clear();
                                }
                                suggestAdapter = new SuggestAdapter(list);
                                recyclerSuggest.setAdapter(suggestAdapter);

                                suggestAdapter.setOnClickListener(
                                        new SuggestAdapter.OnItemClickListener() {
                                            @Override
                                            public void click(String text) {
                                                doSearch(text);
                                                editSearch.setText(text);
                                                hintSoftKeyboard();
                                                recyclerSuggest.setVisibility(View.GONE);

                                                linearViewPager.setVisibility(View.VISIBLE);
                                            }
                                        });
                            }
                        },
                        StaticUtils.consumer);
    }

    private void doSearch(String query) {
        webRequest.setQ(query);
        webRequest.setLang(Language.ENGLISH);
        webRequest.setSregion(Region.UNITEDKINGDOM);
        webRequest.setPn(1);
        webRequest.setPs(10);
        webRequest.setWithin("www.amazon.com");

        commonRequest.setQ(query);
        commonRequest.setLang(Language.ENGLISH);
        commonRequest.setSregion(Region.UNITEDKINGDOM);
        commonRequest.setPn(1);
        commonRequest.setPs(10);

        Observable.create(StaticUtils.observable)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        new Consumer<BaseSearchResponse>() {
                            @Override
                            public void accept(BaseSearchResponse baseSearchResponse) throws Exception {
                                if (baseSearchResponse != null && baseSearchResponse.getData() != null) {
                                    if (mCurrentPage == 0) {
                                        WebFragment webFragment =
                                                (WebFragment) viewPagerAdapter.getFragments().get(mCurrentPage);
                                        webFragment.setValue((List<WebItem>) baseSearchResponse.getData());
                                    } else if (mCurrentPage == 1) {
                                        ImageFragment imageFragment =
                                                (ImageFragment) viewPagerAdapter.getFragments().get(mCurrentPage);
                                        imageFragment.setValue((List<ImageItem>) baseSearchResponse.getData());
                                    } else if (mCurrentPage == 2) {
                                        VideoFragment videoFragment =
                                                (VideoFragment) viewPagerAdapter.getFragments().get(mCurrentPage);
                                        videoFragment.setValue((List<VideoItem>) baseSearchResponse.getData());
                                    } else if (mCurrentPage == 3) {
                                        NewsFragment newsFragment =
                                                (NewsFragment) viewPagerAdapter.getFragments().get(mCurrentPage);
                                        newsFragment.setValue((List<NewsItem>) baseSearchResponse.getData());
                                    }
                                }
                            }
                        },
                        StaticUtils.consumer);
    }

    private void hintSoftKeyboard() {
        InputMethodManager imm = (InputMethodManager) this.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null && imm.isActive() && this.getCurrentFocus() != null) {
            if (this.getCurrentFocus().getWindowToken() != null) {
                imm.hideSoftInputFromWindow(
                        this.getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }
    }

    public void initRetrofit() {
        ApplicationInfo appInfo = null;
        String baseUrl = "";
        try {
            appInfo = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA);
            baseUrl = appInfo.metaData.getString("baseUrl");
        } catch (PackageManager.NameNotFoundException e) {
            Log.e(TAG, "get meta data error: " + e.getMessage());
        }
        QueryService service = NetworkManager.getInstance().createService(this, baseUrl);
        service.getRequestToken(
                        "client_credentials",
                        "103039265",
                        "d64ba957b4c063403aecc700e138feec29ab4900ddd2c92a78bb72bd9325ee2c")
                // service.getRequestToken("client_credentials", "101513199",
                // "6333c6fa883a91f8b0bd783d43fda2b5695cb9d4612a481e2d55e6f80c2d870b")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TokenResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TokenResponse tokenResponse) {
                        if (tokenResponse != null) {
                            if (tokenResponse.getAccess_token() != null) {
                                // Log.e(TAG, response.getBody().getAccess_token());
                                SearchKitInstance.getInstance().setInstanceCredential(tokenResponse.getAccess_token());
                            } else {
                                Log.e(TAG, "get responseBody token is null");
                            }
                        } else {
                            Log.e(TAG, "get responseBody is null");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "get token error: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private static class StaticUtils {
        private static class MyConsumer implements Consumer<Throwable> {
            @Override
            public void accept(Throwable throwable) throws Exception {
                Log.e(TAG, "do search error: " + throwable.getMessage());
            }
        }

        private static Consumer<Throwable> consumer = new MyConsumer();

        private static class MyObservable implements ObservableOnSubscribe<BaseSearchResponse> {
            @Override
            public void subscribe(ObservableEmitter<BaseSearchResponse> emitter) throws Exception {
                if (mCurrentPage == 0) {
                    BaseSearchResponse<List<WebItem>> webResponse =
                            SearchKitInstance.getInstance().getWebSearcher().search(webRequest);
                    emitter.onNext(webResponse);
                } else if (mCurrentPage == 1) {
                    BaseSearchResponse<List<ImageItem>> imageResponse =
                            SearchKitInstance.getInstance().getImageSearcher().search(commonRequest);
                    emitter.onNext(imageResponse);
                } else if (mCurrentPage == 2) {
                    BaseSearchResponse<List<VideoItem>> videoResponse =
                            SearchKitInstance.getInstance().getVideoSearcher().search(commonRequest);
                    emitter.onNext(videoResponse);
                } else if (mCurrentPage == 3) {
                    BaseSearchResponse<List<NewsItem>> newsResponse =
                            SearchKitInstance.getInstance().getNewsSearcher().search(commonRequest);
                    emitter.onNext(newsResponse);
                }
            }
        }

        private static ObservableOnSubscribe<BaseSearchResponse> observable = new MyObservable();
    }
}
