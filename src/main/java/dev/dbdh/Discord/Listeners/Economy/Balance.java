package dev.dbdh.Discord.Listeners.Economy;

import com.mongodb.client.MongoCollection;
import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Data;
import dev.dbdh.Discord.Utilities.Database;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.time.Instant;
import java.util.Arrays;

import static com.mongodb.client.model.Filters.eq;

public class Balance extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        Data data = new Data();
        Database db = new Database();
        Color color = new Color();
        EmbedBuilder eb = new EmbedBuilder();
        String[] aliases = {data.getPrefix() + "balance", data.getPrefix() + "bal", data.getPrefix() + "$"};
        if (Arrays.stream(aliases).anyMatch(args[0]::equals)) {
            db.connect();
            MongoCollection<Document> members = db.getCollection("members");
            Document member = members.find(eq("memberId", event.getMember().getUser().getId())).first();
            if (member == null) {
                eb.setDescription("Your data was not found in the database. \nPlease notify " + event.getGuild().getMemberById("235502382358724611").getAsMention() + " or " + event.getGuild().getMemberById("79693184417931264").getAsMention() + " that your data wasn't added to the database.");
                eb.setColor(color.errorRed);
                eb.setTimestamp(Instant.now());
                eb.setFooter("Entity Database Error", data.getSelfAvatar(event));
            } else {
                eb.setDescription("Your current balance is: $" + member.getString("balance"));
                eb.setColor(color.getRandomColor());
                eb.setTimestamp(Instant.now());
                eb.setFooter("Entity Balance Card", data.getSelfAvatar(event));
                db.close();
            }
        }
    }

}
