package dev.dbdh.Discord.Listeners.Moderation;

import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Data;
import dev.dbdh.Discord.Utilities.RoleCheck;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import java.time.Instant;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Kick extends ListenerAdapter {

    Data data = new Data();

    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        Color color = new Color();
        RoleCheck rc = new RoleCheck();
        EmbedBuilder eb = new EmbedBuilder();
        EmbedBuilder success = new EmbedBuilder();
        EmbedBuilder kicked = new EmbedBuilder();
        if(args[0].equalsIgnoreCase(data.getPrefix() + "kick") || args[0].equalsIgnoreCase("***" + data.getPrefix() + "yeet***")){
            if(rc.isOwner(event) || rc.isDeveloper(event) || rc.isAdministrator(event) || rc.isHeadModerator(event)){
                if(args.length < 2){
                    eb.setDescription("You didn't specify enough arguments");
                    eb.setColor(color.getRandomColor());
                    eb.setFooter("Insufficient Arguments", data.getSelfAvatar(event));
                    eb.setTimestamp(Instant.now());

                    event.getChannel().sendMessage(eb.build()).queue((message) -> {
                        message.delete().queueAfter(15, TimeUnit.SECONDS);
                        event.getMessage().delete().queueAfter(15, TimeUnit.SECONDS);
                        eb.clear();
                    });
                }else if (args.length < 3) {
                    Member mentioned = event.getMessage().getMentionedMembers().get(0);

                    kicked.setDescription("You've been kicked from: " + event.getGuild().getName()
                            + "\n\nReason: \n```\nThere was no reason specified\n```");
                    kicked.setColor(color.getRandomColor());
                    kicked.setFooter(event.getJDA().getSelfUser().getName() + " Kicked",
                            data.getSelfAvatar(event));
                    kicked.setTimestamp(Instant.now());

                    eb.setDescription("You've kicked: " + mentioned.getAsMention() + "\n\nReason:\n```\nNo reason specified\n```");
                    eb.setColor(color.getRandomColor());
                    eb.setFooter(event.getJDA().getSelfUser().getName() + " Kick",
                            data.getSelfAvatar(event));
                    eb.setTimestamp(Instant.now());

                    mentioned.getUser().openPrivateChannel().queue((channel) -> {
                        channel.sendMessage(kicked.build()).queue();
                        kicked.clear();

                        event.getChannel().sendMessage(eb.build()).queue((message) -> {
                            message.delete().queueAfter(20, TimeUnit.SECONDS);
                            event.getMessage().delete().queueAfter(20, TimeUnit.SECONDS);
                            eb.clear();
                            event.getGuild().kick(mentioned, "No reason specified").queue();
                        });
                    });

                } else {
                    Member mentioned = event.getMessage().getMentionedMembers().get(0);
                    String reason = Arrays.stream(args).skip(2).collect(Collectors.joining(" "));

                    kicked.setDescription("You've been kicked from: " + event.getGuild().getName() + "\n\nReason:\n```\n" + reason + "\n```");
                    kicked.setColor(color.getRandomColor());
                    kicked.setFooter(event.getJDA().getSelfUser().getName() + " Kicked",
                            data.getSelfAvatar(event));
                    kicked.setTimestamp(Instant.now());

                    eb.setDescription("You've kicked: " + mentioned.getAsMention() + " \n\nReason: \n```\n" + reason + "\n```");
                    eb.setColor(color.getRandomColor());
                    eb.setFooter(event.getJDA().getSelfUser().getName() + " kicked",
                            data.getSelfAvatar(event));
                    eb.setTimestamp(Instant.now());

                    mentioned.getUser().openPrivateChannel().queue((channel) -> {
                        channel.sendMessage(kicked.build()).queue();
                        kicked.clear();

                        event.getChannel().sendMessage(eb.build()).queue((message) -> {
                            message.delete().queueAfter(20, TimeUnit.SECONDS);
                            event.getMessage().delete().queueAfter(20, TimeUnit.SECONDS);
                            eb.clear();
                            event.getGuild().kick(mentioned, reason).queue();
                        });
                    });

                }
            } else {
                eb.setDescription(event.getMember().getAsMention()
                        + ", You dont have the permission to kick members from this guild.");
                eb.setColor(color.getRandomColor());
                eb.setFooter("Insufficient Permissions", data.getSelfAvatar(event));
                eb.setTimestamp(Instant.now());
                event.getChannel().sendMessage(eb.build()).queue((message) -> {
                    message.delete().queueAfter(15, TimeUnit.SECONDS);
                    event.getMessage().delete().queueAfter(15, TimeUnit.SECONDS);
                    eb.clear();
                });
            }
        }
    }

    public String getName(){
        return "Kick";
    }

    public String getDescription() {
        return "Kicks the specified member for the specified reason. If no reason is specified then the member is kicked for \"No Reason Specified\".";
    }

    public String getShortDescription() {
        return "Kicks the specified member";
    }

    public String getRequiredRoles() {
        return "Owner, Developer, Administrator";
    }

    public String getCommandSyntax() {
        return "```\n" + data.getPrefix() + "kick {@member} [reason]\n```";
    }

    public boolean isDisabled() {
        return false;
    }

}
