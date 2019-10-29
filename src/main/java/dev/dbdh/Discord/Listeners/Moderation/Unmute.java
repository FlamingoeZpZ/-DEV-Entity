package dev.dbdh.Discord.Listeners.Moderation;

import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Data;
import dev.dbdh.Discord.Utilities.RoleCheck;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class Unmute extends ListenerAdapter {
    Data data = new Data();
    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        Color color = new Color();
        RoleCheck rc = new RoleCheck();
        EmbedBuilder eb = new EmbedBuilder();
        EmbedBuilder unmuted = new EmbedBuilder();
        EmbedBuilder success = new EmbedBuilder();

        if(args[0].equalsIgnoreCase(data.getPrefix() + "unmute")){
            if(rc.isOwner(event) || rc.isDeveloper(event)){
                if(args.length < 2) {
                    eb.setDescription("You didn't specify enough arguments \n" + data.getPrefix() + "unmute @<member>");
                    eb.setColor(color.getRandomColor());
                    eb.setFooter("Insufficient Arguments", data.getSelfAvatar(event));
                    eb.setTimestamp(Instant.now());

                    event.getChannel().sendMessage(eb.build()).queue((message) -> {
                        message.delete().queueAfter(15, TimeUnit.SECONDS);
                        event.getMessage().delete().queueAfter(15, TimeUnit.SECONDS);
                        eb.clear();
                    });
                } else if(args.length >= 2){
                    Member mentioned = event.getMessage().getMentionedMembers().get(0);

                    eb.setDescription("Successfully unmuted " + mentioned.getAsMention());
                    eb.setColor(color.getRandomColor());
                    eb.setTimestamp(Instant.now());
                    eb.setFooter("Votrix Unmute", data.getSelfAvatar(event));

                    success.setDescription(event.getMember().getAsMention() + " unmuted " + mentioned.getAsMention());
                    success.setColor(color.getRandomColor());
                    success.setTimestamp(Instant.now());
                    success.setFooter("Votrix Unmute Log", data.getSelfAvatar(event));

                    unmuted.setDescription("You've been unmuted");
                    unmuted.setColor(color.getRandomColor());
                    unmuted.setTimestamp(Instant.now());
                    unmuted.setFooter("Votrix Unmute", data.getSelfAvatar(event));

                    event.getGuild().removeRoleFromMember(mentioned, event.getGuild().getRolesByName("Muted", true).get(0)).queue();

                    mentioned.getUser().openPrivateChannel().complete().sendMessage(unmuted.build()).queue((message) -> {
                        event.getChannel().sendMessage(eb.build()).queue((message1) -> {
                            eb.clear();
                            message.delete().queueAfter(15, TimeUnit.SECONDS);
                            event.getMessage().delete().queueAfter(15, TimeUnit.SECONDS);
                            data.getLogChannel(event).sendMessage(success.build()).queue((message2) -> {
                                success.clear();
                            });
                        });
                    });

                }
            }
        }
    }

    public String getName() {
        return "Unmute";
    }

    public String getDescription() {
        return "Unmutes the specified member";
    }

    public String getShortDescription() {
        return getDescription();
    }

    public String getRequiredRoles() {
        return "Owner, Developer, Administrator, Moderator";
    }

    public String getCommandSyntax() {
        return "```\n" + data.getPrefix() + "unmute {@member}\n```";
    }

    public boolean isDisabled() {
        return false;
    }
}
