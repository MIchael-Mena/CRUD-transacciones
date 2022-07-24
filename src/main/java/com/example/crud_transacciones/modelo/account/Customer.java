package com.example.crud_transacciones.modelo.account;

public class Customer {

    private int id;
    private String name;
/*    private final String surname;
    private final String name;
    private final String phone;
    private final String email;
    private final String password;*/


    public Customer(int id,String name) {
        this.id = id;
        this.name = name;
    }

    public Customer(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

}
