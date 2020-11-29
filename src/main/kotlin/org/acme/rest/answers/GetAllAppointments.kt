package org.acme.rest.answers

data class GetAllAppointments(val appointments: List<AppointmentDto>, val createAppointmentAction: CreateAppointmentAction) {

}
