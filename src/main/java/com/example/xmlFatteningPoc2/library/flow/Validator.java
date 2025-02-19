package com.example.xmlFatteningPoc2.library.flow;


import com.example.xmlFatteningPoc2.library.jacksonpojo.FieldValidationResult;

import java.util.ArrayList;
import java.util.List;

public class Validator {

    private final List<FieldValidationResult> errorList=new ArrayList<>();

    public boolean validateField(String fieldName, String value, boolean isNotNull,int minLength,int maxLength, String regex){
        boolean isValid=true;

        if(isNotNull && (value==null) || value.trim().isEmpty()){
            errorList.add(new FieldValidationResult(fieldName,"Value cannot be null or empty"));
            isValid=false;
        }

        if(minLength!=0 && maxLength!=0  && value !=null){
            int length = value.length();
            if(length<minLength || length > maxLength){
                errorList.add(new FieldValidationResult(fieldName,"Value size must be between "+minLength+" to "+maxLength));
                isValid=false;
            }
        }

        if(regex !=null && value!=null && !value.matches(regex)){
            errorList.add(new FieldValidationResult(fieldName,"Value does not matches with regex: "+regex));
            isValid=false;
        }

        return isValid;
    }


    public List<FieldValidationResult> getErrorList() {
        return errorList;
    }


    public boolean hasErrors(){
        return  !errorList.isEmpty();
    }

    public void clearErrors(){
        errorList.clear();
    }

}
