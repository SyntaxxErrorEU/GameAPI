package chat.belinked;

import com.google.gson.Gson;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** Class to manage the game log of a certain player */
public class GameLog {

    /** The owner of the gamelog */
    private Player player;

    /** The log will be saved in this list */
    private List<Map<String, String>> log = new ArrayList<>();

    /** Creates a new GameLog object
     * @param p The owner of the GameLog */
    public GameLog(Player p) {
        this.player = p;
    }

    /** Request the gamelog and update the variable */
    private void initGameLog() {
        Gson gson = new Gson();
        Map<String, List<Map<String, String>>> response = gson.fromJson(GameAPI.request("read", "getGameLog", this.player.getUniqueId(),""),
                HashMap.class);
        for(int i = 0; i < response.get("result").size(); i++) {
            this.log.add(response.get("result").get(i));
        }
    }

    /** Get the gamelog */
    public List<Map<String, String>> getGameList() {
        this.initGameLog();
        return this.log;
    }

    /** Add a game to the gamelog
     * @param result The GameID of the result */
    public void addGame(GameID result) {
        GameAPI.request("write", "addGame", this.player.getUniqueId(), result.getGameID());
    }
}
