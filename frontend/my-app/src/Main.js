import React, {Component, Fragment} from 'react';

export default class Main extends Component {
    constructor(props) {
        super(props);
        this.state = {
            appointments: [],
            selected: null,
            createAction: null
        };
    }

    componentDidMount() {
        fetch(new Request('api/appointments'))
            .then(r => r.json().then(r => this.setState({
                appointments: r.appointments,
                createAction: r.createAppointmentAction
            })))
    }

    render() {
        console.log(this.state.createAction)
        return (
            <Fragment>
                {this.state.createAction ?
                    <button onClick={() => {
                        let myRequest = new Request(this.state.createAction.url, {
                            method: this.state.createAction.method,
                            headers: {
                                'Content-Type': 'application/json'
                            },
                            body: this.state.createAction.body
                        });
                        fetch(myRequest)
                            .then(r => r.json())
                            .then(x => this.setState({appointments: [...this.state.appointments, x]}))
                    }}>{this.state.createAction.action}
                    </button>
                    : null
                }

                <ul>
                    {this.state.appointments.map((a) => {
                        return <li>
                            <a onClick={() => {
                                fetch(new Request(a.link))
                                    .then(r => r.json())
                                    .then(data => this.setState({selected: data}))
                            }}>{a.id}</a>
                        </li>
                    })}
                </ul>
                {this.state.selected ?
                    <a>
                        <h3>Appointment Details</h3>
                        <p>State: {this.state.selected.state}</p>
                        <p>Participants: {}</p>
                        <ul>
                            {this.state.selected.participantIds.map(id => <li onClick={() => {
                                this.props.history.push({
                                    pathname: "/users/" + id
                                });
                            }}>{id}</li>)}
                        </ul>
                    </a> : null}
            </Fragment>
        );
    }
}