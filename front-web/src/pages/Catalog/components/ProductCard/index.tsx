import React from "react";
import "./styles.scss";
import Index from "core/components/ProductPrice";
import {ProductModel} from "core/types/Product";

type ProductCardProps = {
    product: ProductModel
}

const ProductCard = ({ product }: ProductCardProps) => (
    <div className="card-base border-radius-10 product-card">
        <img src={product.imageUrl} alt={product.name} className="product-card-image"/>
        <div className="product-info">
            <h6 className="product-name">
                {product.name}
            </h6>
            <Index price={product.price} />
        </div>
    </div>
);

export default ProductCard;
