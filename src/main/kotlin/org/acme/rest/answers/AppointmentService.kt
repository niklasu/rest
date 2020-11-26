package org.acme.rest.answers

import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Default
import javax.inject.Inject

@ApplicationScoped
class AppointmentService {

    @Inject
    @field: Default
    lateinit var appointmentRepository: AppointmentRepository

    fun add(createAppointmentDto: CreateAppointmentDto) : AppointmentDto {
        println("Sending out appointment requests to participants: ${createAppointmentDto.participantIds}")
        val appointment = Appointment(createAppointmentDto.date, createAppointmentDto.participantIds, State.PENDING)
        appointmentRepository.add(appointment)
        return AppointmentDto(appointment.id, appointment.date, appointment.participantIds, appointment.state)
    }

    fun getAll() : List<AppointmentDto>{
        return appointmentRepository.appointments.map { a -> AppointmentDto(a.id, a.date, a.participantIds, a.state) }
    }

    fun answer(appointmentId: Int, answer: AnswerDto) {
        val appointment = appointmentRepository.findById(appointmentId)
        appointment.answers.put(answer.participantId, answer.answer)
        if (gotAllResponses(appointment)) {
            updateAppointmentState(appointment)
        }
    }

    private fun updateAppointmentState(appointment: Appointment) {
        appointment.answers.values.forEach {
            if (it == AnswerEnum.REJECTED) {
                appointment.state = State.CALLED_OFF
                return
            }
            appointment.state = State.CONFIRMED
        }
    }

    private fun gotAllResponses(appointment: Appointment) =
            appointment.answers.size == appointment.participantIds.size

    fun getById(id: Int): AppointmentDto {
        val found = appointmentRepository.findById(id)
        return AppointmentDto(found.id, found.date, found.participantIds, found.state)
    }
}
