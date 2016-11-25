package client.yalantis.com.foldingtabbar

/**
 * Created by andrewkhristyan on 11/22/16.
 */
class OddMenuItemsException : Exception() {
    override val message: String? = """Your menu should have non-odd size ¯\_(ツ)_/¯"""
}