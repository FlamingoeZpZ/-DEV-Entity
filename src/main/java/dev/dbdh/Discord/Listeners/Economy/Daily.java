package dev.dbdh.Discord.Listeners.Economy;

import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Data;
import dev.dbdh.Discord.Utilities.EconomyUtilities;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Daily extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        Color color = new Color();
        Data data = new Data();
        EconomyUtilities ecu = new EconomyUtilities();
        EmbedBuilder eb = new EmbedBuilder();
        EmbedBuilder success = new EmbedBuilder();
        if (args[0].equalsIgnoreCase(data.getPrefix() + "daily")) {
            if (ecu.isCooldownReady(event, event.getMember().getUser().getId(), "daily")) {
                ecu.resetCooldown(event, event.getMember().getUser().getId(), "daily");
                int upper = 250;
                int lower = 5;
                int coins = (int) (Math.random() * (upper - lower)) + lower;
                ecu.addCoins(event, event.getMember().getUser().getId(), coins);
                eb.setDescription("You opened your daily and got " + coins + " coins!");
                eb.setColor(color.getRandomColor());
                eb.setTimestamp(Instant.now());
                eb.setFooter("Entity Daily Coins", data.getSelfAvatar(event));

                success.setDescription(event.getAuthor().getAsMention() + " has claimed their daily coins allowance and recieved " + coins + " coins");
                success.setColor(color.getRandomColor());
                success.setTimestamp(Instant.now());
                success.setFooter("Entity Daily Coin Allowance", data.getSelfAvatar(event));

                event.getChannel().sendMessage(eb.build()).queue((message) -> {
                    message.delete().queueAfter(15, TimeUnit.SECONDS);
                    eb.clear();
                });
            } else {
                Date date = new Date();
                String dateInString = String.valueOf(ecu.getCooldown(event, event.getMember().getUser().getId(), "dailyCooldown"));
                date.setTime(Long.parseLong(dateInString + "L"));
                SimpleDateFormat formatter = new SimpleDateFormat("HH:mm MM-dd-YYYY");
                String formattedDate = formatter.format(date);
                eb.setDescription("Your daily cooldown is not ready.\nTry again at " + formattedDate + " UTC");
                eb.setColor(color.errorRed);
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
