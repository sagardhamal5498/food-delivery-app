package com.food_delivery_app.coupon.controller;

import com.food_delivery_app.coupon.entity.Coupon;
import com.food_delivery_app.coupon.service.CouponService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/food-app/coupon")
public class CouponController {

    private CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping("/create")
    public ResponseEntity<Coupon> createCoupon(@RequestParam String code) {
        Coupon createdcoupon = couponService.create50PercentCoupon(code);
        return new ResponseEntity<>(createdcoupon, HttpStatus.CREATED);
    }

}
