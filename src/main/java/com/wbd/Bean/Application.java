package com.wbd.Bean;

public class Application
{
    private String application_code;

    private String address_id;

    private String is_address;

    private String application_id;

    public String getApplication_code()
    {
        return application_code;
    }

    public void setApplication_code(String application_code)
    {
        this.application_code = application_code;
    }

    public String getAddress_id()
    {
        return address_id;
    }

    public void setAddress_id(String address_id)
    {
        this.address_id = address_id;
    }

    public String getIs_address()
    {
        return is_address;
    }

    public void setIs_address(String is_address)
    {
        this.is_address = is_address;
    }

    public String getApplication_id()
    {
        return application_id;
    }

    public void setApplication_id(String application_id)
    {
        this.application_id = application_id;
    }

    @Override
    public String toString()
    {
        return "Application{" +
                "application_code='" + application_code + '\'' +
                ", address_id='" + address_id + '\'' +
                ", is_address='" + is_address + '\'' +
                ", application_id='" + application_id + '\'' +
                '}';
    }
}
