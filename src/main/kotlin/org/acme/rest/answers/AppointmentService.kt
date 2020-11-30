package org.acme.rest.answers

import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Default
import javax.inject.Inject

@ApplicationScoped
class AppointmentService {

    @Inject
    @field: Default
    lateinit var appointmentRepository: AppointmentRepository

    val users = mapOf(1 to "Bernd", 2 to "Fred", 3 to "Anna")

    fun add(createAppointmentDto: CreateAppointmentDto): AppointmentDto {
        println("Sending out appointment requests to participants: ${createAppointmentDto.participantIds}")
        val appointment = Appointment(date = createAppointmentDto.date, participantIds = createAppointmentDto.participantIds, state = State.PENDING)
        appointmentRepository.add(appointment)
        return AppointmentDto(appointment.id, appointment.date, appointment.participantIds, appointment.state)
    }

    fun getAll(): GetAllAppointments {
        val appointments = appointmentRepository.appointments.map { a -> AppointmentDto(a.id, a.date, a.participantIds, a.state) }
        return GetAllAppointments(appointments, CreateAppointmentAction())
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

    fun delete(id: Int) {
        appointmentRepository.delete(id)
    }

    fun getInvitesOfUser(participantId: Int): List<InviteDto> {
        val appointmentsWithUserInvolved = appointmentRepository.appointments
                .filter { it.participantIds.contains(participantId) }
        val map = appointmentsWithUserInvolved.associate { it to it.answers[participantId] }
        val actionMap = map.map { InviteDto(it.key.date, getActionBasedOnCurrentAnswer(it.key.id, it.value, participantId)) } //Appointment -> List of AnserEnums (actions)
        return actionMap
    }

    private fun getActionBasedOnCurrentAnswer(appointmentId: Int, value: AnswerEnum?, participantId: Int): List<Action> {
        val acceptAction = Action(appointmentId, participantId, AnswerEnum.ACCEPTED)
        val rejectAction = Action(appointmentId, participantId, AnswerEnum.REJECTED)
        when (value) {
            AnswerEnum.REJECTED -> {
                return listOf(acceptAction)
            }
            AnswerEnum.ACCEPTED -> {
                return listOf(rejectAction)
            }
        }
        return listOf(acceptAction, rejectAction)
    }

    fun getUserById(id: Int): UserDto {
        return UserDto(id, users[id])
    }

}
