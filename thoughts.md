# HATEOAS
In this project, I implemented the concept of HATEOAS in order to find out what all this *this is not really REST*
is about.

## Use Case
The use case I chose is a doodle-like tool which helps to find a date for an appointment between multiple participants.

- An appointment can be created
- Invitees can *accept* or *reject* an invite

### Client implementation without HATEOAS

1. Call /users/{id}/invites. The response would look like this:

```
[
{ 
   "appointmentId": 1,
   "description": "Go swimming"
   "currentAnswer": null
},
{ 
   "appointmentId": 2,
   "description": "Meet in the park"
   "currentAnswer": "ACCEPTED"
},
{ 
   "appointmentId": 3,
   "description": "Go to cinema"
   "currentAnswer": "REJECTED"
}
]
```

2. For each invite, check the "currentAnswer" field
    - null --> provide 2 action buttons *Reject* and *Accept*
    - accepted --> provide 1 action button: *Reject*
    - rejected --> provide 1 action button *Accept*
   
If the user wants to accept or reject an invite:
```
POST /users/{id}/answers
{
   appointmentId: <id>
   answer: <ACCEPTED|REJECTED>
}
```

Now, the idea about HATEOAS is that the core-data is extended with useful related information such as related data
or related actions.

### Client implementation with HATEOAS

1. Call /users/{id}/invites. The response would look like this:

```
[
{ 
   "appointmentId": 1,
   "description": "Go swimming",
   "actions": [
      {
         "name": "ACCEPT",
         "url": "/users/{id}/answers",
         "method": "POST",
         "body": "{"appointmentId": 1, "answer": "ACCEPTED"}"
      },
      {
         "name": "REJECT",
         "url": "/users/{id}/answers",
         "method": "POST",
         "body": "{"appointmentId": 1, "answer": "REJECTED"}"
      }
   ]
},
{ 
   "appointmentId": 2,
   "description": "Meet in the park"
   "actions": [
      {
         "name": "REJECT",
         "url": "/users/{id}/answers",
         "method": "POST",
         "body": "{"appointmentId": 2, "answer": "REJECTED"}"
      }
   ]
},
{ 
   "appointmentId": 3,
   "description": "Go to cinema"
   "actions": [
      {
         "name": "ACCEPT",
         "url": "/users/{id}/answers",
         "method": "POST",
         "body": "{"appointmentId": 3, "answer": "ACCEPTED"}"
      }
   ]
}
]
```

From a client development point of view, a few things are different now:
1. The client doesn't need to check what the *current* answer for an invitation is in order to find out what the possible 
  actions are. The field isn't even needed. Instead, the possible actions are already given. This means that business rules 
  don't have to be duplicated into the client. 
2. The client doesn't need to know how exactly an answer is submitted. Neither the URL nor the request body have to 
  be constructed. Both are already given.