package eu.soupmc;

public enum ExpAmount {
    BEDWARS_WIN("bedwars-win") {
        public int getExp() {
            return 20;
        }
    }, BEDWARS("bedwars") {
        public int getExp() {
            return 3;
        }
    }, MOLECRAFT_WIN("molecraft-win") {
        public int getExp() {
            return 20;
        }
    }, MOLECRAFT("molecraft") {
        public int getExp() {
            return 3;
        }
    };

    ExpAmount(String exp) {

    }
}