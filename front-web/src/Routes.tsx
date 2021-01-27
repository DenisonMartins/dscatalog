import React from "react";
import {BrowserRouter, Route, Switch} from 'react-router-dom';
import Home from "./pages/Home/Home";
import Catalog from "./pages/Catalog/Catalog";
import Admin from "./pages/Admin/Admin";
import Navbar from "./core/components/Navbar/Navbar";
import ProductDetails from "./pages/Catalog/components/ProductDetails/ProductDetails";

const Routes = () => (
    <BrowserRouter>
        <Navbar />
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
            <Route path="/admin">
                <Admin />
            </Route>
        </Switch>
    </BrowserRouter>
);

export default Routes;
