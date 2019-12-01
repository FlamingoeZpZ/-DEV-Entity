package dev.dbdh.Discord.Listeners.Information;

import net.dv8tion.jda.api.audit.ActionType;
import net.dv8tion.jda.api.audit.AuditLogEntry;
import net.dv8tion.jda.api.events.guild.GuildBanEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class AuditLogTest extends ListenerAdapter {

    public void onGuildBan(GuildBanEvent event){
        AuditLogEntry test = event.getGuild().retrieveAuditLogs().type(ActionType.BAN).getFirst();
        System.out.println(test);
    }

}
