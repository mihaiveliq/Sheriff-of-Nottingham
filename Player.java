package main;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class Player implements PlayerTemplate {
    private String strategy;
    private int countSheriff;
    private boolean sheriff;
    private int pocket;
    private int bribe;
    private int assetsCount;
    private int scor;
    private int nrApple;
    private int nrCheese;
    private int nrBread;
    private int nrChicken;
    private int ok;
    private List<Card> hand;
    private List<Card> sack;
    private List<Card> table;
    private Card declared;

    Player(final String strategy) {
        this.strategy = strategy;
        countSheriff = 0;
        sheriff = false;
        pocket = START_GOLD;
        bribe = 0;
        assetsCount = 0;
        scor = 0;
        nrApple = 0;
        nrCheese = 0;
        nrBread = 0;
        nrChicken = 0;
        ok = 0;
        hand = new LinkedList<>();
        sack = new LinkedList<>();
        table = new LinkedList<>();
        declared = new Card(0);
    }
    /* Afiseaza jucatorul*/
    public void print() {
        if (strategy.equals("basic")) {
            System.out.println("BASIC:" + scor);
        }
        if (strategy.equals("greedy")) {
            System.out.println("GREEDY:" + scor);
        }
        if (strategy.equals("bribed")) {
            System.out.println("BRIBED:" + scor);
        }
        if (strategy.equals("wizard")) {
            System.out.println("WIZARD:" + scor);
        }
    }

    /* Determina a cata oara se afla cartea in mana*/
    private int getFrequency(final String asset) {
        int frequency = 0;
        for (int i = 0; i < hand.size(); ++i) {
            if (hand.get(i).getAsset().equals(asset)) {
                ++frequency;
            }
        }
        return frequency;
    }

    /* Returneaza cea mai frecventa carte legala*/
    /* Daca mai multe carti au aceeasi frecventa,
        se returneaza cea cu cel mai mare profit*/
    /* Daca si profitul este egal, se intoarce
        cartea care apare prima data in mana*/
    private Card getMaxFrequency(final List<Card> list) {
        Card aux = list.get(0);
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i).getLegal()) {
                if (list.get(i).getFreq() > aux.getFreq()) {
                    aux = list.get(i);
                }
                if (list.get(i).getFreq() == aux.getFreq()) {
                    if (list.get(i).getProfit() > aux.getProfit()) {
                        aux = list.get(i);
                    }
                    if (list.get(i).getProfit() == aux.getProfit()) {
                        int one = firstAppearance(list.get(i));
                        int two = firstAppearance(aux);
                        if (one < two) {
                            aux = list.get(i);
                        }
                    }
                }
                if (!aux.getLegal()) {
                    aux = list.get(i);
                }

            }
        }
        if (aux.getLegal()) {
            return aux;
        }
        return null;
    }
    /* Returneaza cartea cu cel mai mare profit din mana*/
    final Card maxProfit() {
        Card maxProfit = hand.get(0);
        for (Card temp : hand) {
            if (maxProfit.getProfit() < temp.getProfit()) {
                maxProfit = temp;
            }
        }
         return maxProfit;
    }

    /* Umple mana cu 6 carti*/
    void fillHand(final List<Card> a) {
        int i = hand.size();
        while (i <= MAX_CARDS_TO_DRAW) {
            hand.add(a.remove(0)); //adaug carti din pachet
            // stabilesc numarul aparitiei cartii in mana
            hand.get(i).setFreq(getFrequency(hand.get(i).getAsset()));
            ++i;
        }
    }
    /* Ne ajuta sa stabilim de cate ori apare o carte legala atat in mana,
    * cat si pe taraba*/
    private void countAssetFull(final Card a) {
        a.setCountCard(0);
        for (Card temp : hand) {
            if ((temp.getId() == a.getId()) && (temp.getLegal())) {
                a.incCountCard();
                this.ok = 1;
            }
        }
        for (Card temp : table) {
            if ((temp.getId() == a.getId()) && (temp.getLegal())) {
                a.incCountCard();
                this.ok = 1;
            }
        }
    }
    /* Calculeaza scorul specific strategiei Royal,
     pentru fiecare carte din mana*/
    private void setAllwizardScores() {
        for (Card temp : hand) {
            temp.setWizardScore(temp.getCountCard() * temp.getProfit());
            if (temp.getId() == APPLE_ID) {
                temp.addWizardScore(APPLE_KING_BONUS + APPLE_QUEEN_BONUS);
            }
            if (temp.getId() == CHEESE_ID) {
                temp.addWizardScore(CHEESE_KING_BONUS + CHEESE_QUEEN_BONUS);
            }
            if (temp.getId() == BREAD_ID) {
                temp.addWizardScore(BREAD_KING_BONUS + BREAD_QUEEN_BONUS);
            }
            if (temp.getId() == CHICKEN_ID) {
                temp.addWizardScore(CHICKEN_KING_BONUS + CHICKEN_QUEEN_BONUS);
            }
        }
    }
    /* Returneaza cartea cu scorul de tip Royal cel mai mare*/
    private Card retAssetFull() {
        for (Card temp : hand) {
            countAssetFull(temp);
        }
        if (this.ok == 0) {
            return maxProfit();
        }
        setAllwizardScores();
        Card maxAssetFull = hand.get(0);
        for (Card temp : hand) {
            if (temp.getWizardScore() > maxAssetFull.getWizardScore()) {
                maxAssetFull = temp;
            }
            if (temp.getWizardScore() == maxAssetFull.getWizardScore()) {
                if (temp.getProfit() > maxAssetFull.getProfit()) {
                    maxAssetFull = temp;
                }
                if (temp.getProfit() == maxAssetFull.getProfit()) {
                    if (firstAppearance(temp)
                            < firstAppearance(maxAssetFull)) {
                        maxAssetFull = temp;
                    }
                }
            }
        }
        return maxAssetFull;
    }
    /* Functie de golire a sacului*/
    final void clearSack() {
        sack.clear();
        bribe = 0;
    }
    /* Returneaza numarul de carti ilegale din mana*/
    final int countIllegals() {
        int count = 0;
        for (Card tmp : hand) {
            if (!tmp.getLegal()) {
                count++;
            }
        }
        return count;
    }
    /* Stabileste daca un jucator minte sau nu*/
    private boolean isLiar(final Player a) {
        boolean x = false;
        for (Card aux : a.sack) {
            if ((!aux.getLegal()) || (!aux.equals(a.declared))) {
                x = true;
            }
        }
        return x;
    }
    /* Stabileste prima aparitie a cartii in mana*/
    private int firstAppearance(final Card a) {
        for (int i = 0; i < hand.size(); ++i) {
            if (a.getId() == hand.get(i).getId()) {
                return i;
            }
        }
        return 0;
    }
    /* Functie ajutatoare pentru creearea sacilor*/
    private  List<Card> helperSack(final Card aux,
                                   final List<Card> toBeRemoved) {
        toBeRemoved.add(aux);
        int id = aux.getId();
        for (Card temp : hand) {
            if (temp.equals(aux)) {
                sack.add(aux);
                assetsCount++;
                toBeRemoved.add(aux);
            }
        }
        hand.removeAll(toBeRemoved);
        List<Integer> out = new ArrayList<>();
        int outIndex;
        int okay = 0;
        int countOk = 0;
        for (int index = 0; index < hand.size(); ++index) {
            if (hand.get(index).getId() == id) {
                out.add(index);
                okay = 1;
                countOk++;
            }
        }
        if (okay == 1) {
            while (countOk != 0) {
                outIndex = out.remove(out.size() - 1);
                hand.remove(outIndex);
                sack.add(aux);
                countOk--;
            }
        }
        declared = aux;
        return sack;
    }

    /* Metode specifice jucatorilor*/
    /* Creeaza sacul te tip Basic*/
    final List<Card> basicSack() {
        List<Card> toBeRemoved = new LinkedList<>();
        Card aux = getMaxFrequency(hand);
        if (aux != null) {
            return helperSack(aux, toBeRemoved);
        } else if (!maxProfit().getLegal()) {
            sack.add(maxProfit());
            assetsCount++;
            hand.remove(maxProfit());
        }
        return sack;
    }
    /* Creeaza sacul te tip wizard*/
    final List<Card> wizardSack() {
        List<Card> toBeRemoved = new LinkedList<>();
        Card aux = retAssetFull();
        if (aux.getLegal()) {
            return helperSack(aux, toBeRemoved);
        }
        sack.add(maxProfit());
        assetsCount++;
        hand.remove(maxProfit());
        return sack;
    }
    /* Inspectie de tip Basic*/
    final void basicInspection(final Player temp, final List<Card> cards) {
        List<Card> toBeremoved = new LinkedList<>();
        if (temp.bribe != 0) {
            temp.pocket += temp.bribe;
            temp.bribe = 0;
        }
        if (temp.isLiar(temp)) {
            for (Card aux : temp.sack) {
                if ((!aux.getAsset().equals(temp.declared.getAsset())) || (!aux.getLegal())) {
                    pocket += aux.getPenalty();
                    temp.pocket -= aux.getPenalty();
                    if (temp.pocket < 0) {
                        int rest = 0 - temp.pocket;
                        temp.pocket = 0;
                        pocket -= rest;
                    }
                    cards.add(aux);
                    toBeremoved.add(aux);
                } else {
                    temp.table.add(aux);
                }
            }
        } else {
            Card aux = temp.sack.get(0);
            pocket -= aux.getPenalty() * temp.sack.size();
            if (pocket < 0) {
                int rest = 0 - pocket;
                pocket = 0;
                temp.pocket += rest + aux.getPenalty()
                        * temp.sack.size();
            }
            temp.pocket += aux.getPenalty() * temp.sack.size();
            for (Card aux2 : temp.sack) {
                temp.table.add(aux2);
                toBeremoved.add(aux2);
            }
        }
        temp.sack.removeAll(toBeremoved);
    }
    /* Metode abstracte de creare a sacilor si de inspectie
    * ce vor fi implementate de fiecare jucator in parte*/
    public abstract List<Card> fillSack(int round);
    public abstract void inspection(List<Player> players, List<Card> cards);
    /* Geters*/
    final String getStrategy() {
        return this.strategy;
    }
    final int getCountSheriff() {
        return this.countSheriff;
    }
    final boolean getSheriff() {
        return this.sheriff;
    }
    final int getPocket() {
        return this.pocket;
    }
    final int getBribe() {
        return this.bribe;
    }
    final int getAssetsCount() {
        return this.assetsCount;
    }
    final int getScor() {
        return this.scor;
    }
    final int getNrApple() {
        return this.nrApple;
    }
    final int getNrCheese() {
        return this.nrCheese;
    }
    final int getNrBread() {
        return this.nrBread;
    }
    final int getNrChicken() {
        return this.nrChicken;
    }
    final List<Card> getHand() {
        return this.hand;
    }
    final List<Card> getSack() {
        return this.sack;
    }
    final List<Card> getTable() {
        return this.table;
    }
    final Card getDeclared() {
        return this.declared;
    }
    /* Setters*/
    final void setSheriff(final boolean i) {
        this.sheriff = i;
    }
    final void setSack(final List<Card> i) {
        this.sack = i;
    }
    final void setAssetsCount(final int i) {
        this.assetsCount = i;
    }
    final void setDeclared(final Card i) {
        this.declared = i;
    }
    final void setCountSheriff() {
        this.countSheriff++;
    }
    final void setPocket(final int i) {
        this.pocket = i;
    }
    final void setBribe(final int i) {
        this.bribe = i;
    }
    final void addScore(final int i) {
        this.scor += i;
    }
    final void addSack(final Card i) {
        this.sack.add(i);
    }
    final void removeHand(final Card i) {
        this.hand.remove(i);
    }
    final void tableAddAll(final List<Card> i) {
        this.table.addAll(i);
    }
    final void incApple() {
        this.nrApple++;
    }
    final void incCheese() {
        this.nrCheese++;
    }
    final void incBread() {
        this.nrBread++;
    }
    final void incChicken() {
        this.nrChicken++;
    }
}
