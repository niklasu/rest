package org.acme.rest.answers

import java.time.LocalDateTime
import java.util.*

data class Appointment(val date: LocalDateTime, val participantIds: List<Int>, var state: State) {
    val id = Random().nextInt()
    val answers = mutableMapOf<Int, AnswerEnum>()
}
