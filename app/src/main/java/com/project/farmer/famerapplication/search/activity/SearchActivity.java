package com.project.farmer.famerapplication.search.activity;

import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.baseandroid.BaseActivity;
import com.baseandroid.util.CommonUtil;
import com.project.farmer.famerapplication.R;
import com.project.farmer.famerapplication.entity.CodeModel;
import com.project.farmer.famerapplication.entity.RecommendTagModel;
import com.project.farmer.famerapplication.entity.Tag;
import com.project.farmer.famerapplication.entity.TransferObject;
import com.project.farmer.famerapplication.http.API;
import com.project.farmer.famerapplication.http.AppHttpResListener;
import com.project.farmer.famerapplication.http.AppRequest;
import com.project.farmer.famerapplication.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends BaseActivity {
    //    private String[] hotAreas = new String[]{};
    //    private String[] hotTags = new String[]{};
    private List<String> hotAreas = new ArrayList<String>();
    private List<String> hotTags = new ArrayList<String>();
    private LinearLayout hotAreasLayout;
    private LinearLayout hotTagsLayout;
    private RecommendTagModel recommendTagModel;
    private List<Tag> areaTags;
    private List<Tag> serviceTags;
    private Tag tags;

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
                    for (int i = 0; i < areaTags.size(); i++) {
                        tags = areaTags.get(i);
                        hotAreas.add(tags.getTagName());
                    }
                }
                if (serviceTags != null && serviceTags.size() > 0) {
                    for (int i = 0; i < serviceTags.size(); i++) {
                        tags = serviceTags.get(i);
                        hotTags.add(tags.getTagName());
                    }
                }

//        int areaLines = (hotAreas.length / 3) + (hotAreas.length % 3 > 0 ? 1 : 0);//计算热门区域有几行
//        int tagLines = (hotTags.length / 3) + (hotTags.length % 3 > 0 ? 1 : 0);//计算热词有几行
                int areaLines = (areaTags.size() / 3) + (areaTags.size() % 3 > 0 ? 1 : 0);
                int tagLines = (serviceTags.size() / 3) + (serviceTags.size() % 3 > 0 ? 1 : 0);
                for (int i = 0; i < areaLines; i++) {
                    addTags(hotAreasLayout, hotAreas, i);
                }
                for (int i = 0; i < tagLines; i++) {
                    addTags(hotTagsLayout, hotTags, i);
                }
            }
        }, data);
        request.execute();
    }

    private void addTags(LinearLayout contentLayout, List<String> strs, int lineIndex) {
        LinearLayout tagsLineLayout = getLineLayout();//生成每行标签的layout
        for (int i = 0; i < 3; i++) {
            int index = i + (lineIndex * 3);
            inflater.inflate(R.layout.item_hot_tag, tagsLineLayout, true);
            View tagLayout = tagsLineLayout.getChildAt(tagsLineLayout.getChildCount() - 1);
            TextView hotNameTextView = (TextView) tagLayout.findViewById(R.id.hot_tag_name);
            String tagName = "";
            if (index >= strs.size()) {
                tagName = "";
                tagLayout.setVisibility(View.INVISIBLE);
            } else
                tagName = strs.get(i);
            hotNameTextView.setText(tagName);
        }
        contentLayout.addView(tagsLineLayout);
    }

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
                    Toast.makeText(getApplicationContext(), "输入为空", Toast.LENGTH_LONG).show();
                } else {
                    Intent intent = new Intent(SearchActivity.this, SearchResultsActivity.class);
                    intent.putExtra("key", editText.getText().toString());
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
