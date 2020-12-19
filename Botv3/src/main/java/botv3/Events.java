package botv3;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

import java.util.LinkedList;
import net.dv8tion.jda.api.entities.Category;
import java.util.logging.Level;

import java.util.logging.Logger;
import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceJoinEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceLeaveEvent;
import net.dv8tion.jda.api.events.guild.voice.GuildVoiceMoveEvent;
//import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import net.dv8tion.jda.api.entities.VoiceChannel;


public class Events extends ListenerAdapter {

    LinkedList<Long> listOfNumbers = new LinkedList<>();
    static String contentOfFile = "";
    File file = new File("Channel-Ids.txt");
    LinkedList<Long> channels;
    LinkedList<Long> gamesChannels;

    public Events() throws LoginException {
        channels = new LinkedList<>();
        gamesChannels = new LinkedList<>();
        loadFromFile();
    }

    public boolean isValidActiveChannel(String id) {
        for (Long i : listOfNumbers) {
            if (i.toString().equals(id)) {
                return true;
            }
        }
        return false;
    }
    public boolean isMod(Member member, Guild guild) {
        return member.getRoles().contains(guild.getRoleById("617703857329274882"));
    }

    public void loadFromFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line = br.readLine();
            while (line != null) {
                contentOfFile = line;
                line = br.readLine();

            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Events.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Events.class.getName()).log(Level.SEVERE, null, ex);
        }
        listOfNumbers.clear();
        if (!"".equals(contentOfFile)) {
            for (String i : contentOfFile.split(",")) {
                System.out.println(i);
                listOfNumbers.add(Long.parseLong(i));
            }
        }
        System.out.println(listOfNumbers.toString());
    }

    public void writeToFile() {
        String newContent = "";
        if (!(listOfNumbers.isEmpty())) {
            for (int i = 0; i < listOfNumbers.size(); i++) {
                if (i == 0) {
                    newContent += listOfNumbers.get(i);
                } else {
                    newContent += "," + listOfNumbers.get(i);

                }
            }
        }
        try {
            FileWriter fileWriter = new FileWriter(file);
            fileWriter.write(newContent);
            fileWriter.close();
        } catch (IOException e) {
            // Exception handling
        }
    }

    public void createMoveChannel(VoiceChannel masterchannel, net.dv8tion.jda.api.entities.Member member) {
        Guild guild = masterchannel.getGuild();
        Category cat = masterchannel.getParent();
        if (masterchannel.getIdLong() == 706068084208697376l) { //IDS ÄNDERN 
            listOfNumbers.clear();
            loadFromFile();
            VoiceChannel vcnew = cat.createVoiceChannel(masterchannel.getName()).complete();
            guild.moveVoiceMember(member, vcnew).queue();
            listOfNumbers.add(vcnew.getIdLong());
            vcnew.getManager().setUserLimit(4).queue();
            writeToFile();

        } else if (masterchannel.getIdLong() == 706068084208697376l) { //IDS ÄNDERN
            listOfNumbers.clear();
            loadFromFile();
            VoiceChannel vcnew = cat.createVoiceChannel(masterchannel.getName()).complete();
            guild.moveVoiceMember(member, vcnew).queue();
            listOfNumbers.add(vcnew.getIdLong());
            vcnew.getManager().setUserLimit(3).queue();
            writeToFile();

        } else if (masterchannel.getIdLong() == 706068084208697376l) { //IDS ÄNDERN
            listOfNumbers.clear();
            loadFromFile();
            VoiceChannel vcnew = cat.createVoiceChannel(masterchannel.getName()).complete();
            guild.moveVoiceMember(member, vcnew).queue();
            listOfNumbers.add(vcnew.getIdLong());
            vcnew.getManager().setUserLimit(2).queue();
            writeToFile();
        }
    }

    @Override
    public void onGuildVoiceJoin(GuildVoiceJoinEvent event) {
        createMoveChannel(event.getChannelJoined(), event.getMember());
    }

    @Override
    public void onGuildVoiceLeave(GuildVoiceLeaveEvent event) {
        loadFromFile();
        if (isValidActiveChannel(event.getChannelLeft().getId()) && event.getChannelLeft().getMembers().isEmpty()) {
            event.getChannelLeft().delete().queue();
            System.out.println(listOfNumbers);
            for (int i = 0; i < listOfNumbers.size(); i++) {
                if (("" + listOfNumbers.get(i)).equals(event.getChannelLeft().getId())) {
                    listOfNumbers.remove(i);
                }
            }
            writeToFile();
        }
    }

    @Override
    public void onGuildVoiceMove(GuildVoiceMoveEvent event) {
        loadFromFile();
        createMoveChannel(event.getChannelJoined(), event.getMember());
        if (isValidActiveChannel(event.getChannelLeft().getId()) && event.getChannelLeft().getMembers().isEmpty()) {
            event.getChannelLeft().delete().queue();
            for (int i = 0; i < listOfNumbers.size(); i++) {
                if (("" + listOfNumbers.get(i)).equals(event.getChannelLeft().getId())) {
                    listOfNumbers.remove(i);
                }
            }
            writeToFile();
        }
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        
        Guild newGuild = event.getGuild();
        if (event.getChannel().getIdLong() == 617704813131726848l) { //IDS ÄNDERN //Spiele-Ecke 
            String[] args = event.getMessage().getContentRaw().split("\\s+");
            Category cat = event.getChannel().getParent();
            if (args[0].equals("!create") && (args.length >= 2)) {
                listOfNumbers.clear();
                loadFromFile();
                loadFromFile();
                String channelName = "";
                event.getChannel().deleteMessageById(event.getMessage().getIdLong()).queue();

                int userLimit = 0;

                boolean userLimitSet = false;
                for (int i = 0; i < args.length; i++) {
                    if (!args[i].equalsIgnoreCase("!create")) {
                        if (args[i].startsWith("$")) {
                            userLimit = Integer.parseInt(args[i].substring(1));
                            userLimitSet = true;
                            break;
                        } else {
                            channelName += (" " + args[i]);
                        }
                    }
                }
                VoiceChannel vcnew = (VoiceChannel) event.getChannel().getParent().createVoiceChannel(channelName).complete();
                vcnew.getManager().setBitrate(96000).queue(); //ÄNDERN
                if (userLimitSet) {
                    if (userLimit >= 2) {
                        vcnew.getManager().setUserLimit(userLimit).queue();
                    } else {
                        PrivateChannel pc = event.getAuthor().openPrivateChannel().complete();
                        pc.sendMessage("Das ChannelLimit muss mindestens 2 betragen!").queue();
                    }

                }
                
                listOfNumbers.add(Long.parseLong(vcnew.getId()));
                writeToFile();
                

                //gamesChannels.add(vcnew.getIdLong());
                Guild guild = vcnew.getGuild();
                try {
                    guild.moveVoiceMember(event.getMember(), vcnew).queue();
                } catch (IllegalStateException e) {

                }
                Date date = new Date();
                Timestamp ts = new Timestamp(date.getTime());
                //newGuild.getTextChannelById(707896059137097768l).sendMessage(ts + ": Der Benutzer <@" + event.getMember().getUser().getIdLong() + "> hat den Channel" + channelName + " erstellt").queue();
                
            } else if ("!edit".equals(args[0]) && args.length >= 3) {

                VoiceChannel channel = null;
                boolean isInVoiceChannel = false;

                for (VoiceChannel vc : newGuild.getCategoryById("457873619779846145").getVoiceChannels()) {
                    //System.out.println(vc.getName());
                    if (vc.getMembers().contains(event.getMember())) {
                        isInVoiceChannel = true;
                        channel = vc;
                    }
                }
                System.out.println(isInVoiceChannel + " :" + channel.getName());
                if (isInVoiceChannel) {
                    MessageAction sendMessage = event.getChannel().sendMessage("dwad");
                    if ("!edit".equals(args[0])) {
                        switch (args[1]) {
                            case "name":
                                channel.getManager().setName("test2").queue();
                                String chName = "";
                                for (int i = 2; i < args.length; i++) {
                                    chName += args[i] + " ";
                                }
                                System.out.println(chName);
                                channel.getManager().setName(chName).queue();
                                break;

                            case "limit":
                                channel.getManager().setUserLimit(Integer.parseInt(args[2])).queue();
                                break;

                            case "bitrate":
                                channel.getManager().setBitrate(1000 * Integer.parseInt(args[2])).queue();
                                break;
                        }
                    }
                } else {
                }
                event.getChannel().deleteMessageById(event.getMessage().getIdLong()).queue();
            } else if ("!list".equals(args[0]) && isMod(event.getMember(), event.getGuild())) {
                loadFromFile();
                event.getChannel().sendMessage(listOfNumbers.toString()).queue();
            }
        }else if (event.getChannel().getIdLong() == 706068109080920084l) { //Emoji-Einsendungen
            event.getMessage().addReaction("?").queue();
        }else if (event.getChannel().getIdLong() == 706068109080920084l) { //spiele-rollen-vorsachläge
            event.getMessage().addReaction("?").queue();
        }
    }
}
