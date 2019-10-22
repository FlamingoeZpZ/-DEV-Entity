package dev.dbdh.Discord.Listeners.Information;

import dev.dbdh.Discord.Utilities.Data;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class LookingToPlayOLD extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        Data data = new Data();
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        Role PC = event.getJDA().getRoleById("541091501908951041");
        Role PS4 = event.getJDA().getRoleById("606527283582468106");
        Role XBOX = event.getJDA().getRoleById("606527228074786851");
        Role MOBILE = event.getJDA().getRoleById("606531352690688035");
        if (args[0].equalsIgnoreCase(data.getPrefix() + "Match")) {
            if (args[1].equalsIgnoreCase(data.getPrefix() + "PC")) {// || (event.getMember().getGame().getName().equalsIgnoreCase("Dead by Daylight") && event.getMember().getRoles().contains(PC))
                event.getChannel().sendMessage(event.getAuthor().getAsMention() + " is now looking to play! This user looking for matches on ***__COMPUTER__***! Come join, " + PC.getAsMention() + "! \n" + event.getAuthor().getAsMention() + " has been given LookingToPlayPC").queue();
                event.getMember().getGuild().addRoleToMember(event.getMember(), PC).queue();
            }
            if (args[1].equalsIgnoreCase(data.getPrefix() + "PS4")) {
                event.getChannel().sendMessage(event.getAuthor().getAsMention() + " is now looking to play! This user looking for matches on ***__PLAYSTATION__***! Come join, " + PS4.getAsMention() + "! \n" + event.getAuthor().getAsMention() + " has been given LookingToPlayPS4").queue();
                event.getMember().getGuild().addRoleToMember(event.getMember(), PS4).queue();
            }
            if (args[1].equalsIgnoreCase(data.getPrefix() + "XBOX")) {
                event.getChannel().sendMessage(event.getAuthor().getAsMention() + " is now looking to play! This user looking for matches on ***__XBOX__***! Come join, " + XBOX.getAsMention() + "! \n" + event.getAuthor().getAsMention() + " has been given LookingToPlayXBox").queue();
                event.getMember().getGuild().addRoleToMember(event.getMember(), XBOX).queue();
            }
            if (args[1].equalsIgnoreCase(data.getPrefix() + "MOBILE")) {
                event.getChannel().sendMessage(event.getAuthor().getAsMention() + " is now looking to play! This user looking for matches on ***__MOBILE__***! Come join, " + MOBILE.getAsMention() + "! \n" + event.getAuthor().getAsMention() + " has been given LookingToPlayMobile").queue();
                event.getMember().getGuild().addRoleToMember(event.getMember(), MOBILE).queue();
            }
            if (args[1].equalsIgnoreCase(data.getPrefix() + "STOP")) {
                event.getChannel().sendMessage(event.getAuthor().getAsMention() + " is __no longer__ looking to play!").queue();
                event.getMember().getGuild().removeRoleFromMember(event.getMember(), PC).queue();
                event.getMember().getGuild().removeRoleFromMember(event.getMember(), XBOX).queue();
                event.getMember().getGuild().removeRoleFromMember(event.getMember(), PS4).queue();
                event.getMember().getGuild().removeRoleFromMember(event.getMember(), MOBILE).queue();
            }
        }
    }
}
