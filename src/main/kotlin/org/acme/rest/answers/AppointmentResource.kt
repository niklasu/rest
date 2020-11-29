package org.acme.rest.answers

import javax.enterprise.inject.Default
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/api/appointments")
class AppointmentResource {

    @Inject
    @field: Default
    lateinit var service: AppointmentService

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    fun add(createAppointmentDto: CreateAppointmentDto): AppointmentDto {
        return service.add(createAppointmentDto)
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    fun getAll(): List<AppointmentDto> {
        return service.getAll()
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    fun getOne(@PathParam("id") id: Int): AppointmentDto {
        return service.getById(id)
    }

    @POST
    @Path("/{id}/answers")
    fun answer(@PathParam("id") id: Int, answer: AnswerDto) {
        service.answer(id, answer)
    }

    @DELETE
    @Path("/{id}")
    fun delete(@PathParam("id") id: Int) {
        service.delete(id)
    }
}