package main;

import java.util.List;

final class Greedy extends Player {
    Greedy(final String strategy) {
        super(strategy);
    }
    /* Creearea sacului*/
    public List<Card> fillSack(final int round) {
        setAssetsCount(0);
        setDeclared(new Card(0));
        getSack().clear();
        if (!getSheriff()) {
            setSack(basicSack());
            Card illegal = maxProfit();
            if (((round % 2 == 0) && (getSack().size() < MAX_CARDS_TO_DRAW))
                    && (!illegal.getLegal())) {
                addSack(illegal);
                setAssetsCount(getAssetsCount() + 1);
                removeHand(illegal);
            }
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
                    if (temp.getBribe() == 0) {
                        basicInspection(temp, cards);
                    } else {
                        setPocket(temp.getBribe() + getPocket());
                        temp.tableAddAll(temp.getSack());
                    }
                }
            }
        }
        setSheriff(false);
    }
}
