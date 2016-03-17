package com.egceo.app.myfarm.user.activity;

import android.widget.TextView;

import com.amap.api.maps.model.Text;
import com.baseandroid.BaseActivity;
import com.egceo.app.myfarm.R;

/**
 * Created by FreeMason on 2016/3/16.
 */
public class AboutUsActivity extends BaseActivity {
    private TextView textView;
    @Override
    protected void initViews() {
        textView = (TextView) this.findViewById(R.id.about_us);
        textView.setText("一款最受欢迎的乡村休闲农庄旅游必备\"神器\"。\n" +
                "         “我的农庄”拥有全国海量在线乡村旅游休闲农业资源，融合休闲农业的‘’一、二、三‘’产业，形成“第六产业”及“第一产业”的运营思维，是对中国农业企业服务领域电子商务生态系统的一次创新。\n" +
                "         “我的农庄”让游客得到更大的实惠、让农庄主坐拥更多的客流、让农庄经理人获取更足的利润、让农庄推荐人拥有更广的商机。\n" +
                "         精准的定位，独特的视点，娱乐的玩法，让您“掌握”休闲农庄民宿、家庭农场、茶场、果园、渔场、农业生态园、旅游度假村、农家乐、古村、古镇等目的地。享受度假住宿，农家土菜，特色农产品、土特产等活动拼团带来的大额返利，让您的乡村之旅充满惊喜与乐趣。\n" +
                "         “我的农庄”，让旅游更便捷、让生意更好做。 ");
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_about_us;
    }

    @Override
    protected String setActionBarTitle() {
        return getString(R.string.about_app);
    }
}
