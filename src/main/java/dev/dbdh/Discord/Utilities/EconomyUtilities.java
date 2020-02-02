package dev.dbdh.Discord.Utilities;

import com.mongodb.client.MongoCollection;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageEmbedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateNameEvent;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.time.Instant;

import static com.mongodb.client.model.Filters.eq;

public class EconomyUtilities {
    Database db = new Database();

    private final long freeChestCooldownMili = 300000; // 5 min
    private final long dailyCooldownMili = 86400000; // 5 min
    private final long chaseCooldownMili = 300000; // 5 min

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
        MongoCollection<Document> members = db.getCollection("members");
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
        db.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        Integer currentLevel = Integer.parseInt(member.get("level").toString());
        db.close();
        return currentLevel;
    }

    public void addXP(GuildMessageReceivedEvent event, String memberID, Integer xp) {
        db.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        Integer currentXP = Integer.parseInt(member.get("xp").toString());
        Bson newMemberDoc = new Document("xp", currentXP + xp);
        Bson updateMemberDoc = new Document("$set", newMemberDoc);
        members.findOneAndUpdate(member, updateMemberDoc);
        db.close();
    }

    public void removeXP(GuildMessageReceivedEvent event, String memberID, Integer xp) {
        db.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        Integer currentXP = Integer.parseInt(member.get("xp").toString());
        Bson newMemberDoc = new Document("xp", currentXP - xp);
        Bson updateMemberDoc = new Document("$set", newMemberDoc);
        members.findOneAndUpdate(member, updateMemberDoc);
        db.close();
    }

    public Integer getXP(GuildMessageReceivedEvent event, String memberID) {
        db.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        Integer currentXP = Integer.parseInt(member.get("xp").toString());
        db.close();
        return currentXP;
    }
    //Is used like a default
    public void addItem(String memberID, String name){
        addItem(memberID, name, 1);
    }
    public void addItem(String memberID, String name, int count) {
        db.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        int chestCount = member.getInteger("items." + name);
        Bson newMemberDoc = new Document("items." + name, chestCount + count);
        Bson updateMemberDoc = new Document("$set", newMemberDoc);
        members.findOneAndUpdate(member, updateMemberDoc);
        db.close();
    }
    //Is used like a default
    public void removeItem(String memberID, String name){
        removeItem(memberID, name, 1);
    }

    public void removeItem(String memberID, String name, int count) {
        db.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        int chestCount = member.getInteger("items." + name);
        Bson newMemberDoc = new Document("items." + name, chestCount - count);
        Bson updateMemberDoc = new Document("$set", newMemberDoc);
        members.findOneAndUpdate(member, updateMemberDoc);
        db.close();
    }
    //Accesses the Databases terms in the "Items" Array
    public Integer getItemCount(String memberID, String itemName) {
        Database.connect();
        MongoCollection<Document> members = Database.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        Integer chestCount = member.getInteger("items." + itemName);
        Database.close();
        return chestCount;
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
    public int getCooldown(GuildMessageReceivedEvent event, String memberID, String type){
        Database.connect();
        int cooldownTime = 0;
        MongoCollection<Document> members = Database.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        if(type.equalsIgnoreCase("freeBasicCooldown")) {
            cooldownTime = (int)(member.getLong("freeBasicCooldown") + freeChestCooldownMili - System.currentTimeMillis());
        } else if(type.equalsIgnoreCase("dailyCooldown")){
            cooldownTime = (int)(member.getLong("dailyCooldown") + dailyCooldownMili - System.currentTimeMillis());
        } else if(type.equalsIgnoreCase("chaseCooldown")){
            cooldownTime = (int)(member.getLong("chaseCooldown") + chaseCooldownMili - System.currentTimeMillis());
        }
        //if(cooldownTime < 0)
            //cooldownTime = 0;
        return cooldownTime;
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
}
