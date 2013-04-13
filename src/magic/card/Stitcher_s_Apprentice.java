package magic.card;

import magic.data.TokenCardDefinitions;
import magic.model.MagicGame;
import magic.model.MagicManaCost;
import magic.model.MagicPayedCost;
import magic.model.MagicPermanent;
import magic.model.MagicPlayer;
import magic.model.action.MagicPermanentAction;
import magic.model.action.MagicPlayTokenAction;
import magic.model.action.MagicSacrificeAction;
import magic.model.choice.MagicTargetChoice;
import magic.model.condition.MagicCondition;
import magic.model.condition.MagicConditionFactory;
import magic.model.event.MagicActivationHints;
import magic.model.event.MagicEvent;
import magic.model.event.MagicEventAction;
import magic.model.event.MagicPayManaCostTapEvent;
import magic.model.event.MagicPermanentActivation;
import magic.model.event.MagicTiming;
import magic.model.target.MagicSacrificeTargetPicker;

public class Stitcher_s_Apprentice {
    public static final MagicPermanentActivation A = new MagicPermanentActivation(
        new MagicCondition[]{
            MagicCondition.CAN_TAP_CONDITION,
            MagicConditionFactory.ManaCost("{1}{U}")
        },
        new MagicActivationHints(MagicTiming.Token),
        "Token"
    ) {
        @Override
        public MagicEvent[] getCostEvent(final MagicPermanent source) {
            return new MagicEvent[]{new MagicPayManaCostTapEvent(source,source.getController(),MagicManaCost.create("{1}{U}"))};
        }
        @Override
        public MagicEvent getPermanentEvent(final MagicPermanent source,final MagicPayedCost payedCost) {
            return new MagicEvent(
                source,
                this,
                "PN puts a 2/2 blue Homunculus creature token " +
                "onto the battlefield, then sacrifices a creature."
            );
        }
        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event) {
            final MagicPlayer player = event.getPlayer();
            game.doAction(new MagicPlayTokenAction(player,TokenCardDefinitions.get("Homunculus2")));
            game.addEvent(new MagicEvent(
                event.getSource(),
                player,
                MagicTargetChoice.SACRIFICE_CREATURE,
                MagicSacrificeTargetPicker.create(),
                EVENT_ACTION,
                "Choose a creature to sacrifice$."
            ));
        }
    };
    
    private static final MagicEventAction EVENT_ACTION = new MagicEventAction() {
        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event) {
            event.processTargetPermanent(game,new MagicPermanentAction() {
                public void doAction(final MagicPermanent permanent) {
                    game.doAction(new MagicSacrificeAction(permanent));
                }
            });
        }
    };
}
