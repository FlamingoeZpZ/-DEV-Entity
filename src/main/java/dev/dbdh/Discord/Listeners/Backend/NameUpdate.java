package dev.dbdh.Discord.Listeners.Backend;

import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Data;
import dev.dbdh.Discord.Utilities.Database;
import dev.dbdh.Discord.Utilities.EconomyUtilities;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.user.update.UserUpdateNameEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.Instant;

public class NameUpdate extends ListenerAdapter {

    public void onUserUpdateName(UserUpdateNameEvent event){
        Color color = new Color();
        Data data = new Data();
        Database db = new Database();
        EconomyUtilities ecu = new EconomyUtilities();
        EmbedBuilder eb = new EmbedBuilder();
        if(!event.getUser().isBot()){

            ecu.updateMemberOnDatabase(event.getUser().getId(), event.getNewName() + "#" + event.getUser().getDiscriminator());

            eb.setDescription(event.getOldName() + "#" + event.getUser().getDiscriminator() + " has changed their name on Discord. \nNew Name: " + event.getNewName() + "#" + event.getUser().getDiscriminator() + "\n\nUpdating database entry.");
            eb.setColor(color.getRandomColor());
            eb.setTimestamp(Instant.now());
            eb.setFooter("Entity Name Update", data.getSelfAvatar(event));

            event.getJDA().getGuildById("537736395268161537").getTextChannelById("635607577794969644").sendMessage(eb.build()).queue();

        }
    }
}
