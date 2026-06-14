package command;

import transformations.Negative;

/** {@code negative} — queues a negative transformation. */
public class NegativeCommand extends ActiveSessionCommand {

    @Override
    protected void run(String[] args, AppContext ctx) {
        ctx.sessions().getCurrentSession().addTransformation(new Negative());
    }
}
