package org.acme.rest.answers

import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

@QuarkusTest
class AppointmentResourceTest {

    @Test
    fun `initial state is pending`() {
        val response = crateAppointment()
        assertThat(response.state, `is`(State.PENDING))
    }

    @Test
    fun `create an appointment - all participants confirm`() {
        val appointmentDto = crateAppointment()
        val appointmentId = appointmentDto.id

        answer(appointmentId, appointmentDto.participantIds[0], AnswerEnum.ACCEPTED)
        answer(appointmentId, appointmentDto.participantIds[1], AnswerEnum.ACCEPTED)
        answer(appointmentId, appointmentDto.participantIds[2], AnswerEnum.ACCEPTED)

        val response =
                given()
                        .contentType(ContentType.JSON)
                        .`when`().get("/appointments/${appointmentId}")
                        .then()
                        .statusCode(200)
                        .extract().`as`(AppointmentDto::class.java)
        assertThat(response.state, `is`(State.CONFIRMED))
    }

    @Test
    fun `one participant rejects`() {
        val appointmentDto = crateAppointment()

        val appointmentId = appointmentDto.id

        answer(appointmentId, appointmentDto.participantIds[0], AnswerEnum.ACCEPTED)
        answer(appointmentId, appointmentDto.participantIds[1], AnswerEnum.REJECTED)
        answer(appointmentId, appointmentDto.participantIds[2], AnswerEnum.ACCEPTED)

        val response =
                given()
                        .contentType(ContentType.JSON)
                        .`when`().get("/appointments/${appointmentId}")
                        .then()
                        .statusCode(200)
                        .extract().`as`(AppointmentDto::class.java)
        assertThat(response.state, `is`(State.CALLED_OFF))
    }


    private fun crateAppointment(): AppointmentDto {
        val response =
                given()
                        .contentType(ContentType.JSON)
                        .body(CreateAppointmentDto(LocalDateTime.of(2020, 10, 10, 10, 10), listOf(1, 2, 3)))
                        .`when`().post("/appointments")
                        .then()
                        .statusCode(200)
                        .extract().`as`(AppointmentDto::class.java)
        return response
    }

    private fun answer(appointmentId: Int, participantId: Int, answerEnum: AnswerEnum) {
        given()
                .contentType(ContentType.JSON)
                .body(AnswerDto(participantId, answerEnum))
                .`when`().post("/appointments/${appointmentId}/answers")
                .then()
                .statusCode(204)
    }

}