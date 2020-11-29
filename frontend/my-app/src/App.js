import React from 'react';
import ReactDOM from 'react-dom';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import AppointmentOverview from "./Main";
import Profile from "./Profile";

const Example = () => <p>Example Komponente</p>;

export default function App() {
    return (
        <Router>
            <Switch>
                <Route path="/appointments" component={AppointmentOverview} exact/>
                <Route path="/users/:id" component={Profile}/>
            </Switch>
        </Router>
    );
};

ReactDOM.render(<App/>, document.getElementById('root'));