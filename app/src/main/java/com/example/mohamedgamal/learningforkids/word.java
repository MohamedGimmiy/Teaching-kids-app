package com.example.mohamedgamal.learningforkids;

/**
 * Created by mohamedGamal on 2/10/2017.
 */

public class word {
    private String eng_word;
    private String translated_word;
    private int Image_res=NO_IMAGE_PROVIDED;

    private int audio_res;

    private static final int NO_IMAGE_PROVIDED=-1;

    public word(String eng_word,String translated_word,int audio_res)
    {
        this.eng_word=eng_word;
        this.translated_word=translated_word;
        this.audio_res=audio_res;
    }
    public word(String eng_word,String translated_word,int Image_res,int audio_res)
    {
        this.eng_word=eng_word;
        this.translated_word=translated_word;
        this.Image_res=Image_res;
        this.audio_res=audio_res;
    }
    public String getEng_word() {
        return eng_word;
    }

    public void setEng_word(String eng_word) {
        this.eng_word = eng_word;
    }

    public String getTranslated_word() {
        return translated_word;
    }

    public void setTranslated_word(String translated_word) {
        this.translated_word = translated_word;
    }

    public int getImage_res() {
        return Image_res;
    }

    public void setImage_res(int image_res) {
        Image_res = image_res;
    }
    // if word has image or not
    public boolean hasImage()
    {
        return Image_res!=NO_IMAGE_PROVIDED;
    }

    public int getAudio_res() {
        return audio_res;
    }

    public void setAudio_res(int audio_res) {
        this.audio_res = audio_res;
    }
}
