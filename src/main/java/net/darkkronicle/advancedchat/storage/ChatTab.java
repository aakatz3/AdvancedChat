/* AdvancedChat: A Minecraft Mod to modify the chat.
Copyright (C) 2020 DarkKronicle
This program is free software: you can redistribute it and/or modify
it under the terms of the GNU General Public License as published by
the Free Software Foundation, either version 3 of the License, or
(at your option) any later version.
This program is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
GNU General Public License for more details.
You should have received a copy of the GNU General Public License
along with this program.  If not, see <https://www.gnu.org/licenses/>.*/

package net.darkkronicle.advancedchat.storage;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class ChatTab {
    private String name;

    private String findString;

    private Filter.FindType findType;

    private String startingMessage;

    private boolean forward;

    private String abreviation;

    public static ChatTab getDefault() {
        return new ChatTab("Default", "Name", Filter.FindType.LITERAL,  "", true, "Dft");
    }

    public ChatTab(String name, String findString, Filter.FindType findType, String startingMessage, boolean forward, String abreviation) {
        this.name = name;
        this.findString = findString;
        this.findType = findType;
        this.startingMessage = startingMessage;
        this.forward = forward;
        this.abreviation = abreviation;
    }

    public static void checkForErrors(List<ChatTab> tabs) {
        for (ChatTab tab : tabs) {
            if (tab.name == null) {
                tab.name = "Default";
            }
            if (tab.findString == null) {
                tab.findString = "Name";
            }
            if (tab.findType == null) {
                tab.findType = Filter.FindType.LITERAL;
            }
            if (tab.startingMessage == null) {
                tab.startingMessage = "";
            }
            if (tab.abreviation == null) {
                tab.abreviation = "Dft";
            }
        }

    }
}
