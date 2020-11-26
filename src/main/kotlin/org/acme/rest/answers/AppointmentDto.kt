package org.acme.rest.answers

import java.time.LocalDateTime

data class AppointmentDto (val id: Int, val date: LocalDateTime, val participantIds: List<Int>, val state: State){
}
