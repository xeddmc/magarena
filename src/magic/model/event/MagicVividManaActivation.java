package magic.model.event;

import magic.model.MagicCounterType;
import magic.model.MagicManaType;
import magic.model.MagicPermanent;
import magic.model.MagicSource;
import magic.model.condition.MagicCondition;
import magic.model.condition.MagicConditionFactory;

import java.util.List;

public class MagicVividManaActivation extends MagicManaActivation {

    private static final MagicCondition[] CONDITIONS= {
        MagicCondition.CAN_TAP_CONDITION,
        MagicConditionFactory.ChargeCountersAtLeast(1)
    };
            
    public MagicVividManaActivation(final List<MagicManaType> manaTypes) {
        super(manaTypes,CONDITIONS,manaTypes.size() -1);
    }
        
    @Override
    public MagicEvent[] getCostEvent(final MagicPermanent permanent) {
        return new MagicEvent[]{
            new MagicTapEvent(permanent),
            new MagicRemoveCounterEvent(permanent,MagicCounterType.Charge,1)
        };
    }    
}
