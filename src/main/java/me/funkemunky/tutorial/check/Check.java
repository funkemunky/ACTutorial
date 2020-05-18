package me.funkemunky.tutorial.check;

import cc.funkemunky.api.utils.Color;
import cc.funkemunky.api.utils.MathUtils;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import me.funkemunky.tutorial.data.UserData;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.BaseComponent;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@RequiredArgsConstructor
public abstract class Check {

    protected String name;
    protected CheckType type;
    public float vl;

    public static List<UserData> alertsEnabled = new ArrayList<>();

    //Used for individual players
    private final UserData data;

    private static TextComponent lbracket = new TextComponent("["),
            rbracket = new TextComponent("]"),
            prefix = new TextComponent("Anticheat"),
            space = new TextComponent(" "),
            message = new TextComponent("has failed"),
            infoPrefix = new TextComponent("Information: ");

    public void flag(String info, Object... objects) {
        String formattedInfo = String.format(info, objects);

        TextComponent playerName = new TextComponent(data.getPlayer().getName());
        playerName.setColor(ChatColor.WHITE);

        TextComponent checkName = new TextComponent(name);
        checkName.setColor(ChatColor.WHITE);

        TextComponent violationCount = new TextComponent(String.valueOf(MathUtils.round(vl, 1)));
        violationCount.setColor(ChatColor.RED);

        TextComponent alertMsg = new TextComponent(lbracket, prefix, rbracket, space, playerName,
                space, message, space, checkName, space, lbracket, violationCount, rbracket);

        TextComponent information = new TextComponent(Color.translate(formattedInfo));

        information.setColor(ChatColor.WHITE);

        alertMsg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
                new BaseComponent[] {infoPrefix, space, information}));

        alertsEnabled.forEach(user -> {
            alertMsg.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/tp " + name));

            user.getPlayer().spigot().sendMessage(alertMsg);
        });
    }

    public abstract void receive(Object packet);

    public abstract void send(Object packet);

    static {
        lbracket.setColor(ChatColor.DARK_GRAY);
        rbracket.setColor(ChatColor.DARK_GRAY);
        prefix.setColor(ChatColor.DARK_RED);
        prefix.setBold(true);
        message.setColor(ChatColor.GRAY);
        infoPrefix.setColor(ChatColor.GOLD);
    }
}
