package io.harjun751.monopoly;

public class getOutJailAction extends SpecialActionCard {
    public void doAction(Player cardDrawer) {
        cardDrawer.addGoojCards(this);
    }

    public getOutJailAction(){
        super("Get out of jail card.");
    }
}
