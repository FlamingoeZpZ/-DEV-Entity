package dev.dbdh.Discord.Utilities;

import com.mongodb.client.MongoCollection;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateNameEvent;
import org.bson.BsonArray;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

import static com.mongodb.client.model.Filters.eq;

public class EconomyUtilities {
    Database db = new Database();

    public void addCoins(GuildMessageReceivedEvent event, String memberID, Integer coins) {
        db.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        Integer balance = Integer.parseInt(member.get("balance").toString());
        Bson newMemberDoc = new Document("balance", balance + coins);
        Bson updateMemberDoc = new Document("$set", newMemberDoc);
        members.findOneAndUpdate(member, updateMemberDoc);
        db.close();
    }

    public void removeCoins(GuildMessageReceivedEvent event, String memberID, Integer coins) {
        db.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();

        Integer balance = Integer.parseInt(member.get("balance").toString());
        Bson newMemberDoc = new Document("balance", balance - coins);
        Bson updateMemberDoc = new Document("$set", newMemberDoc);
        members.findOneAndUpdate(member, updateMemberDoc);

        db.close();
    }

    public Integer getCoins(GuildMessageReceivedEvent event, String memberID) {
        db.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        Integer balance = Integer.parseInt(member.get("balance").toString());
        db.close();
        return balance;
    }

    public void addLevel(GuildMessageReceivedEvent event, String memberID, Integer level) {
        db.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        Integer currentlevel = Integer.parseInt(member.get("level").toString());
        Bson newMemberDoc = new Document("level", currentlevel + level);
        Bson updateMemberDoc = new Document("$set", newMemberDoc);
        members.findOneAndUpdate(member, updateMemberDoc);
        db.close();
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

    public void addChest(GuildMessageReceivedEvent event, String memberID, String type, Integer count) {
        db.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        Integer chestCount = member.getInteger("chestsOwned." + type);
        Bson newMemberDoc = new Document("chestsOwned." + type, chestCount + count);
        Bson updateMemberDoc = new Document("$set", newMemberDoc);
        members.findOneAndUpdate(member, updateMemberDoc);
        db.close();
    }

    public void removeChest(GuildMessageReceivedEvent event, String memberID, String type, Integer count) {
        db.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        Integer chestCount = member.getInteger("chestsOwned." + type);
        Bson newMemberDoc = new Document("chestsOwned." + type, chestCount - count);
        Bson updateMemberDoc = new Document("$set", newMemberDoc);
        members.findOneAndUpdate(member, updateMemberDoc);
        db.close();
    }

    public Integer getChests(GuildMessageReceivedEvent event, String memberID, String type) {
        db.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        Integer chestCount = member.getInteger("chestsOwned." + type);
        db.close();
        return chestCount;
    }

    public void addPerkLevel(GuildMessageReceivedEvent event, String memberID, String perkName) {
        db.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();

        Integer perkLevel = Integer.parseInt(member.get("perksActive." + perkName).toString());
        Bson newMemberDoc = new Document("perksActive." + perkName, perkLevel++); // use . to access objects in arrays ( BSON )
        Bson updateMemberDoc = new Document("$set", newMemberDoc);
        members.findOneAndUpdate(member, updateMemberDoc);
        db.close();
    }

    public void removePerkLevel(GuildMessageReceivedEvent event, String memberID, String perkName) {
        db.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();

        Integer perkLevel = Integer.parseInt(member.get("perksActive." + perkName).toString());
        Bson newMemberDoc = new Document("perksActive." + perkName, perkLevel--); // use . to access objects in arrays ( BSON )
        Bson updateMemberDoc = new Document("$set", newMemberDoc);
        members.findOneAndUpdate(member, updateMemberDoc);
        db.close();
    }

    public Integer getPerkLevel(GuildMessageReceivedEvent event, String memberID, String perkName) {
        db.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        Integer perkLevel = Integer.parseInt(member.get("perksActive." + perkName).toString());
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

    public boolean isCooldownReady(GuildMessageReceivedEvent event, String memberID, String type) {
        db.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        Document cooldowns = (Document) member.get("cooldowns");
        if(type.equalsIgnoreCase("freeChest")) {
            if ((cooldowns.getLong("freeChest") + 300000L) >= System.currentTimeMillis()) {
                db.close();
                return true;
            } else {
                db.close();
                return false;
            }
        } else if(type.equalsIgnoreCase("daily")){
            if((cooldowns.getLong("daily") + 86400000L) >= System.currentTimeMillis()){
                db.close();
                return true;
            } else {
                db.close();
                return false;
            }
        } else if(type.equalsIgnoreCase("chase")){
            if((cooldowns.getLong("chase") + 300000L) >= System.currentTimeMillis()){
                db.close();
                return true;
            } else {
                db.close();
                return false;
            }
        }
        db.close();
        return false;
    }

    public void resetCooldown(GuildMessageReceivedEvent event, String memberID, String type){
        db.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        if(type.equalsIgnoreCase("freeChest")){
            Bson newMemberDoc = new Document("freeChest", System.currentTimeMillis());
            Bson updateMemberDoc = new Document("$set", newMemberDoc);
            members.findOneAndUpdate(member, updateMemberDoc);
            db.close();
        } else if(type.equalsIgnoreCase("daily")){
            Bson newMemberDoc = new Document("daily", System.currentTimeMillis());
            Bson updateMemberDoc = new Document("cooldowns.$set", newMemberDoc);
            members.findOneAndUpdate(member, updateMemberDoc);
            db.close();
        } else if(type.equalsIgnoreCase("chase")){
            Bson newMemberDoc = new Document("chase", System.currentTimeMillis());
            Bson updateMemberDoc = new Document("$set", newMemberDoc);
            members.findOneAndUpdate(member, updateMemberDoc);
            db.close();
        }
    }
}
