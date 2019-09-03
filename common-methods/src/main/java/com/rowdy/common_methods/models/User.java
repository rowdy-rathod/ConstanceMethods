package com.rowdy.common_methods.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
public class User implements Serializable, Parcelable {
    private int id;
    private String name;
    private String email;
    private String mobile;
    private int Branch;
    private int batch;
    private String batchStartDate;
    private int trainer;
    private int db;
    private int status;
    private String image;
    private String addedOn;
    private int fullAmount;
    private int alert;
    private int payment;
    private int placed;
    private int purpose;
    private int domain;
    private int type;
    private String vCode;
    private int mac;
    private int trash;
    private String updatedOn;
    private String pwdUpdatedOn;

    public User() {
    }

    public User(Parcel in) {
        id = in.readInt();
        name = in.readString();
        email = in.readString();
        mobile = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(email);
        dest.writeString(mobile);
    }

    public boolean setPropertiesFromString(String jsonString) throws JSONException {
        JSONObject jsonObject = new JSONObject(jsonString);
        Log.d("RESP JSON", jsonObject.toString());
        return setPropertiesFromJSON(jsonObject);
    }

    public boolean setPropertiesFromJSON(JSONObject jsonObject1) throws JSONException {
        Log.d("Response JSON", jsonObject1.toString());
        String street = "", state = "", city = "", gender = "", companyName = "";
        String address = null;
        String managerName = "";
        boolean flag = false;
        if (jsonObject1.has("success") && !jsonObject1.isNull("success")) {
            JSONArray jsonArray = jsonObject1.getJSONArray("success");
            flag = true;
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                String fname = jsonObject.getString("fname");
                String lname = jsonObject.getString("lname");
                String companyId = jsonObject.getString("companyId");
                String role = jsonObject.getString("role");
                String id = jsonObject.getString("id");
                String lat = jsonObject.getString("lat");
                String lng = jsonObject.getString("lng");
                String attFlag = jsonObject.getString("attFlag");
                String checkInFlag = jsonObject.getString("checkInFlag");
                String profileImageName = jsonObject.getString("profilePic");
                String contactNumber = jsonObject.getString("contact_no");
                String userDesignation = jsonObject.getString("designation");
                int companyType = jsonObject.getInt("companyType");
                String openingBalEmp = null;
                if (jsonObject.has("openingBalEmp")) {
                    openingBalEmp = jsonObject.getString("openingBalEmp");
                }
                if (jsonObject.has("street")) {
                    street = jsonObject.getString("street");
                }
                if (jsonObject.has("state")) {
                    state = jsonObject.getString("state");
                }
                if (jsonObject.has("city")) {
                    city = jsonObject.getString("city");
                }
                if (jsonObject.has("gender")) {
                    gender = jsonObject.getString("gender");
                }
                if (jsonObject.has("companyName")) {
                    companyName = jsonObject.getString("companyName");
                }
                if (jsonObject.has("address")) {
                    address = jsonObject.getString("address");
                    if (address == null) {
                        address = "";
                    }
                }
                if (jsonObject.has("superEmpName")) {
                    managerName = jsonObject.getString("superEmpName");
                    if (managerName == null) {
                        managerName = "";
                    }
                }
                String email = jsonObject.getString("email");

            }
        }
        if (jsonObject1.has("fail")) {
            flag = false;
        }
        return flag;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
