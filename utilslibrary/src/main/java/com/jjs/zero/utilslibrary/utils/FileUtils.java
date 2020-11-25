package com.jjs.zero.utilslibrary.utils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Rect;
import android.graphics.pdf.PdfRenderer;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/11/25
 * @Details: <功能描述>
 *
 *    Android10 文件存储要进行分区
 *
 *    文件位置	                        所需权限	                    访问方法	                         卸载时是否删除文件
 *
 *  应用私有目录	                            无	                getExternalFilesDir()	                    是
 *
 * 媒体集合（照片、视频、音频）       	READ_EXTERNAL_STORAGE           MediaStore	                            否
 *                                 （仅当访问其他应用的文件时）
 * 下载内容（文档和电子书籍）	                无	                    存储访问框架（加载系统的文件选择器	        否
 *
 *
 */
public class FileUtils {

    /**
     * 获取图片保存路径
     * @param activity
     * @return
     */
    public static String getPhotoPath(Activity activity) {
        return  activity.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
                + File.separator + System.currentTimeMillis()+"uuid"+ UUID.randomUUID().toString() +".jpg";
    }

    /**
     * 保存图片到相册不会随着应用删除而删除
     * @param activity
     * @param file
     */
    public static void saveImage(Activity activity,File file) {
        //把文件插入到系统图库
        try {
            MediaStore.Images.Media.insertImage(activity.getContentResolver(),
                    file.getAbsolutePath(), file.getName(), null);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
//        ContentValues values = new ContentValues();
//        values.put(MediaStore.Images.Media.DATA, file.getAbsolutePath());
//        values.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
//        Uri uri = activity.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
//        activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri));

        // 最后通知图库更新
        activity.sendBroadcast(new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, Uri.parse(file.getAbsolutePath())));
    }

    /**
     * 保存资源文件中的图片到本地相册,实时刷新
     * @param activity
     * @param bmp
     */
    public static void saveImage(Activity activity, Bitmap bmp) {
        String path = getPhotoPath(activity);
        File file = new File(path);
        try {
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
       saveImage(activity,file);
    }

    /**
     * 下载图片
     * @param activity
     * @param inputStream
     * @return
     */
    public static File downloadImg(Activity activity,InputStream inputStream){
       return downloadFiles(activity,inputStream,getPhotoPath(activity),null);
    }

    /**
     * 下载文件
     * @param activity
     * @param inputStreams
     * @param fileLength
     * @param suffix  文件后缀 如 ".pdf"
     * @return
     */
    public static File downloadFile(Activity activity, InputStream inputStreams,String suffix,Long fileLength) {
        return downloadFiles(activity,inputStreams,getFilePath(activity)+suffix,fileLength);
    }

    private static File downloadFiles(Activity activity,InputStream inputStream,String path,Long fileLength) {
        FileOutputStream outputStream = null;
        File file = null;

        if (inputStream == null) return null;
        if (TextUtils.isEmpty(path)) return null;

        try {
            file = new File(path);
            outputStream = new FileOutputStream(file);
            byte[] bytes = new byte[2048];
            int len = 0;
            while ((len = inputStream.read(bytes)) != -1) {
                outputStream.write(bytes,0,len);
                //下载进度
//                long currentLen = file.length();
//                int progress = (int)(currentLen*100/fileLength);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        return file;
    }

    /**
     * 获取文件路径
     * @param activity
     * getFilesDir()是在data/data/包名/files下，手机是无法查看的
     * 如果想要查看就放在Download文件下 写入sd卡要添加权限判断
     *  Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
     * @return
     */
    public static String getFilePath(Activity activity) {
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        Date date = new Date(System.currentTimeMillis());
        String fileName = format.format(date);
        return  activity.getFilesDir() + File.separator + fileName;
    }



    /**
     * 删除单个文件
     * @param   filePath    被删除文件的文件名
     * @return 文件删除成功返回true，否则返回false
     */
    public static boolean deleteFile(String filePath) {
        File file = new File(filePath);
        if (file.isFile() && file.exists()) {
            return file.delete();
        }
        return false;
    }


    /**
     * pdf转图片
     * @param activity
     * @param pdfFile
     * @return
     */
    public static ArrayList<String> pdfToBitmap(Activity activity, File pdfFile) {
//        ArrayList<Bitmap> bitmaps = new ArrayList<>();
        ArrayList<String> list = new ArrayList<>();
        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                PdfRenderer renderer = new PdfRenderer(ParcelFileDescriptor.open(pdfFile, ParcelFileDescriptor.MODE_READ_ONLY));
                final int pageCount = renderer.getPageCount();
                Log.i("zero", "图片de 张数： " +pageCount);
                for (int i = 0; i < pageCount; i++) {
                    PdfRenderer.Page page = renderer.openPage(i);
                    int width = activity.getResources().getDisplayMetrics().densityDpi / 72 * page.getWidth();
                    int height = activity.getResources().getDisplayMetrics().densityDpi / 72 * page.getHeight();
                    Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
                    //todo 以下三行处理图片存储到本地出现黑屏的问题，这个涉及到背景问题
                    Canvas canvas = new Canvas(bitmap);
                    canvas.drawColor(Color.WHITE);
                    canvas.drawBitmap(bitmap, 0, 0, null);
                    Rect r = new Rect(0, 0, width, height);
                    page.render(bitmap, r, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY);
//                    bitmaps.add(bitmap);

                    File file = bitmapToFile(activity,bitmap);
                    list.add(file.getAbsolutePath());
                    bitmap.recycle();
                    // close the page
                    page.close();
                }
                // close the renderer
                renderer.close();
            }

        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return list;

    }

    /**
     * bitmap转File
     * @param activity
     * @param bitmap
     * @return
     */
    public static File bitmapToFile(Activity activity,Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
        File file = new File(getPhotoPath(activity));
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            InputStream is = new ByteArrayInputStream(baos.toByteArray());
            int x = 0;
            byte[] b = new byte[1024 * 100];
            while ((x = is.read(b)) != -1) {
                fos.write(b, 0, x);
            }
            fos.close();
            is.close();
            baos.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }
}
