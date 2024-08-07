package com.food_delivery_app.utils;

import com.food_delivery_app.security.TwilioConfig;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    private TwilioConfig twilioConfig;

    public SmsService(TwilioConfig twilioConfig) {
        this.twilioConfig = twilioConfig;
    }

    public void sendSms(String to, String body) {
        String number ="+91"+to;
        Message.creator(
                new PhoneNumber(number),
                new PhoneNumber(twilioConfig.getTrialNumber()),
                body
        ).create();
    }

}
