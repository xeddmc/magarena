[
    new MagicPermanentActivation(
        [MagicConditionFactory.ManaCost("{2}")],
        new MagicActivationHints(MagicTiming.Pump,true),
        "Regen"
    ) {

        @Override
        public MagicEvent[] getCostEvent(final MagicPermanent source) {
            return [new MagicPayManaCostEvent(source,"{2}")];
        }

        @Override
        public MagicEvent getPermanentEvent(final MagicPermanent source,final MagicPayedCost payedCost) {
            return new MagicEvent(
                source,
                MagicTargetChoice.POS_TARGET_SAMURAI,
                MagicRegenerateTargetPicker.getInstance(),
                this,
                "Regenerate target Samurai\$."
            );
        }

        @Override
        public void executeEvent(final MagicGame game, final MagicEvent event) {
            event.processTargetPermanent(game,new MagicPermanentAction() {
                public void doAction(final MagicPermanent creature) {
                    game.doAction(new MagicRegenerateAction(creature));
                }
            });
        }
    }
]
