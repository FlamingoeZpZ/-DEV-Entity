package dev.dbdh.Discord.Listeners.Economy;

import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Data;
import dev.dbdh.Discord.Utilities.EconomyUtilities;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageEmbedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class SuccessfulBump extends ListenerAdapter {

    public void onGuildMessageEmbed(GuildMessageEmbedEvent event){
        Color color = new Color();
        Data data = new Data();
        EconomyUtilities ecu = new EconomyUtilities();
        EmbedBuilder eb = new EmbedBuilder();
        if(event.getMessageEmbeds().get(0).toData().getString("description").contains("<@") && event.getMessageEmbeds().get(0).toData().getString("description").contains(">")){
            String embedDescription = event.getMessageEmbeds().get(0).toData().getString("description");
            String[] embedParts = embedDescription.split(",");

            Member member = event.getGuild().getMembersByName(embedParts[0], true).get(0);

            ecu.addCoins(event, member.getId(), 100);

            eb.setDescription("Successfully gave " + member.getAsMention() + " 100 coins for bumping the server");
            eb.setColor(color.getRandomColor());
            eb.setTimestamp(Instant.now());
            eb.setFooter("Entity Bumping Bribe", data.getSelfAvatar(event));

            event.getChannel().sendMessage(eb.build()).queue((message) -> {
                message.delete().queueAfter(15, TimeUnit.SECONDS);
                data.getLogChannel(event).sendMessage(eb.build()).queue();
            });

        }
    }

}
