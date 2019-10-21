package dev.dbdh.Discord.Listeners.Miscellaneous;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import dev.dbdh.Discord.Utilities.Database;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class AddToDatabaseOnJoin extends ListenerAdapter {

    public void onGuildMemberJoin(GuildMemberJoinEvent event){
        if(!event.getMember().getUser().isBot()){
            Database db = new Database();
            db.connect();
            MongoCollection<Document> members = db.getCollection("members");
            Document member = members.find(eq("memberId", event.getMember().getUser().getId())).first();
            if(member.isEmpty()){
                Document perksActive = new Document(new BasicDBObject("aceInTheHole", 0).append("pharmacy", 0).append("plunderers", 0));
                Document memberInfo = new Document(new BasicDBObject("memberId", event.getMember().getUser().getId()).append("memberName", event.getMember().getUser().getName() + "#" + event.getMember().getUser().getDiscriminator()).append("balance", 0).append("perksActive", perksActive).append("eventWins", 0));
                members.insertOne(memberInfo);
            }
        }
    }
}
