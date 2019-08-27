package com.rowdy.common_methods.methods;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Point;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.Build;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.DatePicker;
import android.widget.FrameLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.rowdy.common_methods.R;
import com.rowdy.common_methods.common.Constance;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ConstanceMethods {

    public static ProgressDialog progressDialog;
    public static String selectedDate = "";
    public static String selectedTime = "";

    public static boolean isValidMobile(String mobileNumber) {
        return !TextUtils.isEmpty(mobileNumber) && Patterns.PHONE.matcher(mobileNumber).matches();
    }


    private static String uniqueID = null;
    private static final String PREF_UNIQUE_ID = "PREF_UNIQUE_ID";

    public synchronized static String getUUID(Context context) {
        if (uniqueID == null) {
            SharedPreferences sharedPrefs = context.getSharedPreferences(
                    PREF_UNIQUE_ID, Context.MODE_PRIVATE);
            uniqueID = sharedPrefs.getString(PREF_UNIQUE_ID, null);
            if (uniqueID == null) {
                uniqueID = UUID.randomUUID().toString();
            }
        }
        return uniqueID;
    }


    public static boolean isValidPassword(final String password) {
        Pattern pattern;
        Matcher matcher;
        final String PASSWORD_PATTERN = "^(?=.*[0-9])(?=.*[A-Z])(?=.*[@#$%^&+=!])(?=\\S+$).{4,}$";
        pattern = Pattern.compile(PASSWORD_PATTERN);
        matcher = pattern.matcher(password);

        return matcher.matches();

    }


    public static void hideKeyboard(Context context, View view) {
        try {
            InputMethodManager inputManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
            inputManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        } catch (Exception e) {
            e.toString();
        }
    }

    public static Bitmap StringToBitMap(String encodedString) {
        try {
            byte[] encodeByte = Base64.decode(encodedString, Base64.DEFAULT);
            Bitmap bitmap = BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager objConnectivityManager;
        try {
            objConnectivityManager = (ConnectivityManager) context.getSystemService(Activity.CONNECTIVITY_SERVICE);
            final boolean IsWifiAvailable = objConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected();
            //final boolean IsInternetAvailable = objConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected();
            boolean IsInternetAvailable = false;
            if (objConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE) != null)
                IsInternetAvailable = objConnectivityManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected();
            if (IsWifiAvailable == true || IsInternetAvailable == true)
                return true;
            else
                return false;
        } catch (Exception e) {
            return false;
        }
    }


    public static boolean isValidEmail(String email) {
        Pattern pattern;
        Matcher matcher;
        final String EMAIL_PATTERN = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
        pattern = Pattern.compile(EMAIL_PATTERN);
        matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public static void showProgressDialog(final Activity context, final String title, final String message) {
        context.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                progressDialog = ProgressDialog.show(context, title, message);
            }
        });
    }

    public static void hideProgressDialog(Activity activity) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
            }
        });
    }

    /**
     * This function will change the progress dialog message runtime.
     *
     * @param message which needs to be shown
     */
    public static void changeProgressDialogMessage(Activity activity, final String message) {
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressDialog != null) {
                    progressDialog.setMessage(message);
                }
            }
        });
    }

	/*public void DownloadFromUrl(String link) {
        try {
			File file2 = new File(link);
			URL url = new URL(link); //you can write here any link
			File file = new File(file2.getName());
			URLConnection ucon = url.openConnection();
			InputStream is = ucon.getInputStream();
			BufferedInputStream bis = new BufferedInputStream(is);
			ByteArrayBuffer baf = new ByteArrayBuffer(50);
			int current = 0;
			while ((current = bis.read()) != -1) {
				baf.append((byte) current);
			}

			FileOutputStream fos = new FileOutputStream(file);
			fos.write(baf.toByteArray());
			fos.close();
		} catch (IOException e) {
			e.toString();
		}
	}*/

    public static void saveStringPreferences(Context context, String strKey, String strValue) {
        try {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString(strKey, strValue);
            editor.commit();
        } catch (Exception e) {
            e.toString();
        }
    }

    public static void saveIntPreferences(Context context, String strKey, int intValue) {
        try {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putInt(strKey, intValue);
            editor.commit();
        } catch (Exception e) {
            e.toString();
        }
    }

    public static void saveBooleanPreferences(Context context, String strKey, boolean boolValue) {
        try {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean(strKey, boolValue);
            editor.commit();
        } catch (Exception e) {
            e.toString();
        }
    }

    public static Object getPreferences(Context context, String key, int preferenceDataType) {
        Object value = null;
        try {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
            if (sharedPreferences.contains(key)) {
                switch (preferenceDataType) {
                    case Constance.PREF_TYPE_BOOLEAN:
                        value = sharedPreferences.getBoolean(key, false);
                        break;
                    case Constance.PREF_TYPE_INT:
                        value = sharedPreferences.getInt(key, 0);
                        break;
                    case Constance.PREF_TYPE_STRING:
                        value = sharedPreferences.getString(key, "");
                        break;
                }
            }
        } catch (Exception e) {
            e.toString();
            return null;
        }
        return value;
    }

    /**
     * This function will open AlertDialog. On click of OK button AlertDialog will simply get dismiss.
     *
     * @param context application context
     * @param title   of AlertDialog
     * @param message of AlertDialog
     */
    public static void showAlertDialogWithDismiss(Context context, String title, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        builder.show();
    }

    public static String getCurrentDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "d-M-yyyy", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getCurrentDateYYMMDD() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-M-d", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getCurrentTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "HH:mm", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static int isDeliveryHours(int h) {
        Calendar rightNow = Calendar.getInstance();
        int currentHour = rightNow.get(Calendar.HOUR_OF_DAY);
        Log.e("", "Current Hour In Common : " + currentHour);
        if (currentHour > h) {
            return 1;
        } else {
            return 0;
        }
    }

    public static String showCalenderDialog(Context context) {
        int mYear, mMonth, mDay;
        Calendar c = Calendar.getInstance();
        mYear = c.get(Calendar.YEAR);
        mMonth = c.get(Calendar.MONTH);
        mDay = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year,
                                          int monthOfYear, int dayOfMonth) {
                        selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                    }


                }, mYear, mMonth, mDay);
        datePickerDialog.show();

        return selectedDate;
    }

    public static String showTimerDialog(Context context) {
        int mHour, mMinute;
        // Get Current Time
        final Calendar c = Calendar.getInstance();
        mHour = c.get(Calendar.HOUR_OF_DAY);
        mMinute = c.get(Calendar.MINUTE);

        // Launch Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(context,
                new TimePickerDialog.OnTimeSetListener() {

                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay,
                                          int minute) {

                        selectedTime = hourOfDay + ":" + minute;
                    }
                }, mHour, mMinute, false);
        timePickerDialog.show();
        return selectedTime;

    }

    public static void toastMessage(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getAudioRealPathFromUri(Context context, Uri uri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Audio.Media.DATA};
            cursor = context.getContentResolver().query(uri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    public static String getRealVideoPathFromUri(Context context, Uri uri) {

        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Video.Media.DATA};
            cursor = context.getContentResolver().query(uri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static String getRealPathFromUri(Context context, Uri uri) {
        Cursor cursor = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};
            cursor = context.getContentResolver().query(uri, proj, null, null, null);
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public static File getPathWithoutFilename(File file) {
        if (file != null) {
            if (file.isDirectory()) {
                // no file to be split off. Return everything
                return file;
            } else {
                String filename = file.getName();
                String filepath = file.getAbsolutePath();

                // Construct path without file name.
                String pathwithoutname = filepath.substring(0,
                        filepath.length() - filename.length());
                if (pathwithoutname.endsWith("/")) {
                    pathwithoutname = pathwithoutname.substring(0, pathwithoutname.length() - 1);
                }
                return new File(pathwithoutname);
            }
        }
        return null;
    }


    public static void showSnackBar(View parentLayout, String message, Context context) {
        Snackbar snack = Snackbar.make(parentLayout, message, Snackbar.LENGTH_LONG);
        View view = snack.getView();
        FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
        params.gravity = Gravity.TOP;
        view.setLayoutParams(params);
        snack.show();

    }

    public static void hideKeyboard(Activity activity) {
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    public static Animation getAnimationBottomToTop(Context context) {
        Animation animBottomToTop = AnimationUtils.loadAnimation(context, R.anim.anime_bottom_to_top);
        return animBottomToTop;
    }

    public static Animation getAnimationTopToBottom(Context context) {
        Animation animTopToBottom = AnimationUtils.loadAnimation(context, R.anim.anime_top_to_bottom);
        return animTopToBottom;
    }

    public static Animation getAnimationRightToLeft(Context context) {
        Animation animRightToLeft = AnimationUtils.loadAnimation(context, R.anim.anime_right_to_left);
        return animRightToLeft;
    }

    public static Animation getAnimationLeftToRight(Context context) {
        Animation animLeftToRight = AnimationUtils.loadAnimation(context, R.anim.anime_left_to_right);
        return animLeftToRight;
    }

    public static Animation getBlinkingAnimation() {
        Animation animation = new AlphaAnimation(1, 0); //to change visibility from visible to invisible
        animation.setDuration(1000); //1 second duration for each animation cycle
        animation.setInterpolator(new LinearInterpolator());
        animation.setRepeatCount(Animation.INFINITE); //repeating indefinitely
        animation.setRepeatMode(Animation.REVERSE); //animation will start from end point once ended.
        return animation;
    }


    /*public static BitmapDescriptor bitmapDescriptorFromVector(Context context, @DrawableRes int vectorDrawableResourceId) {
        Drawable background = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        background.setBounds(0, 0, background.getIntrinsicWidth(), background.getIntrinsicHeight());
        Drawable vectorDrawable = ContextCompat.getDrawable(context, vectorDrawableResourceId);
        vectorDrawable.setBounds(40, 20, vectorDrawable.getIntrinsicWidth() + 80, vectorDrawable.getIntrinsicHeight() + 80);
        Bitmap bitmap = Bitmap.createBitmap(background.getIntrinsicWidth(), background.getIntrinsicHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        background.draw(canvas);
        vectorDrawable.draw(canvas);
        return BitmapDescriptorFactory.fromBitmap(bitmap);
    }
*/


    public static int getCurrentDay() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DATE);
        return day;
    }

    public static int getCurrentMonth() {
        Calendar calendar = Calendar.getInstance();
        int month = calendar.get(Calendar.MONTH) + 1;
        return month;
    }

    public static int getCurrentYear() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        return year;
    }

    public static String getLastWeek() {
        Calendar mCalendar = Calendar.getInstance();

        // Monday
        mCalendar.add(Calendar.DAY_OF_YEAR, -13);
        Date mDateMonday = mCalendar.getTime();

        // Sunday
        mCalendar.add(Calendar.DAY_OF_YEAR, 6);
        Date mDateSunday = mCalendar.getTime();

        // Date format
        String strDateFormat = "dd MMM";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);

        String MONDAY = sdf.format(mDateMonday);
        String SUNDAY = sdf.format(mDateSunday);

        // Substring
        if ((MONDAY.substring(3, 6)).equals(SUNDAY.substring(3, 6))) {
            MONDAY = MONDAY.substring(0, 2);
        }

        return MONDAY + " - " + SUNDAY;
    }

    public static String getCurrentWeek() {
        Calendar mCalendar = Calendar.getInstance();

        Date date = new Date();
        mCalendar.setTime(date);

        // 1 = Sunday, 2 = Monday, etc.
        int day_of_week = mCalendar.get(Calendar.DAY_OF_WEEK);

        int monday_offset;
        if (day_of_week == 1) {
            monday_offset = -6;
        } else
            monday_offset = (2 - day_of_week); // need to minus back
        mCalendar.add(Calendar.DAY_OF_YEAR, monday_offset);

        Date mDateMonday = mCalendar.getTime();

        // return 6 the next days of current day (object cal save current day)
        mCalendar.add(Calendar.DAY_OF_YEAR, 6);
        Date mDateSunday = mCalendar.getTime();

        //Get format date
        String strDateFormat = "dd MMM";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);

        String MONDAY = sdf.format(mDateMonday);
        String SUNDAY = sdf.format(mDateSunday);

        // Sub String
        if ((MONDAY.substring(3, 6)).equals(SUNDAY.substring(3, 6))) {
            MONDAY = MONDAY.substring(0, 2);
        }

        return MONDAY + " - " + SUNDAY;
    }

    public static String getNextWeek() {
        Calendar mCalendar = Calendar.getInstance();

        // Monday
        mCalendar.add(Calendar.DAY_OF_YEAR, 1);
        Date mDateMonday = mCalendar.getTime();

        // Sunday
        mCalendar.add(Calendar.DAY_OF_YEAR, 6);
        Date Week_Sunday_Date = mCalendar.getTime();

        // Date format
        String strDateFormat = "dd MMM";
        SimpleDateFormat sdf = new SimpleDateFormat(strDateFormat);

        String MONDAY = sdf.format(mDateMonday);
        String SUNDAY = sdf.format(Week_Sunday_Date);

        // Sub string
        if ((MONDAY.substring(3, 6)).equals(SUNDAY.substring(3, 6))) {
            MONDAY = MONDAY.substring(0, 2);
        }

        return MONDAY + " - " + SUNDAY;
    }


    public static String getCurrentWeekDate(int week) {
        Calendar c = GregorianCalendar.getInstance();
        c.setFirstDayOfWeek(Calendar.MONDAY);
        c.set(Calendar.DAY_OF_WEEK, c.getFirstDayOfWeek());
        c.add(Calendar.DAY_OF_WEEK, week);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        String startDate;
        String endDate;
        startDate = df.format(c.getTime());
        c.add(Calendar.DAY_OF_MONTH, 6);
        endDate = df.format(c.getTime());
//        System.out.println("Start Date = " + startDate);
//        System.out.println("End Date = " + endDate);
        Log.e("Start Date+", startDate);
        Log.e("End Date ", endDate);

        return startDate + "" + endDate;
    }

    public static String[] getCurrentWeek1() {
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.SUNDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return getNextWeek1();
    }

    public static String[] getNextWeek1() {
        Calendar calendar = Calendar.getInstance();
        DateFormat format = new SimpleDateFormat("M-dd");
        String[] days = new String[7];
        for (int i = 0; i < 7; i++) {
            days[i] = format.format(calendar.getTime());
            calendar.add(Calendar.DATE, 1);
        }
        return days;
    }

    public static String[] getPreviousWeek1() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, -14);
        return getNextWeek1();
    }


    public static ArrayList<String> getDayOfMonth() {
        DateFormat format = new SimpleDateFormat("MM/dd/yyyy");
        Calendar calendar = Calendar.getInstance();
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        ArrayList<String> weekDays = new ArrayList<>();
        for (int i = 0; i < 7; i++) {
            SimpleDateFormat sdf = new SimpleDateFormat("EEEE");
            Date d = new Date();
            String dayOfTheWeek = sdf.format(d);
            weekDays.add(dayOfTheWeek);
            weekDays.add(format.format(calendar.getTime()));
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return weekDays;
    }


    /*public static Bitmap createCustomMarker(Context context, String url, String _name) {

        View marker = ((LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE)).inflate(R.layout.custom_marker_layout, null);

        CircleImageView markerImage = (CircleImageView) marker.findViewById(R.id.user_dp);
        markerImage.setImageURI(Uri.parse(url));
        TextView txt_name = (TextView) marker.findViewById(R.id.name);
        txt_name.setText(_name);

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        marker.setLayoutParams(new ViewGroup.LayoutParams(52, ViewGroup.LayoutParams.WRAP_CONTENT));
        marker.measure(displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.layout(0, 0, displayMetrics.widthPixels, displayMetrics.heightPixels);
        marker.buildDrawingCache();
        Bitmap bitmap = Bitmap.createBitmap(marker.getMeasuredWidth(), marker.getMeasuredHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        marker.draw(canvas);

        return bitmap;
    }

*/

    public static String getCompleteAddressString(Context context, double LATITUDE, double LONGITUDE) {
        String strAdd = "";
        Geocoder geocoder = new Geocoder(context, Locale.getDefault());
        try {
            List<Address> addresses = geocoder.getFromLocation(LATITUDE, LONGITUDE, 1);
            if (addresses != null) {
                Address returnedAddress = addresses.get(0);
                StringBuilder strReturnedAddress = new StringBuilder("");

                for (int i = 0; i <= returnedAddress.getMaxAddressLineIndex(); i++) {
                    strReturnedAddress.append(returnedAddress.getAddressLine(i)).append("\n");
                }
                strAdd = strReturnedAddress.toString();
            } else {
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return strAdd;
    }


    public static boolean isDateAfter(String startDate, String endDate) {
        try {
            String myFormatString = "yyyy-M-d"; // for example
            SimpleDateFormat df = new SimpleDateFormat(myFormatString);
            Date date1 = df.parse(endDate);
            Date startingDate = df.parse(startDate);

            if (date1.after(startingDate))
                return true;
            else
                return false;
        } catch (Exception e) {

            return false;
        }
    }


    public static String getCurrentDateDDYYMM() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd-MM-yyyy", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static String getCurrentDate_DD_MM_YYYY() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "dd/MM/yyyy", Locale.getDefault());
        Date date = new Date();
        return dateFormat.format(date);
    }

    public static boolean isTimeAfter(Date startTime, Date endTime) {
        if (endTime.before(startTime)) { //Same way you can check with after() method also.
            return false;
        } else {
            return true;
        }
    }

    public static RecyclerView.LayoutManager getVerticalLinearLayoutManager(Context context) {
        LinearLayoutManager manager = new LinearLayoutManager(context);
        return manager;
    }

    public static int getClientCode() {
        Random rand = new Random();
        System.out.printf("%04d%n", rand.nextInt(10000));
        int code = rand.nextInt(10000);
        return code;
    }



    public static boolean isNullOrEmpty(String toCheck) {
        return toCheck == null || toCheck.equalsIgnoreCase("");
    }

    public static RecyclerView.LayoutManager getGridViewLayoutManger(Context context, int i) {

        GridLayoutManager gridLayoutManager = new GridLayoutManager(context, i);
        return gridLayoutManager;

    }


    /* private boolean IsSupportedFile(String filePath) {
         String ext = filePath.substring((filePath.lastIndexOf(".") + 1),
                 filePath.length());

         if (Constants.FILE_EXTN
                 .contains(ext.toLowerCase(Locale.getDefault())))
             return true;
         else
             return false;

     }
 */
    /*
     * getting screen width
     */
    public int getScreenWidth(Context _context) {
        int columnWidth;
        WindowManager wm = (WindowManager) _context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();

        final Point point = new Point();
        try {
            display.getSize(point);
        } catch (NoSuchMethodError ignore) { // Older device
            point.x = display.getWidth();
            point.y = display.getHeight();
        }
        columnWidth = point.x;
        return columnWidth;
    }

    public static String ConvertBitmapToString(Bitmap imageBitmap) {
        byte[] imgeByte = new byte[0];
        try {
            if (imageBitmap != null) {
                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                imageBitmap.compress(Bitmap.CompressFormat.JPEG, 50, byteArrayOutputStream);
                imgeByte = byteArrayOutputStream.toByteArray();
            }
        } catch (Exception e) {
            e.getMessage();
        }
        return Base64.encodeToString(imgeByte, Base64.DEFAULT);
    }


}