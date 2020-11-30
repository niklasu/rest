package org.acme.rest.answers

data class UserDto(val id: Int, val name: String?) {
    val inviteLink = "/api/users/${id}/invites"
}
