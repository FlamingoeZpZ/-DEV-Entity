package dev.dbdh.Discord.Listeners.Information;

import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Data;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Help extends ListenerAdapter {
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        Data data = new Data();
        Color color = new Color();
        String [] args = event.getMessage().getContentRaw().split("\\s+");

        if(args[0].equalsIgnoreCase(data.getPrefix() +"help")
            || args[0].equalsIgnoreCase(data.getPrefix() +"info")
                || args[0].equalsIgnoreCase(data.getPrefix() +"i")){ // Terms used to access this command
            EmbedBuilder help = new EmbedBuilder();
            help.setAuthor("Help & Info for " + event.getAuthor().getName(), null, event.getGuild().getIconUrl());
            help.setDescription("The ***prefix for "+ event.getAuthor().getName() +" is "+data.getPrefix()+"***"
                    + "\nThis is a list of all commands and features we currently  implemented into " + event.getAuthor().getName()
                    + "\n```Tools``` **~Help/~Info/~I** > Returns this message and explains how to use the bot."
                    + "\n**Match (Mobile/PC/PS4/XBOX)** @'s all players with the looking to play role, and grants you the looking to play ."
                    + "\n **"+ event.getGuild().getTextChannelById("629510813518004244")+"** > To allow uses to explicitly assign self roles, giving freedom and simplicity."
                    + "\n```Server Change Events```Randomized embeds for when people join the server\nLog for bot updates."
                    + "\n```Our goals``` Currency, Fun events, Raffling system, ~~Join Event~~, Tournament logger."
                    + "\n```Want to help us or learn to create your own bot?``` Message " + event.getGuild().getOwner().getAsMention() + " and join (here)[https://discord.gg/759C4zV] if you're interested in learning to make a bot!" );
            help.setColor(color.getRandomColor());
            help.setFooter("Created by:" + event.getGuild().getMemberById("79693184417931264").getAsMention() + event.getGuild().getMemberById("235502382358724611").getAsMention() +"\n Made with Java and MongoDB for " + event.getGuild().getName(), event.getAuthor().getAvatarUrl());
        }
    }
}
