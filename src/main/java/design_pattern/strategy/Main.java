package design_pattern.strategy;

import design_pattern.strategy.annotation.AnnotationPriceStrategyPayer;
import design_pattern.strategy.factory.Payer_Factory;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niki on 2018/4/2 9:59
 */
public class Main {

    public static void main(String[] args) {
//        testSimpleStrategy();
//        testFactoryStrategy();
        testAnnotationStrategy();
    }

    private static void testSimpleStrategy(){
        List<Customer> customers = createCustomer();

        double price = 1000;
        for(Customer customer : customers){
            Payer payer = new Payer(customer, price) ;
            System.out.println(payer.pay());
        }
    }

    private static void testFactoryStrategy(){
        List<Customer> customers = createCustomer();
        double price = 1000;
        for(Customer customer : customers){
            Payer_Factory payer = new Payer_Factory(customer, price);
            System.out.println(payer.pay());
        }
    }

    private static void testAnnotationStrategy(){
        List<Customer> customers = createCustomer();
        double price = 1000;
        for(Customer customer : customers){
            AnnotationPriceStrategyPayer payer = new AnnotationPriceStrategyPayer(customer, price);
            System.out.println(payer.pay());
        }
    }

    private static List<Customer> createCustomer(){
        Customer c1 = new Customer();
        c1.setIntegral(200);

        Customer c2 = new Customer();
        c2.setIntegral(800);

        Customer c3 = new Customer();
        c3.setIntegral(1500);

        Customer c4 = new Customer();
        c4.setIntegral(4000);

        List<Customer> customers = new ArrayList<>();
        customers.add(c1);
        customers.add(c2);
        customers.add(c3);
        customers.add(c4);

        return customers;
    }
}
