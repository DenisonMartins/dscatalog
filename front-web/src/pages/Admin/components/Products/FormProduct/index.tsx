import React from "react";
import BaseForm from "../../BaseForm";
import "./styles.scss"

const FormProduct = () => {
    return (
        <BaseForm title="cadastrar produto">
            <div className="row">
                <div className="col-6">
                    <input type="text" className="form-control" />
                </div>
            </div>
        </BaseForm>
    )
}

export default FormProduct;
