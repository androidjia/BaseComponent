package com.jjs.zero.javanewlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;


public class JavaNewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //android minSDK 24 N
//        List<Optional<String>> list = Arrays.asList(Optional.empty(),Optional.of("A"));
//        List<String> list1 = list.stream().flatMap(o -> o.isPresent()? Stream.of(o.get()):Stream.empty()).collect(Collectors.toList());


    }

}