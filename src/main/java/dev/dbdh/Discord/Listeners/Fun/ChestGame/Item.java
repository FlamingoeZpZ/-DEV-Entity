package dev.dbdh.Discord.Listeners.Fun.ChestGame;

import java.util.Random;

public class Item extends Chest{

    public String name;
    public String URL;
    public String tierString;
    public int drawChance;
    public int goldGain;
    public long xpGain;
    public String type;
    public String rarityString;
    public int tier;
    public boolean posOrNeg = true;
    Item(String name, String URL, int type, int rarity, int guaranteedPolish){
        Random rng = new Random();
        this.name = name;
        this.URL = URL;
        switch (rarity) {
            case RARITY_BAD:
                drawChance = 240;
                xpGain = -240;
                goldGain = -480;
                rarityString = "**No item:** ";
                posOrNeg = false;
                break;
            case RARITY_BROWN:
                drawChance = 240;
                xpGain = 120;
                goldGain = 240;
                rarityString = "a **Common Item:** ";
                break;
            case RARITY_YELLOW:
                guaranteedPolish += 10;
                drawChance = 180;
                xpGain = 240;
                goldGain = 120;
                rarityString = "an **Uncommon Item:** ";
                break;
            case RARITY_GREEN:
                drawChance = 140;
                xpGain = 240;
                goldGain = 240;
                rarityString = "a **Rare Item:** ";
                break;
            case RARITY_PURPLE:
                drawChance = 100;
                xpGain = 240;
                goldGain = 480;
                rarityString = "a **Very Rare Item:** ";
                break;
            case RARITY_PINK:
                drawChance = 60;
                xpGain = 960;
                goldGain = 960;
                rarityString = "an **Ultra Rare Item:** ";
                break;
            case RARITY_EVENT:
                drawChance = 40;
                xpGain = 1920;
                goldGain = 960;
                rarityString = "an **Event Item:** ";
                break;
            case RARITY_EPIC:
                drawChance = 15;
                xpGain = 1920;
                goldGain = 3840;
                rarityString = "an **Epic Item:** ";
                break;
            case RARITY_LEGENDARY:
                drawChance = 5;
                xpGain = 4800;
                goldGain = 4800;
                rarityString = "a **LEGENDARY ITEM:** ";
                break;
        }
        switch (type){
            case TYPE_BAD:
                xpGain *= 4;
                goldGain *= 8;
                break;
            case TYPE_TOOLBOX:
                xpGain *= 8;
                goldGain /= 4;
                break;
            case TYPE_MEDKIT:
                guaranteedPolish += 40;
                break;
            case TYPE_FLASHLIGHT:
                xpGain /= 4;
                goldGain *= 8;
                break;
            case TYPE_MAP:
                xpGain *= 12;
                goldGain = 0;
                guaranteedPolish -= 5;
                break;
            case TYPE_KEY:
                xpGain = 0;
                goldGain *= 12;
                guaranteedPolish -= 5;
                break;
            case TYPE_EVENT:
                xpGain *= 4;
                goldGain *= 4;
                break;
            case TYPE_EPIC:
                xpGain *= 8;
                goldGain *= 8;
                guaranteedPolish += 5;
                break;
            case TYPE_LEGENDARY:
                xpGain *= 16;
                goldGain *= 16;
                guaranteedPolish += 10;
                break;
            case TYPE_MISC:
                xpGain = 4;
                goldGain = 0;
                break;
        }
        tier = rng.nextInt(101) + guaranteedPolish;
        if(posOrNeg) {
            if(tier < 5){ // 5%
                tierString = "Shattered";
                goldGain /= 12;
                xpGain /= 12;
            }
            else if (tier < 15) { // 10%
                tierString = "Broken";
                goldGain /= 4;
                xpGain /= 4;
            } else if (tier < 60) { // 45%
                tierString = "Damaged";
                goldGain /= 2;
                xpGain /= 2;
            } else if (tier < 85) { // 25%
                tierString = "Fixed";
            } else if (tier < 95) { // 10%
                tierString = "Upgraded";
                goldGain *= 2;
                xpGain *= 2;
            } else { //All other resources
                tierString = "Godly";
                goldGain *= 4;
                xpGain *= 4;
            }
            this.name = name + " in  a **" + tierString + "** state. ";
        }
        this.drawChance = (posOrNeg)?drawChance:-drawChance;
    }
}
