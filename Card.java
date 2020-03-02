package main;

final class Card {
    private int id;
    private int frequency;
    private String asset;
    private boolean legal; // true for legal, false for illegal
    private int profit;
    private int penalty;
    private int bonus;
    private int countCard;
    private int wizardScore;
    private static final int APPLE_ID = 0;
    private static final int CHEESE_ID = 1;
    private static final int BREAD_ID = 2;
    private static final int CHICKEN_ID = 3;
    private static final int SILK_ID = 10;
    private static final int PEPPER_ID = 11;
    private static final int BARREL_ID = 12;
    private static final int APPLE_PROFIT = 2;
    private static final int CHEESE_PROFIT = 3;
    private static final int BREAD_PROFIT = 4;
    private static final int CHICKEN_PROFIT = 4;
    private static final int SILK_PROFIT = 9;
    private static final int PEPPER_PROFIT = 8;
    private static final int BARREL_PROFIT = 7;
    private static final int APPLE_PENALTY = 2;
    private static final int CHEESE_PENALTY = 2;
    private static final int BREAD_PENALTY = 2;
    private static final int CHICKEN_PENALTY = 2;
    private static final int SILK_PENALTY = 4;
    private static final int PEPPER_PENALTY = 4;
    private static final int BARREL_PENALTY = 4;
    private static final int SILK_BONUS = 9;
    private static final int PEPPER_BONUS = 8;
    private static final int BARREL_BONUS = 8;

    Card(final int id) {
        this.id = id;
        frequency = 0;
        countCard = 0;
        wizardScore = 0;

        if (id == APPLE_ID) {
            asset = "Apple";
            legal = true;
            profit = APPLE_PROFIT;
            penalty = APPLE_PENALTY;
            bonus = 0;
        }

        if (id == CHEESE_ID) {
            asset = "Cheese";
            legal = true;
            profit = CHEESE_PROFIT;
            penalty = CHEESE_PENALTY;
            bonus = 0;
        }

        if (id == BREAD_ID) {
            asset = "Bread";
            legal = true;
            profit = BREAD_PROFIT;
            penalty = BREAD_PENALTY;
            bonus = 0;
        }

        if (id == CHICKEN_ID) {
            asset = "Chicken";
            legal = true;
            profit = CHICKEN_PROFIT;
            penalty = CHICKEN_PENALTY;
            bonus = 0;
        }

        if (id == SILK_ID) {
            asset = "Silk";
            legal = false;
            profit = SILK_PROFIT;
            penalty = SILK_PENALTY;
            bonus = SILK_BONUS; //3 * Cheese
        }

        if (id == PEPPER_ID) {
            asset = "Pepper";
            legal = false;
            profit = PEPPER_PROFIT;
            penalty = PEPPER_PENALTY;
            bonus = PEPPER_BONUS; //2 * Chicken
        }

        if (id == BARREL_ID) {
            asset = "Barrel";
            legal = false;
            profit = BARREL_PROFIT;
            penalty = BARREL_PENALTY;
            bonus = BARREL_BONUS; //2 * Bread
        }
    }

    public String toString() {
        return Integer.toString(id);
    }
    /* Getters*/
    int getId() {
        return this.id;
    }
    int getFreq() {
        return this.frequency;
    }
    boolean getLegal() {
        return this.legal;
    }
    String getAsset() {
        return this.asset;
    }
    int getProfit() {
        return this.profit;
    }
    int getPenalty() {
        return this.penalty;
    }
    int getBonus() {
        return this.bonus;
    }
    int getCountCard() {
        return this.countCard;
    }
    int getWizardScore() {
        return this.wizardScore;
    }
    /* Setters*/
    void setFreq(final int i) {
        this.frequency = i;
    }
    void setCountCard(final int i) {
        this.countCard = i;
    }
    void incCountCard() {
        this.countCard++;
    }
    void setWizardScore(final int i) {
        this.wizardScore = i;
    }
    void addWizardScore(final int i) {
        this.wizardScore += i;
    }
}
