package com.example.llz.cloud1.designPattern.strategy;

public class KnifeBehavior implements WeaponBehavior {
    @Override
    public void useWeapon() {
        System.out.println("使用刀攻击一下");
    }
}
