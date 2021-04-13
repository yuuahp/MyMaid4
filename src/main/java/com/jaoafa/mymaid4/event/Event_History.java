/*
 * jaoLicense
 *
 * Copyright (c) 2021 jao Minecraft Server
 *
 * The following license applies to this project: jaoLicense
 *
 * Japanese: https://github.com/jaoafa/jao-Minecraft-Server/blob/master/jaoLICENSE.md
 * English: https://github.com/jaoafa/jao-Minecraft-Server/blob/master/jaoLICENSE-en.md
 */

package com.jaoafa.mymaid4.event;

import com.jaoafa.mymaid4.lib.EventPremise;
import com.jaoafa.mymaid4.lib.Historyjao;
import com.jaoafa.mymaid4.lib.MyMaidData;
import com.jaoafa.mymaid4.lib.MyMaidLibrary;
import net.dv8tion.jda.api.entities.TextChannel;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.ArrayList;
import java.util.List;

public class Event_History extends MyMaidLibrary implements Listener, EventPremise {
    @Override
    public String description() {
        return "historyコマンドに関する処理を行います。";
    }

    @EventHandler
    public void OnEvent_JoinHistory(PlayerJoinEvent event) {
        if (!MyMaidData.isMainDBActive()) {
            return;
        }

        Player player = event.getPlayer();
        Historyjao histjao = Historyjao.getHistoryjao(player);

        if (!histjao.isFound()) {
            return;
        }

        if (histjao.getDataList().isEmpty()) {
            return;
        }

        List<String> data = new ArrayList<>();
        for (Historyjao.Data hist : histjao.getDataList()) {
            data.add("[" + hist.id + "] " + hist.message + " - " + sdfFormat(hist.getCreatedAt()));
        }

        if (data.isEmpty()) {
            return;
        }

        TextChannel jaotan = MyMaidData.getJaotanChannel();
        if (jaotan == null) return;
        jaotan.sendMessage("**-----: Historyjao DATA / `" + player.getName() + "` :-----**\n"
            + "```" + String.join("\n", data) + "```").queue();
    }
}