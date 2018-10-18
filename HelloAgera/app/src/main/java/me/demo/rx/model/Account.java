package me.demo.rx.model;

import java.io.Serializable;

/**
 * @author caosanyang
 * @date 2018/9/30
 */
public class Account implements Serializable {

  public String username;

  public String password;

  public static Account of(String username, String password) {
    Account account = new Account();
    account.username = username;
    account.password = password;
    return account;
  }
}
