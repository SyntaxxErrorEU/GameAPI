package eu.soupmc;

/** Enum to get the EXP of certain events. Usage: ExpAmount.BEDWARS.getXp() : int */
public enum ExpAmount {
    /** The Exp of a Bedwars victory */
    BEDWARS_WIN("bedwars-win") {
        @Override
        public int getExp() {
            expToGive = 20;
            return 20;
        }
    },
    /** The Exp of a Bedwars defeat */
    BEDWARS("bedwars") {
        @Override
        public int getExp() {
            expToGive = 3;
            return 3;
        }
    },
    /** The Exp of a molecraft victory */
    MOLECRAFT_WIN("molecraft-win") {
        @Override
        public int getExp() {
            expToGive = 20;
            return 20;
        }
    },
    /** The Exp of a molecraft defeat */
    MOLECRAFT("molecraft") {
        @Override
        public int getExp() {
            expToGive = 3;
            return 3;
        }
    };

    /** The Exp to give */
    protected int expToGive = 0;

    /** Constructor of this enum
     * @param exp The amount of exp the var expToGive shoudld be set to */
    ExpAmount(String exp) {
        this.expToGive = Integer.parseInt(exp);
    }

    /** Method to get the protected var expToGive
     * @return expToGive */
    public int getExp() {
        return this.expToGive;
    }
}