package net.laggedhero.githubclient.platform

class FakeStringProvider(
    private val creator: (Int) -> String = { it.toString() }
) : StringProvider {
    override fun getString(id: Int): String {
        return creator.invoke(id)
    }
}