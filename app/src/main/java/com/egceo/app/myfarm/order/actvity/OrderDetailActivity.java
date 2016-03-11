package com.egceo.app.myfarm.order.actvity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baseandroid.BaseActivity;
import com.baseandroid.util.ImageLoaderUtil;
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.entity.CommentModel;
import com.egceo.app.myfarm.entity.FarmItemsModel;
import com.egceo.app.myfarm.entity.OrderModel;
import com.egceo.app.myfarm.entity.Resource;
import com.egceo.app.myfarm.entity.TransferObject;
import com.egceo.app.myfarm.http.API;
import com.egceo.app.myfarm.http.AppHttpResListener;
import com.egceo.app.myfarm.http.AppRequest;
import com.egceo.app.myfarm.util.AppUtil;
import com.egceo.app.myfarm.view.PhotoViewPageActivity;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class OrderDetailActivity extends BaseActivity {
    private LinearLayout farmSetItemList;
    private TextView orderSN;
    private TextView buyNum;
    private TextView orderMoney;
    private OrderModel order;
    private View currentItemView;
    private SimpleDateFormat dataFormat;
    private TextView contactName;
    private TextView farmFund;
    private Button orderBtn;
    private View orderBtnLayout;
    private LinearLayout commentLayout;
    private DisplayImageOptions options;
    @Override
    protected void initViews() {
        showProgress();
        findViews();
        initData();
        loadDataFromServer();
    }

    private void initData() {
        order = (OrderModel) this.getIntent().getSerializableExtra("order");
        dataFormat = new SimpleDateFormat("yyyy-MM-dd");
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
    }

    private void findViews() {
        farmSetItemList = (LinearLayout) this.findViewById(R.id.farm_set_item_content);
        orderSN = (TextView) this.findViewById(R.id.order_sn);
        buyNum = (TextView) this.findViewById(R.id.buy_num);
        orderMoney = (TextView) this.findViewById(R.id.order_total_money);
        farmFund = (TextView) this.findViewById(R.id.fund);
        contactName = (TextView) this.findViewById(R.id.contact_name);
        orderBtn = (Button) this.findViewById(R.id.order_btn);
        orderBtnLayout = this.findViewById(R.id.order_btn_layout);
        commentLayout = (LinearLayout) this.findViewById(R.id.comments_layout);
    }

    private void loadDataFromServer() {
        String url = API.URL + API.API_URL.ORDER_INFO;
        TransferObject data = AppUtil.getHttpData(context);
        data.setOrderSn(order.getOrderSn());
        AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
            @Override
            public void onSuccess(TransferObject data) {
                if(data.getOrderModel() != null){
                    showOrderInfo(data.getOrderModel());
                    showComment(data.getCommentModels());
                }
            }

            @Override
            public void onEnd() {
                super.onEnd();
                hideProgress();
            }
        },data);
        request.execute();
    }

    private void showComment(List<CommentModel> commentModels) {
        if(commentModels != null && commentModels.size() > 0){
            commentLayout.setVisibility(View.VISIBLE);
            for(CommentModel commentModel : commentModels){
                View layout = inflater.inflate(R.layout.item_pingjia,null,false);
                RelativeLayout commentHead = (RelativeLayout) layout.findViewById(R.id.comment_head);
                TextView commentUserName = (TextView) layout.findViewById(R.id.comment_user_name);
                TextView commentDate = (TextView) layout.findViewById(R.id.comment_date);
                TextView commentContext = (TextView) layout.findViewById(R.id.comment_context);
                LinearLayout commentBody = (LinearLayout) layout.findViewById(R.id.comment_body);
                GridView photoGrid = (GridView) layout.findViewById(R.id.photo_grid);
                commentHead.setVisibility(View.GONE);
                commentBody.setVisibility(View.VISIBLE);
                commentUserName.setText(commentModel.getCommentName());
                commentDate.setText(dataFormat.format(commentModel.getCreatedTime()));
                commentContext.setText(commentModel.getComment().getCommentContent());
                if(commentModel.getResoursePath() != null && commentModel.getResoursePath().size() > 0) {
                    photoGrid.setVisibility(View.VISIBLE);
                    photoGrid.setTag(commentModel.getResoursePath());
                    photoGrid.setAdapter(new PhotoAdapter(commentModel.getResoursePath()));
                    photoGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            List<Resource> resources = (List<Resource>) parent.getTag();
                            ArrayList<String> url = new ArrayList<>();
                            for(Resource resource : resources){
                                url.add(resource.getResourceLocation());
                            }
                            Intent intent = new Intent(context, PhotoViewPageActivity.class);
                            intent.putExtra("urls",url);
                            intent.putExtra("index",position);
                            startActivity(intent);
                        }
                    });
                }else{
                    photoGrid.setVisibility(View.GONE);
                }
                commentLayout.addView(layout);
            }
        }
    }

    private void showOrderInfo(OrderModel orderModel) {
        orderSN.setText(orderModel.getOrderSn());
        orderMoney.setText(orderModel.getOrdePrice()+"×"+orderModel.getCopies());
        buyNum.setText(orderModel.getCopies()+"");
        farmFund.setText(orderModel.getFund()+"");
        contactName.setText(orderModel.getContact().getConnectName());
        for(FarmItemsModel farmItemsModel : orderModel.getFarmItemsModels()){
            getFarmSetSubItem(farmItemsModel);
        }
        if(AppUtil.ordNP.equals(orderModel.getStatus())){
            orderBtnLayout.setVisibility(View.VISIBLE);
            orderBtn.setText(R.string.go_pay);
            orderBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, OrderChoosePayActivity.class);
                    intent.putExtra("order",order);
                    startActivity(intent);
                    finish();
                }
            });
        }else if(AppUtil.ordHP.equals(orderModel.getStatus())){
            orderBtnLayout.setVisibility(View.VISIBLE);
            orderBtn.setText(R.string.order_code);
            orderBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(context, OrderCodeActivity.class);
                    intent.putExtra("order", order);
                    startActivity(intent);
                }
            });
        }
    }

    class PhotoAdapter extends BaseAdapter {
        private List<Resource> photos;
        public PhotoAdapter(List<Resource> photos){
            this.photos = photos;
        }

        @Override
        public int getCount() {
            return photos.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder viewHolder = null;
            if (convertView == null) {
                convertView = inflater.inflate(R.layout.gf_adapter_photo_list_item, parent,false);
                viewHolder = new ViewHolder();
                viewHolder.mIvThumb = (ImageView) convertView.findViewById(R.id.iv_thumb);
                viewHolder.mIvCheck = (ImageView) convertView.findViewById(R.id.iv_check);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.mIvCheck.setVisibility(View.GONE);
            com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(photos.get(position).getResourceLocation()+ AppUtil.FARM_FACE, new ImageViewAware(viewHolder.mIvThumb), options);
            return convertView;
        }
    }

    class ViewHolder {
        public ImageView mIvThumb;
        public ImageView mIvCheck;
    }

    public View getFarmSetSubItem(FarmItemsModel farmItemsModel) {
        View item = ((LinearLayout) inflater.inflate(R.layout.item_sub_farmset, farmSetItemList, true)).getChildAt(farmSetItemList.getChildCount() - 1);
        TextView farmSetName = (TextView) item.findViewById(R.id.farmset_item_name);
        TextView farmSetDesc = (TextView) item.findViewById(R.id.farmset_item_desc);
        TextView farmSetPrice = (TextView) item.findViewById(R.id.farmset_item_price);
        TextView farmSetTag = (TextView) item.findViewById(R.id.farmset_item_tag);
        TextView farmSetDescList = (TextView) item.findViewById(R.id.farmset_item_desclist);
        ImageView farmSetImg = (ImageView) item.findViewById(R.id.farmset_item_img);
        farmSetPrice.setTextColor(getResources().getColor(R.color.green2));
        farmSetName.setText(farmItemsModel.getFarmItemName());
        farmSetDesc.setText(farmItemsModel.getFarmName());
        farmSetPrice.setText(dataFormat.format(farmItemsModel.getConsumeTime()));
        farmSetTag.setText(AppUtil.getFarmSetTag(farmItemsModel.getFarmItemType()));
        farmSetTag.setBackgroundResource(AppUtil.getFarmSetTagBg(farmItemsModel.getFarmItemType()));
        farmSetDescList.setText(farmItemsModel.getFarmItemDesc());
        ImageLoaderUtil.getInstance().displayImg(farmSetImg, farmItemsModel.getResources().get(0).getResourceLocation()+AppUtil.FARM_SET_DETAIL_IMG_SIZE);
        View setHeader = item.findViewById(R.id.farm_set_item_header);
        View setContent = item.findViewById(R.id.farm_set_item_content);
        setHeader.setTag(setContent);
        setHeader.setOnClickListener(onFarmSetItemClick);
        return item;
    }

    public View.OnClickListener onFarmSetItemClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if(currentItemView == v)return;
            View view = (View) v.getTag();
            if (view.getVisibility() == View.VISIBLE) {
                view.setVisibility(View.GONE);
            } else {
                if(currentItemView != null)
                    currentItemView.setVisibility(View.GONE);
                view.setVisibility(View.VISIBLE);
                currentItemView = view;
            }
        }
    };

    @Override
    protected int getContentView() {
        return R.layout.activity_order_detail;
    }

    @Override
    protected String setActionBarTitle() {
        return getString(R.string.order_info);
    }
}
