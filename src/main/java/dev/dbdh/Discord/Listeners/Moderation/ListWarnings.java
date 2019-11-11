package dev.dbdh.Discord.Listeners.Moderation;

import com.mongodb.client.MongoCollection;
import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Data;
import dev.dbdh.Discord.Utilities.Database;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class ListWarnings extends ListenerAdapter {

    Data data = new Data();

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        Color color = new Color();
        Database db = new Database();
        if(args[0].equalsIgnoreCase(data.getPrefix() + "warnings")){
            if(args.length < 2){
                db.connect();
                MongoCollection<Document> members = db.getCollection("members");
                Bson filter;
                Document member = members.find(eq("memberId", event.getMember().getUser().getId())).first();
                Document warnings = (Document) member.get("warnings");
                System.out.println(warnings);
            }
        }
    }

    public String getName(){
        return "Warnings";
    }

    public String getDescription() {
        return "Lists the warnings of the specified member, If no member is specified it will retrieve the warnings of the command issuer";
    }

    public String getShortDescription() {
        return "Lists the warnings of a member";
    }

    public String getRequiredRoles() {
        return "Everyone";
    }

    public String getCommandSyntax() {
        return "```\n" + data.getPrefix() + "warnings [@member]\n```";
    }

    public boolean isDisabled() {
        return false;
    }


}
