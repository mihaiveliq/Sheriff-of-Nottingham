package main;

import java.util.LinkedList;
import java.util.List;

final class BonusSetter {
    private List<Player> players;
    BonusSetter(final List<Player> players) {
        this.players = players;
        int maxApple = 0;
        int maxCheese = 0;
        int maxBread = 0;
        int maxChicken = 0;
        int minApple = 0;
        int minCheese = 0;
        int minBread = 0;
        int minChicken = 0;
        final int appleID = 0;
        final int cheeseID = 1;
        final int breadID = 2;
        final int chickenID = 3;
        final int silkID = 10;
        final int pepperID = 11;
        final int barrelID = 12;
        final int silkExtraCards = 3;
        final int pepperExtraCards = 2;
        final int barrelExtraCards = 2;
        List<Card> addTable = new LinkedList<>();
        for (Player temp : players) {
            for (Card aux : temp.getTable()) {
                if (aux.getId() == silkID) {
                    for (int i = 0; i < silkExtraCards; ++i) {
                        Card branza = new Card(cheeseID);
                        addTable.add(branza);
                    }
                }
                if (aux.getId() == pepperID) {
                    for (int i = 0; i < pepperExtraCards; ++i) {
                        Card pui = new Card(chickenID);
                        addTable.add(pui);
                    }
                }
                if (aux.getId() == barrelID) {
                    for (int i = 0; i < barrelExtraCards; ++i) {
                        Card paine = new Card(breadID);
                        addTable.add(paine);
                    }
                }
            }
            temp.tableAddAll(addTable);
            addTable.clear();
        }
        for (Player temp : players) {
            for (Card aux : temp.getTable()) {
                temp.addScore(aux.getProfit());
                if (aux.getId() == appleID) {
                    temp.incApple();
                }
                if (aux.getId() == cheeseID) {
                    temp.incCheese();
                }
                if (aux.getId() == breadID) {
                    temp.incBread();
                }
                if (aux.getId() == chickenID) {
                    temp.incChicken();
                }
            }
            temp.addScore(temp.getPocket());

            if (temp.getNrApple() > maxApple) {
                minApple = maxApple;
                maxApple = temp.getNrApple();
            } else if ((temp.getNrApple() > minApple) && (temp.getNrApple() < maxApple)) {
                minApple = temp.getNrApple();
            }
            if (temp.getNrCheese() > maxCheese) {
                minCheese = maxCheese;
                maxCheese = temp.getNrCheese();
            } else if ((temp.getNrCheese() > minCheese) && (temp.getNrCheese() < maxCheese)) {
                minCheese = temp.getNrCheese();
            }
            if (temp.getNrBread() > maxBread) {
                minBread = maxBread;
                maxBread = temp.getNrBread();
            } else if ((temp.getNrBread() > minBread) && (temp.getNrBread() < maxBread)) {
                minBread = temp.getNrBread();
            }
            if (temp.getNrChicken() > maxChicken) {
                minChicken = maxChicken;
                maxChicken = temp.getNrChicken();
            } else if ((temp.getNrChicken() > minChicken) && (temp.getNrChicken() < maxChicken)) {
                minChicken = temp.getNrChicken();
            }
        }

        for (Player temp : players) {
            if ((temp.getNrApple() == maxApple)) {
                temp.addScore(temp.APPLE_KING_BONUS);
            }
            if ((temp.getNrCheese() == maxCheese)) {
                temp.addScore(temp.CHEESE_KING_BONUS);
            }
            if ((temp.getNrBread() == maxBread)) {
                temp.addScore(temp.BREAD_KING_BONUS);
            }
            if ((temp.getNrChicken() == maxChicken)) {
                temp.addScore(temp.CHICKEN_KING_BONUS);
            }
            if ((players.size() > 1)) {
                if ((temp.getNrApple() == minApple) && (maxApple != minApple)) {
                    temp.addScore(temp.APPLE_QUEEN_BONUS);
                }
                if ((temp.getNrCheese() == minCheese) && (maxCheese != minCheese)) {
                    temp.addScore(temp.CHEESE_QUEEN_BONUS);
                }
                if ((temp.getNrBread() == minBread) && (maxBread != minBread)) {
                    temp.addScore(temp.BREAD_QUEEN_BONUS);
                }
                if ((temp.getNrChicken() == minChicken) && (maxChicken != minChicken)) {
                    temp.addScore(temp.CHICKEN_QUEEN_BONUS);
                }
            }
        }
    }
    List<Player> getPlayers() {
        return this.players;
    }
}
