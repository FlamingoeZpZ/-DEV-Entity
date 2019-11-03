package dev.dbdh.Discord.Listeners.Miscellaneous;

import com.mongodb.client.MongoCollection;
import dev.dbdh.Discord.Utilities.Database;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bson.Document;
import org.bson.conversions.Bson;

import static com.mongodb.client.model.Filters.eq;

public class TestDate extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        Database db = new Database();
        if(args[0].equalsIgnoreCase("printshit")){
            db.connect();
            System.out.println(System.currentTimeMillis());
            MongoCollection<Document> members = db.getCollection("members");
            Document member = members.find(eq("memberId", event.getMember().getId())).first();
            Document cooldowns = (Document) member.get("cooldowns");
            long daily = cooldowns.getLong("daily");
            System.out.println(daily);
            db.close();
        }
    }
}
