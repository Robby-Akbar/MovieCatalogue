package monster.myapp.moviecatalogue.core.utils

import java.util.concurrent.Executor

/**
 * Created by robby on 25/05/21.
 */
class TestExecutor : Executor {
    override fun execute(command: Runnable) {
        command.run()
    }
}