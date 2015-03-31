[
    new MagicSpellCardEvent() {
        @Override
        public MagicEvent getEvent(final MagicCardOnStack cardOnStack,final MagicPayedCost payedCost) {
            return new MagicEvent(
                cardOnStack,
                MagicTargetChoice.NEG_TARGET_PLAYER,
                new MagicDamageTargetPicker(4),
                this,
                "SN deals 4 damage to target player\$. " +
                "If you control three or more artifacts, creatures that player controls can't block this turn."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetPlayer(game, {
                game.doAction(new MagicDealDamageAction(event.getSource(),it,4));
                if (MagicCondition.METALCRAFT_CONDITION.accept(event.getSource())) {
                    final Collection<MagicPermanent> targets =
                            game.filterPermanents(it,MagicTargetFilterFactory.CREATURE_YOU_CONTROL);
                    for (final MagicPermanent target : targets) {
                        game.doAction(new MagicGainAbilityAction(
                            target,
                            MagicAbility.CannotBlock
                        ));
                    }
                }
            });
        }
    }
]
