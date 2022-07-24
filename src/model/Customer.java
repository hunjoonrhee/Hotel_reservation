package model;

import java.util.regex.Pattern;

public class Customer {
    String firstName;
    String lastName;
    String email;

    public Customer(String firstName, String lastName, String email){
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;

        String emailRegex = "^(.+)@(.+).(.+)$";
        Pattern pattern = Pattern.compile(emailRegex);
        if(!pattern.matcher(email).matches()){
            throw new IllegalArgumentException("the email does not match the format 'name@domain.com'");
        }
    }
    public String getEmail(){
        return this.email;
    }

    public String toString(){
        return "First Name:" + firstName + ", Last Name: " + lastName + ", email adress: " + email;
    }


}
