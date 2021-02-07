import React from "react";
import { Route, Switch} from "react-router-dom";
import ListProducts from "./ListProducts";
import FormProduct from "./FormProduct";

const Products = () => {
    return (
        <div>
            <Switch>
                <Route path="/admin/products" exact>
                    <ListProducts />
                </Route>
                <Route path="/admin/products/create">
                    <FormProduct />
                </Route>
                <Route path="/admin/products/:idProduct">
                    <h1>Produto</h1>
                </Route>
            </Switch>
        </div>
    );
}

export default Products;
