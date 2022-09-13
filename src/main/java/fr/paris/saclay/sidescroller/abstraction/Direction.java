package fr.paris.saclay.sidescroller.abstraction;

public enum Direction {
//    UP{
//        @Override
//        public void doAction(Player player) {
//            player.yPosition -= player.speed;
//            player.direction = Direction.UP;
//        }
//    },
    LEFT{
        @Override
        public void doAction(Player player) {
            player.xPosition -= player.speed;
            player.direction = Direction.LEFT;
        }
    },RIGHT{
        @Override
        public void doAction( Player player) {
            player.xPosition += player.speed;
            player.direction = Direction.RIGHT;
        }
    };
    public abstract void doAction(Player player);
}
