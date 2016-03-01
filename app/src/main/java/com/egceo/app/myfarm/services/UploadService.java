package com.egceo.app.myfarm.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Binder;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import com.alibaba.sdk.android.oss.ClientException;
import com.alibaba.sdk.android.oss.OSS;
import com.alibaba.sdk.android.oss.OSSClient;
import com.alibaba.sdk.android.oss.ServiceException;
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback;
import com.alibaba.sdk.android.oss.callback.OSSProgressCallback;
import com.alibaba.sdk.android.oss.common.auth.OSSCredentialProvider;
import com.alibaba.sdk.android.oss.common.auth.OSSPlainTextAKSKCredentialProvider;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.android.volley.VolleyError;
import com.baseandroid.util.CommonUtil;
import com.egceo.app.myfarm.db.DBHelper;
import com.egceo.app.myfarm.db.SendComment;
import com.egceo.app.myfarm.db.SendCommentDao;
import com.egceo.app.myfarm.db.SendResource;
import com.egceo.app.myfarm.db.SendResourceDao;
import com.egceo.app.myfarm.entity.CommentModel;
import com.egceo.app.myfarm.entity.Error;
import com.egceo.app.myfarm.entity.Resource;
import com.egceo.app.myfarm.entity.TransferObject;
import com.egceo.app.myfarm.http.API;
import com.egceo.app.myfarm.http.AppHttpResListener;
import com.egceo.app.myfarm.http.AppRequest;
import com.egceo.app.myfarm.util.AppUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class UploadService extends Service {
    private int mins = 5000;
    private SendCommentDao sendCommentDao;
    private SendResourceDao sendResourceDao;
    private Context context;
    private int index;
    private int maxSize;
    private List<SendResource> resources;
    private OSS oss;
    private IBinder binder= new UploadService.LocalBinder();

    public class LocalBinder extends Binder {
        public UploadService getService(){
            return UploadService.this;
        }
    }

    @Override
    public void onCreate() {
        super.onCreate();
        this.context = this.getApplicationContext();
        sendCommentDao = DBHelper.getDaoSession(getApplicationContext()).getSendCommentDao();
        sendResourceDao = DBHelper.getDaoSession(getApplicationContext()).getSendResourceDao();
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider("1watviMQtPwgWYfY", "fVuW6rXCwSfNi5SkLYorUrBqGB2Qgn");
        oss = new OSSClient(getApplicationContext(), "http://oss-cn-hangzhou.aliyuncs.com", credentialProvider);
        handler.sendEmptyMessage(0);

    }

    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        flags = START_STICKY;
        return super.onStartCommand(intent, flags, startId);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo info = cm.getActiveNetworkInfo();
            if(info == null) {
                handler.sendEmptyMessageDelayed(1, mins);
            }else {
                Log.i("++++load res++++++","++++load res++++++");
                resources = sendResourceDao.loadAll();
                if (resources == null || resources.size() <= 0) {
                    handler.sendEmptyMessageDelayed(1, mins);
                }else {
                    index = 0;
                    maxSize = resources.size();
                    uploadImg();
                }
            }
        }
    };

    private void uploadImg() {
        if(index < maxSize){
            Log.i("++++upload res++++++","++++upload res++++++");
            if(resources.get(index).getIsUpload() || !compressImage(resources.get(index))){
                index++;
                uploadImg();
                return;
            }
            String name = System.currentTimeMillis()+"_android.jpg";
            final String url = "s.mycff.com/"+name;
            PutObjectRequest put = new PutObjectRequest("mygoto", name, resources.get(index).getResourceLocation());
            put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
                @Override
                public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {

                }
            });
            oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                @Override
                public void onSuccess(PutObjectRequest request, PutObjectResult result) {
                    resources.get(index).setIsUpload(true);
                    resources.get(index).setResourceLocation(url);
                    sendResourceDao.update(resources.get(index));
                    index++;
                    uploadImg();
                }

                @Override
                public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                    sendResourceDao.delete(resources.get(index));
                    index++;
                    uploadImg();
                }
            });
        }else{
            sendComment();
        }

    }

    private void sendComment() {
        final List<SendComment> list = sendCommentDao.loadAll();
        if(list != null && list.size() > 0) {
            for (final SendComment sendComment : list) {
                List<SendResource> commentReses = sendResourceDao.queryBuilder().where(SendResourceDao.Properties.ReferrenceObjectId.eq(sendComment.getCommentId())).list();
                if(!checkImgIsUpload(commentReses)){
                    continue;
                }
                Log.i("++++send comment++++++","++++send comment++++++");
                String url = API.URL + API.API_URL.SEND_COMMENT;
                TransferObject data = AppUtil.getHttpData(context);
                CommentModel commentModel = new CommentModel();
                commentModel.setCommentScore(sendComment.getCommentScore());
                commentModel.setCommentContent(sendComment.getCommentContent());
                data.setCommentModel(commentModel);
                data.setOrderSn(sendComment.getOrderSn());
                List<Resource> resources = new ArrayList<>();
                for(SendResource commentResource : commentReses){
                    Resource res = new Resource(commentResource);
                    resources.add(res);
                }
                commentModel.setResoursePath(resources);
                AppRequest request = new AppRequest(context, url, new AppHttpResListener() {
                    @Override
                    public void onSuccess(TransferObject data) {
                        delComment(sendComment);
                    }

                    @Override
                    public void onEnd() {
                        if (list.indexOf(sendComment) == (list.size() - 1)) {
                            Log.i("++++send comment end+++","++++send comment end++++++");
                            handler.sendEmptyMessageDelayed(1, mins);
                        }
                    }

                    @Override
                    public void onFailed(Error error) {

                    }
                }, data);
                request.execute();
            }
            ;
        }else{
            handler.sendEmptyMessageDelayed(1, mins);
        }
    }

    private void delComment(SendComment sendComment) {
        List<SendResource> res = sendResourceDao.queryBuilder().where(SendResourceDao.Properties.ReferrenceObjectId.eq(sendComment.getCommentId())).list();
        for(SendResource resource : res){
            File file = new File(resource.getResourceLocation());
            if(!file.exists()){
                try {
                    file.delete();
                }catch (Exception e) {

                }
            }
        }
        sendCommentDao.delete(sendComment);
        sendResourceDao.deleteInTx(res);
    }

    private boolean checkImgIsUpload(List<SendResource>  commentRes){
        for(SendResource sendResource : commentRes){
            if(!sendResource.getIsUpload())
                return false;
        }
        return true;
    }

    private boolean compressImage(SendResource resource) {
        if(resource.getIsCrop())return true;
        File file = new File(resource.getResourceLocation());
        if(!file.exists()){
            sendResourceDao.delete(resource);
            return false;
        }
        Bitmap bitmap = CommonUtil.comPressBitmap(resource.getResourceLocation(),1280,720);
        File f = new File(CommonUtil.getUploadFolder(), System.currentTimeMillis()+"");
        try {
            f.createNewFile();
            FileOutputStream fos = new FileOutputStream(f);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 30, fos);
            fos.flush();
            fos.close();
            resource.setResourceLocation(f.getAbsolutePath());
            resource.setIsCrop(true);
            sendResourceDao.update(resource);
            return true;
        } catch (Exception e) {
            sendResourceDao.delete(resource);
            e.printStackTrace();
            return false;
        }
    }

}
