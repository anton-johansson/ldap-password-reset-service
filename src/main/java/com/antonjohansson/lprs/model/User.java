package com.antonjohansson.lprs.model;

import java.util.Objects;

/**
 * Defines a user in the directory.
 */
public class User
{
    private String distinguishedName = "";
    private String userPrincipalName = "";
    private String name = "";
    private String mail = "";
    private String telephoneNumber = "";

    public String getDistinguishedName()
    {
        return distinguishedName;
    }

    public void setDistinguishedName(String objectGUID)
    {
        this.distinguishedName = objectGUID;
    }

    public String getUserPrincipalName()
    {
        return userPrincipalName;
    }

    public void setUserPrincipalName(String userPrincipalName)
    {
        this.userPrincipalName = userPrincipalName;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getMail()
    {
        return mail;
    }

    public void setMail(String mail)
    {
        this.mail = mail;
    }

    public String getTelephoneNumber()
    {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber)
    {
        this.telephoneNumber = telephoneNumber;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(distinguishedName);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (obj instanceof User)
        {
            User that = (User) obj;
            return Objects.equals(this.distinguishedName, that.distinguishedName)
                && Objects.equals(this.userPrincipalName, that.userPrincipalName)
                && Objects.equals(this.name, that.name)
                && Objects.equals(this.mail, that.mail)
                && Objects.equals(this.telephoneNumber, that.telephoneNumber);
        }
        return false;
    }
}
