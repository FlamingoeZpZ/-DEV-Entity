package dev.dbdh.Discord.Listeners.Miscellaneous;

import dev.dbdh.Discord.Utilities.Database;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import static com.mongodb.client.model.Filters.eq;

public class TestDate extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        Database db = new Database();
        if(args[0].equalsIgnoreCase("printshit")){
            db.connect();
            System.out.println(System.currentTimeMillis());
            System.out.println(db.getCollection("members").find(eq("memberId", event.getMember().getId())).first().getLong("cooldowns.daily"));
            System.out.println(db.getCollection("members").find(eq("memberId", event.getMember().getId())).first().getLong("cooldowns.daily") + 86400000);
            db.close();
        }
    }
}
