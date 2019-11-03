package dev.dbdh.Discord.Listeners.Economy;

import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Data;
import dev.dbdh.Discord.Utilities.Aliases;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.Arrays;

public class Shop extends ListenerAdapter {
    Color color = new Color(); // Each shop has different colour identifiers
    EmbedBuilder eb = new EmbedBuilder();

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String args[] = event.getMessage().getContentRaw().split("\\s+");
        Aliases Al = new Aliases();
        Color color = new Color();
        Data data = new Data();
        String command = data.getPrefix() + args[0];
        EmbedBuilder eb = new EmbedBuilder();
        if (Arrays.stream(Al.shopAliases).anyMatch(command::equalsIgnoreCase)) {
            //Shop Perk 3 Ace in the hole
            if (args.length < 4) {
                eb.setDescription("*Insufficient terms for the 'Shop' command*\nShop command usage: " + data.getPrefix() + "shop <Item Type> <Amount> <Name/ID>" +
                        "\n Item Types: Perks | Chests | ~~Tools~~\n Amount: Enter the amount desired; PERKS CAP AT 3 | CHESTS CAP AT 200" +
                        "\n Name/ID __Perks:__ 1 | ace in the hole, 2 | pharmacy" );
                eb.setColor(color.getRandomColor());

            }
        }
    }
}
/*
 * Shop.setDescription("How to use the " + event.getGuild().getName() + " shop"
 * + data.getPrefix() + "shop <Type> <ID> || <> means required\n **Types:**" +
 * "\nChest: Contains perks used for increasing luck and prizes in the ~chest command\n"
 * + "```\nID: 1 = Pharmacy (Increases coins earned)\n" +
 * "ID: 2 = Ace in the Hole (Increases XP Gained)\n" +
 * "ID: 3 = Plunder's Instinct (Increases amount of chests opened at a time)\n```"
 * +
 * "\nChase: Contains perks and boosters used for increasing luck and rewards in the ~chase command\n"
 * + "``ID: 1 = Quick and Quiet (Allows for extra mistakes)``\n" +
 * "``ID: 2 = Dead Hard (Increases XP Gain)``\n" +
 * "``ID: 3 = Decisive Strike(Increases Gold Gain)``"
 */
