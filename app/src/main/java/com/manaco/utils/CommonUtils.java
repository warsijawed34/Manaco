package com.manaco.utils;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.Snackbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.TextView;
import android.widget.Toast;

import com.manaco.R;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonUtils {

    public static AlertDialog dialog, dialog2;
    static DisplayMetrics displaymetrics;
    public static Dialog customDialog;
    public static TextView messageTv, buttonTv;

    public static void showAlertDialog(Context mContext, String title, String message) {

        if (mContext == null) return;
        if (dialog != null && dialog.isShowing()) return;

        dialog = new AlertDialog.Builder(mContext).create();
        dialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
        dialog.setTitle(title);
        dialog.setMessage(message);
        dialog.setButton(AlertDialog.BUTTON_POSITIVE, mContext.getString(R.string.ok), new OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

//    public static void showAlertDialogWith2Btn(Context mContext, String title, String message) {
//
//        if (mContext == null) return;
//        if (dialog2 != null && dialog2.isShowing()) return;
//
//        dialog2 = new AlertDialog.Builder(mContext).create();
//        dialog2.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
//        dialog2.setTitle(title);
//        dialog2.setMessage(message);
//        dialog2.setButton(AlertDialog.BUTTON_POSITIVE, mContext.getString(R.string.show), new OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//        dialog2.setButton(AlertDialog.BUTTON_NEGATIVE, mContext.getString(R.string.cancel), new OnClickListener() {
//
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                dialog.dismiss();
//            }
//        });
//
//        dialog2.show();
//    }

//    public static void showCustomDialog(Context context, String message, String button){
//        customDialog = new Dialog(context);
//        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        customDialog.setContentView(R.layout.custom_dialog);
//        messageTv = (TextView) customDialog.findViewById(R.id.tv_message);
//        buttonTv= (TextView) customDialog.findViewById(R.id.tv_button);
//
//        messageTv.setText(message);
//        buttonTv.setText(button);
//        customDialog.show();
  // }

/*

   public static void showCustomDialog(Context context, String message, String button){
       customDialog = new Dialog(context);
        customDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        customDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        customDialog.setContentView(R.layout.custom_dialog);
        messageTv = (TextView) customDialog.findViewById(R.id.tv_message);
        buttonTv= (TextView) customDialog.findViewById(R.id.tv_button);
        messageTv.setText(message);
        buttonTv.setText(button);
        customDialog.show();
     }*/
//show msg in Toast
    public static void showToast(Context mContext, String message) {
        Toast toast = Toast.makeText(mContext, message, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }
//show msg in Snackbar
    public static void showSnakBar(View view,String message)
    {
        final Snackbar snackbar = Snackbar
                .make(view, message, Snackbar.LENGTH_LONG)
                .setAction("Cancel", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {


                    }
                });
        snackbar.show();
    }



    public static boolean isValidEmailAddress(String emailAddress) {
        String emailRegEx;
        Pattern pattern;
        // Regex for a valid email address
        emailRegEx = "^[A-Za-z0-9._%+\\-]+@[A-Za-z0-9.\\-]+\\.[A-Za-z]{2,4}$";
        // Compare the regex with the email address
        pattern = Pattern.compile(emailRegEx);
        Matcher matcher = pattern.matcher(emailAddress);
        if (!matcher.find()) {
            return false;
        }
        return true;
    }

    public static boolean checkPassWordAndConfirmPassword(String password, String confirmPassword) {
        boolean pstatus = false;
        if (confirmPassword != null && password != null) {
            if (password.equals(confirmPassword)) {
                pstatus = true;
            }
        }
        return pstatus;
    }

    public static String getCalculatedDate(String dateFormat, int days) {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat s = new SimpleDateFormat(dateFormat);
        cal.add(Calendar.DAY_OF_YEAR, days);
        return s.format(new Date(cal.getTimeInMillis()));
    }

    public static String dateFormate(String dateTime){
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("dd MMMM");
        try{
            Date oneWayTripDate = input.parse(dateTime);
            dateTime = output.format(oneWayTripDate).toString();


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateTime;
    }

    public static String dateFormateMMM(String dateTime){
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("MMM dd");
        try{
            Date oneWayTripDate = input.parse(dateTime);
            dateTime = output.format(oneWayTripDate).toString();


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateTime;
    }

    public static String timeFormate(String timeformate) {
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("h a");
        try {
            Date oneWayTripDate = input.parse(timeformate);
            return output.format(oneWayTripDate);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
    public static String getOrdinalSuffix(int value)
    {
        int hunRem = value % 100;
        int tenRem = value % 10;

        if ( hunRem - tenRem == 10 )
        {
            return "th";
        }
        switch ( tenRem )
        {
            case 1:
                return "st";
            case 2:
                return "nd";
            case 3:
                return "rd";
            default:
                return "th";
        }
    }



    public static Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);

            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



    public static Bitmap createDrawableFromView(Context context, View view) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        view.setLayoutParams(new AppBarLayout.LayoutParams(AppBarLayout.LayoutParams.WRAP_CONTENT, AppBarLayout.LayoutParams.WRAP_CONTENT));
        view.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        view.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(view.getMeasuredWidth(), view.getMeasuredHeight(), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);

        return bitmap;

}
    public static Bitmap getRoundedCornerBitmap(Bitmap bitmap, int pixels) {
        Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap
                .getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(output);

        final int color = 0xff424242;
        final Paint paint = new Paint();
        final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
        final RectF rectF = new RectF(rect);
        final float roundPx = pixels;

        paint.setAntiAlias(true);
        canvas.drawARGB(0, 0, 0, 0);
        paint.setColor(color);
        canvas.drawRoundRect(rectF, roundPx, roundPx, paint);

        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
        canvas.drawBitmap(bitmap, rect, rect, paint);

        return output;
    }



    public static void hideKeyboard(Context ctx) {
        InputMethodManager inputManager = (InputMethodManager) ctx
                .getSystemService(Context.INPUT_METHOD_SERVICE);

        // check if no view has focus:
        View v = ((Activity) ctx).getCurrentFocus();
        if (v == null)
            return;

        inputManager.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

public static String checkStringValue(String value) {

    if (value == null || value.equals(null) || value.equals("null") || value.equals(""))
        value = "";
    return value;
}

    /**
     * convertUTF method used to encode string into UTF-8
     *
     * @param s
     * @return
     */
    public static String convertUTF(String s) {
        String data = "";
        try {
            data = URLEncoder.encode(s, "UTF-8");
            if (data.contains("%5C")) {
                data = data.replaceAll("%5C", "\\\\");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }

    public static boolean isAppInForeground(Context context) {
        List<ActivityManager.RunningTaskInfo> tasks =
                ((ActivityManager) context.getSystemService(
                        Context.ACTIVITY_SERVICE))
                        .getRunningTasks(1);
        if (tasks.isEmpty()) {
            return false;
        }
        return tasks
                .get(0)
                .topActivity
                .getPackageName()
                .equalsIgnoreCase(context.getPackageName());
    }

}