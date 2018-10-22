package com.test.drivesafe.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class LoginReq implements Serializable {
    // Members
    @SerializedName("user")
    private String mUser;

    @SerializedName("password")
    private String mPassword;

    // Constructors
    public LoginReq() {} // default

    public LoginReq(String user, String password) {
        mUser = user;
        mPassword = password;
    }

    // Getters
    public String getUser() {
        return mUser;
    }

    public String getPassword() {
        return mPassword;
    }

    // Setters
    public void setUser(String user) {
        mUser = user;
    }

    public void setPassword(String password) {
        mPassword = password;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LoginReq req = (LoginReq)o;
        return (this.mUser == null ? req.mUser == null : this.mUser.equals(req.mUser)) &&
                (this.mPassword == null ? req.mPassword == null : this.mPassword.equals(req.mPassword));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (this.mUser == null ? 0: this.mUser.hashCode());
        result = 31 * result + (this.mPassword == null ? 0: this.mPassword.hashCode());
        return result;
    }

    @Override
    public String toString()  {
        StringBuilder sb = new StringBuilder();
        sb.append("class LoginReq {\n");

        sb.append("  mUser: ").append(mUser).append("\n");
        sb.append("  mPassword: ").append(mPassword).append("\n");
        sb.append("}\n");
        return sb.toString();
    }
}
