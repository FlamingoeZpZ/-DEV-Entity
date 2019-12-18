package dev.dbdh.Discord.Listeners.Information;

import dev.dbdh.Discord.Listeners.Fun.LookingToPlay;
import dev.dbdh.Discord.Listeners.Fun.Screenshare;
import dev.dbdh.Discord.Listeners.Moderation.*;
import dev.dbdh.Discord.Listeners.Settings.SetPrefix;
import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Data;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class Help extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        Data data = new Data();
        Color color = new Color();
        EmbedBuilder eb = new EmbedBuilder();

        if(args[0].equalsIgnoreCase(data.getPrefix() + "help")){
            if(args.length < 2){

                RoleAdd addrole = new RoleAdd();
                Ban ban = new Ban();
                Clear clear = new Clear();
                Kick kick = new Kick();
                LookingToPlay match = new LookingToPlay();
                Mute mute = new Mute();
                Screenshare screenshare = new Screenshare();
                SetPrefix setprefix = new SetPrefix();
                Softban softban = new Softban();
                Tempmute tempmute = new Tempmute();
                Unmute unmute = new Unmute();
                Warn warn = new Warn();

                eb.setTitle("⚙ Help");
                eb.setDescription("Commands for Entity!\n\n**Fun Commands**\n```\n" + data.getPrefix() + "screenshare --  " + screenshare.getShortDescription() + "\n" + data.getPrefix() + "match       -- " + match.getShortDescription() + "\n```\n**Moderation Commands**\n```\n" + data.getPrefix() + "addrole     --  " + addrole.getShortDescription() + "\n" + data.getPrefix() + "ban         --  " + ban.getShortDescription() + "\n" + data.getPrefix() + "clear       --  " + clear.getShortDescription() + "\n" + data.getPrefix() + "kick        --  " + kick.getShortDescription() + "\n" + data.getPrefix() + "mute        --  " + mute.getShortDescription() + "\n" + data.getPrefix() + "softban     --  " + softban.getShortDescription() + "\n" + data.getPrefix() + "tempmute    --  " + tempmute.getShortDescription() + "\n" + data.getPrefix() + "unmute      --  " + unmute.getShortDescription() + "\n" + data.getPrefix() + "warn        --  " + warn.getShortDescription() + "\n```\n**Setting Commands**\n```\n" + data.getPrefix() + "setprefix   --  " + setprefix.getShortDescription() + "\n```\n\n**For command syntax help do**\n```\n" + data.getPrefix() + "help {command}\n```");
                eb.setColor(color.getRandomColor());
                eb.setTimestamp(Instant.now());
                eb.setFooter("Entity Commands Help Menu", data.getSelfAvatar(event));

                event.getChannel().sendMessage(eb.build()).queue((message) -> {
                    message.delete().queueAfter(2, TimeUnit.MINUTES);
                    event.getMessage().delete().queueAfter(2, TimeUnit.MINUTES);
                    eb.clear();
                });

            } else if(args.length < 3){
                if(args[1].equalsIgnoreCase("addrole")){
                    RoleAdd command = new RoleAdd();
                    eb.setTitle(command.getName() + " Help");
                    eb.setColor(color.getRandomColor());
                    eb.setDescription(command.getDescription() +"\n\n **Required Roles:**\n" + command.getRequiredRoles()  + "\n\n **How to use the addrole command:**\n" + command.getCommandSyntax() + "\n**Is Disabled?** \n```\n" + command.isDisabled() + "\n```\n```\n{} | Required\n[] | Optional\n() | Optional unless previous item chosen to be used\n```");
                    eb.setFooter("Entity Ban Command Help", data.getSelfAvatar(event));

                    event.getChannel().sendMessage(eb.build()).queue((message) -> {
                        message.delete().queueAfter(30, TimeUnit.SECONDS);
                        event.getMessage().delete().queueAfter(30, TimeUnit.SECONDS);
                        eb.clear();
                    });
                }
                else if(args[1].equalsIgnoreCase("ban")){
                    Ban command = new Ban();
                    eb.setTitle(command.getName() + " Help");
                    eb.setColor(color.getRandomColor());
                    eb.setDescription(command.getDescription() +"\n\n **Required Roles:**\n" + command.getRequiredRoles()  + "\n\n **How to use the ban command:**\n" + command.getCommandSyntax() + "\n**Is Disabled?** \n```\n" + command.isDisabled() + "\n```\n```\n{} | Required\n[] | Optional\n```");
                    eb.setFooter("Entity Ban Command Help", data.getSelfAvatar(event));

                    event.getChannel().sendMessage(eb.build()).queue((message) -> {
                        message.delete().queueAfter(30, TimeUnit.SECONDS);
                        event.getMessage().delete().queueAfter(30, TimeUnit.SECONDS);
                        eb.clear();
                    });
                } else if(args[1].equalsIgnoreCase("clear")){
                    Clear command = new Clear();
                    eb.setTitle(command.getName() + " Help");
                    eb.setColor(color.getRandomColor());
                    eb.setDescription(command.getDescription() + "\n\n **Required Roles:**\n" + command.getRequiredRoles() + "\n\n **How to use the clear command:**\n" + command.getCommandSyntax() + "\n**Is Disabled?** \n```\n" + command.isDisabled() + "\n```\n```\n{} | Required\n[] | Optional\n```");
                    eb.setFooter("Entity Clear Command Help", data.getSelfAvatar(event));

                    event.getChannel().sendMessage(eb.build()).queue((message) -> {
                        message.delete().queueAfter(30, TimeUnit.SECONDS);
                        event.getMessage().delete().queueAfter(30, TimeUnit.SECONDS);
                        eb.clear();
                    });
                } else if(args[1].equalsIgnoreCase("kick")){
                    Kick command = new Kick();
                    eb.setTitle(command.getName() + " Help");
                    eb.setColor(color.getRandomColor());
                    eb.setDescription(command.getDescription() +"\n\n **Required Roles:**\n" + command.getRequiredRoles()  + "\n\n **How to use the kick command:**\n" + command.getCommandSyntax() + "\n**Is Disabled?** \n```\n" + command.isDisabled() + "\n```\n```\n{} | Required\n[] | Optional\n```");
                    eb.setFooter("Entity Kick Command Help", data.getSelfAvatar(event));

                    event.getChannel().sendMessage(eb.build()).queue((message) -> {
                        message.delete().queueAfter(30, TimeUnit.SECONDS);
                        event.getMessage().delete().queueAfter(30, TimeUnit.SECONDS);
                        eb.clear();
                    });
                } else if(args[1].equalsIgnoreCase("match")){
                    LookingToPlay command = new LookingToPlay();
                    eb.setTitle(command.getName() + " Help");
                    eb.setColor(color.getRandomColor());
                    eb.setDescription(command.getDescription() + "\n\n **Required Roles:**\n" + command.getRequiredRoles() + "\n\n **How to use the match command:**\n" + command.getCommandSyntax() + "\n**Platforms** \n```\nPC | PS4 | XBOX | SWITCH | MOBILE\n```\n**Is Disabled?** \n```\n" + command.isDisabled() + "\n```\n```\n{} | Required\n[] | Optional\n```");
                    eb.setFooter("Entity Match Command Help", data.getSelfAvatar(event));

                    event.getChannel().sendMessage(eb.build()).queue((message) -> {
                        message.delete().queueAfter(30, TimeUnit.SECONDS);
                        event.getMessage().delete().queueAfter(30, TimeUnit.SECONDS);
                        eb.clear();
                    });
                } else if(args[1].equalsIgnoreCase("mute")){
                    Mute command = new Mute();
                    eb.setTitle(command.getName() + " Help");
                    eb.setColor(color.getRandomColor());
                    eb.setDescription(command.getDescription() + "\n\n **Required Roles:**\n" + command.getRequiredRoles() + "\n\n **How to use the mute command:**\n" + command.getCommandSyntax() + "\n**Is Disabled?** \n```\n" + command.isDisabled() + "\n```\n```\n{} | Required\n[] | Optional\n```");
                    eb.setFooter("Entity Mute Command Help", data.getSelfAvatar(event));

                    event.getChannel().sendMessage(eb.build()).queue((message) -> {
                        message.delete().queueAfter(30, TimeUnit.SECONDS);
                        event.getMessage().delete().queueAfter(30, TimeUnit.SECONDS);
                        eb.clear();
                    });
                } else if(args[1].equalsIgnoreCase("screenshare")){
                    Screenshare command = new Screenshare();
                    eb.setTitle(command.getName() + " Help");
                    eb.setColor(color.getRandomColor());
                    eb.setDescription(command.getDescription() + "\n\n **Required Roles:**\n" + command.getRequiredRoles() + "\n\n **How to use the screenshare command:**\n" + command.getCommandSyntax() + "\n**Is Disabled?** \n```\n" + command.isDisabled() + "\n```\n```\n{} | Required\n[] | Optional\n```");
                    eb.setFooter("Entity Screenshare Command Help", data.getSelfAvatar(event));

                    event.getChannel().sendMessage(eb.build()).queue((message) -> {
                        message.delete().queueAfter(30, TimeUnit.SECONDS);
                        event.getMessage().delete().queueAfter(30, TimeUnit.SECONDS);
                        eb.clear();
                    });
                } else if(args[1].equalsIgnoreCase("setprefix")){
                    SetPrefix command = new SetPrefix();
                    eb.setTitle(command.getName() + " Help");
                    eb.setColor(color.getRandomColor());
                    eb.setDescription(command.getDescription() + "\n\n **Required Roles:**\n" + command.getRequiredRoles() + "\n\n **How to use the setprefix command:**\n" + command.getCommandSyntax() + "\n**Is Disabled?** \n```\n" + command.isDisabled() + "\n```\n```\n{} | Required\n[] | Optional\n```");
                    eb.setFooter("Entity SetPrefix Command Help", data.getSelfAvatar(event));

                    event.getChannel().sendMessage(eb.build()).queue((message) -> {
                        message.delete().queueAfter(30, TimeUnit.SECONDS);
                        event.getMessage().delete().queueAfter(30, TimeUnit.SECONDS);
                        eb.clear();
                    });
                } else if(args[1].equalsIgnoreCase("softban")){
                    Softban command = new Softban();
                    eb.setTitle(command.getName() + " Help");
                    eb.setColor(color.getRandomColor());
                    eb.setDescription(command.getDescription() + "\n\n **Required Roles:**\n" + command.getRequiredRoles() + "\n\n **How to use the softban command:**\n" + command.getCommandSyntax() + "\n**Is Disabled?** \n```\n" + command.isDisabled() + "\n```\n```\n{} | Required\n[] | Optional\n```");
                    eb.setFooter("Entity Softban Command Help", data.getSelfAvatar(event));

                    event.getChannel().sendMessage(eb.build()).queue((message) -> {
                        message.delete().queueAfter(30, TimeUnit.SECONDS);
                        event.getMessage().delete().queueAfter(30, TimeUnit.SECONDS);
                        eb.clear();
                    });
                }  else if(args[1].equalsIgnoreCase("tempmute")){
                    Tempmute command = new Tempmute();
                    eb.setTitle(command.getName() + " Help");
                    eb.setColor(color.getRandomColor());
                    eb.setDescription(command.getDescription() + "\n\n **Required Roles:**\n" + command.getRequiredRoles() + "\n\n **How to use the tempmute command:**\n" + command.getCommandSyntax() + "\n**Time Multipliers**\n```\nS | SECONDS\nM | MINUTES\nH | HOURS\nD | DAYS\n```\n**Is Disabled?** \n```\n" + command.isDisabled() + "\n```\n```\n{} | Required\n[] | Optional\n```");
                    eb.setFooter("Entity Tempmute Command Help", data.getSelfAvatar(event));

                    event.getChannel().sendMessage(eb.build()).queue((message) -> {
                        message.delete().queueAfter(30, TimeUnit.SECONDS);
                        event.getMessage().delete().queueAfter(30, TimeUnit.SECONDS);
                        eb.clear();
                    });
                } else if(args[1].equalsIgnoreCase("unmute")){
                    Unmute command = new Unmute();
                    eb.setTitle(command.getName() + " Help");
                    eb.setColor(color.getRandomColor());
                    eb.setDescription(command.getDescription() + "\n\n **Required Roles:**\n" + command.getRequiredRoles() + "\n\n **How to use the unmute command:**\n" + command.getCommandSyntax() + "\n**Is Disabled?** \n```\n" + command.isDisabled() + "\n```\n```\n{} | Required\n[] | Optional\n```");
                    eb.setFooter("Entity Unmute Command Help", data.getSelfAvatar(event));

                    event.getChannel().sendMessage(eb.build()).queue((message) -> {
                        message.delete().queueAfter(30, TimeUnit.SECONDS);
                        event.getMessage().delete().queueAfter(30, TimeUnit.SECONDS);
                        eb.clear();
                    });
                }  else if(args[1].equalsIgnoreCase("warn")){
                    Warn command = new Warn();
                    eb.setTitle(command.getName() + " Help");
                    eb.setColor(color.getRandomColor());
                    eb.setDescription(command.getDescription() + "\n\n **Required Roles:**\n" + command.getRequiredRoles() + "\n\n **How to use the warn command:**\n" + command.getCommandSyntax() + "\n**Is Disabled?** \n```\n" + command.isDisabled() + "\n```\n```\n{} | Required\n[] | Optional\n```");
                    eb.setFooter("Entity Warn Command Help", data.getSelfAvatar(event));

                    event.getChannel().sendMessage(eb.build()).queue((message) -> {
                        message.delete().queueAfter(30, TimeUnit.SECONDS);
                        event.getMessage().delete().queueAfter(30, TimeUnit.SECONDS);
                        eb.clear();
                    });
                }

            }
        }
    }
}
