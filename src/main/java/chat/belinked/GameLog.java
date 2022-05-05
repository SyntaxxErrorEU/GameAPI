package chat.belinked;

import com.google.gson.Gson;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

/** Class to manage the game log of a certain player */
public class GameLog {

    /** The owner of the gamelog */
    private Player player;

    /** The log will be saved in this list */
    private String log = "";

    /** Creates a new GameLog object
     * @param p The owner of the GameLog */
    public GameLog(Player p) {
        this.player = p;
    }

    /** Request the gamelog and update the variable */
    private void initGameLog() {
        Gson gson = new Gson();
        Map<String, String> response = gson.fromJson(GameAPI.getInstance().request("read", "getGameLog", this.player.getUniqueId(),""),
                HashMap.class);
        this.log = response.get("result");
    }

    /** Get the gamelog */
    public String getGameLog() {
        return this.log;
    }
}
