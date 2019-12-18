package dev.dbdh.Discord.Listeners.Information;

import com.mongodb.BasicDBObject;
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
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Report extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        Color color = new Color();
        Data data = new Data();
        Database db = new Database();
        EmbedBuilder eb = new EmbedBuilder();
        EmbedBuilder reported = new EmbedBuilder();
        if (args[0].equalsIgnoreCase(data.getPrefix() + "report")) {
            if(args.length < 2) {
                eb.setDescription("You haven't specified a member to report");
                eb.setColor(color.getRandomColor());
                eb.setFooter("Insufficient Arguments | Invalid Member");
                eb.setTimestamp(Instant.now());

                event.getChannel().sendMessage(eb.build()).queue((message) -> {
                    event.getMessage().delete().queueAfter(20, TimeUnit.SECONDS);
                    message.delete().queueAfter(20,TimeUnit.SECONDS);
                    eb.clear();
                });
            } else if(args.length < 3) {
                eb.setDescription("You haven't specified a reason for the report");
                eb.setColor(color.getRandomColor());
                eb.setFooter("Insufficient Arguments | Invalid Reason");
                eb.setTimestamp(Instant.now());

                event.getChannel().sendMessage(eb.build()).queue((message) -> {
                    event.getMessage().delete().queueAfter(20, TimeUnit.SECONDS);
                    message.delete().queueAfter(20, TimeUnit.SECONDS);
                    eb.clear();
                });
            } else if (args.length > 3) {
                String reason = Arrays.stream(args).skip(2).collect(Collectors.joining(" "));
                Member mentioned = event.getMessage().getMentionedMembers().get(0);

                db.connect();
                MongoCollection<Document> members = db.getCollection("members");
                BasicDBObject member = new BasicDBObject("memberId", mentioned.getUser().getId());
                BasicDBObject newReport = new BasicDBObject("reason", reason).append("author", event.getMember().getUser().getName() + "#" + event.getMember().getUser().getDiscriminator());
                BasicDBObject updateDoc = new BasicDBObject("$push", new BasicDBObject("reports", newReport));
                members.updateOne(member, updateDoc);
                db.close();

                eb.setDescription("You have reported " + mentioned.getAsMention() + "\n\nReason:\n```\n" + reason + "\n```");
                eb.setColor(color.getRandomColor());
                eb.setFooter("Entity Report");
                eb.setTimestamp(Instant.now());

                reported.setDescription(event.getMember().getAsMention() + " has reported " + mentioned.getAsMention() + "\n\nReason:\n```\n" + reason + "\n```");
                reported.setColor(color.getRandomColor());
                reported.setFooter("Entity Report Log");
                reported.setTimestamp(Instant.now());

                event.getChannel().sendMessage(eb.build()).queue((message) -> {
                    event.getMessage().delete().queueAfter(20, TimeUnit.SECONDS);
                    message.delete().queueAfter(20, TimeUnit.SECONDS);
                    eb.clear();
                    data.getLogChannel(event).sendMessage(reported.build()).queue((message1) -> {
                       reported.clear();
                    });
                });
            }
        }

    }

}

