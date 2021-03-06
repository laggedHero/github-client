package net.laggedhero.githubclient.platform

import io.reactivex.Scheduler
import io.reactivex.schedulers.Schedulers

class FakeSchedulerProvider(
    private val scheduler: Scheduler = Schedulers.trampoline()
) : SchedulerProvider {
    override fun io(): Scheduler {
        return scheduler
    }

    override fun computation(): Scheduler {
        return scheduler
    }

    override fun ui(): Scheduler {
        return scheduler
    }
}