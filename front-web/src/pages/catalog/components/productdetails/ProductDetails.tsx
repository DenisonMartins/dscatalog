import React from "react";
import { useParams } from "react-router-dom";
import "./styles.scss";

type ProductDetailsType = {
    idProduct: string
}

const ProductDetails = () => {

    const { idProduct } = useParams<ProductDetailsType>();
    console.log(idProduct);

    return (
        <div className="product-details-container">
            <h1>Product Details</h1>
        </div>
    )
};

export default ProductDetails;
