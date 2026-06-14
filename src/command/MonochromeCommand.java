package command;

import transformations.Monochrome;

/** {@code monochrome} — queues a monochrome transformation. */
public class MonochromeCommand extends ActiveSessionCommand {

    @Override
    protected void run(String[] args, AppContext ctx) {
        ctx.sessions().getCurrentSession().addTransformation(new Monochrome());
    }
}
