/**
 * First, I wanted to use the id card number as the primary key for the table, but after a bit of documentation I've found
 * out that this would be a bad practice especially because that number should be confidential and not available for others
 */

package model;

public class Client {
    private int id;
    private String name;
    private String idCardNo;
    private String numCode;
    private String address;
    private int telephone;
    private int accountNo;

    public Client(){}

    public Client(int id, String name, String idCardNo, String numCode, String address, int telephone, int accountNo) {
        this.id = id;
        this.name = name;
        this.idCardNo = idCardNo;
        this.numCode = numCode;
        this.address = address;
        this.telephone = telephone;
        this.accountNo = accountNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumCode() {
        return numCode;
    }

    public void setNumCode(String numCode) {
        this.numCode = numCode;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getTelephone() {
        return telephone;
    }

    public void setTelephone(int telephone) {
        this.telephone = telephone;
    }

    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    public int getId() {
        return id;
    }

    public int getAccountNo() {
        return accountNo;
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", idCardNo='" + idCardNo + '\'' +
                ", numCode=" + numCode +
                ", address='" + address + '\'' +
                ", telephone=" + telephone +
                ", accountNo=" + accountNo +
                '}';
    }
}
