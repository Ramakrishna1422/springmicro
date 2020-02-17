package com.lifesight.service.generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Methods {

    @SerializedName("get")
    @Expose
    private Config get;
    @SerializedName("post")
    @Expose
    private Config post;
    @SerializedName("delete")
    @Expose
    private Config delete;

}