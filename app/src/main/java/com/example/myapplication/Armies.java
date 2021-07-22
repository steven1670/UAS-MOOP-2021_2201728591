package com.example.myapplication;

public class Armies {
    private int hero_cav = 0, hero_inf = 0, hero_arch = 0, hero_cat =0;
    private int heroesNumber = 0;
    private Integer calvaryUnits = 0, infantryUnits = 0, archerUnits = 0, catapultUnits = 0;

    public Armies(Integer calvaryUnits,Integer infantryUnits,Integer archerUnits,Integer catapultUnits ){
        this.calvaryUnits = calvaryUnits;
        this.infantryUnits = infantryUnits;
        this.archerUnits = archerUnits;
        this.catapultUnits = catapultUnits;
    }

    public void setHeroes(int hero_cav, int hero_inf, int hero_arch, int hero_cat, int heroesNumber){
        this.hero_cav = hero_cav;
        this.hero_inf = hero_inf;
        this.hero_arch = hero_arch;
        this.hero_cat = hero_cat;
        this.heroesNumber = heroesNumber;
    }

    public int getHero_cav() {
        return hero_cav;
    }

    public int getHero_inf() {
        return hero_inf;
    }

    public int getHero_arch() {
        return hero_arch;
    }

    public int getHero_cat() {
        return hero_cat;
    }

    public int getHeroesNumber() {
        return heroesNumber;
    }

    public Integer getCalvaryUnits() {
        return calvaryUnits;
    }

    public void setCalvaryUnits(Integer calvaryUnits) {
        this.calvaryUnits = calvaryUnits;
    }

    public Integer getInfantryUnits() {
        return infantryUnits;
    }

    public void setInfantryUnits(Integer infantryUnits) {
        this.infantryUnits = infantryUnits;
    }

    public Integer getArcherUnits() {
        return archerUnits;
    }

    public void setArcherUnits(Integer archerUnits) {
        this.archerUnits = archerUnits;
    }

    public Integer getCatapultUnits() {
        return catapultUnits;
    }

    public void setCatapultUnits(Integer catapultUnits) {
        this.catapultUnits = catapultUnits;
    }
}//end
