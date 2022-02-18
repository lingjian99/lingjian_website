package com.gordon.jianweb.dosaic;

public class DosaicImage {

    IProgress progress = null;
    String imgFile;

    public DosaicImage(String str){
        imgFile = str;
    }
    public DosaicImage(IProgress prog, String str) {
        imgFile = str;
        progress = prog;
    }
    public void dosaic(){
        Dosaic lib = new Dosaic();
        lib.dosaic(imgFile);

    }
}
