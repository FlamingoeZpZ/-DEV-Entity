package dev.dbdh.Discord.Listeners.Economy;

import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Data;
import dev.dbdh.Discord.Utilities.EconomyUtilities;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class Daily extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        Color color = new Color();
        Data data = new Data();
        EconomyUtilities ecu = new EconomyUtilities();
        EmbedBuilder eb = new EmbedBuilder();
        if (args[0].equalsIgnoreCase(data.getPrefix() + "daily")) {
            if (ecu.isCooldownReady(event, event.getMember().getUser().getId(), "daily")) {
                ecu.resetCooldown(event, event.getMember().getUser().getId(), "daily");
                int upper = 250;
                int lower = 5;
                int r = (int) (Math.random() * (upper - lower)) + lower;
                ecu.addCoins(event, event.getMember().getUser().getId(), r);
                eb.setDescription("You opened your daily and got " + r + " coins!");
                eb.setColor(color.getRandomColor());
                eb.setTimestamp(Instant.now());
                eb.setFooter("Entity Daily Coins", data.getSelfAvatar(event));

                event.getChannel().sendMessage(eb.build()).queue((message) -> {
                    message.delete().queueAfter(15, TimeUnit.SECONDS);
                    eb.clear();
                });
            } else {
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm MM-dd-YYYY");
                String formattedDate = formatter.format(Date.from(Instant.ofEpochSecond(ecu.getCooldown(event, event.getMember().getUser().getId(), "daily"))));
                eb.setDescription("Your daily cooldown is not ready.");//\nTry again at " + formattedDate);
                eb.setColor(color.getRandomColor());
                eb.setTimestamp(Instant.now());
                eb.setFooter("Entity Daily Cooldown not ready", data.getSelfAvatar(event));

                event.getChannel().sendMessage(eb.build()).queue((message) -> {
                    message.delete().queueAfter(15, TimeUnit.SECONDS);
                    eb.clear();
                });
            }
        }
    }
}
