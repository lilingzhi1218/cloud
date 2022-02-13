package com.example.llz.cloud1.designPattern.strategy;

public class Main {
    public static void main(String[] args) {
        King king = new King();
        king.setWeaponBehavior(new KnifeBehavior());
        king.fight();

        Queen queen = new Queen();
        queen.setWeaponBehavior(new BowAndArrowBehavior());
        queen.fight();
    }
}

/*
* 策略模式定义了算法族，分别封装起来，让他们之间可以互相替换，此模式让算法的变化独立于算法的客户
* 写一个程序角色们可以自由使用任意一个武器攻击
* 1.建立Character角色抽象类，具体角色类King、Queen继承Character角色类
* 2.建立WeaponBehavior接口，让每一个武器类实现它，从而在每个武器类定义中特有的攻击方式
* 3.new一个角色，new一个武器并赋予该角色的武器属性，并在角色的攻击方法中调用武器的攻击方式方法
* */
