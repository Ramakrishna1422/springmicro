package com.lifesight.service.generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Config {
    @SerializedName("limit")
    @Expose
    private int limit;
    @SerializedName("granularity")
    @Expose
    private String granularity;
}
