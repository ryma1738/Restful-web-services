package com.rest.webservices.restfulwebservices.helloWorld;

public class HelloWorldBean {

    private String message;

    public HelloWorldBean() {
    }

    public HelloWorldBean(String helloWorld) {
        this.message = helloWorld;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "HelloWorldBean{" +
                "message='" + message + '\'' +
                '}';
    }
}
