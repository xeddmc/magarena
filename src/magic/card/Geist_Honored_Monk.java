package magic.card;

import magic.data.TokenCardDefinitions;
import magic.model.MagicGame;
import magic.model.MagicPermanent;
import magic.model.MagicPlayer;
import magic.model.MagicPowerToughness;
import magic.model.MagicType;
import magic.model.action.MagicPlayTokensAction;
import magic.model.event.MagicEvent;
import magic.model.mstatic.MagicCDA;
import magic.model.trigger.MagicWhenComesIntoPlayTrigger;

public class Geist_Honored_Monk {
    public static final MagicCDA CDA = new MagicCDA() {
        @Override
        public void modPowerToughness(final MagicGame game, final MagicPlayer player, final MagicPowerToughness pt) {
            final int amount = player.getNrOfPermanentsWithType(MagicType.Creature);
            pt.set(amount, amount);
        }
    };
    
    public static final MagicWhenComesIntoPlayTrigger T = new MagicWhenComesIntoPlayTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicPlayer player) {
            return new MagicEvent(
                    permanent,
                    this,
                    "PN puts two 1/1 white Spirit creature tokens with flying onto the battlefield.");
        }
        
        @Override
        public void executeEvent(
                final MagicGame game,
                final MagicEvent event) {
            game.doAction(new MagicPlayTokensAction(
                event.getPlayer(),
                TokenCardDefinitions.get("Spirit2"),
                2
            ));
        }        
    };
}
