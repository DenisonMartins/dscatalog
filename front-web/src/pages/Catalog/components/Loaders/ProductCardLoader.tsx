import React from "react";
import ContentLoader from "react-content-loader";
import {generateList} from "core/utils/List";

const ProductCardLoader = () => {
    const loaderItems = generateList(10);

    return (
        <>
            {loaderItems.map(item =>
                <ContentLoader
                    key={item}
                    speed={1}
                    width={250}
                    height={355}
                    viewBox="0 0 250 355"
                    backgroundColor="#ecebeb"
                    foregroundColor="#d6d2d2"
                >
                    <rect x={0} y={0} rx={10} ry={10} width={250} height={355}/>
                </ContentLoader>
            )}
        </>
    )
}

export default ProductCardLoader;
