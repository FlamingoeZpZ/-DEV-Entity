package dev.dbdh.Discord.Listeners.Economy;

import dev.dbdh.Discord.Utilities.Color;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

import java.util.Arrays;

public class Shop {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] shopAliases = {"s", "shop", "store", "market"};
        String[] Chest = {"Chest", "Crate", "Box"};
        String[] Chase = {"Chase", "Hunt", "Run"};
        String args[] = event.getMessage().getContentRaw().split("\\s+");
        Color color = new Color(); // Each shop has different colour identifiers
        boolean complete = false;
        if(Arrays.stream(shopAliases).anyMatch(args[0]::equalsIgnoreCase)){
            EmbedBuilder Shop = new EmbedBuilder();
            Shop.setTitle("Entity Shop for the " + event.getGuild().getName());
            Shop.setDescription("How to use the " + event.getGuild().getName() + " shop"
                                + "!~shop <Type> <ID> || <> means required\n **Types:**"
                                + "\nChest: Contains perks used for increasing luck and prizes in the ~chest command\n"
                                + "``ID: 1 = Pharmacy (Increases coins earned)``\n"
                                + "``ID: 2 = Ace in the Hole (Increases XP Gained)``\n"
                                + "``ID: 3 = Plunder's Instinct (Increases amount of chests opened at a time)``"
                                + "\nChase: Contains perks and boosters used for increasing luck and rewards in the ~chase command\n"
                                + "``ID: 1 = Quick and Quiet (Allows for extra mistakes)``\n"
                                + "``ID: 2 = Dead Hard (Increases XP Gain)``\n"
                                + "``ID: 3 = Decisive Strike(Increases Gold Gain)``"
            );
            Shop.setFooter("Entity Shop System", event.getAuthor().getAvatarUrl());

            if(Arrays.stream(Chest).anyMatch(args[1]::equalsIgnoreCase)) {
                switch (Integer.parseInt(args[2])) {
                    case 1 :
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    default:
                        Shop.setDescription("You did not enter the correct amount of arguments, "
                                + "\nor the ID you are trying to refrence does not exist");
                        break;

                }
            }
            else if(Arrays.stream(Chase).anyMatch(args[1]::equalsIgnoreCase)) {
                switch (Integer.parseInt(args[2])) {
                    case 1 :
                        Shop.setDescription("");
                        break;
                    case 2:
                        break;
                    case 3:
                        break;
                    default:
                        Shop.setDescription("You did not enter the correct amount of arguments, "
                                + "\nor the ID you are trying to refrence does not exist");
                    break;
                }
            }
            if(complete==false){

            }
            event.getChannel().sendMessage(Shop.build());
        }

    }
}
