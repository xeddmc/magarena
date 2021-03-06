def TARGET_PERMANENT_OR_PLAYER = new MagicTargetChoice(
    new MagicTargetFilterImpl() {
        @Override
        public boolean acceptType(final MagicTargetType targetType) {
            return targetType == MagicTargetType.Player || targetType == MagicTargetType.Permanent;
        }
        @Override
        public boolean accept(final MagicSource source,final MagicPlayer player,final MagicTarget target) {
            return true;
        }
    },
    "target permanent or player"
);

[
    new ThisCombatDamagePlayerTrigger() {
        @Override
        public MagicEvent executeTrigger(final MagicGame game,final MagicPermanent permanent, final MagicDamage dealtDamage) {
            return new MagicEvent(
                permanent,
                TARGET_PERMANENT_OR_PLAYER,
                this,
                "For each kind of counter on target permanent or player\$, " +
                "give that permanent or player another counter of that kind."
            );
        }
        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTarget(game, {
                for (final MagicCounterType counterType : MagicCounterType.values()) {
                    if (it.hasCounters(counterType)) {
                        game.doAction(new ChangeCountersAction(event.getPlayer(), it, counterType, 1));
                    }
                }
            });
        }
    }
]

