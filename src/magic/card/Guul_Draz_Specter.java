package magic.card;
import java.util.*;
import magic.model.event.*;
import magic.model.stack.*;
import magic.model.choice.*;
import magic.model.target.*;
import magic.model.action.*;
import magic.model.trigger.*;
import magic.model.condition.*;
import magic.model.*;
import magic.data.*;
import magic.model.variable.*;

public class Guul_Draz_Specter {
	
    private static final MagicLocalVariable GUUL_DRAZ_SPECTER=new MagicDummyLocalVariable() {
		@Override
		public void getPowerToughness(final MagicGame game,final MagicPermanent permanent,final MagicPowerToughness pt) {
			if (game.getOpponent(permanent.getController()).getHand().isEmpty()) {
				pt.power+=3;
				pt.toughness+=3;
			}
		}		
	};

    public static final MagicTrigger V7579 =new MagicSpecterTrigger("Guul Draz Specter",true,false);
    
    public static final MagicChangeCardDefinition SET = new MagicChangeCardDefinition() {
        @Override
        public void change(MagicCardDefinition cdef) {
		    cdef.addLocalVariable(MagicStaticLocalVariable.getInstance());
		    cdef.addLocalVariable(GUUL_DRAZ_SPECTER);
        }
    };
    
}
