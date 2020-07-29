package com.example.mohamedgamal.learningforkids;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by mohamedGamal on 2/10/2017.
 */

public class wordAdapter extends ArrayAdapter<word> {

int color_src;

    public wordAdapter(Activity context, ArrayList<word> words,int color_src)
    {
        //zero is layout res ID
        super(context,0,words);
        this.color_src=color_src;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        // this method returns item at the given index position
        word currentword=getItem(position);

        View listItemView=convertView;


        if(listItemView==null)
        {
            listItemView= LayoutInflater.from(getContext()).inflate(R.layout.list_item,parent,false);
        }

        TextView t1=(TextView)listItemView.findViewById(R.id.textView);
        t1.setText(currentword.getEng_word());

        TextView t2=(TextView)listItemView.findViewById(R.id.textView2);
        t2.setText(currentword.getTranslated_word());

        ImageView m1= (ImageView)listItemView.findViewById(R.id.imageView);
        if(currentword.hasImage())
        {
            m1.setImageResource(currentword.getImage_res());
            m1.setVisibility(View.VISIBLE);
        }
        else
            m1.setVisibility(View.GONE);

        // declartation & initialization for linearlayout holds textviews
        View textContainer=listItemView.findViewById(R.id.text_container);

        // get color by its ID
        int color= ContextCompat.getColor(getContext(),color_src);
        // set color to each activity listview
        textContainer.setBackgroundColor(color);


        return listItemView;
    }
}
