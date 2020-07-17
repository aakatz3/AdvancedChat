package net.darkkronicle.advancedchat.gui.tabs;

import lombok.Data;
import net.darkkronicle.advancedchat.AdvancedChat;
import net.darkkronicle.advancedchat.gui.AdvancedChatHud;
import net.darkkronicle.advancedchat.gui.AdvancedChatLine;
import net.darkkronicle.advancedchat.util.SplitText;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.client.util.ChatMessages;
import net.minecraft.text.StringRenderable;
import net.minecraft.util.math.MathHelper;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * <h1>AbstractChatTab</h1>
 * Base ChatTab that allows for custom chat tabs in AdvancedChatHud.
 */
@Environment(EnvType.CLIENT)
@Data
public abstract class AbstractChatTab {

    // Each tab stores their own messages.
    public final List<AdvancedChatLine> messages = new ArrayList<>();
    public final List<AdvancedChatLine> visibleMessages = new ArrayList<>();
    private final String name;
    private final AdvancedChatHud hud;
    private final MinecraftClient client;

    public AbstractChatTab(String name) {
        hud = AdvancedChat.getAdvancedChatHud();
        client = MinecraftClient.getInstance();
        this.name = name;
    }

    /**
     * <h1>shouldAdd</h1>
     * If the inputted message should be put into the chat tab.
     * @param stringRenderable Object to search.
     * @return True if it should be added.
     */
    public abstract boolean shouldAdd(StringRenderable stringRenderable);

    /**
     * <h1>reset</h1>
     * Method to reformat the messages if Chat size changes or something.
     */
    public void reset() {
        this.visibleMessages.clear();

        for(int i = this.messages.size() - 1; i >= 0; --i) {
            AdvancedChatLine chatHudLine = this.messages.get(i);
            this.addMessage(chatHudLine.getText(), chatHudLine.getId(), chatHudLine.getCreationTick(), true);
        }

    }

    /**
     * <h1>addMessage</h1>
     * Used for adding messages into the tab.
     * @param stringRenderable StringRenderable to add.
     * @param messageId ID of message.
     * @param timestamp Amount of ticks when it was created.
     * @param bl Add to messages
     */
    public void addMessage(StringRenderable stringRenderable, int messageId, int timestamp, boolean bl) {
        addMessage(stringRenderable, messageId, timestamp, bl, LocalTime.now());
    }

    public void addMessage(StringRenderable stringRenderable, int messageId, int timestamp, boolean bl, LocalTime time) {
        if (!shouldAdd(stringRenderable)) {
            return;
        }

        if (messageId != 0) {
            this.removeMessage(messageId);
        }

        StringRenderable logged = stringRenderable;
        boolean showtime = AdvancedChat.configStorage.chatLogConfig.showTime;
        if (showtime) {
            DateTimeFormatter format = DateTimeFormatter.ofPattern(AdvancedChat.configStorage.timeFormat);
            SplitText text = new SplitText(stringRenderable);
            text.addTime(format, time);
            stringRenderable = text.getStringRenderable();
        }
        int width = MathHelper.floor((double)hud.getWidth() / hud.getChatScale());
        List<StringRenderable> list = ChatMessages.breakRenderedChatMessageLines(stringRenderable, width, this.client.textRenderer);

        StringRenderable stringRenderable2;
        for(Iterator var8 = list.iterator(); var8.hasNext(); this.visibleMessages.add(0, new AdvancedChatLine(timestamp, stringRenderable2, messageId, time, null))) {
            stringRenderable2 = (StringRenderable)var8.next();
            hud.messageAddedToTab(this);
        }

        int visibleMessagesMaxSize = AdvancedChat.configStorage.chatConfig.storedLines;
        while(this.visibleMessages.size() > visibleMessagesMaxSize) {
            this.visibleMessages.remove(this.visibleMessages.size() - 1);
        }

        if (!bl) {
            this.messages.add(0, new AdvancedChatLine(timestamp, logged, messageId, time, null));

            while(this.messages.size() > visibleMessagesMaxSize) {
                this.messages.remove(this.messages.size() - 1);
            }
        }
    }

    public void removeMessage(int messageId) {
        Iterator iterator = this.visibleMessages.iterator();

        ChatHudLine chatHudLine2;
        while(iterator.hasNext()) {
            chatHudLine2 = (ChatHudLine)iterator.next();
            if (chatHudLine2.getId() == messageId) {
                iterator.remove();
            }
        }

        iterator = this.messages.iterator();

        while(iterator.hasNext()) {
            chatHudLine2 = (ChatHudLine)iterator.next();
            if (chatHudLine2.getId() == messageId) {
                iterator.remove();
                break;
            }
        }
    }

}
