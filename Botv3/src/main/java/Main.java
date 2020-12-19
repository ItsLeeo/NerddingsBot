


import javax.security.auth.login.LoginException;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.OnlineStatus;


public class Main {
    public static JDA jdabuilder;
    
    public static void main(String[] args) throws LoginException {
        JDABuilder builder = new JDABuilder(AccountType.BOT);
        builder.setToken("NzA1NTczMDgzNDAyMjA3NDEx.XqtqIw.KmIrGO9Sgm5hO_FdchZBOD5bfqI");
        builder.setStatus(OnlineStatus.DO_NOT_DISTURB);
        jdabuilder = builder.build();
        jdabuilder.addEventListener(new botv3.Events());
    }
    public static JDA getJDA() {
        return jdabuilder;
    }
    
}