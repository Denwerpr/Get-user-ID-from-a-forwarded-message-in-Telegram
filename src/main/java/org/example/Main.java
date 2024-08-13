package org.example;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;


public class Main extends TelegramLongPollingBot {
    public void onUpdateReceived(Update update) {
        if (update.hasMessage()) {
            if (update.getMessage().getForwardFrom() != null) {
                long forwardedUserId = update.getMessage().getForwardFrom().getId();
                String forwardedUserName = update.getMessage().getForwardFrom().getUserName();
                SendMessage message = new SendMessage();
                message.setChatId(String.valueOf(update.getMessage().getChatId()));
                message.setText("This message was forwarded from user: " + forwardedUserName + " (ID: " + forwardedUserId + ")");
                try {
                    execute(message);
                } catch (TelegramApiException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        // Bot name
        return "";
    }

    @Override
    public String getBotToken() {
        // Bot token
        return "";
    }

    public static void main(String[] args) {
        // Bot registration
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new Main());
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }
}