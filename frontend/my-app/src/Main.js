import React, {Component, Fragment} from 'react';

export default class Main extends Component {
    constructor(props) {
        super(props);
        this.state = {
            appointments: [],
            selected: null
        };
    }

    componentDidMount() {
        fetch(new Request('api/appointments'))
            .then(r => r.json().then(r => this.setState({appointments: r})))
    }

    render() {
        return (
            <Fragment>
                <button onClick={() => {
                    let myRequest = new Request('api/appointments', {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json'
                        },
                        body: JSON.stringify({
                            date: '2011-10-05T14:48:00.000Z',
                            participantIds: [1, 2, 3]
                        })
                    });
                    fetch(myRequest)
                        .then(r => r.json())
                        .then(x => this.setState({appointments: [...this.state.appointments, x]}))
                }}> Create Appointment
                </button>
                <ul>
                    {this.state.appointments.map((a) => {
                        return <li>
                            <a onClick={() => {
                                fetch(new Request(a.link))
                                    .then(r => r.json())
                                    .then(data => this.setState({selected: data}))
                            }}>{a.id}</a>
                            <a href={a.deletelink}> Löschen</a>
                        </li>
                    })}
                </ul>
                {this.state.selected ?
                    <a>
                        <h3>Appointment Details</h3>
                        <p>State: {this.state.selected.state}</p>
                        <p>Participants: {this.state.selected.participantIds}</p>
                    </a> : null}
            </Fragment>
        );
    }
}