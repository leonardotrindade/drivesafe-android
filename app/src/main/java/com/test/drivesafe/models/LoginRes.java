package com.test.drivesafe.models;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class LoginRes implements Serializable {
    // Members
    @SerializedName("token")
    private String mToken;

    // Constructors
    public LoginRes() {} // default

    public LoginRes(String token) {
        mToken = token;
    }

    // Getters
    public String getToken() {
        return mToken;
    }

    // Setters
    public void setToken(String token) {
        mToken = token;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        LoginRes req = (LoginRes)o;
        return (this.mToken == null ? req.mToken == null : this.mToken.equals(req.mToken));
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (this.mToken == null ? 0: this.mToken.hashCode());
        return result;
    }

    @Override
    public String toString()  {
        StringBuilder sb = new StringBuilder();
        sb.append("class LoginRes {\n");

        sb.append("  mToken: ").append(mToken).append("\n");
        sb.append("}\n");
        return sb.toString();
    }
}
