import React from "react";
import {BrowserRouter, Route, Switch} from 'react-router-dom';
import Home from "./pages/home/Home";
import Catalog from "./pages/catalog/Catalog";
import Admin from "./pages/admin/Admin";
import Navbar from "./core/components/navbar/Navbar";

const Routes = () => (
    <BrowserRouter>
        <Navbar />
        <Switch>
            <Route path="/" exact>
                <Home />
            </Route>
            <Route path="/catalog">
                <Catalog />
            </Route>
            <Route path="/admin">
                <Admin />
            </Route>
        </Switch>
    </BrowserRouter>
);

export default Routes;
