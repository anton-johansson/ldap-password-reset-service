/**
 * Copyright (c) Anton Johansson <antoon.johansson@gmail.com>
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
