package magic.card;

import magic.model.MagicGame;
import magic.model.MagicLocationType;
import magic.model.MagicPermanent;
import magic.model.MagicPlayer;
import magic.model.action.MagicPermanentAction;
import magic.model.action.MagicRemoveFromPlayAction;
import magic.model.action.MagicSacrificeAction;
import magic.model.choice.MagicMayChoice;
import magic.model.choice.MagicTargetChoice;
import magic.model.event.MagicEvent;
import magic.model.target.MagicBounceTargetPicker;
import magic.model.trigger.MagicWhenComesIntoPlayTrigger;

public class Glint_Hawk {
    public static final MagicWhenComesIntoPlayTrigger T = new MagicWhenComesIntoPlayTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent, final MagicPlayer player) {
            return new MagicEvent(
                permanent,
                new MagicMayChoice(
                    MagicTargetChoice.ARTIFACT_YOU_CONTROL
                ),
                MagicBounceTargetPicker.getInstance(),
                this,
                "You may$ return an artifact you control to its owner's hand. " +
                "If you don't, sacrifice SN."
            );
        }
        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event) {
            if (event.isNo()) {
                game.doAction(new MagicSacrificeAction(event.getPermanent()));
            } else {
                event.processTargetPermanent(game,new MagicPermanentAction() {
                    public void doAction(final MagicPermanent creature) {
                        game.doAction(new MagicRemoveFromPlayAction(creature,MagicLocationType.OwnersHand));
                    }
                });
            }
        }
    };
}
