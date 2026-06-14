package command;

import transformations.Grayscale;

/** {@code grayscale} — queues a grayscale transformation. */
public class GrayscaleCommand extends ActiveSessionCommand {

    @Override
    protected void run(String[] args, AppContext ctx) {
        ctx.sessions().getCurrentSession().addTransformation(new Grayscale());
    }
}
