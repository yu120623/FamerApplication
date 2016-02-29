package com.egceo.app.myfarm.services;

import android.app.Service;
import android.content.Intent;
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
import com.alibaba.sdk.android.oss.internal.OSSAsyncTask;
import com.alibaba.sdk.android.oss.model.PutObjectRequest;
import com.alibaba.sdk.android.oss.model.PutObjectResult;
import com.egceo.app.myfarm.db.DBHelper;
import com.egceo.app.myfarm.db.SendComment;
import com.egceo.app.myfarm.db.SendCommentDao;
import com.egceo.app.myfarm.db.SendResource;
import com.egceo.app.myfarm.db.SendResourceDao;

import java.util.List;

/**
 * Created by FreeMason on 2016/2/28.
 */
public class UploadService extends Service {
    private int time = 5000;
    private SendCommentDao sendCommentDao;
    private SendResourceDao sendResourceDao;

    private int maxResSize = 0;
    private int currentResSize = 0;
    private OSS oss;
    private IBinder binder= new UploadService.LocalBinder();

    public class LocalBinder extends Binder {
        public UploadService getService(){
            return UploadService.this;
        }
    }
    public IBinder onBind(Intent intent) {
        return binder;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        sendCommentDao = DBHelper.getDaoSession(getApplicationContext()).getSendCommentDao();
        sendResourceDao = DBHelper.getDaoSession(getApplicationContext()).getSendResourceDao();
        OSSCredentialProvider credentialProvider = new OSSPlainTextAKSKCredentialProvider("1watviMQtPwgWYfY", "fVuW6rXCwSfNi5SkLYorUrBqGB2Qgn");
        oss = new OSSClient(getApplicationContext(), "mygoto.oss-cn-hangzhou.aliyuncs.com", credentialProvider);
        handler.sendEmptyMessage(0);
        return super.onStartCommand(intent, flags, startId);
    }

    private Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            List<SendComment> sendCommentList = sendCommentDao.loadAll();
            if(sendCommentList == null || sendCommentList.size() <= 0){
                handler.sendEmptyMessageDelayed(0,time);
            }else{
                sendComment(sendCommentList);
            }
        }
    };

    private void sendComment(List<SendComment> sendCommentList) {
        for(SendComment sendComment : sendCommentList){
            List<SendResource> sendResources = sendResourceDao.queryBuilder().where(SendResourceDao.Properties.ReferrenceObjectId.eq(sendComment.getCommentId())).list();
            maxResSize = sendResources.size();
            if(sendResources != null && sendResources.size() > 0){
                currentResSize = 0;
                maxResSize = sendResources.size();
                PutObjectRequest put = new PutObjectRequest("mygoto", System.currentTimeMillis()+"_android", sendResources.get(currentResSize).getResourceLocation());
                put.setProgressCallback(new OSSProgressCallback<PutObjectRequest>() {
                    @Override
                    public void onProgress(PutObjectRequest request, long currentSize, long totalSize) {

                    }
                });
                OSSAsyncTask task = oss.asyncPutObject(put, new OSSCompletedCallback<PutObjectRequest, PutObjectResult>() {
                    @Override
                    public void onSuccess(PutObjectRequest request, PutObjectResult result) {

                    }

                    @Override
                    public void onFailure(PutObjectRequest request, ClientException clientExcepion, ServiceException serviceException) {
                        // 请求异常
                        if (clientExcepion != null) {
                            clientExcepion.printStackTrace();
                        }
                        if (serviceException != null) {
                            Log.e("ErrorCode", serviceException.getErrorCode());
                            Log.e("RequestId", serviceException.getRequestId());
                            Log.e("HostId", serviceException.getHostId());
                            Log.e("RawMessage", serviceException.getRawMessage());
                        }
                    }
                });
                task.waitUntilFinished();
            }
        }
    }

    private void uploadImg(SendComment sendComment, List<SendResource> sendResources, int currentResSize) {

    }


    private void upload(SendResource sendResource) {
        sendResource.setIsUpload(true);
        sendResourceDao.update(sendResource);
    }

    private void crop(SendResource sendResource) {
        sendResource.setIsCrop(true);
        sendResourceDao.update(sendResource);
    }
}
