import React, {Component, Fragment} from 'react';

export default class Profile extends Component {
    constructor(props) {
        super(props);
        this.state = {
            invites: []
        }
    }

    componentDidMount() {
        fetch(new Request(`/api/users/${this.props.match.params.id}/invites`))
            .then(d => d.json())
            .then(x => this.setState({invites: x}))
    }

    render() {
        return (
            <Fragment>
                <ul>
                    {this.state.invites.map(invite => {
                        return <Fragment>
                            <a>{invite.date}</a>
                            {invite.actions.map(action => {
                                return <button onClick={() => {
                                    fetch(new Request(action.url, {
                                        method: action.method,
                                        headers: {
                                            'Content-Type': 'application/json'
                                        },
                                        body: action.body
                                    }))
                                }}>{action.name}</button>
                            })}
                        </Fragment>;
                    })}
                </ul>
            </Fragment>
        );
    }
}