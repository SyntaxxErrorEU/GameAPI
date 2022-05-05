package chat.belinked;

/** Enum to get the EXP of certain events. Usage: ExpAmount.BEDWARS.getXp() : int */
public enum ExpAmount {
    /** The Exp of a Bedwars victory */
    BEDWARS_WIN(20),
    /** The Exp of a Bedwars defeat */
    BEDWARS(3),
    /** The Exp of a molecraft victory */
    MOLECRAFT_WIN(20),
    /** The Exp of a molecraft defeat */
    MOLECRAFT(3);

    /** The Exp to give */
    protected int expToGive = 0;

    /** Constructor of this enum
     * @param exp The amount of exp the var expToGive shoudld be set to */
    ExpAmount(int exp) {
        this.expToGive = exp;
    }

    /** Method to get the protected var expToGive
     * @return expToGive */
    public int getExp() {
        return this.expToGive;
    }
}