package dev.dbdh.Discord;

import dev.dbdh.Discord.Listeners.Economy.*;
import dev.dbdh.Discord.Listeners.Fun.Chest;
import dev.dbdh.Discord.Listeners.Fun.Cleverbot;
import dev.dbdh.Discord.Listeners.Fun.LookingToPlay;
import dev.dbdh.Discord.Listeners.Fun.Screenshare;
import dev.dbdh.Discord.Listeners.Information.Help;
import dev.dbdh.Discord.Listeners.Information.ReactionRoleAssign;
import dev.dbdh.Discord.Listeners.Miscellaneous.*;
import dev.dbdh.Discord.Listeners.Moderation.*;
import dev.dbdh.Discord.Listeners.Settings.SetPrefix;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;
import java.io.IOException;
import java.net.UnknownHostException;
import java.security.acl.LastOwnerException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class Entity {
    public static void main(String[] args) throws LoginException, RateLimitedException, InterruptedException, UnknownHostException {

        Logger logger = Logger.getLogger("dev.dbdh.Discord.Entity");
        FileHandler fh;

        try {
            // This block configure the logger with handler and formatter
            fh = new FileHandler("./entity.log");
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();
            fh.setFormatter(formatter);

            // the following statement is used to log any messages
            logger.setUseParentHandlers(true);

        } catch (SecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        JDABuilder entity = new JDABuilder(AccountType.BOT).setToken(System.getenv("ENTITYDEVTOKEN"));

        entity.setStatus(OnlineStatus.DO_NOT_DISTURB);
        entity.setActivity(Activity.watching("Loading Bars"));

        entity.addEventListeners(
                // Economy Listeners
                new AddEveryoneToDatabase(),
                new Balance(),
                new Daily(),
                new EditCoins(),
                new SuccessfulBump(),

                //Fun Listeners
                new Chest(),
                new Cleverbot(),
                new LookingToPlay(),
                new Screenshare(),

                //Information Listeners
                new Help(),
                new ReactionRoleAssign(),

                //Misc Listeners
                new Join(),
                new Leave(),
                new NameUpdate(),
                new Ready(),

                //Moderation Listeners
                new Ban(),
                new Clear(),
                new Kick(),
                new Mute(),
                new RoleAdd(),
                new RoleRemove(),
                new Softban(),
                new Tempmute(),
                new Unmute(),
                new Warn(),

                // Party Listeners


                // Settings Listeners
                new SetPrefix()
        );

        entity.build();
    }

}
