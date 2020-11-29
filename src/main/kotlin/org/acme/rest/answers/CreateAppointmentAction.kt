package org.acme.rest.answers

class CreateAppointmentAction {
    val url = "/api/appointments"
    val action = "CREATE"
    val method = "POST"
    val body = """{"date": "2011-10-05T14:48:00.000Z", "participantIds" : [1,2,3]}"""
}
