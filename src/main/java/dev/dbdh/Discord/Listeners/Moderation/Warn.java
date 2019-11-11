package dev.dbdh.Discord.Listeners.Moderation;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Updates;
import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Data;
import dev.dbdh.Discord.Utilities.Database;
import dev.dbdh.Discord.Utilities.RoleCheck;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bson.Document;

import java.time.Instant;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.mongodb.client.model.Filters.eq;

public class Warn extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        Color color = new Color();
        Data data = new Data();
        Database db = new Database();
        RoleCheck rc = new RoleCheck();
        EmbedBuilder eb = new EmbedBuilder();
        EmbedBuilder warned = new EmbedBuilder();
        EmbedBuilder success = new EmbedBuilder();
        if (args[0].equalsIgnoreCase(data.getPrefix() + "warn")) {
            if (rc.isOwner(event) || rc.isDeveloper(event) || rc.isAdministrator(event) || rc.isHeadModerator(event) || rc.isModerator(event)) {
                if (args.length < 2) {
                    eb.setDescription("You haven't specified the member that you would like to warn. Please refer to `" + data.getPrefix() + "help warn` for the correct syntax");
                    eb.setColor(color.getRandomColor());
                    eb.setFooter("Insufficient Arguments", data.getSelfAvatar(event));
                    eb.setTimestamp(Instant.now());

                    event.getChannel().sendMessage(eb.build()).queue((message) -> {
                        event.getMessage().delete().queueAfter(20, TimeUnit.SECONDS);
                        message.delete().queueAfter(20, TimeUnit.SECONDS);
                        eb.clear();
                    });
                } else if (args.length < 3) {
                    eb.setDescription("You haven't specified a reason for the warning");
                    eb.setColor(color.getRandomColor());
                    eb.setFooter("Invalid Reason", data.getSelfAvatar(event));
                    eb.setTimestamp(Instant.now());

                    event.getChannel().sendMessage(eb.build()).queue((message) -> {
                        event.getMessage().delete().queueAfter(20, TimeUnit.SECONDS);
                        message.delete().queueAfter(20, TimeUnit.SECONDS);
                        eb.clear();
                    });
                } else if (args.length >= 3) {
                    Member mentioned = event.getMessage().getMentionedMembers().get(0);
                    String reason = Arrays.stream(args).skip(2).collect(Collectors.joining(" "));
                    db.connect();
                    MongoCollection<Document> members = db.getCollection("members");
                    BasicDBObject member = new BasicDBObject("memberId", mentioned.getUser().getId());
                    BasicDBObject newWarning = new BasicDBObject("reason", reason).append("author", event.getMember().getUser().getName() + "#" + event.getMember().getUser().getDiscriminator());
                    BasicDBObject updateDoc = new BasicDBObject("$push", new BasicDBObject("warnings", newWarning));
                    members.updateOne(member, updateDoc);
                    db.close();

                    eb.setDescription("You have warned " + mentioned.getAsMention() + "\n\nReason:\n```\n " + reason + "\n```");
                    eb.setColor(color.getRandomColor());
                    eb.setFooter("Entity Warning", data.getSelfAvatar(event));
                    eb.setTimestamp(Instant.now());

                    success.setDescription(event.getMember().getAsMention() + " has warned " + mentioned.getAsMention() + "\n\nReason:\n```\n" + reason + "\n```");
                    success.setColor(color.getRandomColor());
                    success.setFooter("Entity Warning", data.getSelfAvatar(event));
                    success.setTimestamp(Instant.now());

                    warned.setDescription("You've been warned by " + event.getMember().getAsMention() + "\n\nReason:\n```\n" + reason + "\n```");
                    warned.setColor(color.getRandomColor());
                    warned.setFooter("Entity Warning", data.getSelfAvatar(event));
                    warned.setTimestamp(Instant.now());

                    mentioned.getUser().openPrivateChannel().complete().sendMessage(warned.build()).queue((message) -> {
                        warned.clear();
                        event.getChannel().sendMessage(eb.build()).queue((message1) -> {
                            eb.clear();
                            message1.delete().queueAfter(30, TimeUnit.SECONDS);
                            event.getMessage().delete().queueAfter(30, TimeUnit.SECONDS);
                            data.getLogChannel(event).sendMessage(success.build()).queue();
                        });
                    });
                }
            }
        }
    }
}
