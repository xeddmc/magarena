package magic.card;

import magic.model.MagicCard;
import magic.model.MagicGame;
import magic.model.MagicLocationType;
import magic.model.MagicPermanent;
import magic.model.MagicPlayer;
import magic.model.action.MagicExileUntilThisLeavesPlayAction;
import magic.model.action.MagicPermanentAction;
import magic.model.action.MagicPlayCardAction;
import magic.model.action.MagicRemoveCardAction;
import magic.model.action.MagicSacrificeAction;
import magic.model.action.MagicTapAction;
import magic.model.choice.MagicChoice;
import magic.model.choice.MagicMayChoice;
import magic.model.choice.MagicTargetChoice;
import magic.model.event.MagicEvent;
import magic.model.target.MagicExileTargetPicker;
import magic.model.target.MagicTargetFilter;
import magic.model.target.MagicTargetHint;
import magic.model.trigger.MagicWhenComesIntoPlayTrigger;
import magic.model.trigger.MagicWhenLeavesPlayTrigger;

import java.util.Collection;

public class Mistbind_Clique {
    public static final MagicWhenComesIntoPlayTrigger T1 = new MagicWhenComesIntoPlayTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent, final MagicPlayer player) {
            final MagicTargetFilter<MagicPermanent> targetFilter = 
                    new MagicTargetFilter.MagicOtherPermanentTargetFilter(
                    MagicTargetFilter.TARGET_FAERIE_YOU_CONTROL,permanent);
            final MagicTargetChoice targetChoice = 
                    new MagicTargetChoice(
                    targetFilter,false,MagicTargetHint.None,"another Faerie to exile");
            final MagicChoice championChoice = 
                    new MagicMayChoice(targetChoice);
            return new MagicEvent(
                permanent,
                championChoice,
                MagicExileTargetPicker.create(),
                this,
                "PN may$ exile another Faerie you control$. " +
                "If you don't, sacrifice SN."
            );
        }
        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event) {
            final MagicPermanent permanent = event.getPermanent();
            if (event.isYes()) {
                event.processTargetPermanent(game,new MagicPermanentAction() {
                    public void doAction(final MagicPermanent creature) {
                        game.doAction(new MagicExileUntilThisLeavesPlayAction(permanent,creature));
                    }
                });
                final Collection<MagicPermanent> targets = game.filterPermanents(
                        event.getPlayer().getOpponent(),
                        MagicTargetFilter.TARGET_LAND_YOU_CONTROL);
                for (final MagicPermanent land : targets) {
                    game.doAction(new MagicTapAction(land,true));
                }
            } else {
                game.doAction(new MagicSacrificeAction(permanent));
            }
        }
    };
    
    public static final MagicWhenLeavesPlayTrigger T2 = new MagicWhenLeavesPlayTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent, final MagicPermanent left) {
            if (permanent == left &&
                !permanent.getExiledCards().isEmpty()) {
                final MagicCard exiledCard = permanent.getExiledCards().get(0);
                return new MagicEvent(
                        permanent,
                        permanent.getController(),
                        exiledCard,
                        this,
                        "Return " + exiledCard + " to the battlefield");
            }
            return MagicEvent.NONE;
        }
        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event) {
            final MagicCard exiledCard = event.getRefCard();
            game.doAction(new MagicRemoveCardAction(exiledCard,MagicLocationType.Exile));
            game.doAction(new MagicPlayCardAction(exiledCard,exiledCard.getOwner(),MagicPlayCardAction.NONE));
        }
    };
}
