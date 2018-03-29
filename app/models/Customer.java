package models;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Customer
{
    @Id
    private int customerId;
    private String firstName;
    private String lastName;
    private String address;
    private String city;
    private String phone;
    private boolean textAlert;
    private boolean emailAlert;
    private String email;
    private byte[] password;
    private byte[] salt;


    public int getCustomerId()
    {
        return customerId;
    }

    public void setCustomerId(int customerId)
    {
        this.customerId = customerId;
    }

    public String getFirstName()
    {
        return firstName;
    }

    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    public String getLastName()
    {
        return lastName;
    }

    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    public String getAddress()
    {
        return address;
    }

    public void setAddress(String address)
    {
        this.address = address;
    }

    public String getCity()
    {
        return city;
    }

    public void setCity(String city)
    {
        this.city = city;
    }

    public String getPhone()
    {
        return phone;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public boolean isTextAlert()
    {
        return textAlert;
    }

    public void setTextAlert(boolean textAlert)
    {
        this.textAlert = textAlert;
    }

    public boolean isEmailAlert()
    {
        return emailAlert;
    }

    public void setEmailAlert(boolean emailAlert)
    {
        this.emailAlert = emailAlert;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public byte[] getPassword()
    {
        return password;
    }

    public void setPassword(byte[] password)
    {
        this.password = password;
    }

    public byte[] getSalt()
    {
        return salt;
    }

    public void setSalt(byte[] salt)
    {
        this.salt = salt;
    }
}
