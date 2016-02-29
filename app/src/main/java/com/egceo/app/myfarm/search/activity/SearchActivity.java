package com.egceo.app.myfarm.search.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.baseandroid.BaseActivity;
import com.baseandroid.util.CommonUtil;
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.entity.RecommendTagModel;
import com.egceo.app.myfarm.entity.Tag;
import com.egceo.app.myfarm.entity.TransferObject;
import com.egceo.app.myfarm.http.API;
import com.egceo.app.myfarm.http.AppHttpResListener;
import com.egceo.app.myfarm.http.AppRequest;
import com.egceo.app.myfarm.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity {
    private LinearLayout hotAreasLayout;
    private LinearLayout hotTagsLayout;
    private RecommendTagModel recommendTagModel;
    private List<Tag> areaTags;
    private List<Tag> serviceTags;

    @Override
    protected void initViews() {
        findViews();
        initData();
    }

    private void initData() {
        recommendTagModel = new RecommendTagModel();
        areaTags = new ArrayList<Tag>();
        serviceTags = new ArrayList<Tag>();
        loadDataFromServer();
    }

    private void loadDataFromServer() {
        String url = API.URL + API.API_URL.SEARCH_KEY_LIST;
        TransferObject data = AppUtil.getHttpData(context);
        data.setCityCode("0512");

        AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                recommendTagModel = data.getRecommendTagModel();
                areaTags = recommendTagModel.getAreaTags();
                serviceTags = recommendTagModel.getServiceTags();
                if (areaTags != null && areaTags.size() > 0) {

                }
                if (serviceTags != null && serviceTags.size() > 0) {

                }
                int areaLines = (areaTags.size() / 3) + (areaTags.size() % 3 > 0 ? 1 : 0);//计算热门区域有几行
                int tagLines = (serviceTags.size() / 3) + (serviceTags.size() % 3 > 0 ? 1 : 0);//计算热词有几行
                for (int i = 0; i < areaLines; i++) {
                    addTags(hotAreasLayout, areaTags, i, AreaOnClickListener);
                }
                for (int i = 0; i < tagLines; i++) {
                    addTags(hotTagsLayout, serviceTags, i, ServiceOnClickListener);
                }
            }
        }, data);
        request.execute();
    }

    private void addTags(LinearLayout contentLayout, List<Tag> tags, int lineIndex, View.OnClickListener listener) {
        LinearLayout tagsLineLayout = getLineLayout();//生成每行标签的layout
        for (int i = 0; i < 3; i++) {
            int index = i + (lineIndex * 3);
            inflater.inflate(R.layout.item_hot_tag, tagsLineLayout, true);
            View tagLayout = tagsLineLayout.getChildAt(tagsLineLayout.getChildCount() - 1);
            TextView hotNameTextView = (TextView) tagLayout.findViewById(R.id.hot_tag_name);
            String tagName = "";
            if (index >= tags.size()) {
                tagName = "";
                tagLayout.setVisibility(View.INVISIBLE);
            } else {
                tagName = tags.get(i).getTagName();
                tagLayout.setTag(tags.get(i).getTagId());
            }
            hotNameTextView.setText(tagName);
            tagLayout.setOnClickListener(listener);
        }
        contentLayout.addView(tagsLineLayout);
    }

    public View.OnClickListener AreaOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String id = v.getTag().toString();
            Intent intent = new Intent(SearchActivity.this, SearchResultsActivity.class);
            intent.putExtra("key", id);
            intent.putExtra("type", "B");
            startActivity(intent);
        }
    };


    private View.OnClickListener ServiceOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String id = v.getTag().toString();
            Intent intent = new Intent(SearchActivity.this, SearchResultsActivity.class);
            intent.putExtra("key", id);
            intent.putExtra("type", "C");
            startActivity(intent);
        }
    };


    private LinearLayout getLineLayout() {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = CommonUtil.Dp2Px(context, 10);
        linearLayout.setLayoutParams(params);
        linearLayout.setWeightSum(3);
        return linearLayout;
    }

    @Override
    public void initActonBar() {
        actionbarView = (RelativeLayout) this.findViewById(R.id.action_bar_view);
        inflater.inflate(R.layout.search_action_bar, actionbarView, true);
        View backBtn = this.findViewById(R.id.back_btn);
        final EditText editText = (EditText) this.findViewById(R.id.search_key);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        View searchBtn = this.findViewById(R.id.user_login);
        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String str = editText.getText().toString();
                if (str == null || str.equals("") || str.length() == 0) {
                    CommonUtil.showMessage(context, "输入为空");
                } else {
                    Intent intent = new Intent(SearchActivity.this, SearchResultsActivity.class);
                    intent.putExtra("key", editText.getText().toString());
                    intent.putExtra("type", "A");
                    startActivity(intent);
                }
            }
        });
    }

    private void findViews() {
        hotAreasLayout = (LinearLayout) this.findViewById(R.id.hot_area_layout);
        hotTagsLayout = (LinearLayout) this.findViewById(R.id.hot_tags_layout);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_search;
    }

    @Override
    protected String setActionBarTitle() {
        return null;
    }
}
