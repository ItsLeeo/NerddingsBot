
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author BrucknerLeo3BHIF
 */
public class newevent extends ListenerAdapter{
    public newevent() {
        System.out.println("new event");
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        System.out.println(event.getMessage().getContentRaw());
        //Guild guild = event.getGuild();#
        JDA jda = Main.getJDA();
        System.out.println(jda.getAccountType());
        System.out.println(jda.getUserById(332851021498023938l));
        System.out.println(jda.getUsersByName("Mevolent", true));
        User user = jda.getUserById(268039581373300736l);
        //System.out.println(user.getAvatarUrl());
        System.out.println(event.getAuthor().isBot());
        
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        System.out.println("messafe");
    }

    
    
}
