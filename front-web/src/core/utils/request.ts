import axios, {Method} from "axios"

type RequestParms = {
    method?: Method;
    url: string;
    data?: object;
    params?: object;
}

const BASE_URL = 'http://localhost:3000';

export const makeRequest = ({ method = 'GET', url, data, params } : RequestParms) => {
    return axios({
        method,
        url: `${BASE_URL}${url}`,
        data,
        params
    })
}
