package org.acme.rest.answers

import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class AppointmentRepository {
    val appointments = mutableListOf<Appointment>()
    fun add(appointment: Appointment) {
        appointments.add(appointment)
    }

    fun findById(id: Int): Appointment {
        return appointments.filter { a -> a.id == id }.first()
    }

    fun delete(id: Int) {
        appointments.removeIf { it.id == id }
    }
}