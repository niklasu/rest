package org.acme.rest.answers

import javax.enterprise.inject.Default
import javax.inject.Inject
import javax.ws.rs.*
import javax.ws.rs.core.MediaType

@Path("/api/users")
class UserResouce {

    @Inject
    @field: Default
    lateinit var service: AppointmentService

    @GET
    @Path("/{id}/invites")
    @Produces(MediaType.APPLICATION_JSON)
    fun getOne(@PathParam("id") id: Int): List<InviteDto> {
        return service.getInvitesOfUser(id)
    }

}