package com.example.llz.cloud1.designPattern.strategy;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Character {
    WeaponBehavior weaponBehavior;
    void fight() {
        List<Object> objects = new ArrayList<>();
        objects.stream().filter(e-> e instanceof String).collect(Collectors.toList());
        weaponBehavior.useWeapon();
    }

    public WeaponBehavior getWeaponBehavior() {
        return weaponBehavior;
    }

    public void setWeaponBehavior(WeaponBehavior weaponBehavior) {
        this.weaponBehavior = weaponBehavior;
    }
}