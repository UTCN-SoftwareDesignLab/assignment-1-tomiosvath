package model.validation;

import model.Client;

import java.util.ArrayList;
import java.util.List;

public class ClientValidator {
    private static final int ID_CARD_LENGTH = 8;
    private static final int NUM_CODE_LENGTH = 13;

    public List<String> getErrors() {
        return errors;
    }

    private final List<String> errors;

    private Client client;

    public ClientValidator(Client client){
        this.client = client;
        errors = new ArrayList<>();
    }

    public boolean validate(){
        validateIdCardNo(client.getIdCardNo());
        validateNumCode(client.getNumCode());
        validateTelephone(client.getTelephone());
        return errors.isEmpty();
    }

    private void validateIdCardNo(String idCardNo){
        if (idCardNo.length() != ID_CARD_LENGTH)
            errors.add("Id Card Number is invalid!");
    }

    private void validateNumCode(String numCode){
        try{
            Long.parseLong(numCode);
            if (numCode.length() != 13)
                errors.add("Num Code is invalid!");
        } catch (NumberFormatException e) {
            System.out.println(numCode);
            errors.add("Num Code is invalid!");
        }
    }

    private void validateTelephone(int telephone){
        if (telephone < 700000000 || telephone > 799999999)
            errors.add("Telephone number is invalid!");
    }

}
