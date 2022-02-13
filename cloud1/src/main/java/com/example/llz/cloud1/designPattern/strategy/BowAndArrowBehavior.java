package com.example.llz.cloud1.designPattern.strategy;

public class BowAndArrowBehavior implements WeaponBehavior {
    @Override
    public void useWeapon() {
        System.out.println("使用弓箭攻击一下");
    }
}
