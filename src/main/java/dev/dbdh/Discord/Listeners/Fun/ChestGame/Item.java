package dev.dbdh.Discord.Listeners.Fun.ChestGame;

import java.util.Random;

public class Item extends Chest{

    public String name;
    public String URL;
    public String tierString;
    public int drawChance;
    public int goldGain;
    public int xpGain;
    public String type;
    public String rarityString;
    private int tier;
    boolean posOrNeg = true;
    Item(String name, String URL, int type, int rarity, int guaranteedPolish){
        Random rng = new Random();
        this.name = name;
        this.URL = URL;
        switch (rarity) {
            case RARITY_BAD:
                drawChance = 40;
                xpGain = -48;
                goldGain = -96;
                rarityString = "**No item:** ";
                posOrNeg = false;
                break;
            case RARITY_BROWN:
                drawChance = 120;
                xpGain = 24;
                goldGain = 24;
                rarityString = "a **Common Item:** ";
                break;
            case RARITY_YELLOW:
                guaranteedPolish += 5;
                drawChance = 80;
                xpGain = 48;
                goldGain = 24;
                rarityString = "an **Uncommon Item:** ";
                break;
            case RARITY_GREEN:
                drawChance = 70;
                xpGain = 48;
                goldGain = 48;
                rarityString = "a **Rare Item:** ";
                break;
            case RARITY_PURPLE:
                drawChance = 60;
                xpGain = 48;
                goldGain = 96;
                rarityString = "a **Very Rare Item:** ";
                break;
            case RARITY_PINK:
                drawChance = 20;
                xpGain = 192;
                goldGain = 192;
                rarityString = "an **Ultra Rare Item:** ";
                break;
            case RARITY_EVENT:
                drawChance = 10;
                xpGain = 384;
                goldGain = 192;
                rarityString = "an **Event Item:** ";
                break;
            case RARITY_EPIC:
                drawChance = 5;
                xpGain = 384;
                goldGain = 768;
                rarityString = "an **Epic Item:** ";
                break;
            case RARITY_LEGENDARY:
                drawChance = 2;
                xpGain = 768;
                goldGain = 960;
                rarityString = "a **LEGENDARY ITEM:** ";
                break;
        }
        switch (type){
            case TYPE_BAD:
                xpGain *= 2;
                goldGain *= 4;
                break;
            case TYPE_TOOLBOX:
                xpGain *= 4;
                goldGain /= 2;
                break;
            case TYPE_MEDKIT:
                guaranteedPolish += 25;
                break;
            case TYPE_FLASHLIGHT:
                xpGain /= 2;
                goldGain *= 4;
                break;
            case TYPE_MAP:
                xpGain *= 6;
                goldGain = 0;
                guaranteedPolish -= 10;
                break;
            case TYPE_KEY:
                xpGain = 0;
                goldGain *= 6;
                guaranteedPolish += 10;
                break;
            case TYPE_EVENT:
                xpGain *= 2;
                goldGain *= 2;
                break;
            case TYPE_EPIC:
                xpGain *= 4;
                goldGain *= 4;
                guaranteedPolish += 5;
                break;
            case TYPE_LEGENDARY:
                xpGain *= 8;
                goldGain *= 8;
                guaranteedPolish += 10;
                break;
            case TYPE_MISC:
                xpGain = 4;
                goldGain = 0;
                break;
        }
        tier = rng.nextInt(101) + guaranteedPolish;
        if(posOrNeg) {
            if (tier < 15) { // 15%
                tierString = "Broken";
                goldGain /= 4;
                xpGain /= 4;
            } else if (tier < 40) { // 25%
                tierString = "Damaged";
                goldGain /= 2;
                xpGain /= 2;
            } else if (tier < 85) { // 40%
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
