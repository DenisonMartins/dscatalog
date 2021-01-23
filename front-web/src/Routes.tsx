import React from "react";
import {BrowserRouter, Route, Switch} from 'react-router-dom';
import Home from "./pages/home/Home";
import Catalog from "./pages/catalog/Catalog";
import Admin from "./pages/admin/Admin";
import Navbar from "./core/components/navbar/Navbar";
import ProductDetails from "./pages/catalog/components/productdetails/ProductDetails";

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
