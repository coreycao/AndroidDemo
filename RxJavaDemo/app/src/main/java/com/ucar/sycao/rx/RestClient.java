package com.ucar.sycao.rx;

import android.content.Context;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sycao on 2017/8/4.
 * this is a mock rest client
 */

public class RestClient {
    private Context context;

    RestClient(Context context){
        this.context = context;
    }

    public List<String> getFavoriteTvShows(){
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return createTvShowList();
    }

    public List<String> getFavoriteTvShowsWithException(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        throw new RuntimeException("Failed to load the data");
    }

    private List<String> createTvShowList() {
        List<String> tvShows = new ArrayList<>();
        tvShows.add("The Joy of Painting");
        tvShows.add("The Simpsons");
        tvShows.add("Futurama");
        tvShows.add("Rick & Morty");
        tvShows.add("The X-Files");
        tvShows.add("Star Trek: The Next Generation");
        tvShows.add("Archer");
        tvShows.add("30 Rock");
        tvShows.add("Bob's Burgers");
        tvShows.add("Breaking Bad");
        tvShows.add("Parks and Recreation");
        tvShows.add("House of Cards");
        tvShows.add("Game of Thrones");
        tvShows.add("Law And Order");
        return tvShows;
    }
}
