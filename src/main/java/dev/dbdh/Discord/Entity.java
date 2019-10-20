package dev.dbdh.Discord;

import dev.dbdh.Discord.Listeners.Economy.AddCoins;
import dev.dbdh.Discord.Listeners.Economy.AddEveryoneToDatabase;
import dev.dbdh.Discord.Listeners.Economy.Balance;
import dev.dbdh.Discord.Listeners.Information.*;
import dev.dbdh.Discord.Listeners.Miscellaneous.*;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;
import java.net.UnknownHostException;

public class Entity {
    public static void main(String[] args) throws LoginException, RateLimitedException, InterruptedException, UnknownHostException {
        JDABuilder entity = new JDABuilder(AccountType.BOT).setToken(System.getenv("ENTITYDEVTOKEN"));

        entity.setStatus(OnlineStatus.DO_NOT_DISTURB);
        entity.setActivity(Activity.watching("Loading Bars"));

        entity.addEventListeners(
                //Misc Listeners
                new Join(),
                new Ready(),
                new Leave(),

                //Economy Listeners
                new AddCoins(),
                new Balance(),

                //Information Listeners
                new ReactionRoleAssign(),
                new Help(),
                new LookingToPlay(),

                new AddEveryoneToDatabase()

        );

        entity.build();
    }

}
