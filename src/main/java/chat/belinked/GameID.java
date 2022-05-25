package chat.belinked;

/** Enum to get the gameid of a game. Usage: GameID.BEDWARS.getGameID() : String */
public enum GameID {
    /** The ID of a Bedwars victory */
    BEDWARS_WIN("bedwars:win"),
    /** The ID of a Bedwars defeat */
    BEDWARS("bedwars:defeat"),
    /** The ID of a molecraft victory */
    MOLECRAFT_WIN("molecraft:win"),
    /** The ID of a molecraft defeat */
    MOLECRAFT("molecraft:defeat");

    /** The ID of the game */
    protected String gameID = "";

    /** Constructor of this enum
     * @param key The key the GameID should be set to */
    GameID(String key) {
        this.gameID = key;
    }

    /** Method to get the protected var expToGive
     * @return The GameID */
    public String getGameID() {
        return this.gameID;
    }
}
