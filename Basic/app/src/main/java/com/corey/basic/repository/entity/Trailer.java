package com.corey.basic.repository.entity;

/**
 * Created by caosanyang on 2018/7/20.
 */
public class Trailer {

  private String cover;

  private String title;

  private String duration;

  public String getCover() {
    return cover;
  }

  public void setCover(String cover) {
    this.cover = cover;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getDuration() {
    return duration;
  }

  public void setDuration(String duration) {
    this.duration = duration;
  }

  public static Trailer newInstance(int index) {
    Trailer trailer = new Trailer();
    trailer.setCover("");
    trailer.setTitle("一步之遥" + index);
    trailer.setDuration("02:00");
    return trailer;
  }
}
