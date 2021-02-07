import React from "react";
import {BrowserRouter, Redirect, Route, Switch} from 'react-router-dom';
import Home from "./pages/Home";
import Catalog from "./pages/Catalog";
import Admin from "./pages/Admin";
import NavBar from "./core/components/Navbar";
import ProductDetails from "./pages/Catalog/components/ProductDetails";

const Routes = () => (
    <BrowserRouter>
        <NavBar />
        <Switch>
            <Route path="/" exact>
                <Home />
            </Route>
            <Route path="/products" exact>
                <Catalog />
            </Route>
            <Route path="/products/:idProduct">
                <ProductDetails />
            </Route>
            <Redirect from="/admin" to="/admin/products" exact />
            <Route path="/admin">
                <Admin />
            </Route>
        </Switch>
    </BrowserRouter>
);

export default Routes;
