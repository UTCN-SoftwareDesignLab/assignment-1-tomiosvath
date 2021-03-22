package model.builder;

import model.Client;

public class ClientBuilder {

    private Client client;

    public ClientBuilder(){
        client = new Client();
    }

    public ClientBuilder setName(String name){
        client.setName(name);
        return this;
    }

    public ClientBuilder setIdCardNo(String idCardNo){
        client.setIdCardNo(idCardNo);
        return this;
    }

    public ClientBuilder setNumCode(String numCode){
        client.setNumCode(numCode);
        return this;
    }

    public ClientBuilder setAddress(String address){
        client.setAddress(address);
        return this;
    }

    public ClientBuilder setTelephone(int telephone){
        client.setTelephone(telephone);
        return this;
    }

    public Client build(){
        return client;
    }
}
