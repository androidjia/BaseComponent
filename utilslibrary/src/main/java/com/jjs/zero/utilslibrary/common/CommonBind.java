package com.jjs.zero.utilslibrary.common;

import android.widget.ImageView;


import androidx.databinding.BindingAdapter;

import com.jjs.zero.utilslibrary.R;
import com.squareup.picasso.Picasso;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/11/16
 * @Details: <功能描述>
 */
public class CommonBind {

    @BindingAdapter("imgCircleUrl")
    public static void bindCircleImageUrl(ImageView imageView,String url) {
        if (url != null && url.length()>0) {
            Picasso.get().load(url)
                    .placeholder(R.drawable.ic_launcher)
                    .error(R.drawable.ic_launcher)
                    .transform(new CircleTransform())
                    .into(imageView);
        }
    }

    @BindingAdapter("imgUrl")
    public static void bindImageUrl(ImageView imageView,String url) {
        if (url != null && url.length()>0) {
            Picasso.get().load(url)
                    .placeholder(R.drawable.ic_launcher)
                    .error(R.drawable.ic_launcher)
                    .transform(new CircleTransform())
                    .into(imageView);
        }
    }




}
