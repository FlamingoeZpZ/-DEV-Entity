package dev.dbdh.Discord.Utilities;

import com.mongodb.client.MongoCollection;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
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
}
