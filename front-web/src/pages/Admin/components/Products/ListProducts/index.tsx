import React from "react";
import { useHistory } from "react-router-dom";

const ListProducts = () => {

    const history = useHistory();

    const handleCreate = () => {
        history.push('/admin/products/create');
    }

    return (
      <div className="admin-products-container">
          <button className="btn btn-primary btn-lg" onClick={handleCreate}>
              ADICIONAR
          </button>
      </div>
    );
}

export default ListProducts;
