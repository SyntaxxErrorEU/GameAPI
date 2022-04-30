package eu.soupmc;

import com.google.gson.Gson;
import de.dytanic.cloudnet.ext.bridge.bukkit.BukkitCloudNetHelper;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.*;

public class GameAPI {

    public Class<? extends JavaPlugin> plugin;
    public static GameAPI instance;

    public GameAPI(Class<? extends JavaPlugin> passedPlugin) {
        this.plugin = passedPlugin;
        instance = this;
        Bukkit.getConsoleSender().sendMessage("------ Hello from the GameApi ------");
        Bukkit.getConsoleSender().sendMessage("------ Developed by jobebe07  ------");
    }

    public void setIngameStatus() {
        BukkitCloudNetHelper.changeToIngame();
    }

    public void setMaxPlayers(int maxPlayers) {
        BukkitCloudNetHelper.setMaxPlayers(maxPlayers);
    }

    public int getMaxPlayers() {
        return BukkitCloudNetHelper.getMaxPlayers();
    }

    public static void addXp(Player player, int xp) {
        instance.request("write", "addXP", player.getUniqueId(), "" + xp);
    }

    public static int getXp(Player p) {
        Gson gson = new Gson();
        Map<String, String> response = gson.fromJson(instance.request("read", "getXP", p.getUniqueId(), ""), HashMap.class);
        return Integer.parseInt(response.get("result"));
    }

    public static void addCoins(Player player, int coins) {
        instance.request("write", "addCoins", player.getUniqueId(), "" + coins);
    }

    public static int getCoins(Player p) {
        Gson gson = new Gson();
        Map<String, String> response = gson.fromJson(instance.request("read", "getCoins", p.getUniqueId(), ""), HashMap.class);
        return Integer.parseInt(response.get("result"));
    }

    public static String getNick(Player p) {
        Gson gson = new Gson();
        Map<String, String> response = gson.fromJson(instance.request("read", "getNick", p.getUniqueId(), ""), HashMap.class);
        return response.get("result").trim();
    }

    public static void setNick(Player p, boolean value) {
        instance.request("write", "setNick", p.getUniqueId(), (value ? "true" : "false"));
    }

    public String request(String type, String key, UUID uuid, String amount) {

        HttpPost postRequest = new HttpPost("https://belinked.chat/soupmcGameApi.php");
        DefaultHttpClient httpClient = new DefaultHttpClient();

        List<NameValuePair> nameValuePairs = new ArrayList<>(1);
        nameValuePairs.add(new BasicNameValuePair("type", type));
        nameValuePairs.add(new BasicNameValuePair("key", key));
        nameValuePairs.add(new BasicNameValuePair("player", uuid.toString()));
        nameValuePairs.add(new BasicNameValuePair("amount", amount));

        try {
            postRequest.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF8"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        HttpResponse response = null;
        try {
            response = httpClient.execute(postRequest);
        } catch (IOException e) {
            e.printStackTrace();
        }
        HttpEntity entity = response.getEntity();
        String result = null;
        try {
            result = EntityUtils.toString(entity);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }

    public static GameAPI getInstance() {
        return instance;
    }

    public static class GameProfile {
        public GameProfile(Player p) {
            this.xp = getXp(p);
            this.coins = getCoins(p);
            String nick = getNick(p);
            this.nick = (nick.contains("true") ? true : (nick.contains("false")? false : false));
            this.player = p;
        }

        private int xp;
        private int coins;
        private boolean nick;
        private Player player;

        public int getPlayerXp() {
            return this.xp;
        }
        public int getPlayerCoins() {
            return this.coins;
        }
        public boolean getPlayerNick() {
            return this.nick;
        }

        public void addPlayerXp(int xp) {
            addXp(this.player, xp);
        }
        public void addPlayerCoins(int coins) {
            addCoins(this.player, coins);
        }
        public void setPlayerNick(boolean nick) {
            setNick(this.player, nick);
        }
    }
}