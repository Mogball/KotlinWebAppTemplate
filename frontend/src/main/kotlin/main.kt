import org.w3c.dom.*
import org.w3c.dom.events.Event
import org.w3c.xhr.XMLHttpRequest
import kotlin.browser.document
import kotlin.browser.window
import kotlin.js.Json

/**
* Created by Jeff Niu
*/

fun main(args: Array<String>) {
	window.onload = {
		fetch("1")
		val head = document.getElementsByTagName("head")
		head[0]?.appendChild(createStyleSheetLink("style.css"))
		val input = document.getElementById("count_id") as HTMLInputElement
		val button = document.getElementById("button_id") as HTMLButtonElement
		button.addEventListener("click", fun(_: Event) {
			fetch(input.value)
		})
	}
}

fun fetch(count: String): Unit {
	val url = "http://localhost:8080/api/ping/$count"
	val req = XMLHttpRequest()
	req.onloadend = fun(_: Event) {
		val text = req.responseText
		println(text)
		val objArray = JSON.parse<Array<Json>>(text)
		val textarea = document.getElementById("textarea_id") as HTMLTextAreaElement
		textarea.value = ""
		objArray.forEach {
			val message = it["message"]
			textarea.value += "$message\n"
		}
	}
	req.open("GET", url, true)
	req.send()
}

fun createStyleSheetLink(filePath: String): Element {
	val style = document.createElement("link")
	style.setAttribute("rel", "stylesheet")
	style.setAttribute("href", filePath)
	return style
}