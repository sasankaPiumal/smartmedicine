package com.nibm.smartmedicine.service;

public interface EmailService {

    void sendSimpleMessage(String to, String subject, String text);

}
