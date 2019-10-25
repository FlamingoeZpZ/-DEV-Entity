package dev.dbdh.Discord.Listeners.Miscellaneous;


import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import dev.dbdh.Discord.Utilities.Counter;
import dev.dbdh.Discord.Utilities.Data;
import dev.dbdh.Discord.Utilities.Database;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bson.Document;

import static com.mongodb.client.model.Filters.eq;

public class Join extends ListenerAdapter {
    public void onGuildMemberJoin(GuildMemberJoinEvent event){
        Counter counter = new Counter();
        Data data = new Data();
        Database db = new Database();
        EmbedBuilder eb = new EmbedBuilder();
        if(!event.getMember().getUser().isBot()) {
            db.connect();
            MongoCollection<Document> members = db.getCollection("members");
            if (members.find(eq("memberID", event.getMember().getUser().getId())).first() == null) {
                    Document perksActive = new Document(new BasicDBObject("aceInTheHole", 0).append("pharmacy", 0).append("plunderers", 0));
                    Document memberInfo = new Document(new BasicDBObject("memberId", event.getMember().getUser().getId()).append("memberName", event.getMember().getUser().getName() + "#" + event.getMember().getUser().getDiscriminator()).append("balance", 0).append("perksActive", perksActive).append("eventWins", 0));
                    members.insertOne(memberInfo);
            }
            db.close();
        }
    }
}
