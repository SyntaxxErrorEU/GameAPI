package chat.belinked;

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

/** Main class of the Game API, used for basic cloudnet stuff + XP, Coins and Nick requests */
public class GameAPI {

    /** The main class of the plugin using this API */
    public Class<? extends JavaPlugin> plugin;
    /** The instance of this class */
    public static GameAPI instance;

    /** Constructor to get the plugin class
     * @param passedPlugin The plugin extending JavaPlugin */
    public GameAPI(Class<? extends JavaPlugin> passedPlugin) {
        this.plugin = passedPlugin;
        instance = this;
        Bukkit.getConsoleSender().sendMessage("------ Hello from the GameApi ------");
        Bukkit.getConsoleSender().sendMessage("------ Developed by jobebe07  ------");
    }

    /** Set the ingame status of the server using the cloudnet API */
    public void setIngameStatus() {
        BukkitCloudNetHelper.changeToIngame();
    }

    /** Set max players of a server using the cloudnet API
     * @param maxPlayers The maximum of players who can join the server*/
    public void setMaxPlayers(int maxPlayers) {
        BukkitCloudNetHelper.setMaxPlayers(maxPlayers);
    }

    /** Get max players of a server using the cloudnet API
     * @return The max players value of the current server */
    public int getMaxPlayers() {
        return BukkitCloudNetHelper.getMaxPlayers();
    }

    /** Add a specific amount of xp points to a player
     * @param player The player the Xp should be added to
     * @param xp The amount of xp the player should receive */
    protected static void addXp(Player player, int xp) {
        instance.request("write", "addXP", player.getUniqueId(), "" + xp);
    }

    /** Get the XP from a player
     * @param p The player of the xp
     * @return The players xp */
    protected static int getXp(Player p) {
        Gson gson = new Gson();
        Map<String, String> response = gson.fromJson(instance.request("read", "getXP", p.getUniqueId(), ""), HashMap.class);
        if(response.get("errors") != "") {
            Bukkit.getConsoleSender().sendMessage(response.get("errors"));
        }
        return Integer.parseInt(response.get("result"));
    }

    /** Add a specific amount of coins to a player
     * @param player The player the coins should be added to
     * @param coins The amount of coins the player should receive */
    protected static void addCoins(Player player, int coins) {
        instance.request("write", "addCoins", player.getUniqueId(), "" + coins);
    }

    /** Get the coins from a player
     * @param p The player of the coins ä
     * @return The players coins */
    protected static int getCoins(Player p) {
        Gson gson = new Gson();
        Map<String, String> response = gson.fromJson(instance.request("read", "getCoins", p.getUniqueId(), ""), HashMap.class);
        if(response.get("errors") != "") {
            Bukkit.getConsoleSender().sendMessage(response.get("errors"));
        }
        return Integer.parseInt(response.get("result"));
    }

    /** Get the nick status from a player
     * @param p The player of the nick status
     * @return The String value of the nick status of the player */
    protected static String getNick(Player p) {
        Gson gson = new Gson();
        Map<String, String> response = gson.fromJson(instance.request("read", "getNick", p.getUniqueId(), ""), HashMap.class);
        if(response.get("errors") != "") {
            Bukkit.getConsoleSender().sendMessage(response.get("errors"));
        }
        return response.get("result").trim();
    }

    /** Set the nick status of a player
     * @param p The player which nick should be set
     * @param value The boolean value the nick of the player should have */
    protected static void setNick(Player p, boolean value) {
        instance.request("write", "setNick", p.getUniqueId(), (value ? "true" : "false"));
    }

    /** Method for a basic POST request to the WebClient API
     * @param type Specifies the type of the request, in this case either 'read' or 'write'
     * @param key Specifies the key of the request, e.g. getCoins
     * @param uuid The uuid of the player in the database
     * @param amount Optional, if not required, '', sets the amount of a write request, e.g. for addCoins: 20
     * @return A json string containing the http response */
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

    /** Returns the Game API instance
     * @return GameAPI instance */
    public static GameAPI getInstance() {
        return instance;
    }

    /** Sub class to create a game profile of a certain player to have all methods at one location */
    public static class GameProfile {
        /** Constructor of GameProfile, requests the Xp of the profile when its created
         * @param p The player of the game profile */
        public GameProfile(Player p) {
            //this.update(p);
            this.player = p;
            this.log = new GameLog(this.player);
        }

        private Player player;
        private GameLog log;

        /** Get the xp points of the Game Profile
         * @return Players xp */
        public int getPlayerXp() {
            //return this.xp;
            return getXp(this.player);
        }

        /** Get the coins of the Game Profile
         * @return Players coins */
        public int getPlayerCoins() {
            //return this.coins;
            return getCoins(this.player);
        }

        /** Get the nick status of the Game Profile
         * @return Players nick status*/
        public boolean getPlayerNick() {
            //return this.nick;
            return (getNick(this.player).contains("true") ? true : false);
        }

        /** Add xp points to the Game Profile
         * @param xp The xp the player should receive*/
        public void addPlayerXp(int xp) {
            addXp(this.player, xp);
        }

        /** Add coins to the Game Profile
         * @param coins The coins the player should receive */
        public void addPlayerCoins(int coins) {
            addCoins(this.player, coins);
        }

        /** Set the nick status of the Game Profile
         * @param nick The nick status of the player */
        public void setPlayerNick(boolean nick) {
            setNick(this.player, nick);
        }

        /** Return the gamelog as a string */
        public String getGameLog() {
            return this.log.getGameLog();
        }

        /** Requests again
         * @param p Updates the values of the player from the database
         * @deprecated */
        public void update(Player p) {
            this.xp = getXp(p);
            this.coins = getCoins(p);
            String nick = getNick(p);
            this.nick = (nick.contains("true") ? true : (nick.contains("false")? false : false));
            this.player = p;
        }
        private int xp;
        private int coins;
        private boolean nick;
    }
}
