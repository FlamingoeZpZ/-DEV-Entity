package dev.dbdh.Discord.Listeners.Miscellaneous;

import com.mongodb.BasicDBObject;
import com.mongodb.client.MongoCollection;
import dev.dbdh.Discord.Utilities.Color;
import dev.dbdh.Discord.Utilities.Data;
import dev.dbdh.Discord.Utilities.Database;
import dev.dbdh.Discord.Utilities.Webhooks;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.bson.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.time.Instant;
import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

public class Suggest extends ListenerAdapter {

    private static Logger LOGGER = LoggerFactory.getLogger(Suggest.class);

    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {

        String[] args = event.getMessage().getContentRaw().split("\\s+");
        Data data = new Data();
        Color color = new Color();
        EmbedBuilder eb = new EmbedBuilder();
        String[] images = {"https://quiver.nestedvar.dev/assets/huh.jpg", "https://quiver.nestedvar.dev/assets/jackie_chan_huh.jpg", "https://quiver.nestedvar.dev/assets/wat.png", "https://quiver.nestedvar.dev/assets/wat_magik.png"};
        if (args[0].equalsIgnoreCase(data.getPrefix() + "suggest") || args[0].equalsIgnoreCase(data.getPrefix() + "suggestion")) {
            if (args.length < 2) {
                Random rand = new Random();
                int image = rand.nextInt(images.length);
                eb.setDescription("I can't read your mind reeeeeeeeeeeee");
                eb.setColor(color.getRandomColor());
                eb.setImage(images[image]);
                eb.setFooter("Entity Suggestions", data.getSelfAvatar(event));

                event.getChannel().sendMessage(eb.build()).queue((message) -> {
                    message.delete().queueAfter(30, TimeUnit.SECONDS);
                    event.getMessage().delete().queueAfter(30, TimeUnit.SECONDS);
                    eb.clear();
                });
            } else if (args.length > 1) {
                try {
                    String sug = Arrays.stream(args).skip(1).collect(Collectors.joining(" "));
                    Webhooks webhook = new Webhooks(System.getenv("ENTITYSUGGESTIONWEBHOOK"));
                    webhook.setAvatarUrl(event.getMember().getUser().getEffectiveAvatarUrl());
                    webhook.setUsername(event.getMember().getUser().getName());
                    webhook.addEmbed(new Webhooks.EmbedObject()
                            .setTitle("New Suggestion")
                            .setColor(new java.awt.Color(color.getRandomColor()))
                            .setDescription(sug)
                    );
                    webhook.execute();
                    eb.setDescription(":white_check_mark: Successfully sent the suggestion");
                    eb.setColor(color.getRandomColor());
                    eb.setTimestamp(Instant.now());
                    eb.setFooter("Entity Suggestions", data.getSelfAvatar(event));
                } catch (IOException ex) {
                    event.getChannel().sendMessage("Well shit there was an error with this command tell " + event.getGuild().getMemberById("79693184417931264").getAsMention() + " he retarded").queue();
                    LOGGER.error("A fatal error has occured: ", ex);
                }
            }
        }
    }

    public String getName() {
        return "Suggest";
    }

    public String getDescription() {
        return "Make a suggestion for a feature to be added to the server and/or bot.";
    }

    public String getShortDescription() {
        return "Make a suggestion";
    }

    public String getRequiredRoles() {
        return "Everyone";
    }

    public String getCommandSyntax() {
        return "```\n" + Data.getPrefix() + "suggest {suggestion in as much detail as you can give}\n```";
    }

    public boolean isDisabled() {
        return false;
    }

}
