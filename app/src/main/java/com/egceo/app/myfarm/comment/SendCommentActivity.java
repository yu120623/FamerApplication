package com.egceo.app.myfarm.comment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.baseandroid.BaseActivity;
import com.baseandroid.util.CommonUtil;
import com.egceo.app.myfarm.R;
import com.egceo.app.myfarm.util.GalleryImageLoader;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageSize;
import com.nostra13.universalimageloader.core.imageaware.ImageViewAware;

import java.util.ArrayList;

import galleryfinal.GalleryHelper;
import galleryfinal.ImageLoader;
import galleryfinal.model.PhotoInfo;

/*import cn.finalteam.galleryfinal.GalleryHelper;
import cn.finalteam.galleryfinal.utils.GalleryImageLoader;*/

/**
 * Created by FreeMason on 2016/2/20.
 */
public class SendCommentActivity extends BaseActivity {
    private RatingBar ratingBar;
    private EditText commentEditText;
    private GridView photoGridView;
    private BaseAdapter adapter;
    private ArrayList<PhotoInfo> photos;
    private int mScreenWidth;
    private DisplayImageOptions options;
    private ImageSize imageSize;

    @Override
    protected void initViews() {
        findViews();
        initData();
        initClick();
    }

    private void initClick() {
        photoGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                GalleryHelper.openGalleryMuti(activity, 5, new GalleryImageLoader());
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode == GalleryHelper.GALLERY_RESULT_SUCCESS){
            if(requestCode == GalleryHelper.GALLERY_REQUEST_CODE){
                photos = (ArrayList<PhotoInfo>) data.getSerializableExtra(GalleryHelper.RESULT_LIST_DATA);
                adapter.notifyDataSetChanged();
            }
        }
    }

    class PhotoAdapter extends BaseAdapter{
        @Override
        public int getCount() {
            if(photos.size() < 5){
                return photos.size()+1;
            }else{
                return photos.size();
            }
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
                setHeight(convertView);
                viewHolder = new ViewHolder();
                viewHolder.mIvThumb = (ImageView) convertView.findViewById(R.id.iv_thumb);
                viewHolder.mIvCheck = (ImageView) convertView.findViewById(R.id.iv_check);
                convertView.setTag(viewHolder);
            }
            viewHolder = (ViewHolder) convertView.getTag();
            viewHolder.mIvCheck.setVisibility(View.GONE);
            if (position == 0 && photos.size() < 5){
                viewHolder.mIvThumb.setImageResource(R.mipmap.camera);
            }else{
                String url = "";
                if(photos.size() < 5){
                    url = "file://"+photos.get(position-1).getPhotoPath();
                }else{
                    url = "file://"+photos.get(position).getPhotoPath();
                }
                com.nostra13.universalimageloader.core.ImageLoader.getInstance().displayImage(url, new ImageViewAware(viewHolder.mIvThumb), options, imageSize, null, null);
            }
            return convertView;
        }
    }

    class ViewHolder {
        public ImageView mIvThumb;
        public ImageView mIvCheck;
    }

    private void initData() {
        options = new DisplayImageOptions.Builder()
                .cacheInMemory(true)
                .cacheOnDisk(false)
                .showImageOnLoading(R.mipmap.ic_gf_default_photo)
                .showImageForEmptyUri(R.mipmap.ic_gf_default_photo)
                .showImageOnFail(R.mipmap.ic_gf_default_photo)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .build();
        imageSize = new ImageSize(200,200);
        mScreenWidth = CommonUtil.getScreenWith(getWindowManager());
        rightTextBtn.setText(R.string.comment_btn);
        rightTextBtn.setVisibility(View.VISIBLE);
        rightTextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        photos = new ArrayList<>();
        adapter = new PhotoAdapter();
        photoGridView.setAdapter(adapter);
    }

    private void setHeight(final View convertView) {
        int height = mScreenWidth / 5 -10;
        convertView.setLayoutParams(new AbsListView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, height));
    }
    

    private void findViews() {
        ratingBar = (RatingBar) this.findViewById(R.id.rating_bar);
        commentEditText = (EditText) this.findViewById(R.id.comment_content);
        photoGridView = (GridView) this.findViewById(R.id.photo_grid);
    }

    @Override
    protected int getContentView() {
        return R.layout.activity_send_comment;
    }

    @Override
    protected String setActionBarTitle() {
        return getString(R.string.send_comment);
    }
}
