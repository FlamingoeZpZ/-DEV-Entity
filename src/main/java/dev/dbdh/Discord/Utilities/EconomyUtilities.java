package dev.dbdh.Discord.Utilities;

import com.mongodb.client.MongoCollection;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateNameEvent;
import org.bson.BsonArray;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;

public class EconomyUtilities {
    Database db = new Database();
    public void addCoins(GuildMessageReceivedEvent event, String memberName, Integer coins){
        String memberID = event.getGuild().getMembersByName(memberName, true).get(0).getId();

        db.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();

        Integer balance = Integer.parseInt(member.get("balance").toString());
        Bson newMemberDoc = new Document("balance", balance + coins);
        Bson updateMemberDoc = new Document("$set", newMemberDoc);
        members.findOneAndUpdate(member, updateMemberDoc);

        db.close();
    }

    public void removeCoins(GuildMessageReceivedEvent event, String memberName, Integer coins){
        String memberID = event.getGuild().getMembersByName(memberName, true).get(0).getId();
        db.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();

        Integer balance = Integer.parseInt(member.get("balance").toString());
        Bson newMemberDoc = new Document("balance", balance - coins);
        Bson updateMemberDoc = new Document("$set", newMemberDoc);
        members.findOneAndUpdate(member, updateMemberDoc);

        db.close();
    }

    public Integer getCoins(GuildMessageReceivedEvent event, String memberName){
        String memberID = event.getGuild().getMembersByName(memberName, true).get(0).getId();
        db.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        Integer balance = Integer.parseInt(member.get("balance").toString());
        db.close();
        return balance;
    }

    public void addLevel(GuildMessageReceivedEvent event, String memberName, Integer level){
        String memberID = event.getGuild().getMembersByName(memberName, true).get(0).getId();
        db.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        Integer currentlevel = Integer.parseInt(member.get("level").toString());
        Bson newMemberDoc = new Document("level", currentlevel + level);
        Bson updateMemberDoc = new Document("$set", newMemberDoc);
        members.findOneAndUpdate(member, updateMemberDoc);
        db.close();
    }

    public void removeLevel(GuildMessageReceivedEvent event, String memberName, Integer level){
        String memberID = event.getGuild().getMembersByName(memberName, true).get(0).getId();
        db.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        Integer currentLevel = Integer.parseInt(member.get("level").toString());
        Bson newMemberDoc = new Document("level", currentLevel - level);
        Bson updateMemberDoc = new Document("$set", newMemberDoc);
        members.findOneAndUpdate(member, updateMemberDoc);
        db.close();
    }

    public Integer getLevel(GuildMessageReceivedEvent event, String memberName){
        String memberID = event.getGuild().getMembersByName(memberName, true).get(0).getId();
        db.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        Integer currentLevel = Integer.parseInt(member.get("level").toString());
        db.close();
        return currentLevel;
    }

    public void addXP(GuildMessageReceivedEvent event, String memberName, Integer xp){
        String memberID = event.getGuild().getMembersByName(memberName, true).get(0).getId();
        db.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        Integer currentXP = Integer.parseInt(member.get("xp").toString());
        Bson newMemberDoc = new Document("xp", currentXP + xp);
        Bson updateMemberDoc = new Document("$set", newMemberDoc);
        members.findOneAndUpdate(member, updateMemberDoc);
        db.close();
    }

    public void removeXP(GuildMessageReceivedEvent event, String memberName, Integer xp){
        String memberID = event.getGuild().getMembersByName(memberName, true).get(0).getId();
        db.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        Integer currentXP = Integer.parseInt(member.get("xp").toString());
        Bson newMemberDoc = new Document("xp", currentXP - xp);
        Bson updateMemberDoc = new Document("$set", newMemberDoc);
        members.findOneAndUpdate(member, updateMemberDoc);
        db.close();
    }

    public Integer getXP(GuildMessageReceivedEvent event, String memberName){
        String memberID = event.getGuild().getMembersByName(memberName, true).get(0).getId();
        db.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        Integer currentXP = Integer.parseInt(member.get("xp").toString());
        db.close();
        return currentXP;
    }

    public void addChest(GuildMessageReceivedEvent event, String memberName, String type, Integer count){
        String memberID = event.getGuild().getMembersByName(memberName, true).get(0).getId();
        db.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        //Integer chestCount = Integer.parseInt(member.get(""))
    }

    public void addPerkLevel(GuildMessageReceivedEvent event, String memberName, String perkName){
        String memberID = event.getGuild().getMembersByName(memberName, true).get(0).getId();
        db.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();

        Integer perkLevel = Integer.parseInt(member.get("perksActive." + perkName).toString());
        Bson newMemberDoc = new Document("perksActive." + perkName,  perkLevel++); // use . to access objects in arrays ( BSON )
        Bson updateMemberDoc = new Document("$set", newMemberDoc);
        members.findOneAndUpdate(member, updateMemberDoc);
        db.close();
    }

    public void removePerkLevel(GuildMessageReceivedEvent event, String memberName, String perkName){
        String memberID = event.getGuild().getMembersByName(memberName, true).get(0).getId();
        db.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();

        Integer perkLevel = Integer.parseInt(member.get("perksActive." + perkName).toString());
        Bson newMemberDoc = new Document("perksActive." + perkName,  perkLevel--); // use . to access objects in arrays ( BSON )
        Bson updateMemberDoc = new Document("$set", newMemberDoc);
        members.findOneAndUpdate(member, updateMemberDoc);
        db.close();
    }

    public Integer getPerkLevel(GuildMessageReceivedEvent event, String memberName, String perkName){
        String memberID = event.getGuild().getMembersByName(memberName, true).get(0).getId();
        db.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        Integer perkLevel = Integer.parseInt(member.get("perksActive." + perkName).toString());
        db.close();
        return perkLevel;
    }

    public void updateMemberOnDatabase(UserUpdateNameEvent event, String memberID, String newMemberName){
        db.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        Bson newMemberDoc = new Document("memberName", newMemberName);
        Bson updateMemberDoc = new Document("$set", newMemberDoc);
        members.findOneAndUpdate(member, updateMemberDoc);
        db.close();
    }

    public void removeMemberFromDatabase(GuildMemberLeaveEvent event, String memberID){
        db.connect();
        MongoCollection<Document> members = db.getCollection("members");
        Document member = members.find(eq("memberId", memberID)).first();
        members.deleteOne(member);
        db.close();
    }
}
