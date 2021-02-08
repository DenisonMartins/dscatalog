import React, {useState} from "react";
import BaseForm from "../../BaseForm";
import "./styles.scss"
import {makeRequest} from "core/utils/request";

type FormProductState = {
    name: string;
    price: string;
    category: string;
    description: string;
}

type FormProductEvent = React.ChangeEvent<HTMLInputElement | HTMLSelectElement | HTMLTextAreaElement>;

const FormProduct = () => {
    const [formData, setFormData] = useState<FormProductState>({
        name: '',
        price: '',
        category: '1',
        description: ''
    });

    const handleOnChange = (event: FormProductEvent) => {
        const name = event.target.name;
        const value = event.target.value;

        setFormData(data => ({...data, [name]: value}))
    }

    const handleSubmit = (event: React.FormEvent<HTMLFormElement>) => {
        event.preventDefault();
        const payload = {
            ...formData,
            imageUrl: '',
            categories: [{ id: formData.category }]
        }

        makeRequest({
            url: '/products',
            method: "POST",
            data: payload
        })
    }

    return (
        <form onSubmit={handleSubmit}>
            <BaseForm title="cadastrar produto">
                <div className="row">
                    <div className="col-6">
                        <input
                            value={formData.name}
                            name="name"
                            type="text"
                            className="form-control mb-5"
                            onChange={handleOnChange}
                            placeholder="Nome do produto"
                        />
                        <select
                            value={formData.category}
                            name="category"
                            className="form-control mb-5"
                            onChange={handleOnChange}
                        >
                            <option value="1">Livros</option>
                            <option value="2">Eletrônicos</option>
                            <option value="3">Computadores</option>
                        </select>
                        <input
                            value={formData.price}
                            name="price"
                            type="text"
                            className="form-control"
                            onChange={handleOnChange}
                            placeholder="Preço"
                        />
                    </div>
                    <div className="col-6">
                        <textarea
                            name="description"
                            onChange={handleOnChange}
                            cols={30}
                            rows={10}
                        />
                    </div>
                </div>
            </BaseForm>
        </form>
    )
}

export default FormProduct;
