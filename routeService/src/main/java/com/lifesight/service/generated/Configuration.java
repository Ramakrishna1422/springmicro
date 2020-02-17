package com.lifesight.service.generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class Configuration {

    @SerializedName("service")
    @Expose
    private String service;
    @SerializedName("host")
    @Expose
    private String host;
    @SerializedName("port")
    @Expose
    private String port;
    @SerializedName("globalLimits")
    @Expose
    private Methods globalLimits;
    @SerializedName("apiLimits")
    @Expose
    private List<ApiLimit> apiLimits = new ArrayList<>();

}
