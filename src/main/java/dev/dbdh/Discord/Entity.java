package dev.dbdh.Discord;

import dev.dbdh.Discord.Listeners.Economy.*;
import dev.dbdh.Discord.Listeners.Fun.*;
import dev.dbdh.Discord.Listeners.Fun.ChestGame.Chest;
import dev.dbdh.Discord.Listeners.Fun.Custom.AddToRole;
import dev.dbdh.Discord.Listeners.Information.*;
import dev.dbdh.Discord.Listeners.Miscellaneous.*;
import dev.dbdh.Discord.Listeners.Moderation.*;
import dev.dbdh.Discord.Listeners.Settings.*;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.net.UnknownHostException;

public class Entity {
    public static void main(String[] args) throws LoginException, RateLimitedException, InterruptedException, UnknownHostException {
        JDABuilder entity = new JDABuilder(AccountType.BOT).setToken(System.getenv("ENTITYDEVTOKEN"));

        entity.setStatus(OnlineStatus.DO_NOT_DISTURB);
        entity.setActivity(Activity.watching("Loading Bars"));

        entity.addEventListeners(
                // Economy Listeners
                new buildShop(),
                new Shop(),
                new AddEveryoneToDatabase(),
                new Balance(),
                new Daily(),
                new EditCoins(),
                //new SuccessfulBump(),

                //Fun Listeners
                new AddToRole(),
                new Chest(),
                new Cleverbot(),
                new LookingToPlay(),
                new Screenshare(),

                //Information Listeners
                new AliasesHelp(),
                new Help(),
                new ListWarnings(),
                new ReactionRoleAssign(),
                new ReactionRoleUnassign(),
                new Report(),

                //Misc Listeners
                //new Censor(),
                new DBCompare(),
                new Join(),
                new Leave(),
                new NameUpdate(),
                new Ready(),
                new ReactionMessage(),

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
