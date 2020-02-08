package dev.dbdh.Discord.Utilities;

import com.mongodb.client.MongoCollection;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import org.bson.Document;

public class RoleCheck {

    Database db = new Database();

    public boolean isOwner(GuildMessageReceivedEvent event){
        return event.getMember().getRoles().contains(event.getGuild().getRoleById("552572692163985408"));
    }

    public boolean isAdministrator(GuildMessageReceivedEvent event){
        return event.getMember().getRoles().contains(event.getGuild().getRoleById("608869161594126346"));
    }

    public boolean isDeveloper(GuildMessageReceivedEvent event){
        if(event.getMember().getId().equals("79693184417931264") || event.getMember().getId().equals("235502382358724611")){
            return true;
        } else return false;
    }

    public boolean isHeadModerator(GuildMessageReceivedEvent event){
        return event.getMember().getRoles().contains(event.getGuild().getRoleById("608869497998278657")) || event.getMember().getRoles().contains(event.getGuild().getRoleById("661056789886009347"));
    }

    public boolean isModerator(GuildMessageReceivedEvent event){
        return event.getMember().getRoles().contains(event.getGuild().getRoleById("540745858929393689"));
    }

    public boolean isChannelOwner(GuildMessageReceivedEvent event) {
        boolean isChannelOwner = false; // wat
        db.connect();
        MongoCollection<Document> channels = db.getCollection("channels");
        channels.find();
        return isChannelOwner;
    }
}
