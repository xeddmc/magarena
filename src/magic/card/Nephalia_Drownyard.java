package magic.card;

import magic.model.MagicGame;
import magic.model.MagicManaCost;
import magic.model.MagicPayedCost;
import magic.model.MagicPermanent;
import magic.model.MagicPlayer;
import magic.model.action.MagicMillLibraryAction;
import magic.model.action.MagicPlayerAction;
import magic.model.choice.MagicTargetChoice;
import magic.model.condition.MagicCondition;
import magic.model.condition.MagicConditionFactory;
import magic.model.event.MagicActivationHints;
import magic.model.event.MagicEvent;
import magic.model.event.MagicPayManaCostEvent;
import magic.model.event.MagicPermanentActivation;
import magic.model.event.MagicTapEvent;
import magic.model.event.MagicTiming;

public class Nephalia_Drownyard {
    public static final MagicPermanentActivation A = new MagicPermanentActivation(
            new MagicCondition[]{
                MagicConditionFactory.ManaCost("{2}{U}{B}"), //add ONE for the card itself
                MagicCondition.CAN_TAP_CONDITION
            },
            new MagicActivationHints(MagicTiming.Pump),
            "Pump") {

        @Override
        public MagicEvent[] getCostEvent(final MagicPermanent source) {
            return new MagicEvent[]{
                    new MagicTapEvent(source),
                    new MagicPayManaCostEvent(source,source.getController(),MagicManaCost.create("{1}{U}{B}"))};
        }

        @Override
        public MagicEvent getPermanentEvent(final MagicPermanent source,final MagicPayedCost payedCost) {
            return new MagicEvent(
                    source,
                    source.getController(),
                    MagicTargetChoice.TARGET_PLAYER,
                    this,
                    "Target player$ puts the top three cards of " +
                    "his or her library into his or her graveyard.");
        }

        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event) {
            event.processTargetPlayer(game,new MagicPlayerAction() {
                public void doAction(final MagicPlayer player) {
                    game.doAction(new MagicMillLibraryAction(player,3));
                }
            });
        }
    };
}
