package dev.dbdh.Discord.Listeners.Economy;

import com.mongodb.client.MongoCollection;
import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Data;
import dev.dbdh.Discord.Utilities.Database;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bson.Document;

import java.time.Instant;
import java.util.Arrays;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.eq;

public class Balance extends ListenerAdapter {
    Data data = new Data();
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        Database db = new Database();
        Color color = new Color();
        EmbedBuilder eb = new EmbedBuilder();
        String[] aliases = {data.getPrefix() + "balance", data.getPrefix() + "bal", data.getPrefix() + "$"};
        if (Arrays.stream(aliases).anyMatch(args[0]::equals)) {
            if (args.length < 2) {
                db.connect();
                MongoCollection<Document> members = db.getCollection("members");
                Document member = members.find(eq("memberId", event.getMember().getUser().getId())).first();
                if (member == null) {
                    eb.setDescription("Your data was not found in the database. \nPlease notify " + event.getGuild().getMemberById("235502382358724611").getAsMention() + " or " + event.getGuild().getMemberById("79693184417931264").getAsMention() + " that your data wasn't added to the database.");
                    eb.setColor(color.errorRed);
                    eb.setTimestamp(Instant.now());
                    eb.setFooter("Entity Database Error", data.getSelfAvatar(event));
                    event.getChannel().sendMessage(eb.build()).queue((message) -> {
                        eb.clear();
                        db.close();
                    });
                } else {
                    eb.setDescription("Your current balance is: " + member.get("balance"));
                    eb.setColor(color.getRandomColor());
                    eb.setTimestamp(Instant.now());
                    eb.setFooter("Entity Balance Card", data.getSelfAvatar(event));
                    event.getChannel().sendMessage(eb.build()).queue((message) -> {
                        eb.clear();
                        db.close();
                    });
                }
            } else if(args.length >= 2) {
                String name = Arrays.stream(args).skip(1).collect(Collectors.joining(" "));
                Member mentioned = event.getGuild().getMembersByName(name, true).get(0);
                db.connect();
                MongoCollection<Document> members = db.getCollection("members");
                Document member = members.find(eq("memberId", mentioned.getUser().getId())).first();
                if (member == null) {
                    eb.setDescription("That user's data was not found in the database. \nPlease notify " + event.getGuild().getMemberById("235502382358724611").getAsMention() + " or " + event.getGuild().getMemberById("79693184417931264").getAsMention() + " that your data wasn't added to the database.");
                    eb.setColor(color.errorRed);
                    eb.setTimestamp(Instant.now());
                    eb.setFooter("Entity Database Error", data.getSelfAvatar(event));
                    event.getChannel().sendMessage(eb.build()).queue((message) -> {
                        eb.clear();
                        db.close();
                    });
                } else {
                    eb.setDescription(mentioned.getAsMention() + "'s current balance is: " + member.get("balance"));
                    eb.setColor(color.getRandomColor());
                    eb.setTimestamp(Instant.now());
                    eb.setFooter("Entity Balance Card", data.getSelfAvatar(event));
                    event.getChannel().sendMessage(eb.build()).queue((message) -> {
                        eb.clear();
                        db.close();
                    });
                }
            }
        }
    }

    public String getName(){
        return "balance";
    }

    public String getDescription(){
        return "Shows the balance of the specified member if no member is specified this will show the balance of the command issuer";
    }

    public String getShortDescription(){
        return "Shows the balance of the specified member";
    }

    public String getRolesRequired(){
        return "`Everyone`";
    }

    public String getCommandSyntax(){
        return "```\n" + data.getPrefix() + "balance {member}\n```";
    }

    public boolean isDisabled(){
        return false;
    }

}
