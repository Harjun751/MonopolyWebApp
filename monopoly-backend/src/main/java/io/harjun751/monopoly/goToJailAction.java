package io.harjun751.monopoly;

public class goToJailAction extends SpecialActionCard {
    public void doAction(Player cardDrawer) {
        cardDrawer.changeState(new jailPlayerState(cardDrawer));
    }

    public goToJailAction(){
        super("Go to jail.");
    }
}
