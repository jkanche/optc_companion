package com.jkanche.optc.optccompanion;

import java.io.Serializable;
import java.util.Comparator;

/**
 * Created by jayar on 10/10/2015.
 */
public class optcChar implements Serializable {

    public int getCharId() {

        return charId;
    }

    public void setCharId(int charId) {
        this.charId = charId;
    }

    public String getCharName() {
        return charName;
    }

    public void setCharName(String charName) {
        this.charName = charName;
    }

    public String getCharType() {
        return charType;
    }

    public void setCharType(String charType) {
        this.charType = charType;
    }

    public int getCharHealth() {
        return charHealth;
    }

    public void setCharHealth(int charHealth) {
        this.charHealth = charHealth;
    }

    public int getCharAttack() {
        return charAttack;
    }

    public void setCharAttack(int charAttack) {
        this.charAttack = charAttack;
    }

    public int getCharCost() {
        return charCost;
    }

    public void setCharCost(int charCost) {
        this.charCost = charCost;
    }

    public int getCharStars() {
        return charStars;
    }

    public void setCharStars(int charStars) {
        this.charStars = charStars;
    }

    public int getCharMaxLevel() {
        return charMaxLevel;
    }

    public void setCharMaxLevel(int charMaxLevel) {
        this.charMaxLevel = charMaxLevel;
    }

    public int getCharCombo() {
        return charCombo;
    }

    public void setCharCombo(int charCombo) {
        this.charCombo = charCombo;
    }

    public int getCharMaxExp() {
        return charMaxExp;
    }

    public void setCharMaxExp(int charMaxExp) {
        this.charMaxExp = charMaxExp;
    }



    public optcChar(int charId, String charName, String charType, String charClass, int charHealth,
                    int charAttack, int charRecovery, int charCost, int charStars, int charMaxLevel,
                    int charCombo, int charMaxExp, String charSpecial, String charSpecialName,
                    String captainSpl, String charCooldown, int charSlots) {
        this.charId = charId;
        this.charName = charName;
        this.charType = charType;
        this.charClass = charClass;
        this.charHealth = charHealth;
        this.charAttack = charAttack;
        this.charRecovery = charRecovery;
        this.charCost = charCost;
        this.charStars = charStars;
        this.charMaxLevel = charMaxLevel;
        this.charCombo = charCombo;
        this.charMaxExp = charMaxExp;
        this.charSpecial = charSpecial;
        this.charSpecialName = charSpecialName;
        this.captainSpl = captainSpl;
        this.charCooldown = charCooldown;
        this.charSlots = charSlots;
    }

    private String charClass;

    public void setCharClass(String charClass) {
        this.charClass = charClass;
    }

    public String getCharClass() {
        return charClass;
    }

    public int getCharSlots() {
        return charSlots;
    }

    public void setCharSlots(int charSlots) {
        this.charSlots = charSlots;
    }

    private int charId;
    private String charName;
    private String charType;
    private int charHealth;
    private int charAttack;

    public boolean isCharBaseForm() {
        return charBaseForm;
    }

    public void setCharBaseForm(boolean charBaseForm) {
        this.charBaseForm = charBaseForm;
    }

    private int charRecovery;
    private int charCost;
    private int charStars, charSlots;
    private int charMaxLevel;
    private int charCombo;
    private boolean charBaseForm;

    public int getCharRecovery() {
        return charRecovery;
    }

    public void setCharRecovery(int charRecovery) {
        this.charRecovery = charRecovery;
    }

    public String getCharSpecial() {
        return charSpecial;
    }

    public void setCharSpecial(String charSpecial) {
        this.charSpecial = charSpecial;
    }

    public String getCharSpecialName() {
        return charSpecialName;
    }

    public void setCharSpecialName(String charSpecialName) {
        this.charSpecialName = charSpecialName;
    }

    public String getCaptainSpl() {
        return captainSpl;
    }

    public void setCaptainSpl(String captainSpl) {
        this.captainSpl = captainSpl;
    }

    public String getCharCooldown() {
        return charCooldown;

    }

    public void setCharCooldown(String charCooldown) {
        this.charCooldown = charCooldown;
    }

    private int charMaxExp;

    private String charSpecial, charSpecialName, captainSpl, charCooldown;

    public static class Comparators {

        public static Comparator<optcChar> HEALTH = new Comparator<optcChar>() {
            @Override
            public int compare(optcChar o1, optcChar o2) {
                return o1.getCharHealth() - o2.getCharHealth();
            }
        };
        public static Comparator<optcChar> ATTACK = new Comparator<optcChar>() {
            @Override
            public int compare(optcChar o1, optcChar o2) {
                return o1.getCharAttack() - o2.getCharAttack();
            }
        };
        public static Comparator<optcChar> RECOVERY = new Comparator<optcChar>() {
            @Override
            public int compare(optcChar o1, optcChar o2) {
                return o1.getCharRecovery() - o2.getCharRecovery();
            }
        };
        public static Comparator<optcChar> COST = new Comparator<optcChar>() {
            @Override
            public int compare(optcChar o1, optcChar o2) {
                return o1.getCharCost()- o2.getCharCost();
            }
        };
        public static Comparator<optcChar> CHARID = new Comparator<optcChar>() {
            @Override
            public int compare(optcChar o1, optcChar o2) {
                return o1.getCharId()- o2.getCharId();
            }
        };
    }


}
