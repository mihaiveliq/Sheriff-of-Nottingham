package main;

import java.util.List;

final class Wizard extends Player {
    Wizard(final String strategy) {
        super(strategy);
    }
    /* Creearea sacului*/
    public List<Card> fillSack(final int round) {
        setAssetsCount(0);
        setDeclared(new Card(0));
        getSack().clear();
        if (!getSheriff()) {
            setSack(wizardSack());
            return getSack();
        }
        return null;
    }
    /* Inspectia*/
    public void inspection(final List<Player> players, final List<Card> cards) {
        if (getSheriff()) {
            for (Player temp : players) {
                if (!temp.getSheriff()) {
                    if (temp.getSack().size() >= SUSPECT_NR_CARDS) {
                        basicInspection(temp, cards);
                    }
                }
            }
        }
        setSheriff(false);
    }
}
