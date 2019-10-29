package dev.dbdh.Discord.Listeners.Economy;

import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Data;
import dev.dbdh.Discord.Utilities.Aliases;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;

public class Shop {
    Color color = new Color(); // Each shop has different colour identifiers
    EmbedBuilder eb = new EmbedBuilder();
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Aliases Al = new Aliases();
        Data data = new Data();
        String args[] = event.getMessage().getContentRaw().split("\\s+");
        String command = data.getPrefix() + args[0];
        if (Arrays.stream(Al.shopAliases).anyMatch( command::equalsIgnoreCase)) {
            
    }
}

/*
Shop.setDescription("How to use the " + event.getGuild().getName() + " shop"
                    + data.getPrefix() + "shop <Type> <ID> || <> means required\n **Types:**"
                    + "\nChest: Contains perks used for increasing luck and prizes in the ~chest command\n"
                    + "```\nID: 1 = Pharmacy (Increases coins earned)\n"
                    + "ID: 2 = Ace in the Hole (Increases XP Gained)\n"
                    + "ID: 3 = Plunder's Instinct (Increases amount of chests opened at a time)\n```"
                    + "\nChase: Contains perks and boosters used for increasing luck and rewards in the ~chase command\n"
                    + "``ID: 1 = Quick and Quiet (Allows for extra mistakes)``\n"
                    + "``ID: 2 = Dead Hard (Increases XP Gain)``\n"
                    + "``ID: 3 = Decisive Strike(Increases Gold Gain)``"
                    + "``ID: 3 = Decisive Strike(Increases Gold Gain)``"
                    */
