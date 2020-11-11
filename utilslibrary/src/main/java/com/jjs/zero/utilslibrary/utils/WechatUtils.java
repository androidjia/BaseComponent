package com.jjs.zero.utilslibrary.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;


import androidx.annotation.DrawableRes;
import androidx.annotation.IntRange;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXAppExtendObject;
import com.tencent.mm.opensdk.modelmsg.WXEmojiObject;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXMusicObject;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/10/29
 * @Details: <功能描述>
 */
public class WechatUtils {
    public static final String TAG = "WechatUtils";

    @Retention(RetentionPolicy.SOURCE)
    public @interface WXShareStatus {
        int TYPE_TEXT = 0;          //文本
        int TYPE_IMG_RESOURCE = 1;  //app资源图片
        int TYPE_IMG_LOCAL_FILE = 2; //本地图片地址
        int TYPE_IMG_ULR = 3;       //网络图片地址
        int TYPE_MUSIC = 4;         //音乐地址
        int TYPE_VIDEO = 5;         //音乐地址
        int TYPE_WEB_PAGE = 6;      //web地址
        int TYPE_EMOJI = 7;         //发送表情
        int TYPE_FILE = 8;         //发送文件
    }
    private static final int THUMB_SIZE = 150;//默认图片大小

    private IWXAPI wxAPI;
    private static WechatUtils instance = null;
    private static final String appId = "wx4d8ebe2675fd55b0";
    private Context context;
    private WechatUtils(Context context) {
        this.context = context;
        initWXAPI();
    }

    public static interface OnShareListener {
        void OnSuccessListener();
        void OnFailListener();
    }

    public static WechatUtils getInstance(Context context) {
        if (instance == null) {
            synchronized (WechatUtils.class) {
                if (instance == null) {
                    instance = new WechatUtils(context);
                }
            }
        }
        return instance;
    }

    private void initWXAPI() {
        if (this.wxAPI == null) {
            this.wxAPI = WXAPIFactory.createWXAPI(context, appId, true);
            this.wxAPI.registerApp(appId);
            //后期可用广播注册
//            context.registerReceiver(new BroadcastReceiver() {
//                @Override
//                public void onReceive(Context context, Intent intent) {
//                    wxAPI.registerApp(appId);
//                }
//            },new IntentFilter(ConstantsAPI.ACTION_REFRESH_WXAPP));

        }

    }

    public IWXAPI getWXAPI() {
        return this.wxAPI;
    }

    /**
     * 判断微信是否注册
     * @return
     */
    private boolean isWxAppInstalled () {
        return getWXAPI().isWXAppInstalled();
    }

    /**
     * 发送支付请求
     * @param args
     * @return
     */
    public boolean sendPayRequest(java.util.Map<String, String> args) {
        if (args == null) return false;
        if (!isWxAppInstalled()) {
            Toast.makeText(context, "未安装微信", Toast.LENGTH_SHORT).show();
            return false;
        }
        IWXAPI api = this.getWXAPI();
        PayReq req = new PayReq();
        req.appId = appId;
        req.partnerId = args.get("partnerId");
        req.prepayId = args.get("prepayId");
        req.nonceStr = args.get("nonceStr");
        req.timeStamp = args.get("timeStamp");
        req.sign = args.get("sign");
        req.packageValue = "Sign=WXPay";
        return api.sendReq(req);
    }


    /**
     * 分享文本
     * @param text
     * @param isShareWithFriends
     * @param description
     */
    public void shareWXText(String text, boolean isShareWithFriends, String description, OnShareListener onShareListener) {
        shareWX(WXShareStatus.TYPE_TEXT,isShareWithFriends,text,0,"",description,onShareListener);
    }

    /**
     * 分享图片
     * @param status    WXShareStatus.TYPE_IMG_
     * @param isShareWithFriends
     * @param imgRes
     * @param imgUrl                本地图表地址或者网络图片地址
     */
    public void shareWXIMG(@IntRange(from = 1,to = 3) @WXShareStatus int status, boolean isShareWithFriends, @DrawableRes int imgRes, String imgUrl, OnShareListener onShareListener){
        shareWX(status,isShareWithFriends,"",imgRes,imgUrl,"",onShareListener);
    }

    /**
     * 分享音乐
     * @param isShareWithFriends
     * @param title
     * @param url       http://staff2.ustc.edu.cn/~wdw/softdown/index.asp/0042515_05.ANDY.mp3
     * @param description
     */
    public void shareWXMusic(boolean isShareWithFriends, String title, String url, String description, OnShareListener onShareListener) {
        shareWX(WXShareStatus.TYPE_MUSIC,isShareWithFriends,title,0,url,description,onShareListener);
    }

    /**
     * 分享视频
     * @param isShareWithFriends
     * @param title
     * @param url
     * @param description
     */
    public void shareWXVideo(boolean isShareWithFriends, String title, String url, String description, OnShareListener onShareListener){
        shareWX(WXShareStatus.TYPE_VIDEO,isShareWithFriends,title,0,url,description,onShareListener);
    }

    /**
     * 分享webpage
     * @param isShareWithFriends
     * @param title
     * @param url
     * @param description
     */
    public void shareWXWebPage(boolean isShareWithFriends, String title, @NonNull String url, String description, OnShareListener onShareListener){
        shareWX(WXShareStatus.TYPE_WEB_PAGE,isShareWithFriends,title,0,url,description,onShareListener);
    }

    /**
     * 分享文件
     * @param isShareWithFriends
     * @param title
     * @param url
     * @param description
     */
    public void shareWXFile(boolean isShareWithFriends, String title, @Nullable String url, String description, OnShareListener onShareListener){
        shareWX(WXShareStatus.TYPE_FILE,isShareWithFriends,title,0,url,description,onShareListener);
    }
    /**
     *
     * @param status
     * @param isShareWithFriends   是否分享朋友圈
     * @param text
     * @param imgRes
     */
    private void shareWX(@WXShareStatus int status, boolean isShareWithFriends, String text, @DrawableRes int imgRes, String url, String description, OnShareListener onShareListener) {
        if (!getWXAPI().isWXAppInstalled()) {
            Toast.makeText(context, "未安装微信", Toast.LENGTH_SHORT).show();
            return;
        }
        WXMediaMessage msg = new WXMediaMessage();
        String type = "";
        switch (status) {
            case WXShareStatus.TYPE_TEXT:
               WXTextObject textObject = new WXTextObject();
               textObject.text = text;
               msg.mediaObject = textObject;
               type = "text";
                break;
            case WXShareStatus.TYPE_IMG_RESOURCE:
                type = "img";
                Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), imgRes);
                WXImageObject wxImageObject = new WXImageObject(bmp);
                msg.mediaObject = wxImageObject;
                Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp,THUMB_SIZE,THUMB_SIZE,true);
                bmp.recycle();
                msg.thumbData = bmpToByteArray(thumbBmp,true);//设置缩略图
                break;
            case WXShareStatus.TYPE_IMG_LOCAL_FILE:
                type = "img";
                File file = new File(url);
                if (!file.exists()) {
                    Toast.makeText(context,"文件路径不存在", Toast.LENGTH_SHORT).show();
                    return;
                }
                WXImageObject wxImageLocal = new WXImageObject();
                wxImageLocal.setImagePath(url);
                msg.mediaObject = wxImageLocal;

                Bitmap bmpLocal = BitmapFactory.decodeFile(url);
                Bitmap thumbBmpLocal =  Bitmap.createScaledBitmap(bmpLocal, THUMB_SIZE, THUMB_SIZE, true);
                bmpLocal.recycle();
                msg.thumbData = bmpToByteArray(thumbBmpLocal,true);

                break;
            case WXShareStatus.TYPE_IMG_ULR:
                try {
                    type = "img";
                    WXImageObject wxImageUrl = new WXImageObject();
                    wxImageUrl.imagePath = url;//旧版本使用wxImageUrl.imageUrl = url;
                    msg.mediaObject = wxImageUrl;
                    Bitmap bmpUrl = BitmapFactory.decodeStream(new java.net.URL(url).openStream());
                    Bitmap thumbBmpUrl = Bitmap.createScaledBitmap(bmpUrl, THUMB_SIZE, THUMB_SIZE, true);
                    bmpUrl.recycle();
                    msg.thumbData = bmpToByteArray(thumbBmpUrl,true);
                } catch (IOException e) {
                    e.printStackTrace();
                    Toast.makeText(context,"图片地址解析失败", Toast.LENGTH_SHORT).show();
                    return;
                }
                break;
            case WXShareStatus.TYPE_MUSIC:
                type = "music";
                WXMusicObject music = new WXMusicObject();
                music.musicUrl = url;//如果是低带宽音乐使用 music.musicLowBandUrl
                msg.mediaObject = music;

                if (imgRes != 0) {//如果想要添加图片可以使用本地资源或者地址生成缩略图 地址图片缩略图同上
                    Bitmap thumb = BitmapFactory.decodeResource(context.getResources(),imgRes);
                    msg.thumbData = bmpToByteArray(thumb, true);
                }
                break;
            case WXShareStatus.TYPE_VIDEO:
                type = "video";
                WXVideoObject video = new WXVideoObject();
                video.videoUrl = url;//如果是低带宽视频使用video.videoLowBandUrl
                msg.mediaObject = video;

                if (imgRes != 0) {//如果想要添加图片可以使用本地资源或者地址生成缩略图 地址图片缩略图同上
                    Bitmap thumb = BitmapFactory.decodeResource(context.getResources(),imgRes);
                    msg.thumbData = bmpToByteArray(thumb, true);
                }
                break;
            case WXShareStatus.TYPE_WEB_PAGE:
                type = "webpage";
                WXWebpageObject webpageObject = new WXWebpageObject();
                webpageObject.webpageUrl = url;
                msg.mediaObject = webpageObject;
                if (imgRes != 0) {//如果想要添加图片可以使用本地资源或者地址生成缩略图 地址图片缩略图同上
                    Bitmap thumb = BitmapFactory.decodeResource(context.getResources(),imgRes);
                    msg.thumbData = bmpToByteArray(thumb, true);
                }
                break;
            case WXShareStatus.TYPE_EMOJI:
                type = "emoji";
                //地址未知 待开发
                final String EMOJI_FILE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/emoji.gif";
                final String EMOJI_FILE_THUMB_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/emojithumb.jpg";
                WXEmojiObject emoji = new WXEmojiObject();
                emoji.emojiPath = EMOJI_FILE_PATH; //发送二进制文件使用emoji.emojiData = readFromFile(EMOJI_FILE_PATH, 0, (int) new File(EMOJI_FILE_PATH).length());
                msg.mediaObject = emoji;
                msg.thumbData = readFromFile(EMOJI_FILE_THUMB_PATH, 0, (int) new File(EMOJI_FILE_THUMB_PATH).length());
                 break;
            case WXShareStatus.TYPE_FILE:
                 type = "appdata";
                WXAppExtendObject appdata = new WXAppExtendObject();
                if (!TextUtils.isEmpty(url)) {
                    appdata.fileData = readFromFile(url, 0, -1);
                    msg.setThumbImage(extractThumbNail(url, 150, 150, true));
                }
                appdata.extInfo = text;//"this is ext info"
                msg.mediaObject = appdata;


                 break;


        }
        msg.title = text;
        msg.description = description;
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction(type);  // transaction字段用于唯一标识一个请求
        req.message = msg;
        req.scene = isShareWithFriends ? SendMessageToWX.Req.WXSceneTimeline : SendMessageToWX.Req.WXSceneSession;
//        req.openId =
       boolean isSuccess = getWXAPI().sendReq(req);
       if (onShareListener != null) {
           if (isSuccess) {
               onShareListener.OnSuccessListener();
           } else {
               onShareListener.OnFailListener();
           }
       }
    }



    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
    private static final int MAX_DECODE_PICTURE_SIZE = 1920 * 1440;
    public static Bitmap extractThumbNail(final String path, final int height, final int width, final boolean crop) {
//        Assert.assertTrue(path != null && !path.equals("") && height > 0 && width > 0);
        if (TextUtils.isEmpty(path) || height <= 0 || width <= 0) return null;

        BitmapFactory.Options options = new BitmapFactory.Options();

        try {
            options.inJustDecodeBounds = true;
            Bitmap tmp = BitmapFactory.decodeFile(path, options);
            if (tmp != null) {
                tmp.recycle();
                tmp = null;
            }

            Log.d(TAG, "extractThumbNail: round=" + width + "x" + height + ", crop=" + crop);
            final double beY = options.outHeight * 1.0 / height;
            final double beX = options.outWidth * 1.0 / width;
            Log.d(TAG, "extractThumbNail: extract beX = " + beX + ", beY = " + beY);
            options.inSampleSize = (int) (crop ? (beY > beX ? beX : beY) : (beY < beX ? beX : beY));
            if (options.inSampleSize <= 1) {
                options.inSampleSize = 1;
            }

            // NOTE: out of memory error
            while (options.outHeight * options.outWidth / options.inSampleSize > MAX_DECODE_PICTURE_SIZE) {
                options.inSampleSize++;
            }

            int newHeight = height;
            int newWidth = width;
            if (crop) {
                if (beY > beX) {
                    newHeight = (int) (newWidth * 1.0 * options.outHeight / options.outWidth);
                } else {
                    newWidth = (int) (newHeight * 1.0 * options.outWidth / options.outHeight);
                }
            } else {
                if (beY < beX) {
                    newHeight = (int) (newWidth * 1.0 * options.outHeight / options.outWidth);
                } else {
                    newWidth = (int) (newHeight * 1.0 * options.outWidth / options.outHeight);
                }
            }

            options.inJustDecodeBounds = false;

            Log.i(TAG, "bitmap required size=" + newWidth + "x" + newHeight + ", orig=" + options.outWidth + "x" + options.outHeight + ", sample=" + options.inSampleSize);
            Bitmap bm = BitmapFactory.decodeFile(path, options);
            if (bm == null) {
                Log.e(TAG, "bitmap decode failed");
                return null;
            }

            Log.i(TAG, "bitmap decoded size=" + bm.getWidth() + "x" + bm.getHeight());
            final Bitmap scale = Bitmap.createScaledBitmap(bm, newWidth, newHeight, true);
            if (scale != null) {
                bm.recycle();
                bm = scale;
            }

            if (crop) {
                final Bitmap cropped = Bitmap.createBitmap(bm, (bm.getWidth() - width) >> 1, (bm.getHeight() - height) >> 1, width, height);
                if (cropped == null) {
                    return bm;
                }

                bm.recycle();
                bm = cropped;
                Log.i(TAG, "bitmap croped size=" + bm.getWidth() + "x" + bm.getHeight());
            }
            return bm;

        } catch (final OutOfMemoryError e) {
            Log.e(TAG, "decode bitmap failed: " + e.getMessage());
            options = null;
        }

        return null;
    }


    public static byte[] readFromFile(String fileName, int offset, int len) {
        if (fileName == null) {
            return null;
        }

        File file = new File(fileName);
        if (!file.exists()) {
            Log.i(TAG, "readFromFile: file not found");
            return null;
        }

        if (len == -1) {
            len = (int) file.length();
        }

        Log.d(TAG, "readFromFile : offset = " + offset + " len = " + len + " offset + len = " + (offset + len));

        if(offset <0){
            Log.e(TAG, "readFromFile invalid offset:" + offset);
            return null;
        }
        if(len <=0 ){
            Log.e(TAG, "readFromFile invalid len:" + len);
            return null;
        }
        if(offset + len > (int) file.length()){
            Log.e(TAG, "readFromFile invalid file len:" + file.length());
            return null;
        }

        byte[] b = null;
        try {
            RandomAccessFile in = new RandomAccessFile(fileName, "r");
            b = new byte[len]; // 创建合适文件大小的数组
            in.seek(offset);
            in.readFully(b);
            in.close();

        } catch (Exception e) {
            Log.e(TAG, "readFromFile : errMsg = " + e.getMessage());
            e.printStackTrace();
        }
        return b;
    }


    private String buildTransaction(String type) {
        return TextUtils.isEmpty(type) ? String.valueOf(System.currentTimeMillis()):type + System.currentTimeMillis();
    }



//    protected boolean sendAuthRequest(CordovaArgs args, CallbackContext callbackContext) {
//        IWXAPI api = this.getWXAPI();
//        com.tencent.mm.sdk.modelmsg.SendAuth.Req req = new com.tencent.mm.sdk.modelmsg.SendAuth.Req();
//
//        try {
//            req.scope = args.getString(0);
//            req.state = args.getString(1);
//        } catch (JSONException var6) {
//            Log.e("Cordova.Plugin.Wechat", var6.getMessage());
//            req.scope = "snsapi_userinfo";
//            req.state = "wechat";
//        }
//
//        if (api.sendReq(req)) {
//            Log.i("Cordova.Plugin.Wechat", "Auth request has been sent successfully.");
////            this.sendNoResultPluginResult(callbackContext);
//        } else {
//            Log.i("Cordova.Plugin.Wechat", "Auth request has been sent unsuccessfully.");
//            callbackContext.error("发送请求失败");
//        }
//
//        return true;
//    }





}
