package com.example.xuxiaopeng002.myapplication.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;

import com.blankj.utilcode.util.AppUtils;
import com.blankj.utilcode.util.LogUtils;
import com.example.xuxiaopeng002.myapplication.app.MyApp;

import java.io.File;

public class UriTool {
    public static String getFilePathByUri(Context context, Uri uri) {
        String path = null;
        String scheme = uri.getScheme();
        // 以 file:// 开头的
        if (ContentResolver.SCHEME_FILE.equals(uri.getScheme())) {
            path = uri.getPath();
            return path;
        }
        // 4.4及之后的 是以 content:// 开头的，比如 content://com.android.providers.media.documents/document/image%3A235700
        if (ContentResolver.SCHEME_CONTENT.equals(uri.getScheme())) {
            if (DocumentsContract.isDocumentUri(context, uri)) {
                if (isExternalStorageDocument(uri)) {
                    // ExternalStorageProvider
                    final String docId = DocumentsContract.getDocumentId(uri);
                    final String[] split = docId.split(":");
                    final String type = split[0];
                    if ("primary".equalsIgnoreCase(type)) {
                        path = Environment.getExternalStorageDirectory() + "/" + split[1];
                        return path;
                    }
                } else if (isDownloadsDocument(uri)) {
                    // DownloadsProvider
                    final String id = DocumentsContract.getDocumentId(uri);
                    final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"),
                            Long.valueOf(id));
                    path = getDataColumn(context, contentUri, null, null);
                    return path;
                } else if (isMediaDocument(uri)) {
                    // MediaProvider
                    final String docId = DocumentsContract.getDocumentId(uri);
                    String id = docId.split(":")[1];
                    Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://media/external/file"), Long.valueOf(id));
                    path = getRealPath(context, contentUri, null);

//                    final String docId = DocumentsContract.getDocumentId(uri);
//                    final String[] split = docId.split(":");
//                    final String type = split[0];
//                    Uri contentUri = null;
//                    if ("image".equals(type)) {
//                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
//                    } else if ("video".equals(type)) {
//                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
//                    } else if ("audio".equals(type)) {
//                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
//                    }
//                    final String selection = "_id=?";
//                    final String[] selectionArgs = new String[]{split[1]};
//                    path = getDataColumn(context, contentUri, selection, selectionArgs);
                    return path;
                }
            } else if (ContentResolver.SCHEME_CONTENT.equals(scheme)) {
                //微信文件打开的uri
                path = uri.getPath();
                LogUtils.e("path = "+path);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N
                        && path != null && path.startsWith("/external_storage_root")) {
                    return new File(Environment.getExternalStorageDirectory().getAbsolutePath()
                            + path.replace("/external_storage_root", "")).getPath();
                } else if (path.startsWith("/external/")){
                    return  getRealPath(context, uri, null);
                }else {
                    String[] paths = uri.getPath().split("/0/");
                    if (paths.length == 2) {
                        return Environment.getExternalStorageDirectory() + "/" + paths[1];
                    }
                }
            } else {
                String[] paths = uri.getPath().split("/0/");
                if (paths.length == 2) {
                    return Environment.getExternalStorageDirectory() + "/" + paths[1];
                }

            }
        }
        return null;
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


    public static Uri PathToUri(String path){
        Uri uri;
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            uri = Uri.fromFile(new File(path));
        } else {
            /**
             * 7.0 调用系统相机拍照不再允许使用Uri方式，应该替换为FileProvider
             * 并且这样可以解决MIUI系统上拍照返回size为0的情况
             */
            uri = FileProvider.getUriForFile(MyApp.INSTANCE,  AppUtils.getAppPackageName() + ".fileprovider", new File(path));
        }

        return uri;
    }

    private static String getRealPath(Context context, Uri uri, String selection) {

        String path = null;
        // 通过Uri和selection来获取真实的图片路径
        Cursor cursor = context.getContentResolver().query(uri, null, selection, null, null);
        if (cursor != null && cursor.moveToFirst()) {
            path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
            cursor.close();
        }else {
            path = uri.getPath();
        }
        return path;
    }


    //根据文件后缀获取补充的MIMETYPE
    public static String getExTraMiMeType(String type) {
        String result = "";
        switch (type) {
            case "7z":
                result = "application/application/x-7z-compressed";
                break;
            case "zip":
                result = "application/zip";
                break;
            case "z":
                result = "application/x-compress";
                break;
            case "xla":
            case "xlc":
            case "xlm":
            case "xls":
            case "xlt":
            case "xlw":
                result = "application/vnd.ms-excel";
                break;
            case "xlsx":
                result = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
                break;
            case "xml":
                result = "text/xml";
                break;
            case "ws":
                result = "text/vnd.wap.wmlscript";
                break;
            case "wrz":
            case "wrl":
                result = "x-world/x-vrml";
                break;
            case "wpt":
                result = "x-lml/x-gps";
                break;
            case "wps":
                result = "application/vnd.ms-works";
                break;
            case "wpng":
                result = "image/x-up-wpng";
                break;
            case "wmv":
                result = "audio/x-ms-wmv";
                break;
            case "wma":
                result = "audio/x-ms-wma";
                break;
            case "web":
                result = "application/vnd.xara";
                break;
            case "wbmp":
                result = "image/vnd.wap.wbmp";
                break;
            case "wav":
                result = "audio/x-wav";
                break;
            case "txt":
                result = "text/plain";
                break;
            case "ttf":
                result = "application/octet-stream";
                break;
            case "tif":
            case "tiff":
                result = "image/tiff";
                break;
            case "tgz":
            case "taz":
            case "tar":
                result = "application/x-tar";
                break;
            case "rmvb":
                result = "audio/x-pn-realaudio";
                break;
            case "rm":
                result = "audio/x-pn-realaudio";
                break;
            case "rar":
                result = "application/x-rar-compressed";
                break;
            case "pptx":
                result = "application/vnd.openxmlformats-officedocument.presentationml.presentation";
                break;
            case "ppt":
            case "pps":
                result = "application/vnd.ms-powerpoint";
                break;
            case "ppsx":
                result = "application/vnd.openxmlformats-officedocument.presentationml.slideshow";
                break;
            case "png":
                result = "image/png";
                break;
            case "pdf":
                result = "application/pdf";
                break;
            case "mpg4":
            case "mp4":
                result = "video/mp4";
                break;
            case "mpg":
            case "mpeg":
            case "mpe":
                result = "video/mpeg";
                break;
            case "mp3":
            case "mp2":
                result = "audio/x-mpeg";
                break;
            case "m4v":
                result = "video/x-m4v";
                break;
            case "m4p":
            case "m4b":
            case "m4a":
                result = "audio/mp4a-latm";
                break;
            case "m3u":
            case "m3url":
                result = "audio/x-mpegurl";
                break;
            case "jpz":
            case "jpg":
            case "jpeg":
            case "jpe":
                result = "image/jpeg";
                break;
            case "html":
            case "htm":
            case "hts":
                result = "text/html";
                break;
            case "gz":
                result = "application/x-gzip";
                break;
            case "gtar":
                result = "application/x-gtar";
                break;
            case "exe":
                result = "application/octet-stream";
                break;
            case "docx":
                result= "application/vnd.openxmlformats-officedocument.wordprocessingml.document";
                break;
            case "doc":
                result = "application/msword";
                break;
            case "avi":
                result = "video/x-msvideo";
                break;
            case "apk":
                result = "application/vnd.android.package-archive";
                break;
            case "3gp":
                result = "video/3gpp";
                break;
            case "bmp":
                result = "image/bmp";
                break;
        }

        return result;
    }



}
