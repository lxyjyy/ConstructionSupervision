package com.csc.bean;

/**
 * 用户实体
 * id": 4,
 "name": "wangwu",
 "mobile": "12345678",
 "age": 0,
 "sex": 0,
 "token": "53175406508f178817927cfdad7ac2e5",
 "deptId": 1,
 "roleId": 2,
 "openfireId": "",
 "headPicUrl": ""
 */

public class User {
    //用户ID
    private String id;
    //用户名称
    private String name;
    //用户手机号
    private String mobile;
    //用户年龄
    private String age;
    //用户性别
    private String sex;
    //用户token
    private String token;
    //部门ID
    private String deptId;
    //角色ID
    private String roleId;
    //openFireID
    private String openfireId;
    //头像地址
    private String headPicUrl;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getDeptId() {
        return deptId;
    }

    public void setDeptId(String deptId) {
        this.deptId = deptId;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public String getOpenfireId() {
        return openfireId;
    }

    public void setOpenfireId(String openfireId) {
        this.openfireId = openfireId;
    }

    public String getHeadPicUrl() {
        return headPicUrl;
    }

    public void setHeadPicUrl(String headPicUrl) {
        this.headPicUrl = headPicUrl;
    }
}
