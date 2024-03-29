package com.common;

public class User {
    private String userName;
    private String firstName;
    private String lastName;
    private String password;
    private ShippingAddress shippingAddress;

    public String getUserName(){
        return userName;
    } 
    public void setUserName(String userName){
        this.userName= userName;
    } 
    public String getFirstName(){
        return firstName;
    } 
    public void setFirstName(String firstName){
        this.firstName= firstName;
    } 
    public String getLastName(){
        return lastName;
    } 
    public void setLastName(String lastName){
        this.lastName= lastName;
    } 
    public String getPassword(){
        return password;
    } 
    public void setPassword(String password){
        this.password= password;
    } 
    public ShippingAddress getShippingAddress() {
        return shippingAddress;
    }

    public void setShippingAddress(ShippingAddress shippingAddress) {
        this.shippingAddress = shippingAddress;
    }
}
