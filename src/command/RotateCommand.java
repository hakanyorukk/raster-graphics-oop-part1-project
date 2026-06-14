package command;

import transformations.Direction;
import transformations.Rotate;

/** {@code rotate <left|right>} — queues a 90-degree rotation. */
public class RotateCommand extends ActiveSessionCommand {

    @Override
    protected void run(String[] args, AppContext ctx) {
        Args.requireLength(args, 2);
        Direction direction = Direction.parse(args[1]);
        ctx.sessions().getCurrentSession().addTransformation(new Rotate(direction));
    }
}
