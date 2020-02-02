package dev.dbdh.Discord.Utilities;

import com.mongodb.client.MongoCollection;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageEmbedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.events.user.update.UserUpdateNameEvent;
import org.bson.Document;
import org.bson.conversions.Bson;

import java.util.Random;

public class Data {
    Random RNJesus = new Random();
    private static final Database db = new Database();

    public static String getPrefix() {
        String prefix = "!~";
        db.connect();
        MongoCollection<Document> guild = db.getCollection("guild");
        prefix = guild.find().first().getString("prefix");
        db.close();
        return prefix;
    }

    public static void setPrefix(String prefix) {
        db.connect();
        MongoCollection<Document> guild = db.getCollection("guild");
        String oldPrefix = guild.find().first().getString("prefix");
        Bson filter = new Document("prefix", oldPrefix);
        Bson newPrefix = new Document("prefix", prefix);
        Bson updatePrefix = new Document("$set", newPrefix);
        guild.updateOne(filter, updatePrefix);
        db.close();
    }
    public int getJoinNumber(){
        int i = RNJesus.nextInt(23);
        return i;
    }

    public TextChannel getJoinChannel(GuildMemberJoinEvent event){
        return event.getGuild().getTextChannelById("640005609760227371");
    }

    public String getSelfAvatar(GuildMemberJoinEvent event){
        return event.getJDA().getSelfUser().getEffectiveAvatarUrl();
    }

    public String getSelfAvatar(GuildMemberLeaveEvent event){
        return event.getJDA().getSelfUser().getEffectiveAvatarUrl();
    }

    public String getSelfAvatar(GuildMessageReceivedEvent event){
        return event.getJDA().getSelfUser().getEffectiveAvatarUrl();
    }

    public String getSelfAvatar(UserUpdateNameEvent event){
        return event.getJDA().getSelfUser().getEffectiveAvatarUrl();
    }

    public String getSelfAvatar(GuildMessageReactionAddEvent event){
        return event.getJDA().getSelfUser().getEffectiveAvatarUrl();
    }

    public String getSelfAvatar(GuildMessageReactionRemoveEvent event){
        return event.getJDA().getSelfUser().getEffectiveAvatarUrl();
    }

    public String getSelfAvatar(GuildMessageEmbedEvent event){
        return event.getJDA().getSelfUser().getEffectiveAvatarUrl();
    }
    public String intToTime(int milliInt){
        String phrase = "";
        phrase += (milliInt % 60) + " seconds, ";
        milliInt /= 60;
        phrase += (milliInt % 60) + " minutes, ";
        milliInt /= 60;
        phrase += (milliInt % 24) + " hours and ";
        milliInt /= 24;
        phrase += milliInt + " days";
        return phrase;
    }

    public String getSelfName(GuildMemberJoinEvent event){
        return event.getJDA().getSelfUser().getName();
    }

    public String getGuildName(GuildMemberJoinEvent event){
        return event.getGuild().getName();
    }

    public TextChannel getLogChannel(GuildMessageReceivedEvent event){
        return event.getGuild().getTextChannelById("635607577794969644");
    }

    public TextChannel getLogChannel(GuildMemberJoinEvent event){
        return event.getGuild().getTextChannelById("635607577794969644");
    }

    public TextChannel getLogChannel(GuildMemberLeaveEvent event){
        return event.getGuild().getTextChannelById("635607577794969644");
    }
    public TextChannel getLogChannel(GuildMessageEmbedEvent event){
        return event.getGuild().getTextChannelById("635607577794969644");
    }
}
