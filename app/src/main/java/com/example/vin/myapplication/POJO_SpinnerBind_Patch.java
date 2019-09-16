package com.example.vin.myapplication;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by vin on 01/02/2017.
 */

public class POJO_SpinnerBind_Patch extends RealmObject {
    @PrimaryKey
    private String name;
    private String patch_name;

    public POJO_SpinnerBind_Patch()
    {

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPatch_name() {
        return patch_name;
    }

    public void setPatch_name(String patch_name) {
        this.patch_name = patch_name;
    }

    public  POJO_SpinnerBind_Patch(String name,String patch_name)
    {
        this.name=name;
        this.patch_name=patch_name;
    }

    @Override
    public String toString() {
        return patch_name;
    }
}
