package com.example.newsappadmin.model;

public class User
{
    private String userID, userName , userContact , userAddress , userEmail;

    public User()
    {
    }

    public User(String userID, String userName, String userContact, String userAddress, String userEmail)
    {
        this.userID = userID;
        this.userName = userName;
        this.userContact = userContact;
        this.userAddress = userAddress;
        this.userEmail = userEmail;
    }

    public String getUserID()
    {
        return userID;
    }

    public void setUserID(String userID)
    {
        this.userID = userID;
    }

    public String getUserName()
    {
        return userName;
    }

    public void setUserName(String userName)
    {
        this.userName = userName;
    }

    public String getUserContact()
    {
        return userContact;
    }

    public void setUserContact(String userContact)
    {
        this.userContact = userContact;
    }

    public String getUserAddress()
    {
        return userAddress;
    }

    public void setUserAddress(String userAddress)
    {
        this.userAddress = userAddress;
    }

    public String getUserEmail()
    {
        return userEmail;
    }

    public void setUserEmail(String userEmail)
    {
        this.userEmail = userEmail;
    }


    @Override
    public String toString()
    {
        return "User{" +
                "userID='" + userID + '\'' +
                ", userName='" + userName + '\'' +
                ", userContact='" + userContact + '\'' +
                ", userAddress='" + userAddress + '\'' +
                ", userEmail='" + userEmail + '\'' +
                '}';
    }
}
