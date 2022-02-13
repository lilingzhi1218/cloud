package com.example.llz.cloud1.designPattern.strategy;

public abstract class Character {
    WeaponBehavior weaponBehavior;
    void fight() {
        weaponBehavior.useWeapon();
    }

    public WeaponBehavior getWeaponBehavior() {
        return weaponBehavior;
    }

    public void setWeaponBehavior(WeaponBehavior weaponBehavior) {
        this.weaponBehavior = weaponBehavior;
    }
}