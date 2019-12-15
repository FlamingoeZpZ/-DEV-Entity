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

    Data data = new Data();

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        Color color = new Color();
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
                    eb.setFooter("Insufficient Arguments | Invalid Member", data.getSelfAvatar(event));
                    eb.setTimestamp(Instant.now());

                    event.getChannel().sendMessage(eb.build()).queue((message) -> {
                        event.getMessage().delete().queueAfter(20, TimeUnit.SECONDS);
                        message.delete().queueAfter(20, TimeUnit.SECONDS);
                        eb.clear();
                    });
                } else if (args.length < 3) {
                    eb.setDescription("You haven't specified a severity level for the warning");
                    eb.setColor(color.getRandomColor());
                    eb.setFooter("Insufficient Arguments | Missing Severity Level", data.getSelfAvatar(event));
                    eb.setTimestamp(Instant.now());

                    event.getChannel().sendMessage(eb.build()).queue((message) -> {
                        event.getMessage().delete().queueAfter(20, TimeUnit.SECONDS);
                        message.delete().queueAfter(20, TimeUnit.SECONDS);
                        eb.clear();
                    });
                } else if (args.length < 4) {
                    eb.setDescription("You haven't specified a reason for the warning");
                    eb.setColor(color.getRandomColor());
                    eb.setFooter("Insufficient Arguments | Invalid Reason");
                    eb.setTimestamp(Instant.now());

                    event.getChannel().sendMessage(eb.build()).queue((message) -> {
                        event.getMessage().delete().queueAfter(20, TimeUnit.SECONDS);
                        message.delete().queueAfter(20, TimeUnit.SECONDS);
                        eb.clear();
                    });
                } else if (args.length >= 4) {
                    Member mentioned = event.getMessage().getMentionedMembers().get(0);
                    String reason = Arrays.stream(args).skip(3).collect(Collectors.joining(" "));
                    db.connect();
                    MongoCollection<Document> members = db.getCollection("members");
                    BasicDBObject member = new BasicDBObject("memberId", mentioned.getUser().getId());
                    BasicDBObject newWarning = new BasicDBObject("reason", reason).append("severityLevel", args[2]).append("author", event.getMember().getUser().getName() + "#" + event.getMember().getUser().getDiscriminator());
                    BasicDBObject updateDoc = new BasicDBObject("$push", new BasicDBObject("warnings", newWarning));
                    members.updateOne(member, updateDoc);
                    db.close();

                    eb.setDescription("You have warned " + mentioned.getAsMention() + "\n\nReason:\n```\n " + reason + "\nSeverity: " + args[2] + "\n```");
                    eb.setColor(color.getRandomColor());
                    eb.setFooter("Entity Warning", data.getSelfAvatar(event));
                    eb.setTimestamp(Instant.now());

                    success.setDescription(event.getMember().getAsMention() + " has warned " + mentioned.getAsMention() + "\n\nReason:\n```\n" + reason + "\nSeverity: " + args[2] + "\n```");
                    success.setColor(color.getRandomColor());
                    success.setFooter("Entity Warning", data.getSelfAvatar(event));
                    success.setTimestamp(Instant.now());

                    warned.setDescription("You've been warned by " + event.getMember().getAsMention() + "\n\nReason:\n```\n" + reason + "\nSeverity: " + args[2] + "\n```");
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

    public String getName(){
        return "Warn";
    }

    public String getDescription() {
        return "Warns the specified member for the specified reason";
    }

    public String getShortDescription() {
        return "Warn someone";
    }

    public String getRequiredRoles() {
        return "Owner, Developer, Administrator, Head Moderator, Moderator";
    }

    public String getCommandSyntax() {
        return "```\n" + data.getPrefix() + "warn {@member} {severity 1-5} [Descriptive reason for warning]\n```";
    }

    public boolean isDisabled() {
        return false;
    }

}
