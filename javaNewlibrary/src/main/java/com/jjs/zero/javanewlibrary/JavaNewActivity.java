package com.jjs.zero.javanewlibrary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        List<Optional<String>> list = Arrays.asList(Optional.empty(),Optional.of("A"));
        List<String> list1 = list.stream().flatMap(o -> o.isPresent()? Stream.of(o.get()):Stream.empty()).collect(Collectors.toList());

        
    }
}