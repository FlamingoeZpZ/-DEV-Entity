package dev.dbdh.Discord.Utilities;

import java.io.PrintWriter;
import java.io.StringWriter;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;

public class Logger {
    Data data = new Data();
    public Logger(int code, Exception error, GuildMessageReceivedEvent event) {
        StringWriter sw = new StringWriter();
        error.printStackTrace(new PrintWriter(sw));
        String exception = sw.toString();

        EmbedBuilder fatal = new EmbedBuilder();
        fatal.setDescription(exception);
        data.getLogChannel(event).sendMessage(fatal.build()).queue();
        System.out.println(exception);
    }

    public Logger(int code, String error) {
        System.out.println(error);
    }
}

