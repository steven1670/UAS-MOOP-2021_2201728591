package com.example.myapplication;

public class Hero {
    private String heroname;
    private double boost = 0.4;
    private int herolevel = 1;
    final private int maxlevel = 50;

    public Hero(String heroname){
        this.heroname = heroname;
    }

    public String getHeroname() {
        return heroname;
    }
    public void setHeroname(String heroname) {
        this.heroname = heroname;
    }

    public double getBoost() {
        return boost;
    }

    public int getHerolevel() {
        return herolevel;
    }
    public void setHerolevel(int herolevel) {
        this.herolevel = herolevel;
    }

    public int getMaxlevel() {
        return maxlevel;
    }
}
