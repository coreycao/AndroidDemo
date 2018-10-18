package me.demo.rx.model;

import java.util.List;

/**
 * @author caosanyang
 * @date 2018/9/30
 */
public class LoginInfo extends BaseResult {

  /**
   * data : {"collectIds":[3437],"email":"","icon":"","id":11045,"password":"Corey_tech2018","token":"","type":0,"username":"coreyyyang"}
   */

  private DataBean data;

  public DataBean getData() {
    return data;
  }

  public void setData(DataBean data) {
    this.data = data;
  }

  public static class DataBean {
    /**
     * collectIds : [3437]
     * email :
     * icon :
     * id : 11045
     * password : Corey_tech2018
     * token :
     * type : 0
     * username : coreyyyang
     */

    private String email;
    private String icon;
    private int id;
    private String password;
    private String token;
    private int type;
    private String username;
    private List<Integer> collectIds;

    public String getEmail() {
      return email;
    }

    public void setEmail(String email) {
      this.email = email;
    }

    public String getIcon() {
      return icon;
    }

    public void setIcon(String icon) {
      this.icon = icon;
    }

    public int getId() {
      return id;
    }

    public void setId(int id) {
      this.id = id;
    }

    public String getPassword() {
      return password;
    }

    public void setPassword(String password) {
      this.password = password;
    }

    public String getToken() {
      return token;
    }

    public void setToken(String token) {
      this.token = token;
    }

    public int getType() {
      return type;
    }

    public void setType(int type) {
      this.type = type;
    }

    public String getUsername() {
      return username;
    }

    public void setUsername(String username) {
      this.username = username;
    }

    public List<Integer> getCollectIds() {
      return collectIds;
    }

    public void setCollectIds(List<Integer> collectIds) {
      this.collectIds = collectIds;
    }
  }
}
