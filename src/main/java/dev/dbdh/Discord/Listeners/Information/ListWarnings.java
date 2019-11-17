package dev.dbdh.Discord.Listeners.Information;

import com.mongodb.client.MongoCollection;
import com.mongodb.util.JSON;
import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Data;
import dev.dbdh.Discord.Utilities.Database;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bson.Document;

import java.time.Instant;

import static com.mongodb.client.model.Filters.eq;

public class ListWarnings extends ListenerAdapter {

    Data data = new Data();

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        Color color = new Color();
        Database db = new Database();
        EmbedBuilder eb = new EmbedBuilder();
        if(args[0].equalsIgnoreCase(data.getPrefix() + "warnings")){
            if(args.length < 2){
                db.connect();
                MongoCollection<Document> members = db.getCollection("members");
                Document member = members.find(eq("memberId", event.getMember().getUser().getId())).first();
                String warnings = JSON.serialize(member.get("warnings"));
                System.out.println(warnings);
                eb.setDescription("Warnings for " + event.getMember().getAsMention() + "");
                eb.setTimestamp(Instant.now());
                eb.setFooter("Warnings List", data.getSelfAvatar(event));
                db.close();
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
