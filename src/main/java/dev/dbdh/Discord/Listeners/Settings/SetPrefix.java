package votrix.Discord.commands.Settings;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import votrix.Discord.utils.Data;
import votrix.Discord.utils.RoleCheck;

import java.awt.*;
import java.time.Instant;
import java.util.concurrent.TimeUnit;

public class SetPrefix extends ListenerAdapter {

    public void onGuildMessageReceived(GuildMessageReceivedEvent event){
        String[] args = event.getMessage().getContentRaw().split("\\s+");
        Data data = new Data();
        RoleCheck rc = new RoleCheck();
        EmbedBuilder eb = new EmbedBuilder();
        EmbedBuilder success = new EmbedBuilder();
        if(args[0].equalsIgnoreCase(data.getPrefix() + "setprefix")) {
            if (rc.isOwner(event) || rc.isDeveloper(event)) {
                data.setPrefix(args[1]);
                eb.setDescription("Successfully set the prefix to `" + args[1] + "`");
                eb.setColor(new Color(data.getColor()));
                eb.setFooter("Votrix Set Prefix", data.getSelfAvatar(event));
                eb.setTimestamp(Instant.now());

                success.setDescription(event.getMember().getAsMention() + " set the prefix to `" + args[1] + "`");
                success.setColor(new Color(data.getColor()));
                success.setFooter("Votrix Set Prefix Log", data.getSelfAvatar(event));
                success.setTimestamp(Instant.now());

                event.getChannel().sendMessage(eb.build()).queue((message) -> {
                    message.delete().queueAfter(15, TimeUnit.SECONDS);
                    event.getMessage().delete().queueAfter(15, TimeUnit.SECONDS);
                    eb.clear();
                    data.getLogChannel(event).sendMessage(success.build()).queue((message2) -> {
                        success.clear();
                    });
                });

                event.getGuild().modifyNickname(event.getGuild().getSelfMember(), "Votrix ( " + args[1] + " )").queue();
            }
        }
    }

    public String getName() {
        return "Setprefix";
    }

    public String getDescription() {
        return "Sets the prefix for all subsequent commands";
    }

    public String getShortDescription() {
        return "Set the prefix for commands";
    }

    public String getRequiredRoles() {
        return "Owner, Developer";
    }

    public String getCommandSyntax() {
        return "```\n" + Data.getPrefix() + "setprefix {new prefix}\n```";
    }

    public boolean isDisabled() {
        return false;
    }
}
