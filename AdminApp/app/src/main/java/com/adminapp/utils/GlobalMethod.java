package com.adminapp.utils;

import android.app.Activity;
import android.app.Dialog;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.IBinder;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.adminapp.R;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.net.URL;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GlobalMethod extends Activity {

    static Toast toast;
    Dialog dialog;

    /***
     * this method will use to clear all notification from mobile if user has logout from app.
     * @param activity we need to pass context when you are calling.
     */
    static public void cancel_All_Notification(Activity activity) {
        try {
            NotificationManager notifManager= (NotificationManager) activity.getSystemService(Context.NOTIFICATION_SERVICE);
            notifManager.cancelAll();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public static int pixelToDp(int px, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }

    public static int dpToPx(int dp, Context context) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int px = Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return px;
    }



    public static void showToast(Activity ctx, String msg) {
//        showCustomToastInCente/r(ctx, msg);
    }




    public static boolean isPackageInstalled(Context c, String targetPackage) {
        PackageManager pm = c.getPackageManager();
        try {
            PackageInfo info = pm.getPackageInfo(targetPackage, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
        return true;
    }



    public static String convertDate(String toconvert) {
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date d = sdf.parse(toconvert);
            toconvert = dateFormat.format(d);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return toconvert;
    }




    /**
     * to hide default keyboard if open .
     *
     * @param activity we have to pass only activity.
     */
    public static void hideSoftKeyboard(Activity activity) {
        try {
            if (activity != null) {
                InputMethodManager inputMethodManager = (InputMethodManager) activity
                        .getSystemService(Activity.INPUT_METHOD_SERVICE);
                View v = activity.getCurrentFocus();
                if (v != null) {
                    IBinder binder = activity.getCurrentFocus()
                            .getWindowToken();
                    if (binder != null) {
                        inputMethodManager.hideSoftInputFromWindow(binder, 0);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
/*
class description : this class is for setting fonts to the sign up screen.
 */
    }



    public static void write(String value) {
        System.out.println(value);
    }


    public static String encodeURL(String urlStr) {

        URL url = null;
        try {
            System.out.println("url==" + urlStr);
            if (urlStr != null) {
                if (urlStr.length() > 4) {
                    if (urlStr.startsWith("http") || urlStr.contains("http://")) {
                    } else {
                        urlStr = "http://" + urlStr;
                    }
                    url = new URL(urlStr);
                    return url.toString().replaceAll("&amp;", "&");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return urlStr;
    }


    static Bitmap photoBitmap;

    public static Bitmap resizeBitmap(String path, Activity activity) {
        try {
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inJustDecodeBounds = true;
            ExifInterface exif = new ExifInterface(path);
            int orientation = exif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);

            Matrix matrix = new Matrix();
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    matrix.postRotate(90);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    matrix.postRotate(180);
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    matrix.postRotate(270);
                    break;
                default:
                    break;
            }

            int outWidth, outHeight;
            if (path != null) {
                BitmapFactory.decodeFile(path, options);
                outWidth = options.outWidth;
            } else {
                if (photoBitmap != null) {
                    outWidth = photoBitmap.getWidth();
                } else {
                    return null;
                }
            }
            if (path != null) {
                options.inJustDecodeBounds = false;
                options.inSampleSize = 0;
                photoBitmap = BitmapFactory.decodeFile(path, options);
                if (photoBitmap != null)
                    photoBitmap = Bitmap.createBitmap(photoBitmap, 0, 0,
                            photoBitmap.getWidth(), photoBitmap.getHeight(), matrix, true);
                return photoBitmap;
            } else {
                if (photoBitmap != null)
                    photoBitmap = Bitmap.createBitmap(photoBitmap, 0, 0, outWidth, outWidth, matrix, true);
                return photoBitmap;
            }
        } catch (OutOfMemoryError e) {
            GlobalMethod.showToast(activity, "Retry");
            e.printStackTrace();

        } catch (Exception e) {
            GlobalMethod.showToast(activity, "Image is wrong, Please select another image");
            e.printStackTrace();
        }
        return null;
    }

    // CALL THIS METHOD TO GET THE URI FROM THE BITMAP
    public static Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 80, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    // CALL THIS METHOD TO GET THE ACTUAL PATH
    public static String getRealPathFromURI(Uri uri, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }


    /**
     * get time Difference
     */
    public static String getDiff(String serverTime) {
        long diferent = 0, differenceLong, calculateTime;
        String timeAgo = "";
        try {
            Date now = new Date();
            String localTime = new SimpleDateFormat("yyyy-MM-dd hh:mm a").format(now);
            write("serverTime====" + serverTime + "===" + localTime);
            SimpleDateFormat parseFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm a");
            Date serverTimeDate = parseFormat.parse(serverTime);
            Date localTimeDate = parseFormat.parse(localTime);
            write("start_time==DATE==" + serverTimeDate + "===" + localTimeDate);
            diferent = localTimeDate.getTime() - serverTimeDate.getTime();
            differenceLong = TimeUnit.MILLISECONDS.toSeconds(diferent);
            write("====different" + diferent + "==seconds==" + differenceLong);
            timeAgo = timeDifference(differenceLong, serverTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return timeAgo;
    }

    private static String timeDifference(long differenceLong, String serverTime) {
        String timeAgo = "";
        long calculateTime;
        if (differenceLong < 60) {
            if (differenceLong == 1 || differenceLong == 0)
                timeAgo = "few seconds ago";
            else
                timeAgo = "few seconds ago";
        } else if (differenceLong >= 60 && differenceLong < 3600) {
            calculateTime = differenceLong / 60;
            write("roundValue===minute" + calculateTime);
            if (calculateTime == 1L) {
                timeAgo = 1 + " minute ago";
            } else {
                timeAgo = calculateTime + " minutes ago";
            }
        } else if (differenceLong >= 3600L && differenceLong <= 86400L) {
            calculateTime = differenceLong / (60 * 60);
            write("roundValue===hour" + calculateTime);
            if (calculateTime == 1)
                timeAgo = calculateTime + " hour ago";
            else
                timeAgo = calculateTime + " hours ago";
        } else {
            timeAgo = serverTime;
        }
        return timeAgo;
    }

    /*
    method for email validation
     */

    public static boolean isValidEmail(String target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target.trim()).matches());
    }


    public static boolean isValidUsername(String target) {
        return (!TextUtils.isEmpty(target) && target.trim().length()>=4);
    }

    public static boolean isValidName(String target) {
        return (!TextUtils.isEmpty(target) && target.trim().length()>=3);
    }

    public static boolean isValidphone(String target) {
        return (!TextUtils.isEmpty(target) && target.trim().length()>=5);
    }

    public static boolean isValidCountry(String target) {
        return (!TextUtils.isEmpty(target) && !target.trim().equalsIgnoreCase("Select Country"));
    }


      /*
    method for password validation
     */

    public static boolean isValidPassword(String target) {
        return (!TextUtils.isEmpty(target) && target.trim().length()>=6);
    }


//    public void imgShow(String message, Context mContext) {
//        // //Log("path", message);
//
//        if (dialog != null) {
//            dialog.dismiss();
//        }
//        dialog = new Dialog(mContext, R.style.CustomDialogTheme);
//        ImageView Img;
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,
//                ViewGroup.LayoutParams.WRAP_CONTENT);
//        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND,
//                WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//        dialog.setContentView(R.layout.dialog_img_view);
//        Img = (ImageView) dialog.findViewById(R.id.img);
//        dialog.setCancelable(true);
//        // Img.setImageDrawable(Drawable.createFromPath(message));
//
//        Button close_btn = (Button) dialog.findViewById(R.id.btn_close);
//        close_btn.setOnClickListener(new View.OnClickListener() {
//            public void onClick(View v) {
//                dialog.dismiss();
//
//            }
//        });
//
//        try {
//            Bitmap bmp = BitmapFactory.decodeFile(message);
//            Img.setImageBitmap(bmp);
//            dialog.show();
//        } catch (Exception e) {
//            if (dialog != null) {
//                dialog.dismiss();
//            }
//        }
//    }

    public void PDFdisplay(String path, Activity mContext) {
        File file = new File(path);
        try {
            if (file.isFile()) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri uri = Uri.fromFile(file);
                intent.setDataAndType(uri, "application/pdf");

                startActivity(intent);
            }
        } catch (Exception e) {
            GlobalMethod.showToast(mContext,"Please install the PDF reader");

        }
    }

    public void TIFdisplay(String path, Activity mContext) {
        File file = new File(path);
        try {
            if (file.isFile()) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri uri = Uri.fromFile(file);
                intent.setDataAndType(uri, "application/tif");
                startActivity(intent);
            }
        } catch (Exception e) {
            GlobalMethod.showToast(mContext,"Please install the tif reader");

        }
    }

    public void DOCdisplay(String path, Activity mContext) {
        File file = new File(path);
        try {
            if (file.isFile()) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                Uri uri = Uri.fromFile(file);
                intent.setDataAndType(uri, "text/*");

                startActivity(intent);
            }
        } catch (Exception e) {
            GlobalMethod.showToast(mContext,"Please install the DOC reader");

        }
    }



    public static String parseYoutubeVideoId(String youtubeUrl)
    {
        String video_id = null;
        if (youtubeUrl != null && youtubeUrl.trim().length() > 0 &&
                youtubeUrl.startsWith("http"))
        {
            // ^.*((youtu.be\/)|(v\/)|(\/u\/\w\/)|(embed\/)|(watch\?))\??v?=?([^#\&\?]*).*/
            String expression = "^.*((youtu.be" + "\\/)"
                    + "|(v\\/)|(\\/u\\/w\\/)|(embed\\/)|(watch\\?))\\??v?=?([^#\\&\\?]*).*";
            CharSequence input = youtubeUrl;
            Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(input);
            if (matcher.matches())
            {
                // Regular expression some how doesn't work with id with "v" at
                // prefix
                String groupIndex1 = matcher.group(7);
                if (groupIndex1 != null && groupIndex1.length() == 11)
                    video_id = groupIndex1;
                else if (groupIndex1 != null && groupIndex1.length() == 10)
                    video_id = "v" + groupIndex1;
            }
        }
        return video_id;
    }



    public static String getYouTubeVideoId(String video_url) {

        if (video_url != null && video_url.length() > 0) {

            Uri video_uri = Uri.parse(video_url);
            String video_id = video_uri.getQueryParameter("v");

            if (video_id == null)
                video_id = parseYoutubeVideoId(video_url);

            return video_id;
        }
        return null;
    }


    public static String getMonthForInt(int m) {
        String month = "invalid";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (m >= 0 && m <= 11 ) {
            month = months[m];
        }
        return month;
    }



}
