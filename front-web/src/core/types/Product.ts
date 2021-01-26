export type ProductsResponse = {
    content: ProductModel[];
    totalPages: number;
}

export type ProductModel = {
    id: number;
    name: string;
    description: string;
    price: number;
    imageUrl: string;
    date: string;
    categories: CategoryModel[]
}

export type CategoryModel = {
    id: number;
    name: string
}
