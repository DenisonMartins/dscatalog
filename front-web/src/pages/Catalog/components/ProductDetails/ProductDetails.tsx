import React, {useEffect, useState} from "react";
import {Link, useParams} from "react-router-dom";
import {ReactComponent as ArrowIcon} from "core/assets/images/arrow.svg";
import "./styles.scss";
import ProductPrice from "core/components/ProductPrice/ProductPrice";
import {makeRequest} from "core/utils/request";
import {ProductModel} from "core/types/Product";
import ProductInfoLoader from "../Loaders/ProductInfoLoader";
import ProductDescriptionLoader from "../Loaders/ProductDescriptionLoader";

type ProductDetailsType = {
    idProduct: string
}

const ProductDetails = () => {

    const {idProduct} = useParams<ProductDetailsType>();
    const [product, setProduct] = useState<ProductModel>();
    const [isLoading, setIsLoading] = useState(false);

    useEffect(() => {
        setIsLoading(true);
        makeRequest({url: `/products/${idProduct}`})
            .then(response => setProduct(response.data))
            .finally(() => setIsLoading(false));
    }, [idProduct]);

    return (
        <div className="product-details-container">
            <div className="card-base border-radius-20 product-details">
                <Link to="/products" className="product-details-goback">
                    <ArrowIcon className="icon-goback"/>
                    <h1 className="text-goback">voltar</h1>
                </Link>
                <div className="row">
                    {isLoading ? <ProductInfoLoader /> : (
                        <>
                            <div className="col-6 pr-5">
                                <div className="product-details-card text-center ">
                                    <img src={product?.imageUrl} alt={product?.name} className="product-details-image"/>
                                </div>
                                <h1 className="product-details-name">
                                    {product?.name}
                                </h1>
                                {product?.price && <ProductPrice price={product?.price}/>}
                            </div>
                        </>
                    )}
                    <div className="col-6 product-details-card">
                        {isLoading ? <ProductDescriptionLoader /> : (
                            <>
                                <h1 className="product-description-title">
                                    Descrição do produto
                                </h1>
                                <p className="product-description-subtitle">
                                    {product?.description}
                                </p>
                            </>
                        )}
                    </div>
                </div>
            </div>
        </div>
    )
};

export default ProductDetails;
