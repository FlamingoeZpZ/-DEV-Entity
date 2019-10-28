package dev.dbdh.Discord.Listeners.Economy;

import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Data;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;

public class Shop {
    Color color = new Color(); // Each shop has different colour identifiers
    EmbedBuilder eb = new EmbedBuilder();
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] shopAliases = {"s", "shop", "store", "market"};
        String[] chestAliases = {"Chest", "Crate", "Box"};
        String[] chaseAliases = {"Chase", "Hunt", "Run"};
        Data data = new Data();
        String args[] = event.getMessage().getContentRaw().split("\\s+");
        String command = data.getPrefix() + args[0];

        if (Arrays.stream(shopAliases).anyMatch( command::equalsIgnoreCase)) {
            if(args.length < 4) {
                eb.setFooter("Entity Shop System " + data.getSelfAvatar(event)); // Global ending
                eb.setDescription("Insufficient Arguments.\n\n```\n" + data.getPrefix() + "shop <chest/chase>");

            } else if(args.length == 3){
                if(Arrays.stream(chestAliases).anyMatch(args[1]::equalsIgnoreCase)){

                } else if (Arrays.stream(chaseAliases).anyMatch(args[1]::equalsIgnoreCase)){

                }

            }
            EmbedBuilder Shop = new EmbedBuilder();

            Shop.setTitle("Entity Shop for the " + event.getGuild().getName());
            Shop.setFooter("Entity Shop System", event.getAuthor().getAvatarUrl());

            if (Arrays.stream(chestAliases).anyMatch(args[1]::equalsIgnoreCase)) {
            }
            else if (Arrays.stream(chaseAliases).anyMatch(args[1]::equalsIgnoreCase)) {
            }
            event.getChannel().sendMessage(Shop.build());
        }
    }
    public void getChestCommands(){
        eb.setTitle("");
        eb.setDescription("");
        eb.setColor(color.yellow);

    }
    public void getChaseCommands(){
        eb.setTitle("");
        eb.setDescription("");
        eb.setColor(color.deepRed);
    }
    public void getDefaultCommands(){
        eb.setTitle("");
        eb.setDescription("");
        //eb.setColor(color.white); add me
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
