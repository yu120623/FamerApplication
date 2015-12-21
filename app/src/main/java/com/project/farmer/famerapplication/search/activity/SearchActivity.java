package com.project.farmer.famerapplication.search.activity;

import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baseandroid.BaseActivity;
import com.baseandroid.util.CommonUtil;
import com.project.farmer.famerapplication.R;

import org.w3c.dom.Text;

public class SearchActivity extends BaseActivity{
    private String[] hotTags = new String[]{"东山","西山","相城","阳澄湖","旺山","巴城","苏州"};
    private LinearLayout hotTagsLayout;
    @Override
    protected void initViews() {
        findViews();
        initData();
    }

    private void initData() {
        int lines = (hotTags.length / 3)+(hotTags.length % 3 > 0 ? 1:0);//计算热门标签有几行
        for(int i = 0;i < lines;i++){
            LinearLayout tagsLineLayout = getTagsLineLayout();//生成每行标签的layout
            addTags(tagsLineLayout,i);
        }
    }

    private void addTags(LinearLayout tagsLineLayout, int lineIndex) {
        for(int i = 0;i < 3;i++){
            int index = i+(lineIndex*3);
            inflater.inflate(R.layout.hot_tag_item,tagsLineLayout,true);
            View tagLayout = tagsLineLayout.getChildAt(tagsLineLayout.getChildCount()-1);
            TextView hotNameTextView = (TextView) tagLayout.findViewById(R.id.hot_tag_name);
            String tagName = "";
            if(index >= hotTags.length) {
                tagName = "";
                tagLayout.setVisibility(View.INVISIBLE);
            }
            else
                tagName = hotTags[index];
            hotNameTextView.setText(tagName);
        }
        hotTagsLayout.addView(tagsLineLayout);
    }

    private LinearLayout getTagsLineLayout() {
        LinearLayout linearLayout = new LinearLayout(context);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        params.bottomMargin = CommonUtil.Dp2Px(context,10);
        linearLayout.setLayoutParams(params);
        linearLayout.setWeightSum(3);
        return linearLayout;
    }

    @Override
    public void initActonBar() {
        actionbarView = (RelativeLayout) this.findViewById(R.id.action_bar_view);
        inflater.inflate(R.layout.search_action_bar,actionbarView,true);
        View backBtn = this.findViewById(R.id.back_btn);
        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void findViews() {
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
