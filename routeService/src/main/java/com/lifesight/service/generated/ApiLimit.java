package com.lifesight.service.generated;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiLimit {

    @SerializedName("api")
    @Expose
    private String api;
    @SerializedName("methods")
    @Expose
    private Methods methods;

}