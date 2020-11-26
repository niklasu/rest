# REST beyond CRUD - Patterns
##Answers-Pattern
An action depends on answers from multiple participants. However, the answers come in asynchronously. 

### Example
I take Doodle as an example use-case. Creating an appointment is done via `POST /appointments`. 
When the appointment is created, invitations are sent out to all participants which lead them to a website that
provides accept or reject buttons. When all participants answer, a notification is sent out which tells if the appointment
is confirmed or called off.

### Implementation
```
POST /appointments/{id}/answers

{
    "participantId": 123
    "answer": "ACCEPTED|DECLINED"
}
```

### Attributes of this pattern
- The REST API for the answers does not tell anything about the follow-up process (in this case the notification)


#When a participant responds
## Approach: appointments/{id}/actions/
### POST appointments/{id}/actions/accept?participantId=123
### POST appointments/{id}/actions/deny?participantId=123

## Approach: appointments/{id}/answers
### POST appointments/{id}/answers
``
{participantId: 123, answer: DENIED|ACCEPTED}
``

## Approach: PUT /invitations/{id}
Invitation is a resource. Entries will be created by the server. Client answers update the invitation's state. 
``
{participantId: 123, answer: DENIED|ACCEPTED}
``

## Approach: POST /appointments/{id}/{answer}
### POST /appointments/{id}/rejected
### POST /appointments/{id}/accepted