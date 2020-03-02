package main;

import java.util.List;

final class Basic extends Player {
    Basic(final String strategy) {
        super(strategy);
    }
    /* Creearea sacului*/
    public List<Card> fillSack(final int round) {
        setAssetsCount(0);
        setDeclared(new Card(0));
        getSack().clear();
        if (!getSheriff()) {
            setSack(basicSack());
            return getSack();
        }
        return null;
    }
    /* Inspectia*/
    public void inspection(final List<Player> players,
                           final List<Card> cards) {
        if (getSheriff()) {
            for (Player temp : players) {
                if (!temp.getSheriff()) {
                    basicInspection(temp, cards);
                }
            }
        }
        setSheriff(false);
    }
}
