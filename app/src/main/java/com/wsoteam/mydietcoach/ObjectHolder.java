package com.wsoteam.mydietcoach;

import com.wsoteam.mydietcoach.POJOS.Global;
import com.wsoteam.mydietcoach.POJOS.POJOS.GlobalObject;

public class ObjectHolder {
    private static GlobalObject globalObject;

    public void bindObject(GlobalObject globalObject){
        this.globalObject = globalObject;
    }

    public static GlobalObject getClobalObject(){
        return globalObject;
    }
}
