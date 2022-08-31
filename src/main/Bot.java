package main;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;
import org.jetbrains.annotations.NotNull;

import javax.security.auth.login.LoginException;

import static net.dv8tion.jda.api.interactions.commands.OptionType.STRING;

public class Bot extends ListenerAdapter {
    public static void main(String[] args) throws LoginException {
        if (args.length < 1) {
            System.out.println("Missing argument : token");
            System.exit(1);
        }
        // args[0] should be the token
        // We only need 2 intents in this bot. We only respond to messages in guilds and private channels.
        // All other events will be disabled.
        final String token = args[0];
        CommandListUpdateAction commands = JDABuilder.create(
                    token,
                    GatewayIntent.GUILD_MESSAGES,
                    GatewayIntent.DIRECT_MESSAGES)
                .addEventListeners(new Bot())
                .setActivity(Activity.listening("your commands ;)"))
                .build()
                .updateCommands();

        //commands
        commands.addCommands(
                Commands.slash("showprojects", "List of all the pending projects"));

        commands.addCommands(
                Commands.slash("showmyprojects", "List of projects in which I am included"));

        commands.addCommands(
                Commands.slash("alltasks", "List of all tasks of a project ")
                        .addOption(STRING, "fromproject", "The project id"));

        commands.addCommands(
                Commands.slash("mytasks", "List of tasks assigned to me")
                        .addOptions(new OptionData(STRING, "fromproject", "The project id").setRequired(false)));

        commands.addCommands(
                Commands.slash("markdone", "Mark task as done")
                        .addOptions(
                                new OptionData(STRING, "fromproject", "The project in which the task to mark is").setRequired(true),
                                new OptionData(STRING, "task", "The task to mark as done").setRequired(true)));

        commands.addCommands(
                Commands.slash("createproject", "Create a new project")
                        .addOptions(
                                new OptionData(STRING, "project", "The ID of the project, an acronym for example").setRequired(true),
                                new OptionData(STRING, "description", "The description of the project").setRequired(false)));

        commands.addCommands(
                Commands.slash("removeproject", "Project to remove")
                        .addOptions(
                                new OptionData(STRING, "project", "The project in which the task to mark is").setRequired(true)));

        commands.addCommands(
                Commands.slash("addtask", "Add a new task to a project")
                        .addOptions(
                                new OptionData(STRING, "toproject", "The project to which the task should be added").setRequired(true),
                                new OptionData(STRING, "task", "The task to add").setRequired(true)));

        commands.addCommands(
                Commands.slash("removetask", "Remove a task from a project")
                        .addOptions(
                                new OptionData(STRING, "fromproject", "The project from which the task should be removed").setRequired(true),
                                new OptionData(STRING, "task", "The task to remove").setRequired(true)));

        commands.addCommands(
                Commands.slash("assigntask", "Assign a task to a user")
                        .addOptions(
                                new OptionData(STRING, "toproject", "The project in which the task to assign is").setRequired(true),
                                new OptionData(STRING, "task", "The task to assign").setRequired(true),
                                new OptionData(STRING, "user", "The user to assign the task to").setRequired(true)));

        commands.addCommands(
                Commands.slash("unassigntask", "Unassign a task from a user")
                        .addOptions(
                                new OptionData(STRING, "fromproject", "The project in which the task to unassign is").setRequired(true),
                                new OptionData(STRING, "task", "The task to unassign").setRequired(true),
                                new OptionData(STRING, "user", "The user to unassign the task from").setRequired(true)));

        commands.addCommands(
                Commands.slash("help", "Show the help")
                        .addOptions(
                                new OptionData(STRING, "command", "The command to show the help for").setRequired(false)));

        commands.addCommands(
                Commands.slash("ping", "Ping the bot."));

        commands.queue();
    }

    @Override
    public void onReady(@NotNull ReadyEvent event) {
        System.out.println("Bot is ready!");
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event){
        if(event.getGuild() == null){
            event.reply("This bot only works in guilds.").queue();
            return;
        }

        switch (event.getName()) {
            case "showprojects":
                event.reply("List of all the pending projects").queue();
                break;

            case "showmyprojects" :
                event.reply("List of projects in which I am included").queue();
                break;

            case "alltasks" :
                event.reply("List of all tasks of a project ").queue();
                break;

            case "mytasks" :
                event.reply("List of tasks assigned to me").queue();
                break;

            case "markdone" :
                event.reply("Mark task as done").queue();
                break;

            case "createproject" :
                event.reply("Create a new project").queue();
                break;

            case "removeproject" :
                event.reply("Project to remove").queue();
                break;

            case "addtask" :
                event.reply("Add a new task to a project").queue();
                break;

            case "removetask" :
                event.reply("Remove a task from a project").queue();
                break;

            case "assigntask" :
                event.reply("Assign a task to a user").queue();
                break;

            case "unassigntask" :
                event.reply("Unassign a task from a user").queue();
                break;

            case "help" :
                event.reply("Show the help").queue();
                break;

            case "ping" :
                event.reply("Ping the bot").queue();
                break;

            default :
                event.reply("Unknown command").queue();
        }
    }
}
