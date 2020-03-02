package main;

import java.util.List;

final class Bribed extends Player {
    Bribed(final String strategy) {
        super(strategy);
    }
    /* Creearea sacului*/
    public List<Card> fillSack(final int round) {
        setAssetsCount(0);
        int countIllegal = 0;
        setDeclared(new Card(0));
        getSack().clear();
        if (!getSheriff()) {
            countIllegal = countIllegals();
            if (countIllegal == 0 || getPocket() < SMALL_BRIBE) {
                setSack(basicSack());
                return getSack();
            }
            if (getPocket() < BIG_BRIBE || countIllegal < BIG_BRIBE_MIN_NR_OBJECTS) {
                for (int i = 0; i < 2; ++i) {
                    if (maxProfit().getLegal()) {
                        break;
                    }
                    addSack(maxProfit());
                    removeHand(maxProfit());
                    setAssetsCount(getAssetsCount() + 1);
                }
                setBribe(SMALL_BRIBE);
                setPocket(getPocket() - SMALL_BRIBE);
                setDeclared(new Card(0));
            } else {
                for (int i = 0; i < MAX_CARDS_TO_DRAW; ++i) {
                    if (maxProfit().getLegal()) {
                        break;
                    }
                    addSack(maxProfit());
                    removeHand(maxProfit());
                    setAssetsCount(getAssetsCount() + 1);
                }
                setBribe(BIG_BRIBE);
                setPocket(getPocket() - BIG_BRIBE);
                setDeclared(new Card(0));
            }
            return getSack();
        }
        return null;
    }
    /* Inspectia*/
    public void inspection(final List<Player> players,
                           final List<Card> cards) {
        if (getSheriff()) {
            int index = players.indexOf(this);
            Player left;
            Player right;
            if (index == 0) {
                left = (players.get(players.size() - 1));
            } else {
                left = (players.get(index - 1));
            }
            if (index == players.size() - 1) {
                right = (players.get(0));
            } else {
                right = (players.get(index + 1));
            }
            for (Player temp : players) {
                if (!temp.getSheriff()) {
                    if ((temp.equals(left)) || (temp.equals(right))) {
                        basicInspection(temp, cards);
                    } else {
                        setPocket(getBribe() + getPocket());
                        temp.tableAddAll(temp.getSack());
                    }
                }
            }
        }
        setSheriff(false);
    }
}
