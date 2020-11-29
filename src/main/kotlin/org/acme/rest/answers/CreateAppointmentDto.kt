package org.acme.rest.answers

import java.time.LocalDateTime

data class CreateAppointmentDto(val date: LocalDateTime, val participantIds: List<Int>) {
}
