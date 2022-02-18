package com.gordon.jianweb.dosaic;

import com.sun.jna.*;
/*
import com.sun.jna.win32.*;
import com.sun.jna.Structure;
import com.sun.jna.win32.StdCallLibrary;
import java.util.*;
*/
public class Dosaic{

   // This is the standard, stable way of mapping, which supports extensive
    // customization and mapping of Java to native types.

    public interface CLibrary extends Library {
        CLibrary INSTANCE = (CLibrary) Native.loadLibrary("dosaic", CLibrary.class);
		void dosaic(WString file);
        void dosaic1();
    }

    public void dosaic(String file){
        WString ws = new WString(file);
        CLibrary.INSTANCE.dosaic(ws);
    }
    public void dosaic1(){
        CLibrary.INSTANCE.dosaic1();     
    }
}
