package com.nailmehdiyev.mymysqlinstagram.data.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.NonNull;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class User implements Parcelable {


    @SerializedName("userid")
    private Integer userid;

    @SerializedName("useremail")
    private String useremail;

    @SerializedName("username")
    private String username;

    @SerializedName("userpassword")
    private String userpassword;

    @SerializedName("userfullname")
    private String userfullname;

    @SerializedName("userphoto")
    private String userphoto;

    @SerializedName("userbio")
    private String userbio;

    @SerializedName("userprofileprivate")
    private Integer userprofileprivate;

    @SerializedName("followers")
    private List<User> followers;

    @SerializedName("following")
    private List<User> following;


    public User() {
    }


    protected User(Parcel in) {
        if (in.readByte() == 0) {
            userid = null;
        } else {
            userid = in.readInt();
        }
        useremail = in.readString();
        username = in.readString();
        userpassword = in.readString();
        userfullname = in.readString();
        userphoto = in.readString();
        userbio = in.readString();
        if (in.readByte() == 0) {
            userprofileprivate = null;
        } else {
            userprofileprivate = in.readInt();
        }
        followers = in.createTypedArrayList(User.CREATOR);
        following = in.createTypedArrayList(User.CREATOR);
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

    public Boolean followerListContains(int userid) {
        for (User u : followers) {
            if (u.userid == userid) {
                return true;
            }
        }

        return false;
    }


    public int getUserid() {
        return userid;
    }

    public void setUserid(Integer userid) {
        this.userid = userid;
    }

    public String getUseremail() {
        return useremail;
    }

    public void setUseremail(String useremail) {
        this.useremail = useremail;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUserpassword() {
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
    }

    public String getUserfullname() {
        return userfullname;
    }

    public void setUserfullname(String userfullname) {
        this.userfullname = userfullname;
    }

    public String getUserphoto() {
        return userphoto;
    }

    public void setUserphoto(String userphoto) {
        this.userphoto = userphoto;
    }

    public String getUserbio() {
        return userbio;
    }

    public void setUserbio(String userbio) {
        this.userbio = userbio;
    }

    public Integer getUserprofileprivate() {
        return userprofileprivate;
    }

    public void setUserprofileprivate(Integer userprofileprivate) {
        this.userprofileprivate = userprofileprivate;
    }

    public List<User> getFollowers() {
        return followers;
    }

    public void setFollowers(List<User> followers) {
        this.followers = followers;
    }

    public List<User> getFollowing() {
        return following;
    }

    public void setFollowing(List<User> following) {
        this.following = following;
    }



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(@NonNull Parcel dest, int flags) {
        if (userid == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(userid);
        }
        dest.writeString(useremail);
        dest.writeString(username);
        dest.writeString(userpassword);
        dest.writeString(userfullname);
        dest.writeString(userphoto);
        dest.writeString(userbio);
        if (userprofileprivate == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(userprofileprivate);
        }
        dest.writeTypedList(followers);
        dest.writeTypedList(following);
    }





}









