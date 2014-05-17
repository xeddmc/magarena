[
    new MagicWhenOtherSpellIsCastTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent,final MagicCardOnStack cardOnStack) {
            return (cardOnStack.isEnemy(permanent) && cardOnStack.hasType(MagicType.Creature)) ?
                new MagicEvent(
                    permanent,
                    cardOnStack.getController(),
                    this,
                    "SN deals 1 damage to PN."
                ):
                MagicEvent.NONE;
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            final MagicDamage damage = new MagicDamage(
                event.getSource(),
                event.getPlayer(),
                1
            );
            game.doAction(new MagicDealDamageAction(damage));
        }
    }
]
