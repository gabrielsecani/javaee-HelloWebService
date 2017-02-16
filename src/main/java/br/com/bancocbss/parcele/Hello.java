package br.com.bancocbss.parcele;

import javax.jws.WebMethod;
import javax.jws.WebService;

@WebService
public class Hello {

    @WebMethod
    public String sayHello(String name) {
        System.out.println("Webservice sayHello called...");
        return "Hello " + name;
    }
    
    @WebMethod
    public String sayHi(String name) {
        System.out.println("Webservice sayHi called...");
        return "Hi there, " + name;
    }
}
