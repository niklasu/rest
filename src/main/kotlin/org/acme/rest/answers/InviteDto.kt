package org.acme.rest.answers

import java.time.LocalDateTime

data class InviteDto(val date: LocalDateTime, val actions: List<Action>) {
}

class Action(appointmentId: Int, participantId: Int, val name: AnswerEnum) {
    val url = "/api/appointments/${appointmentId}/answers"
    val body = """{"participantId": "${participantId}", "answer" : "${name}"}"""
    val method = "POST"
}
