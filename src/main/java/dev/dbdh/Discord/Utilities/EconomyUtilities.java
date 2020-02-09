package dev.dbdh.Discord.Utilities;

import com.mongodb.client.MongoCollection;
import dev.dbdh.Discord.Listeners.Fun.ChestGame.Chest;
import dev.dbdh.Discord.Listeners.Fun.ChestGame.Item;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageEmbedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateNameEvent;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.mongodb.client.model.Filters.eq;

public class EconomyUtilities {
    Database db = new Database();

    private final long freeChestCooldownMili = 300000; // 5 min
    private final long dailyCooldownMili = 86400000; // 1 day
    private final long chaseCooldownMili = 300000; // 5 min
    private final int PERKS = 0;
    private final int CHESTS= 1;

    public void addCoins(GuildMessageReceivedEvent event, String memberID, Integer coins) {
        Database.connect();
        MongoCollection<Document> members = Database.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        int balance = Integer.parseInt(member.get("balance").toString());
        Bson newMemberDoc = new Document("balance", balance + coins);
        Bson updateMemberDoc = new Document("$set", newMemberDoc);
        members.findOneAndUpdate(member, updateMemberDoc);
        Database.close();
    }

    public void addCoins(GuildMessageEmbedEvent event, String memberID, Integer coins) {
        Database.connect();
        MongoCollection<Document> members = Database.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        int balance = Integer.parseInt(member.get("balance").toString());
        Bson newMemberDoc = new Document("balance", balance + coins);
        Bson updateMemberDoc = new Document("$set", newMemberDoc);
        members.findOneAndUpdate(member, updateMemberDoc);
        Database.close();
    }

    public void removeCoins(GuildMessageReceivedEvent event, String memberID, Integer coins) {
        Database.connect();
        MongoCollection<Document> members = Database.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        int balance = Integer.parseInt(member.get("balance").toString());
        Bson newMemberDoc = new Document("balance", balance - coins);
        Bson updateMemberDoc = new Document("$set", newMemberDoc);
        members.findOneAndUpdate(member, updateMemberDoc);
        Database.close();
    }

    public Integer getCoins(GuildMessageReceivedEvent event, String memberID) {
        Database.connect();
        MongoCollection<Document> members = Database.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        int balance = Integer.parseInt(member.get("balance").toString());
        Database.close();
        return balance;
    }

    public void addLevel(GuildMessageReceivedEvent event, String memberID, Integer level) {
        Database.connect();
        MongoCollection<Document> members = Database.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        int currentlevel = Integer.parseInt(member.get("level").toString());
        Bson newMemberDoc = new Document("level", currentlevel + level);
        Bson updateMemberDoc = new Document("$set", newMemberDoc);
        members.findOneAndUpdate(member, updateMemberDoc);
        Database.close();
    }

    public void removeLevel(GuildMessageReceivedEvent event, String memberID, Integer level) {
        db.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        Integer currentLevel = Integer.parseInt(member.get("level").toString());
        Bson newMemberDoc = new Document("level", currentLevel - level);
        Bson updateMemberDoc = new Document("$set", newMemberDoc);
        members.findOneAndUpdate(member, updateMemberDoc);
        db.close();
    }

    public Integer getLevel(GuildMessageReceivedEvent event, String memberID) {
        Database.connect();
        MongoCollection<Document> members = Database.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        Integer currentLevel = Integer.parseInt(member.get("level").toString());
        Database.close();
        return currentLevel;
    }

    public void addXP(GuildMessageReceivedEvent event, String memberID, long xp) {
        Database.connect();
        MongoCollection<Document> members = Database.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        long XPChange = getXP(event, memberID);
        int currentLevel = getLevel(event, memberID);
        int levelCost;
        while (true) {
            levelCost = (int)(Math.pow(currentLevel * 100, 2) * 1.8);
            event.getChannel().sendMessage(xp + " >= " + levelCost).queue();
            if (XPChange >= levelCost) {
                ++currentLevel; //Adds level but in doing so, increases cost
                XPChange -= levelCost;
            }
            else
                break;
        }
        Bson newMemberLevelDoc = new Document("level", currentLevel);
        Bson updateMemberLevelDoc = new Document("$set", newMemberLevelDoc);
        members.findOneAndUpdate(member, updateMemberLevelDoc);
        Bson newMemberXPDoc = new Document("experience", XPChange);
        Bson updateMemberXPDoc = new Document("$set", newMemberXPDoc);
        members.findOneAndUpdate(member, updateMemberXPDoc);
        Database.close();
    }

    public void removeXP(GuildMessageReceivedEvent event, String memberID, Integer xp) {
        Database.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        int currentXP = Integer.parseInt(member.get("xp").toString());
        Bson newMemberDoc = new Document("xp", currentXP - xp);
        Bson updateMemberDoc = new Document("$set", newMemberDoc);
        members.findOneAndUpdate(member, updateMemberDoc);
        Database.close();
    }

    public long getXP(GuildMessageReceivedEvent event, String memberID) {
        Database.connect();
        MongoCollection<Document> members = Database.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        long XP = member.getLong("experience");
        Database.close();
        return XP;
    }
    //Is used like a default
    public void addItem(String memberID, String name){
        addItem(memberID, name, 1);
    }
    public void addItem(String memberID, String name, int count) {
        Database.connect();
        MongoCollection<Document> members = Database.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        int chestCount = member.getInteger("items." + name);
        Bson newMemberDoc = new Document("items." + name, chestCount + count);
        Bson updateMemberDoc = new Document("$set", newMemberDoc);
        members.findOneAndUpdate(member, updateMemberDoc);
        Database.close();
    }
    //Is used like a default
    public void removeItem(String memberID, String name) {
        removeItem(memberID, name, 1);
    }

    public void removeItem(String memberID, String name, int count) {
        Database.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        int chestCount = member.getInteger("items." + name);
        Bson newMemberDoc = new Document("items." + name, chestCount - count);
        Bson updateMemberDoc = new Document("$set", newMemberDoc);
        members.findOneAndUpdate(member, updateMemberDoc);
        Database.close();
    }
    //Accesses the Databases terms in the "Items" Array
    public int getItemCount(String memberID, String itemName) {
        return getItemCount(memberID,  itemName, 0);
    }
    public int getItemCount(String memberID, String itemName, int itemType) {
        Database.connect();
        MongoCollection<Document> members = Database.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        Document itemsDoc = (Document) member.get("items");
        int items = 0;
        switch (itemType) {
            case PERKS:
                 items = itemsDoc.getInteger(itemName.toUpperCase());
                break;
            case CHESTS:
                 items = itemsDoc.getInteger(itemName.toUpperCase() + "_CHEST");
                break;
        }
        Database.close();
        return items;
    }

    public void addItem(GuildMessageReceivedEvent event, String memberID, String perkName) {
        db.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();

        Integer perkLevel = Integer.parseInt(member.get("items." + perkName).toString());
        Bson newMemberDoc = new Document("items." + perkName, perkLevel++); // use . to access objects in arrays ( BSON )
        Bson updateMemberDoc = new Document("$set", newMemberDoc);
        members.findOneAndUpdate(member, updateMemberDoc);
        db.close();
    }

    public void removePerkLevel(GuildMessageReceivedEvent event, String memberID, String perkName) {
        db.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();

        Integer perkLevel = Integer.parseInt(member.get("items." + perkName).toString());
        Bson newMemberDoc = new Document("items." + perkName, perkLevel--); // use . to access objects in arrays ( BSON )
        Bson updateMemberDoc = new Document("$set", newMemberDoc);
        members.findOneAndUpdate(member, updateMemberDoc);
        db.close();
    }

    public Integer getPerkLevel(GuildMessageReceivedEvent event, String memberID, String perkName) {
        db.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        Integer perkLevel = Integer.parseInt(member.get("items." + perkName).toString());
        db.close();
        return perkLevel;
    }

    public void updateMemberOnDatabase(UserUpdateNameEvent event, String memberID, String newMemberName) {
        db.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        Bson newMemberDoc = new Document("memberName", newMemberName);
        Bson updateMemberDoc = new Document("$set", newMemberDoc);
        members.findOneAndUpdate(member, updateMemberDoc);
        db.close();
    }

    public void removeMemberFromDatabase(GuildMemberLeaveEvent event, String memberID) {
        db.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        members.deleteOne(member);
        db.close();
    }
    public long getCooldown(String memberID, String type){
        Database.connect();
        long cooldownTime = 0;
        MongoCollection<Document> members = Database.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        if(type.equalsIgnoreCase("freeBasicCooldown")) {
            cooldownTime = member.getLong("freeBasicCooldown") + freeChestCooldownMili - System.currentTimeMillis();
        } else if(type.equalsIgnoreCase("dailyCooldown")){
            cooldownTime = member.getLong("dailyCooldown") + dailyCooldownMili - System.currentTimeMillis();
        } else if(type.equalsIgnoreCase("chaseCooldown")){
            cooldownTime = member.getLong("chaseCooldown") + chaseCooldownMili - System.currentTimeMillis();
        }
        Database.close();
        if(cooldownTime < 0)
            cooldownTime = 0;
        return cooldownTime;
    }

    public boolean isMemberInDB(String memberID){
        Database.connect();
        MongoCollection<Document> members = Database.getCollection("members");
        try {
            members.find(eq("memberId", memberID)).first();
        }
        catch (Exception e){
            return  false;
        }
        Database.close();
        return true;
    }

    public boolean isCooldownReady(GuildMessageReceivedEvent event, String memberID, String type) {

        Database.connect();
        MongoCollection<Document> members = Database.getCollection("members"); // make this segment public as a .getDBMember;
        Document member = members.find(eq("memberId", memberID)).first();
        if(type.equalsIgnoreCase("freeChest")) { //It's getting the cooldown time > 0 and then adding free chest cooldown??? which is bigger than current time?
            if ((member.getLong("freeBasicCooldown") + freeChestCooldownMili) <= System.currentTimeMillis()) {
                Database.close();
                return true;
            } else {
                Database.close();
                return false;
            }
        } else if(type.equalsIgnoreCase("daily")){
            if((member.getLong("dailyCooldown") + dailyCooldownMili) <= System.currentTimeMillis()){
                Database.close();
                return true;
            } else {
                Database.close();
                return false;
            }
        } else if(type.equalsIgnoreCase("chase")){
            if((member.getLong("chaseCooldown") + chaseCooldownMili) <= System.currentTimeMillis()){
                Database.close();
                return true;
            } else {
                Database.close();
                return false;
            }
        }
        Database.close();
        return false;
    }
    public void resetCooldown(GuildMessageReceivedEvent event, String memberID, String type){
        db.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        if(type.equalsIgnoreCase("freeChest")){
            Bson newMemberDoc = new Document("freeBasicCooldown", System.currentTimeMillis());
            Bson updateMemberDoc = new Document("$set", newMemberDoc);
            members.findOneAndUpdate(member, updateMemberDoc);
            db.close();
        } else if(type.equalsIgnoreCase("daily")){
            Bson newMemberDoc = new Document("dailyCooldown", System.currentTimeMillis());
            Bson updateMemberDoc = new Document("$set", newMemberDoc);
            members.findOneAndUpdate(member, updateMemberDoc);
            db.close();
        } else if(type.equalsIgnoreCase("chase")){
            Bson newMemberDoc = new Document("chaseCooldown", System.currentTimeMillis());
            Bson updateMemberDoc = new Document("$set", newMemberDoc);
            members.findOneAndUpdate(member, updateMemberDoc);
            db.close();
        }
    }

    public void setRoleAssignMessageID(Message message){
        db.connect();
        MongoCollection<Document> guild = db.getCollection("guild");
        Document guildDoc = guild.find().first();
        Bson newDoc = new Document("roleAssignMessageID", message.getId());
        Bson updateDoc = new Document("$set", newDoc);
        guild.findOneAndUpdate(guildDoc, updateDoc);
        db.close();
    }
    public void openChest(GuildMessageReceivedEvent event, EmbedBuilder eb, List<Item> items, boolean freeChest, String chestType, int repeatChance, boolean forceShiny) {
        Random rng = new Random();
        List<Item> sortedItems = new ArrayList<>();
        boolean isShiny = false;
        boolean ItemFound;
        int pos;
        int GennedNum;
        int maxRange = 1;
        int minRange = 0;
        int count;
        //sorts the items
        for (Item item : items) {
            if (item.posOrNeg) {
                maxRange += item.drawChance;
            } else {
                minRange += item.drawChance;
            }
            if (!sortedItems.isEmpty()) { // If the list is not empty
                ItemFound = false;
                pos = 0;
                for (Item sI : sortedItems) {
                    if (item.drawChance < sI.drawChance) {
                        ItemFound = true;
                        break;
                    } // If it's smaller we break at the index and add it
                    else if (item.drawChance > sortedItems.get(sortedItems.size() - 1).drawChance) { // if it's bigger than the last index, we skip all the steps
                        break;
                    }
                    pos++;
                }
                if (ItemFound) sortedItems.add(pos, item);
                else sortedItems.add(item);
            } else {
                sortedItems.add(item);
            }
        }
        int randomNum =rng.nextInt(100);
        do{
            repeatChance = (repeatChance / 2) - 5;
            GennedNum = rng.nextInt(maxRange - minRange) + minRange;
            if(forceShiny)
                isShiny = GennedNum == rng.nextInt(maxRange); // Sets shiny to a random POSITIVE value in the list making shiny bads impossible
            count = minRange; // sets the count to the bottom of the list
            //Gets the range and spits out a random number
            for (Item sortedItem : sortedItems) {
                count += Math.abs(sortedItem.drawChance); // 9 + 8 + 4 + 6 + 11 + 12
                if (count >= GennedNum) { // adds together all terms from least to most until count is bigger than genned num
                    eb.setColor(Color.deepRed);
                    if (isShiny || forceShiny) {
                        eb.setColor(Color.gold);
                        sortedItem.goldGain *= 4;
                        sortedItem.xpGain *= 4;
                        eb.appendDescription("\n\n***" + event.getAuthor().getAsMention() + " FOUND " + sortedItem.rarityString + "SHINY" + sortedItem.name + event.getAuthor().getAsMention() + " earned " + sortedItem.goldGain + "c and " + sortedItem.xpGain + "XP***");
                    } else if (sortedItem.posOrNeg) {
                        eb.setColor(Color.darkGreen);
                        eb.appendDescription("\n\n" + event.getAuthor().getAsMention() + " found " + sortedItem.rarityString + sortedItem.name + event.getAuthor().getAsMention() + " earned " + sortedItem.goldGain + "c and " + sortedItem.xpGain + "XP");
                        eb.setImage(sortedItem.URL);
                        event.getChannel().sendMessage(eb.build()).queue();
                    } else {
                        eb.setColor(Color.deepRed);
                        eb.appendDescription("\n\n" + event.getAuthor().getAsMention() + " found " + sortedItem.rarityString + sortedItem.name + event.getAuthor().getAsMention() + " lost " + sortedItem.goldGain + "c and " + sortedItem.xpGain + "XP");
                        eb.setImage(sortedItem.URL);
                        event.getChannel().sendMessage(eb.build()).queue();
                    }
                    addXP(event, event.getMember().getId(), sortedItem.xpGain);
                    addCoins(event, event.getMember().getId(), sortedItem.goldGain);
                    eb.clear(); // Note: Spam embedding appending into multiple opens?
                    break;
                }
            }
            Database.connect();
            MongoCollection<Document> members = Database.getCollection("members");
            Document member = members.find(eq("memberId", event.getMember().getId())).first();
            Document itemsDoc = (Document) member.get("items");
            Document openedDoc = (Document) member.get("chestsOpened");
            if (!freeChest) {
                int chests = itemsDoc.getInteger(chestType.toUpperCase() + "_CHEST");
                Bson newMemberchestsDoc = new Document("items." + chestType.toUpperCase() + "_CHEST", --chests);
                Bson updateMemberchestsDoc = new Document("$set", newMemberchestsDoc);
                members.findOneAndUpdate(member, updateMemberchestsDoc);
            }
            freeChest = true;
            int openedchests = openedDoc.getInteger(chestType.toUpperCase() + "_CHEST");
            Bson newMemberopenedchestsDoc = new Document("chestsOpened." + chestType.toUpperCase() + "_CHEST", ++openedchests);
            Bson updateMemberopenedchestsDoc = new Document("$set", newMemberopenedchestsDoc);
            members.findOneAndUpdate(member, updateMemberopenedchestsDoc);
            Database.close();
            randomNum = rng.nextInt(100);
        }
        while (repeatChance > randomNum);
    }
}
