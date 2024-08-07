package com.food_delivery_app.coupon.service;

import com.food_delivery_app.coupon.entity.Coupon;
import com.food_delivery_app.coupon.repository.CouponRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CouponService {

    @Autowired
    private CouponRepository couponRepository;

    public Coupon create50PercentCoupon(String code) {
        Coupon coupon = new Coupon(code);
        return couponRepository.save(coupon);
    }
}

