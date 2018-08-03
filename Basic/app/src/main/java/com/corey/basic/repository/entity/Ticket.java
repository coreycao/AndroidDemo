package com.corey.basic.repository.entity;

/**
 * Created by caosanyang on 2018/7/25.
 */
public class Ticket {

  private String title;

  private String effectiveTime;

  private boolean owned;

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getEffectiveTime() {
    return effectiveTime;
  }

  public void setEffectiveTime(String effectiveTime) {
    this.effectiveTime = effectiveTime;
  }

  public boolean isOwned() {
    return owned;
  }

  public void setOwned(boolean owned) {
    this.owned = owned;
  }

  public static Ticket newInstance(int index) {
    Ticket ticket = new Ticket();
    ticket.setTitle("我不是药神" + index);
    return ticket;
  }
}
