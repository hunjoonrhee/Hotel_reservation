package service;

import model.Customer;

import javax.management.InstanceAlreadyExistsException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class CustomerService {

    private static final CustomerService SINGLETON = new CustomerService();
    private static final Map<String, Customer> customers = new HashMap<>();
    public CustomerService(){}

    public static CustomerService getSingleton() {
        return SINGLETON;
    }

    public void addCustomer(String email, String firstName, String lastName){
        if(customers.isEmpty()){
            customers.put(email, new Customer(firstName, lastName, email));
        }else{
            if(!customers.containsKey(email)){
                customers.put(email, new Customer(firstName, lastName, email));
            }else{
                System.out.println("This email is already used by other user.");
            }
        }
    }
    public Customer getCustomer(String customerEmail){
        return customers.get(customerEmail);
    }

    public Collection<Customer> getAllCustomers(){
        ArrayList<Customer> AllCustomers = new ArrayList<Customer>();
        for(String key:customers.keySet()){
            AllCustomers.add(customers.get(key));
        }
        return AllCustomers;
    }

}
