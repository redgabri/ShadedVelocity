package it.redgabri.velocitycore.utils;

import dev.dejvokep.boostedyaml.YamlDocument;
import it.redgabri.velocitycore.ShadedVelocity;

public class Cache {
    public static String NO_PERMISSION, INVALID_ARGS, INVALID_PLAYER;
    public static String FIND_MESSAGE, FIND_ERROR_NOT_FOUND;
    public static String SEND_MESSAGE, SEND_ERROR_SERVER_NOT_FOUND, SEND_ERROR_ARG_NOT_FOUND;
    public static String GLOBAL_PREFIX;
    public static String KICK_MESSAGE, DEFAULT_KICK_REASON;
    public static String CLIENTMESSAGE;
    public static String SAME_SERVER, TPTO_SENDED_SUCCESSFULLY;
    public static String SAME_SERVER_GOTO, GOTO_SENDED_SUCCESSFULLY;


    public static void init(){
        YamlDocument messages = ShadedVelocity.getConfig().getMessages();

        //ERRORS
        NO_PERMISSION = messages.getString("errors.no-permission");
        INVALID_ARGS = messages.getString("errors.invalid-command-syntax");
        INVALID_PLAYER = messages.getString("errors.invalid-player");

        //FIND
        FIND_MESSAGE = messages.getString("command-messages.find-command.message-success");
        FIND_ERROR_NOT_FOUND = messages.getString("command-messages.find-command.player-not-found");

        //SEND
        SEND_MESSAGE = messages.getString("command-messages.send-command.message-success");
        SEND_ERROR_ARG_NOT_FOUND = messages.getString("command-messages.send-command.server-not-found");
        SEND_ERROR_SERVER_NOT_FOUND = messages.getString("command-messages.send-command.args-not-found");

        //GLOBAL
        GLOBAL_PREFIX = messages.getString("command-messages.global-command.message-prefix");

        //KICKALL
        KICK_MESSAGE = messages.getString("command-messages.kickall-command.message-success");
        DEFAULT_KICK_REASON = messages.getString("command-messages.kickall-command.default-kick-reason");

        //CLIENT
        CLIENTMESSAGE = messages.getString("command-messages.client-command.message-success");

        //TPTO
        TPTO_SENDED_SUCCESSFULLY = messages.getString("command-messages.tpto-command.message-success");
        SAME_SERVER = messages.getString("command-messages.tpto-command.same-server");

        //GOTO
        GOTO_SENDED_SUCCESSFULLY = messages.getString("command-messages.goto-command.message-success");
        SAME_SERVER_GOTO = messages.getString("command-messages.goto-command.same-server");
    }
}
