package com.harun.offloader002;

import android.support.v7.widget.Toolbar;

/**
 * Created by HARUN on 4/3/2016.
 */
public class Constants {
    public static Toolbar toolbar = null;

    /**
     The caller references the constants using <tt>Consts.EMPTY_STRING</tt>,
     and so on. Thus, the caller should be prevented from constructing objects of
     this class, by declaring this private constructor.
     */
    private Constants(){
        //this prevents even the native class from
        //calling this ctor as well :
        throw new AssertionError();
    }

}
