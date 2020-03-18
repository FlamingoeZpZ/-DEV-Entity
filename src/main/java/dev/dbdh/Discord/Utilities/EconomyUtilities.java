package dev.dbdh.Discord.Utilities;

import com.mongodb.client.MongoCollection;
import dev.dbdh.Discord.Listeners.Fun.ChestGame.Item;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.bson.Document;
import org.bson.conversions.Bson;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import static com.mongodb.client.model.Filters.eq;

public class EconomyUtilities {
    private Database db = new Database();

    private final long freeChestCooldownMili = 300000; // 5 min
    private final long dailyCooldownMili = 86400000; // 1 day
    private final long chaseCooldownMili = 300000; // 5 min

    public void editCoins(String memberID, int coinChange) {
        Database.connect();
        MongoCollection<Document> members = Database.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        long balance = Long.parseLong(member.get("balance").toString());
        Bson newMemberDoc = new Document("balance", balance + coinChange);
        Bson updateMemberDoc = new Document("$set", newMemberDoc);
        members.findOneAndUpdate(member, updateMemberDoc);
        Database.close();
    }

    public void editLevel(String memberID, int modifyLevels) { //Niche as it directly changed the level instead of adding or subtracting.
        Database.connect();
        MongoCollection<Document> members = Database.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        Bson newMemberDoc = new Document("level", modifyLevels);
        Bson updateMemberDoc = new Document("$set", newMemberDoc);
        members.findOneAndUpdate(member, updateMemberDoc);
        Database.close();
    }

    public double getXPMultipliers(GuildMessageReceivedEvent event, int gameMode){
        double Boost = 1; // Multiply by this number
        Database.connect();
        MongoCollection<Document> ServerEvent = Database.getCollection("event");
        //Global boosters first
        if(ServerEvent.countDocuments() > 0){
           Document ServerBoost = ServerEvent.find(eq("ServerID", event.getGuild().getId())).first(); // Check if valid?
           Document GlobalBoost = ServerEvent.find(eq("Master")).first(); // Check if valid?
           Boost += ServerBoost.getInteger("Xmultiplier");
           Boost += GlobalBoost.getInteger("Xmultiplier");
        }
        for (Role r : event.getMember().getRoles()){
            switch (r.getId()) {
                case "634878331728297996"://Adept
                    Boost += 0.05;
                    break;
                case "552578223972810753"://Rank 1
                    Boost += 0.1;
                    break;
                case "634879348767457281":// Entities Champion
                    Boost += 0.15;
                    break;
                case "638944062736433173":// Nitro Booster
                    Boost += 0.25;
                    break;
            }
        }
        /*
        MongoCollection<Document> members = Database.getCollection("members");
        MongoCollection<Document> shopItems = Database.getCollection("shopItems");

        Document curMember = members.find(eq("memberId", event.getMember().getId())).first();
        Document itemsDoc = (Document) curMember.get("items");
        switch (gameMode){
            case 0: // Chest
                //Document shopItem = itemsDoc.getInteger()
                break;
            case 1: // Feed and Grow: Survivors
                break;
            case 2: // Lil' Killers
                break;
        }
        */
        Database.close();
        return Boost;
    }

    public double getCoinMultipliers(){
        double Boost = 1;
        return Boost;
    }

    public void editXP(GuildMessageReceivedEvent event, String memberID, long xp) {
        long XPChange = getXP(memberID) + xp;
        if(XPChange < 0)
            XPChange = 0;
        int EditLevelTo = getLevel(memberID);
        if(xp > 0 && EditLevelTo != 100) { // Skips over this part to prevent losing your level
            int levelCost;
            while (true) {
                levelCost =  120 * (int)(Math.pow(EditLevelTo, 2)  + 1000); // Equivalent to y = 120(1)^2\ + 1000
                if (XPChange >= levelCost) {
                    ++EditLevelTo; //Adds level but in doing so, increases cost
                    XPChange -= levelCost;
                } else
                    break;
            }
            Database.connect();
            MongoCollection<Document> members = Database.getCollection("members");
            Document member = members.find(eq("memberId", memberID)).first();
            Bson newMemberXPDoc = new Document("experience", XPChange);
            Bson updateMemberXPDoc = new Document("$set", newMemberXPDoc);
            members.findOneAndUpdate(member, updateMemberXPDoc);
            editLevel(memberID, EditLevelTo);
        }
    }
    public void editItem(String memberID, String perkName, int change) {
        Database.connect();
        MongoCollection<Document> members = Database.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        Document itemsDoc = (Document) member.get("items");
        int prvPerkLevel = itemsDoc.getInteger(perkName);
        Bson newMemberDoc = new Document("items." + perkName, prvPerkLevel + change); // use . to access objects in arrays ( BSON )
        Bson updateMemberDoc = new Document("$set", newMemberDoc);
        members.findOneAndUpdate(member, updateMemberDoc);
        Database.close();
    }
    public void editHistoryItem(String memberID, String perkName, int change){
        Database.connect();
        MongoCollection<Document> members = Database.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        Document itemsDoc = (Document) member.get("chestsOpened");
        int prvPerkLevel = itemsDoc.getInteger(perkName);
        Bson newMemberDoc = new Document("chestsOpened." + perkName, prvPerkLevel + change); // use . to access objects in arrays ( BSON )
        Bson updateMemberDoc = new Document("$set", newMemberDoc);
        members.findOneAndUpdate(member, updateMemberDoc);
        Database.close();
    }
    public long getCoins(String memberID) {
        Database.connect();
        MongoCollection<Document> members = Database.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        long bal = member.getLong("balance");
        Database.close();
        return bal;
    }
    public long getXP(String memberID) {
        Database.connect();
        MongoCollection<Document> members = Database.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        long XP = member.getLong("experience");
        Database.close();
        return XP;
    }
    public int getItemCount(String memberID, String itemName) {
        Database.connect();
        MongoCollection<Document> members = Database.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        Document itemsDoc = (Document) member.get("items");
        int items = Integer.parseInt(itemsDoc.get(itemName.toUpperCase()).toString());
        Database.close();
        return items;
    }

    public int getLevel(String memberID) {
        Database.connect();
        MongoCollection<Document> members = Database.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        int currentLevel = member.getInteger("level");
        Database.close();
        return currentLevel;
    }

    public int getPerkLevel(String memberID, String perkName) {
        Database.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        int perkLevel = Integer.parseInt(member.get("items." + perkName).toString());
        Database.close();
        return perkLevel;
    }

    public void updateMemberOnDatabase(String memberID, String newMemberName) {
        Database.connect();
        MongoCollection<Document> members = Database.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        Bson newMemberDoc = new Document("memberName", newMemberName);
        Bson updateMemberDoc = new Document("$set", newMemberDoc);
        members.findOneAndUpdate(member, updateMemberDoc);
        Database.close();
    }

    public void removeMemberFromDatabase(String memberID) {
        Database.connect();
        MongoCollection<Document> members = Database.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        members.deleteOne(member);
        Database.close();
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

    public boolean isCooldownReady(String memberID, String type) {
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
        Database.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        if(type.equalsIgnoreCase("freeChest")){
            Bson newMemberDoc = new Document("freeBasicCooldown", System.currentTimeMillis());
            Bson updateMemberDoc = new Document("$set", newMemberDoc);
            members.findOneAndUpdate(member, updateMemberDoc);
        } else if(type.equalsIgnoreCase("daily")){
            Bson newMemberDoc = new Document("dailyCooldown", System.currentTimeMillis());
            Bson updateMemberDoc = new Document("$set", newMemberDoc);
            members.findOneAndUpdate(member, updateMemberDoc);
        } else if(type.equalsIgnoreCase("chase")){
            Bson newMemberDoc = new Document("chaseCooldown", System.currentTimeMillis());
            Bson updateMemberDoc = new Document("$set", newMemberDoc);
            members.findOneAndUpdate(member, updateMemberDoc);

        }
        Database.close();
    }

    public void setRoleAssignMessageID(Message message){
        Database.connect();
        MongoCollection<Document> guild = Database.getCollection("guild");
        Document guildDoc = guild.find().first();
        Bson newDoc = new Document("roleAssignMessageID", message.getId());
        Bson updateDoc = new Document("$set", newDoc);
        guild.findOneAndUpdate(guildDoc, updateDoc);
        Database.close();
    }
    public void openChest(GuildMessageReceivedEvent event, EmbedBuilder eb, List<Item> items, boolean freeChest, String chestType, int repeatChance, boolean forceShiny) {
        String Member = event.getMember().getId();
        Random rng = new Random();
        List<Item> sortedItems = new ArrayList<>();
        boolean isShiny = false;
        boolean ItemFound;
        int pos;
        int GennedNum;
        int maxRange = 1;
        int minRange = 0;
        int count;
        int retryRNG = 0;
        int shinyChance = 1000; //(1 out of 1000)
        int playerNum;
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
        chestType = chestType.toUpperCase() + "_CHEST"; //SO all chests are recognized properly

        while (repeatChance >= retryRNG)
            {
                count = minRange; // sets the count to the bottom of the list
                //Gets the range and spits out a random number
                GennedNum = rng.nextInt(maxRange - minRange) + minRange;
                playerNum = rng.nextInt(shinyChance);
                for (Item sortedItem : sortedItems) {
                    count += Math.abs(sortedItem.drawChance); // 9 + 8 + 4 + 6 + 11 + 12
                    if (count >= GennedNum) { // adds together all terms from least to most until count is bigger than genned num THIS check when true will stop the loop
                        eb.setColor(Color.deepRed);
                        if(sortedItem.posOrNeg) { // Checks if the item is a bad (false) or good (true) item
                            isShiny = 1 == playerNum; // Will only calculate if
                            if (isShiny || forceShiny) {
                                eb.setColor(Color.gold);
                                sortedItem.goldGain *= 4;
                                sortedItem.xpGain *= 4;
                                eb.appendDescription("\n\n***" + event.getAuthor().getAsMention() + " FOUND " + sortedItem.rarityString + "SHINY " + sortedItem.name + event.getAuthor().getAsMention() + " earned " + sortedItem.goldGain + "c and " + sortedItem.xpGain + "XP***");
                            }
                            else // Runs default for if shiny is false
                            {
                                eb.setColor(Color.darkGreen);
                                eb.appendDescription("\n\n" + event.getAuthor().getAsMention() + " found " + sortedItem.rarityString + sortedItem.name + event.getAuthor().getAsMention() + " earned " + sortedItem.goldGain + "c and " + sortedItem.xpGain + "XP");
                            }
                        }
                        else
                        {
                            eb.setColor(Color.deepRed);
                            eb.appendDescription("\n\n" + event.getAuthor().getAsMention() + " found " + sortedItem.rarityString + sortedItem.name + event.getAuthor().getAsMention() + " lost " + sortedItem.goldGain + "c and " + sortedItem.xpGain + "XP");
                        }
                        eb.setImage(sortedItem.URL);
                        editXP(event, event.getMember().getId(), sortedItem.xpGain);
                        editCoins(event.getMember().getId(), sortedItem.goldGain);
                        eb.setFooter("Entity Chest Game | Free Basic Chests every 5 minutes " + Data.getPrefix() + "chest or " + Data.getPrefix() + "chest basic", Data.getSelfAvatar(event));
                        event.getChannel().sendMessage(eb.build()).queue();
                        eb.clear();
                        break;
                    }
                }
                if (!freeChest) {
                    editItem(Member, chestType, -1);
                }
                editHistoryItem(Member, chestType, 1);
                freeChest = true;
                repeatChance = (repeatChance / 2) + rng.nextInt(11) - 5; //Changes chests odds of opening multiple times
                retryRNG = rng.nextInt(100); //Changes the chests odds of 'wanting' to be opened multiple times
            }
    }

}
